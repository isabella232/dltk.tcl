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

namespace eval mynamespace {
	proc a1 { aa1 } {
		return $aa1
	}

	proc a2 { aa21 aa22 aa23 } {
		return $aa23
	}
}

namespace eval mynamespace2 {
	proc a1 { aa1 } {
		return $aa1
	}

	proc a2 { aa21 aa22 aa23 } {
		return $aa23
	}
}	

namespace eval mynamespace3 {
	proc a1 { aa1 } {
		return $aa1
	}

	proc a2 { aa21 aa22 aa23 } {
		return $aa23
	}
}

namespace eval mynamespace2 {
	proc a3 { aa1 } {
		return $aa1
	}

	proc a4 { aa21 aa22 aa23 } {
		return $aa23
	}
}

