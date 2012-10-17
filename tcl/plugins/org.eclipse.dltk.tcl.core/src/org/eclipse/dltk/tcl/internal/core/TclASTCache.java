package org.eclipse.dltk.tcl.internal.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.ast.parser.IASTCache;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemCollector;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.caching.IContentCache;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.tcl.ast.TclModule;
import org.eclipse.dltk.tcl.ast.TclModuleDeclaration;
import org.eclipse.dltk.tcl.internal.core.serialization.TclASTLoader;
import org.eclipse.dltk.tcl.internal.core.serialization.TclASTSaver;
import org.eclipse.dltk.tcl.internal.parser.NewTclSourceParser;

@SuppressWarnings("restriction")
public class TclASTCache implements IASTCache {
	public static final String TCL_AST_ATTRIBUTE = "_ast";
	public static final String TCL_PKG_INFO = "_pinf";
	public static final String TCL_STRUCTURE_INDEX = IContentCache.STRUCTURE_INDEX;
	public static final String TCL_MIXIN_INDEX = IContentCache.MIXIN_INDEX;

	private static class StoreEntry {
		ProblemCollector problems;
		IFileHandle handle;
		TclModule module;
	}

	private static class TclCacheSaver extends Job {

		final Queue<StoreEntry> queue = new LinkedList<StoreEntry>();

		public TclCacheSaver() {
			super("Tcl Cache Saver");
			setSystem(true);
			setPriority(SHORT);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			for (;;) {
				final StoreEntry entry;
				synchronized (queue) {
					entry = queue.poll();
				}
				if (entry == null) {
					break;
				}
				IContentCache cache = ModelManager.getModelManager()
						.getCoreCache();
				OutputStream stream = cache.getCacheEntryAttributeOutputStream(
						entry.handle, TCL_AST_ATTRIBUTE);

				storeTclEntryInCache(entry.problems, entry.module, stream);
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
			return Status.OK_STATUS;
		}

		@Override
		public boolean shouldRun() {
			synchronized (queue) {
				return !queue.isEmpty();
			}
		}

		void addToQueue(StoreEntry entry) {
			final int prevSize;
			synchronized (queue) {
				prevSize = queue.size();
				if (prevSize <= 256) {
					queue.add(entry);
				}
			}
			if ((prevSize & 0x0F) == 0) {
				schedule();
			}
		}
	}

	private final TclCacheSaver cacheSaverJob = new TclCacheSaver();

	public ASTCacheEntry restoreModule(ISourceModule module) {
		IFileHandle handle = EnvironmentPathUtils.getFile(module);
		if (handle == null) {
			return null;
		}
		IContentCache cache = ModelManager.getModelManager().getCoreCache();

		ProblemCollector collector = new ProblemCollector();
		TclModule tclModule = null;
		tclModule = restoreTclModuleFromCache(handle, cache, collector);
		if (tclModule != null) {
			ASTCacheEntry entry = new ASTCacheEntry();
			NewTclSourceParser parser = new NewTclSourceParser();
			entry.problems = collector;
			entry.module = parser.parse(null, tclModule, null);
			if (entry.problems.isEmpty()) {
				entry.problems = null;
			}
			return entry;
		}
		return null;
	}

	public static TclModule restoreTclModuleFromCache(IFileHandle handle,
			IContentCache cache, IProblemReporter collector) {
		InputStream stream = cache.getCacheEntryAttribute(handle,
				TCL_AST_ATTRIBUTE);
		if (stream != null) {
			try {
				TclASTLoader loader = new TclASTLoader(stream);
				TclModule tclModule = loader.getModule(collector);
				if (tclModule != null) {
					return tclModule;
				}
			} catch (Exception e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public void storeModule(ISourceModule module,
			IModuleDeclaration moduleDeclaration, ProblemCollector problems) {
		IFileHandle handle = EnvironmentPathUtils.getFile(module, false);
		if (handle == null) {
			return;
		}
		if (moduleDeclaration instanceof TclModuleDeclaration) {
			TclModuleDeclaration decl = (TclModuleDeclaration) moduleDeclaration;
			TclModule tclModule = decl.getTclModule();
			if (tclModule != null) {
				StoreEntry entry = new StoreEntry();
				entry.handle = handle;
				entry.module = tclModule;
				if (problems != null) {
					entry.problems = new ProblemCollector();
					problems.copyTo(entry.problems);
				}
				cacheSaverJob.addToQueue(entry);
			}
		}
	}

	public static void storeTclEntryInCache(ProblemCollector problems,
			TclModule tclModule, OutputStream stream) {
		TclASTSaver saver;
		try {
			saver = new TclASTSaver();
			saver.save(tclModule, problems, stream);
		} catch (IOException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
	}
}
