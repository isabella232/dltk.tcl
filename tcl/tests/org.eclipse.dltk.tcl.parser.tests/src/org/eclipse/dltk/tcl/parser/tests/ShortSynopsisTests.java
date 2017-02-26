package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.List;

import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.Scope;
import org.eclipse.dltk.tcl.parser.ITclParserOptions;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionLoader;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.parser.definitions.SynopsisBuilder;
import org.junit.Test;

public class ShortSynopsisTests {
	NamespaceScopeProcessor processor = new NamespaceScopeProcessor();

	@Test
	public void test001() throws Exception {
		String source = "after";
		String synopsis = "after [ms ...|cancel ...|idle ...|info ...]";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test002() throws Exception {
		String source = "after cancel puts lala";
		String synopsis = "after cancel script ?script ...?";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test003() throws Exception {
		String source = "after c";
		String synopsis = "after cancel [id|script ...]";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test004() throws Exception {
		String source = "after b";
		String synopsis = "after [ms ...|cancel ...|idle ...|info ...]";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test005() throws Exception {
		String source = "append";
		String synopsis = "append varName ?value ...?";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test006() throws Exception {
		String source = "array names arrayName -exact";
		String synopsis = "array names arrayName ?[-exact|-glob|-regexp]? ?pattern?";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test007() throws Exception {
		String source = "chan read channelId";
		String synopsis = "chan read channelId ?numChars?";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test008() throws Exception {
		String source = "apply {{a {b 4}} {puts $a;puts $b}} 3 7";
		String synopsis = "apply {args body ?namespace?} ?arg ...?";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test009() throws Exception {
		String source = "chan configure channelId -blocking 1";
		String synopsis = "chan configure channelId ?option? ?value? ?option value ...?";
		typedCheck(source, synopsis, "8.5");
	}

	@Test
	public void test010() throws Exception {
		String source = "switch";
		String synopsis = "switch ?options ...? ?--? string [pattern ...|{pattern ...]";
		typedCheck(source, synopsis, "8.5");
	}

	private void typedCheck(String source, String expected, String version)
			throws Exception {
		Scope scope = DefinitionLoader.loadDefinitions(new URL(
				"platform:///plugin/org.eclipse.dltk.tcl.tcllib/definitions/builtin.xml"));
		assertNotNull(scope);
		processor.addScope(scope);
		TclParser parser = TestUtils.createParser(version);
		TclErrorCollector errors = new TclErrorCollector();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, true);
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertEquals(1, module.size());
		TclCommand command = module.get(0);
		SynopsisBuilder synopsis = new SynopsisBuilder();
		String actual = synopsis.getShortHint(command);
		assertNotNull(actual);
		System.out.println(
				"===================" + version + "===================");
		System.out.println(actual);
		System.out.println("-----------------------------------------");
		System.out.println(expected);
		assertNotNull(expected);
		assertFalse(expected.equals(""));
		assertEquals(expected, actual);
	}
}
