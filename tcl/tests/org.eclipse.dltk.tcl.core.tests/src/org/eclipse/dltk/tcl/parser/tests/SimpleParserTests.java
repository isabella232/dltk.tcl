/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.dltk.core.tests.xml.DOMSerializer;
import org.eclipse.dltk.launching.LaunchingMessages;
import org.eclipse.dltk.tcl.core.tests.model.Activator;
import org.eclipse.dltk.tcl.internal.parser.raw.BracesSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.CommandSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.MagicBackslashSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.NormalBackslashSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.QuotesSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.SimpleTclParser;
import org.eclipse.dltk.tcl.internal.parser.raw.TclCommand;
import org.eclipse.dltk.tcl.internal.parser.raw.TclScript;
import org.eclipse.dltk.tcl.internal.parser.raw.TclWord;
import org.eclipse.dltk.tcl.internal.parser.raw.VariableSubstitution;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SimpleParserTests {

	private String getContents(URL url) throws IOException {
		InputStream input = null;
		StringBuffer result = new StringBuffer(512);
		try {
			input = new BufferedInputStream(url.openStream());

			// Simple copy
			int ch = -1;
			while ((ch = input.read()) != -1) {
				if (ch != '\r') {
					result.append((char) ch);
				}
			}
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return result.toString();
	}

	private void print(TclWord s) {
		System.out.println(
				" - - word from " + s.getStart() + " to " + s.getEnd());
		List<?> cmd = s.getContents();
		for (Object o : cmd) {
			System.out.println(" - - - " + o.toString());
			if (o instanceof CommandSubstitution) {
				System.out.println("# internal script #");
				print(((CommandSubstitution) o).getScript());
				System.out.println("# end of internal script #");
			}
		}
	}

	private void print(TclCommand s) {
		System.out.println(
				" - command from " + s.getStart() + " to " + s.getEnd());
		List<TclWord> cmd = s.getWords();
		for (TclWord word : cmd) {
			print(word);
		}
	}

	private void print(TclScript s) {
		System.out.println("script from " + s.getStart() + " to " + s.getEnd());
		List<TclCommand> cmd = s.getCommands();
		for (TclCommand c : cmd) {
			if (c == null)
				System.out.println(
						"comment(AND ERROR TOO, no null ptr should be in result) here!");
			else
				print(c);
		}
	}

	/**
	 * Returns a Document that can be used to build a DOM tree
	 *
	 * @return the Document
	 * @throws ParserConfigurationException
	 *             if an exception occurs creating the document builder
	 */
	public static Document getDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		return doc;
	}

	/**
	 * Serializes a XML document into a string - encoded in UTF8 format, with
	 * platform line separators.
	 *
	 * @param doc
	 *            document to serialize
	 * @return the document as a string
	 */
	public static String serializeDocument(Document doc)
			throws IOException, TransformerException {
		return new DOMSerializer().serialize(doc).trim();
	}

	private String getCodeRange(String code, int start, int end) {
		return code.substring(start, end + 1);
	}

	private Document parseXML(String source) {
		Document doc = null;
		try {
			// Wrapper the stream for efficient parsing
			InputStream stream = new ByteArrayInputStream(source.getBytes());

			// Do the parsing and obtain the top-level node
			try {
				DocumentBuilder parser = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				parser.setErrorHandler(new DefaultHandler());
				doc = parser.parse(new InputSource(stream));
			} catch (SAXException e) {
				throw new IOException(
						LaunchingMessages.ScriptRuntime_badFormat);
			} catch (ParserConfigurationException e) {
				stream.close();
				throw new IOException(
						LaunchingMessages.ScriptRuntime_badFormat);
			} finally {
				stream.close();
			}
		} catch (IOException e) {
		}
		return doc;
	}

	private Document createXMLFromSource(String source, TclScript s) {
		Document doc;
		try {
			doc = getDocument();
			getXML(source, s, doc);
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Node getWordAsNode(String source, TclWord word, Document doc) {
		Element w = doc.createElement("word");
		w.setAttribute("start", Integer.toString(word.getStart()));
		w.setAttribute("end", Integer.toString(word.getEnd()));
		String wordRawText = getCodeRange(source, word.getStart(),
				word.getEnd());
		w.setAttribute("text", wordRawText);

		List<?> parts = word.getContents();
		for (Object o : parts) {
			Element p = null;
			if (o instanceof String) {
				p = doc.createElement("string");
				p.setAttribute("text", o.toString());
			} else if (o instanceof CommandSubstitution) {
				CommandSubstitution cs = (CommandSubstitution) o;
				p = doc.createElement("command");
				String code = getCodeRange(source, cs.getStart(), cs.getEnd());
				if (code == null) {
					throw new NullPointerException("failed to get source from "
							+ cs.getStart() + " to " + cs.getEnd());
				}
				p.setAttribute("text", code);
			} else if (o instanceof QuotesSubstitution) {
				p = doc.createElement("quotes");
				// String rawText = ((QuotesSubstitution)o).getRawText();
				String rawText = getCodeRange(source,
						((QuotesSubstitution) o).getStart(),
						((QuotesSubstitution) o).getEnd());
				if (rawText == null)
					throw new NullPointerException(
							o.toString() + " has null raw text");
				p.setAttribute("text", rawText);
			} else if (o instanceof BracesSubstitution) {
				p = doc.createElement("braces");
				// String rawText = ((BracesSubstitution)o).getRawText();
				String rawText = getCodeRange(source,
						((BracesSubstitution) o).getStart(),
						((BracesSubstitution) o).getEnd());
				if (rawText == null)
					throw new NullPointerException(
							o.toString() + " has null raw text");
				p.setAttribute("text", rawText);
			} else if (o instanceof VariableSubstitution) {
				p = doc.createElement("variable");
				VariableSubstitution vs = (VariableSubstitution) o;
				p.setAttribute("start", Integer.toString(vs.getStart()));
				p.setAttribute("end", Integer.toString(vs.getEnd()));
				p.setAttribute("kind", Integer.toString(vs.getKind()));
				if (vs.getName() == null)
					throw new NullPointerException(
							o.toString() + " has null name");
				p.setAttribute("name", vs.getName());
				if (vs.getIndex() != null)
					p.appendChild(getWordAsNode(source, vs.getIndex(), doc));
			} else if (o instanceof NormalBackslashSubstitution) {
				p = doc.createElement("normalbs");
				// String rawText =
				// ((NormalBackslashSubstitution)o).getRawText();
				String rawText = getCodeRange(source,
						((NormalBackslashSubstitution) o).getStart(),
						((NormalBackslashSubstitution) o).getEnd());
				if (rawText == null)
					throw new NullPointerException(
							o.toString() + " has null raw text");
				p.setAttribute("text", rawText);
			} else if (o instanceof MagicBackslashSubstitution) {
				p = doc.createElement("newlinebs");
			}
			w.appendChild(p);
		}
		return w;
	}

	private Node getXML(String source, TclScript s, Document doc) {
		Element script = doc.createElement("script");
		doc.appendChild(script);
		List<TclCommand> cmd = s.getCommands();
		for (TclCommand c : cmd) {
			Element command = doc.createElement("command");
			command.setAttribute("start", Integer.toString(c.getStart()));
			command.setAttribute("end", Integer.toString(c.getEnd()));
			script.appendChild(command);
			List<TclWord> words = c.getWords();
			for (TclWord word : words) {
				Node w = getWordAsNode(source, word, doc);
				command.appendChild(w);
			}
		}
		return script;
	}

	private void megaprint(String source, TclScript result) {
		String xml;
		try {
			xml = serializeDocument(createXMLFromSource(source, result));
			System.out.println(xml);
		} catch (IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runTestOn(String name) throws Exception {
		System.out.println("Running " + name);
		URL in = Activator.getDefault().getBundle().getEntry(name);
		URL ans = Activator.getDefault().getBundle().getEntry(name + ".xml");
		if (in == null || ans == null)
			throw new Exception();
		try {
			String content = getContents(in);
			TclScript s = SimpleTclParser.staticParse(content);

			Document docFromSource = createXMLFromSource(content, s);
			String resultXML = serializeDocument(docFromSource);
			String expectedXML = serializeDocument(parseXML(getContents(ans)));

			assertEquals(expectedXML, resultXML);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void _testAll() throws Exception {
		Enumeration<String> e = Activator.getDefault().getBundle()
				.getEntryPaths("rawtests/");
		while (e.hasMoreElements()) {
			String o = e.nextElement();
			System.out.println("found test " + o);
			// runTestOn ((new Path(o.toString())).lastSegment());
			runTestOn(o);
		}
	}

	@Test
	public void test0() throws Exception {
		runTestOn("rawtests/0.tcl");
	}

	@Test
	public void test1() throws Exception {
		runTestOn("rawtests/1.tcl");
	}

	@Test
	public void test2() throws Exception {
		runTestOn("rawtests/2.tcl");
	}

	@Test
	public void test3() throws Exception {
		runTestOn("rawtests/3.tcl");
	}

	@Test
	public void test4() throws Exception {
		runTestOn("rawtests/4.tcl");
	}

	@Test
	public void test5() throws Exception {
		runTestOn("rawtests/5.tcl");
	}

	@Test
	public void test6() throws Exception {
		runTestOn("rawtests/6.tcl");
	}

	@Test
	public void test7() throws Exception {
		runTestOn("rawtests/simple0.tcl");
	}

	@Test
	public void test8() throws Exception {
		runTestOn("rawtests/simple1.tcl");
	}

	@Test
	public void test9() throws Exception {
		runTestOn("rawtests/simple3.tcl");
	}

	@Test
	public void test10() throws Exception {
		runTestOn("rawtests/all.tcl");
	}

	@Test
	public void test11() throws Exception {
		runTestOn("rawtests/a.tcl");
	}

	@Test
	public void test12() throws Exception {
		String content = "\r\n	    puts $a ; puts \"wow!\"\r";
		SimpleTclParser.staticParse(content);
	}

	@Test
	public void test13() throws Exception {
		runTestOn("rawtests/7.tcl");
	}

	@Test
	public void test14() throws Exception {
		runTestOn("rawtests/8.tcl");
	}

	@Test
	public void test15() throws Exception {
		runTestOn("rawtests/9.tcl");
	}

	@Test
	public void test16() throws Exception {
		runTestOn("rawtests/b.tcl");
	}

	@Test
	public void test17() throws Exception {
		runTestOn("rawtests/c.tcl");
	}

	@Test
	public void test18() throws Exception {
		runTestOn("rawtests/d.tcl");
	}

	@Test
	public void test19() throws Exception {
		runTestOn("rawtests/e.tcl");
	}

	@Test
	public void test20() throws Exception {
		runTestOn("rawtests/f.tcl");
	}

	@Test
	public void test21() throws Exception {
		runTestOn("rawtests/g.tcl");
	}

	public void _testUtil() throws Exception { // used to visually in-place
		// debug
		// String content = "{ \r\n variable asdf\r\n variable {[<>#%\"]} \r\n
		// \r\n \r\n proc foo {} {\r\n \n }\n \n}";
		String content = "lreplace $list $index $index $newValue";
		// URL in =
		// TestsPlugin.getDefault().getBundle().getEntry("rawtests/all.tcl");
		// String content = getContents(in);
		TclScript s = SimpleTclParser.staticParse(content);
		print(s);
		megaprint(content, s);
	}

}
