/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/

package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclArgumentList;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.ast.impl.TclArgumentListImpl;
import org.eclipse.dltk.tcl.parser.ITclParserOptions;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import junit.framework.TestCase;

public class DefinitionTests {
	NamespaceScopeProcessor processor;

	@Test
	public void test001() {
		String source = "if { true } {" + "set a 4 5" + "} else {" + "set"
				+ "}";
		typedCheck(source, 2, 2, "8.4");
	}

	@Test
	public void test002() {
		String source = "puts [chan configured stdin -blocking]";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test003() {
		String source = "eval return {\"lala\"}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test004() {
		String source = "return";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test005() {
		String source = "lrange $argv 2 $args_length";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test006() {
		String source = "after 10000";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test007() {
		// parser error - ats_rmserver
		String source = "catch [file delete -force [ file join \"/temp\" $rm1.$rm2.rm ] ] err";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test008() {
		String source = "lrange $args 0 end-1";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test009() {
		String source = "set retCode [catch $exec_cmd retVal]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test010() {
		String source = "lindex $cmd_argv $idx";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test011() {
		String source = "lsort $_cmd($opts)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test012() {
		String source = "incr i -$length";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test013() {
		String source = "string first \"\\{\" $retval";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test014() {
		String source = "string last \"\\}\" $retval";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test015() {
		String source = "string range $retval $start_str $end_str";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test016() {
		String source = "if 0==[catch {exec mkfifo $f}] return";
		typedCheck(source, 0, 1, "8.4");
	}

	@Test
	public void test017() {
		String source = "proc h {} help";
		typedCheck(source, 1, 1, "8.4");
	}

	@Test
	public void test018() {
		String source = "close";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test019() {
		String source = "lsort $images";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test020() {
		String source = "catch domainname domainname";
		typedCheck(source, 1, 1, "8.4");
	}

	@Test
	public void test021() {
		String source = "foreach {name options} $rd break";
		typedCheck(source, 0, 1, "8.4");
	}

	@Test
	public void test022() {
		String source = "array set g {}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test023() {
		String source = "array set o $options";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test024() {
		String source = "catch \"rmdir $file\"";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test025() {
		String source = "catch \"open $lockfile {RDWR}\" lock_handle";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test026() {
		String source = "proc $name {subcmd args} \"apply $name \\$subcmd \\$args\"";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test027() {
		String source = "array name options";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test028() {
		String source = "lsearch $options dev*";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test029() {
		String source = "info frame 1";
		typedCheck(source, 1, 0, "8.4");
		typedCheck(source, 0, 0, "8.5");
	}

	@Test
	public void test030() {
		String source = "after 300 SendData";
		typedCheck(source, 1, 1, "8.4");
	}

	@Test
	public void test031() {
		String source = "linsert $args 0 vfs::mkcl::Mount $mkfile $local";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test032() {
		String source = "after $flush [list ::mk4vfs::periodicCommit $db]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test033() {
		String source = "lindex $args 0";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test034() {
		String source = "linsert $dst 0 interp alias $path [lindex $m 0] {}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test035() {
		String source = "set value [[$_rep(root) select $xpath] text]";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test036() {
		String source = "after 2000 [list Feedback $win {}]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test037() {
		String source = "regexp -- {^(.+),.*$} $entry dummy_var";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test038() {
		String source = "array set map {\n" + "0 2\n" + "1 3\n" + "}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test039() {
		String source = "fconfigure stdin -block lala";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test040() {
		String source = "package present -exact trofs 0.4.4";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test041() {
		String source = "file attributes $f -group";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test042() {
		String source = "fconfigure $fhandle -translation binary -encoding binary";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test043() {
		String source = "fconfigure $data(sock2a) -sockname";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test044() {
		String source = "subst -nocommands -novariables $val";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test045() {
		String source = "array set typemap {Q {4 i} Y {2 s}}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test048() {
		String source = "string trim $var";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test049() {
		String source = "string equa $var1 $var2";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test050() {
		String source = "set i [interp create [lindex $args 0]]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test050_2() {
		String source = "interp create [lindex $args 0]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test050_3() {
		String source = "lindex $args 0";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test051() {
		String source = "regexp -- {^(.+),.*$} $entry dummy_var console_name";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test053() {
		String source = "fconfigure $data(sock2) -translation $data(mode)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test054() {
		String source = "return -code error -errorcode DATA lala";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test055() {
		String source = "regexp {bench/(.*)$} $head -> format";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test056() {
		String source = "fconfigure $sock -buffering line -blocking 1 -translation {auto crlf}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test058() {
		String source = "list ::qweaa::accept $sock";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test059() {
		String source = "return -errorcode DATA -code error lala";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test060() {
		String source = "set file_list [glob -nocomplain -type f                  \\"
				+ "                  [file join $tcl_path bin" + " tclsh]   \\"
				+ "                  [file join $tcl_path bin wish]    \\"
				+ "                  [file join $tcl_path bin expect]  \\"
				+ "                  [file join $tcl_path bin expectk] \\"
				+ "                  [file join $tcl_path bin tkcon]   \\"
				+ "                  [file join $tcl_path bin teacup]  \\"
				+ "                  [file join $tcl_path bin dtplite] \\"
				+ "                  [file join $tcl_path bin page]    \\"
				+ "                  [file join $tcl_path bin tcldocstrip] \\"
				+ "                  [file join / moto qwe bin nmicmpd]   \\"
				+ "                  [file join / moto qwe bin nmtrapd]]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test061() {
		String source = "string first < $address";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test062() {
		String source = "string last > $address";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test063() {
		String source = "lindex $mem 0 0";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test064() {
		String source = "fconfigure $ifh -eofchar {} -encoding binary -translation lf";
		// deprecated
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test065() {
		String source = "linsert $optlist \"end-1\" \"or\"";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test066() {
		String source = "string first $url $str";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test067() {
		String source = "open $file $mode";
		// $mode is var not const
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test068() {
		String source = "info procs ::dom::xpathFunc::*";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test069() {
		String source = "string first = $x";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test070() {
		String source = "trace add variable receiver {write unset} Track";
		// Unknown command:Track
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test071() {
		String source = "trace vdelete var wu $data(listVarTraceCmd)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test072() {
		String source = "trace variable var wu $data(listVarTraceCmd)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test073_FAILED() {
		String source = "file array set destructive {"
				+ "atime 0       attributes 0  copy 1       delete 1      dirname 0"
				+ "executable 0  exists 0      extension 0  isdirectory 0 isfile 0"
				+ "join 0        lstat 0       mkdir 1      mtime 0       nativename 0"
				+ "owned 0       pathtype 0    readable 0   readlink 0    rename 1"
				+ "rootname 0    size 0        split 0      stat 0        tail 0"
				+ "type 0        volumes 0     writable 0}";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test074() {
		String source = "lsearch -all -inline -glob -index 0 $state(names) $partial*";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test075() {
		String source = "regsub \"${schema}:\" $type {}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test076() {
		String source = "open $path \"r\"";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test077() {
		String source = "file attributes $appfile -creator TKd4";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test078() {
		String source = "file attributes $appfile -permissions +x}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test079() {
		String source = "regexp \"compress\" $file_out";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test081() {
		String source = "open $::globals(high) {WRONLY CREAT TRUNC}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test082() {
		String source = "fconfigure $n(fh) -mode $speed,n,8,1 -handshake xonxoff -buffering line -translation crlf";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test083() {
		String source = "open $logfile \"a+\"";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test084() {
		String source = "safe::setLogCmd [namespace current]::itrace";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test085() {
		String source = "safe::interpCreate $interp";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test086() {
		String source = "safe::interpAddToAccessPath $slave $path";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test087() {
		String source = "safe::interpDelete $impl(interp)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test088() {
		String source = "package verbose 1";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test089() {
		String source = "fconfigure $sock -blocking no -translation binary -buffering full";
		typedCheck(source, 0, 0, "8.4");
	}

	//
	// public void test090() {
	// String source = "trace \"Message $messageId finalized\"";
	// typedCheck(source, 0, 0, "8.4");
	// }
	@Test
	public void test091() {
		String source = "info namespace tail $name";
		typedCheck(source, 1, 0, "8.4");
		typedCheck(source, 1, 0, "8.5");
	}

	@Test
	public void test092() {
		String source = "file attributes $temp/$constructTag -group $fileStats(gid)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test093() {
		String source = "file attributes $existingFile -readonly 0";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test094() {
		String source = "lsort -index $sortcol -$order -$sortmode $records";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test095() {
		String source = "source -rsrc itk:tclIndex";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test096() {
		String source = "catch {string is . .} charclasses";
		typedCheck(source, 1, 1, "8.4");
	}

	@Test
	public void test096_2() {
		String source = "string is . .";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test097() {
		String source = "registry set $key $value $data $mod";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test098() {
		String source = "regexp {^(Date.+|Time.+)$} $class";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test099() {
		String source = "open $outF { WRONLY CREAT APPEND }";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test100_FAILED() {
		String source = "file {set fname $urlarray(path)}";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test101() {
		String source = "unset a";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test102() {
		String source = "unset a($b)";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test103() {
		String source = "file delete $newSuiteFile";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test104() {
		String source = "glob [file join $dir library *.tcl]";
		typedCheck(source, 0, 0, "8.4");
	}

	// xbz
	@Test
	public void test105() {
		String source = "info namespace all itcl";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test106() {
		String source = "exec [file join $autotest install checkenv.tcl]";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test107() {
		String source = "puts stdout \"+$value -- \" nonewline";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test108() {
		String source = "regexp $compareTo $value";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test109() {
		String source = "switch -exact -- $keyword {proc - method {set retval {}}}";
		typedCheck(source, 0, 1, "8.4");
	}

	@Test
	public void test110() {
		String source = "namespace {set retval {}}";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test111() {
		String source = "registry set $key $value $data $mod";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test112() {
		String source = "switch $beta {Darwin - SunOS {puts alpha} SunOS {puts alpha}}";
		typedCheck(source, 0, 2, "8.4");
	}

	@Test
	public void test113() {
		String source = "switch $beta {Darwin - SunOS -}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test114() {
		String source = "switch $beta {{Darwin} -}";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test115() {
		String source = "switch $beta {Darwin {puts alpha} SunOS {puts alpha}}";
		typedCheck(source, 0, 2, "8.4");
	}

	@Test
	public void test116() {
		String source = "switch $os {Darwin - FreeBSD - Linux - OSF1 - SunOS {return 0} HP-UX {return 0}}";
		typedCheck(source, 0, 2, "8.4");
	}

	@Test
	public void test117() {
		String source = "switch -- $option {\"-a\" {} -starttime - -stoptime {} default {}}";
		typedCheck(source, 0, 3, "8.4");
	}

	@Test
	public void test118() {
		String source = "switch $a {Darwin - SunOS {puts 2} Windows -}";
		typedCheck(source, 0, 1, "8.4");
	}

	@Test
	public void test119() {
		String source = "switch -- $beta {Darwin - SunOS {puts alpha}}";
		typedCheck(source, 0, 1, "8.4");
	}

	@Test
	public void test120() {
		String source = "package require $args";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test121() {
		String source = "package present $pkg";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test122() {
		String source = "socket $host daytime";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test123() {
		String source = "return -code error \"window manager name not supported\"";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test124() {
		String source = "dde execute $app WWW_OpenURL $url";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test125() {
		String source = "http::formatQuery [string range $msg 6 end]";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test126() {
		String source = "namespace ensemble create -command ::clock";
		typedCheck(source, 0, 0, "8.5");
	}

	@Test
	public void test127() {
		String source = "return -code error -errorCode $::errorCode $result";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test128() {
		String source = "return -code error -errorCode $::errorCode $result";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test129() {
		String source = "namespace upvar ${selfns} options options";
		typedCheck(source, 0, 0, "8.5");
	}

	@Test
	public void test130() {
		String source = "fconfigure $chan $o $tmp($o)";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test131_FAILED() {
		String source = "lsort $sort [array names histogram]";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test132() {
		String source = "if {1} $cmd elseif {1} $cmd";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test133_FAILED() {
		String source = "lset result $idx {}";
		typedCheck(source, 0, 0, "8.5");
	}

	@Test
	public void test134() {
		String source = "package proc unknown args {namespace eval :: tcl_package $args}";
		typedCheck(source, 2, 0, "8.4");
	}

	@Test
	public void test135() {
		String source = "package proc verbose value {my set verbose $value}";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test136() {
		String source = "string range [expr {$idx+1}] end";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test137() {
		String source = "string compare -length b string1 string2";
		typedCheck(source, 1, 0, "8.4");
	}

	@Test
	public void test138() {
		String source = "string compare -length 5 string1 string2";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test139() {
		String source = "trace add variable var {array unset write} history";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test140() {
		String source = "trace variable var auw history";
		typedCheck(source, 0, 0, "8.4");
	}

	@Test
	public void test141_FAILED() {
		String source = "fconfigure $chan $o $tmp($o)";
		typedCheck(source, 1, 0, "8.4");
	}

	private void typedCheck(String source, int errs, int code, String version) {
		processor = DefinitionManager.getInstance().createProcessor();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2];
		System.out.println("%%%%%%%%%%%%%%%%Test:" + element.getMethodName());
		TclParser parser = TestUtils.createParser(version);
		TclErrorCollector errors = new TclErrorCollector();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, true);
		List<TclCommand> module = parser.parse(source, errors, processor);
		TestCase.assertEquals(1, module.size());
		TclCommand tclCommand = module.get(0);
		EList<TclArgument> args = tclCommand.getArguments();
		int scripts = 0;
		for (int i = 0; i < args.size(); i++) {
			if (args.get(i) instanceof Script) {
				scripts++;
				TestUtils.outCode(source, args.get(i).getStart(),
						args.get(i).getEnd());
			}
			if (args.get(i) instanceof TclArgumentListImpl) {
				EList<TclArgument> innerArgs = ((TclArgumentList) args.get(i))
						.getArguments();
				for (int k = 0; k < innerArgs.size(); k++) {
					if (innerArgs.get(k) instanceof Script) {
						scripts++;
						TestUtils.outCode(source, innerArgs.get(i).getStart(),
								innerArgs.get(i).getEnd());
					}
				}
			}

		}
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(code, scripts);
		assertEquals(errs, errors.getCount());
	}
}
