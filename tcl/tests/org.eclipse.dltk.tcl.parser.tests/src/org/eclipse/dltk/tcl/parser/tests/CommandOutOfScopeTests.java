package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.dltk.tcl.parser.ITclParserOptions;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.junit.Test;

public class CommandOutOfScopeTests {
	NamespaceScopeProcessor processor = new NamespaceScopeProcessor();

	@Test
	public void test001() {
		String source = "break";
		typedCheck(source, 1, "8.4");
	}

	@Test
	public void test002() {
		String source = "for {set i 0} {i<3} {incr i} {set i 4; break}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test003() {
		String source = "while {true} {if (true) break}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test004() {
		String source = "foreach x $values {if (true) break}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test005() {
		String source = "continue";
		typedCheck(source, 1, "8.4");
	}

	@Test
	public void test006() {
		String source = "for {set i 0} {i<3} {incr i} {set i 4; continue}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test007() {
		String source = "while {true} {if (true) continue}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test008() {
		String source = "foreach x $values {if (true) continue}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test009() {
		String source = "if (i==0) {set i 4; break;continue}";
		typedCheck(source, 2, "8.4");
	}

	@Test
	public void test010() {
		String source = "for {set i 0} {i<3} {incr i} {if (i==0) {set i 4; break}}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test011() {
		String source = "if (0==0) {for {set i 0} {i<3} {incr i} {set i 4; break}}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test012() {
		String source = "if (i>0) {if (i==3) {set i 4; break}}";
		typedCheck(source, 1, "8.4");
	}

	@Test
	public void test013() {
		String source = "set i 0; break i";
		typedCheck(source, 2, "8.4");
	}

	@Test
	public void test014() {
		String source = "set i 0; continue i";
		typedCheck(source, 2, "8.4");
	}

	@Test
	public void test015() {
		String source = "namespace eval mns {while {true} {if (true) break}}";
		typedCheck(source, 0, "8.4");
	}

	@Test
	public void test016() {
		String source = "namespace eval mns {while {true} {if (true) break};continue};break";
		typedCheck(source, 2, "8.4");
	}

	@Test
	public void test017() {
		String source = "while {true} {foreach x $values {"
				+ "for {set i 0} {i<3} {incr i} {set i 4; continue}; break}; continue}";
		typedCheck(source, 0, "8.4");
	}

	private void typedCheck(String source, int errs, String version) {
		processor = DefinitionManager.getInstance().createProcessor();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2];
		System.out.println("%%%%%%%%%%%%%%%%Test:" + element.getMethodName());
		TclParser parser = TestUtils.createParser(version);
		TclErrorCollector errors = new TclErrorCollector();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, true);
		parser.parse(source, errors, processor);
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(errs, errors.getCount());
	}
}
