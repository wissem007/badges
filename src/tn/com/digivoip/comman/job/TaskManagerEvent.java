// The contents of this file are subject to the Mozilla Public License Version 1.1
// (the "License"); you may not use this file except in compliance with the
// License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
// for the specific language governing rights and
// limitations under the License.
//
// The Original Code is "The Columba Project"
//
// The Initial Developers of the Original Code are Frederik Dietz and Timo Stich.
// Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
// All Rights Reserved.
package tn.com.digivoip.comman.job;

import java.util.EventObject;

/** Encapsulates an event from a worker list. */
public class TaskManagerEvent extends EventObject{

	/** The <code>serialVersionUID</code> which was generated from the compiler. */
	private static final long	serialVersionUID	= 4738977594206080618L;
	protected Worker			worker;

	/** Creates a new event from the given source with the given worker that has changed. */
	public TaskManagerEvent(Object theSource, Worker theWorker) {
		super(theSource);
		this.worker = theWorker;
	}
	/** Returns the worker that has been added or removed. */
	public Worker getWorker() {
		return worker;
	}
}
