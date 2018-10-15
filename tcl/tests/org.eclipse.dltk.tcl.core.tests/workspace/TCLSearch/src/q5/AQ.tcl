###############################################################################
# Copyright (c) 2005, 2007 IBM Corporation and others.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0 which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# SPDX-License-Identifier: EPL-2.0
#

###############################################################################

package provide q5

namespace eval I {
	proc k { arg } {
	}
	::I::k 1
	k 2
	::m
}

namespace eval I2 {
	proc k { arg } {
	}
	::I::k2 k2_1
	k2 k2_2
	::m
}

proc m { } {
	::I::k 3
}
m
I::k 4
::I::k 5

if { [m] } {
    puts "m"
}

