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

proc foo0 { a b c d } {
	proc qwe {} {}
}
namespace eval namespace1 {
	proc a {} {}
	namespace eval inner1 {
		proc b {} {}
	}
}

namespace eval namespace2 {
	proc c {} {}
	namespace eval inner2 {
		proc d {} {}
		namespace eval inner4 {
			proc e {} {}
		}
	}
}