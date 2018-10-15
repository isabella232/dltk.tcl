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
proc a::b::alfa { } {}
proc a::d::alfa { } {}
proc b::d::alfa { } {}
proc alfa {} {}
proc a::alfa {} {}
proc a::beta {} {}

#References
a::b::alfa 
a::d::alfa
b::d::alfa
alfa
a::alfa
a::beta

