// The contents of this file are subject to the Mozilla Public License Version
// 1.1
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
// The Initial Developers of the Original Code are Frederik Dietz and Timo
// Stich.
// Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
// All Rights Reserved.
package tn.com.digivoip.comman.job;

import java.util.List;
import java.util.Vector;
import javax.swing.event.EventListenerList;
import tn.com.digivoip.comman.Mutex;

public class TaskManager{

	private List<Worker>		workerList;
	protected Mutex				workerListMutex;
	/** Listeners which are interested in status changes */
	protected EventListenerList	listenerList	= new EventListenerList();
	private static TaskManager	instance		= new TaskManager();

	/** Default constructor */
	private TaskManager() {
		workerList = new Vector<Worker>();
		workerListMutex = new Mutex();
	}
	public static TaskManager getInstance() {
		return TaskManager.instance;
	}
	/** Get list of currently running workers.
	 * @return list of workers */
	public Worker[] getWorkers() {
		return workerList.toArray(new Worker[0]);
	}
	public boolean exists(Worker worker) {
		try {
			workerListMutex.lock();
			if (workerList.contains(worker)) { return true; }
		} finally {
			workerListMutex.release();
		}
		return false;
	}
	/** Get number of workers
	 * @return number of currenlty running workers */
	public int count() {
		return workerList.size();
	}
	/* (non-Javadoc)
	 * @see org.columba.core.taskmanager.ITaskManager#register(org.columba.core.command.Worker) */
	public void register(Worker t) {
		try {
			workerListMutex.lock();
			workerList.add(t);
		} finally {
			workerListMutex.release();
		}
		fireWorkerAdded(t);
	}
	public void unregister(SwingWorker.ThreadVar tvar) {
		try {
			workerListMutex.lock();
			for (Worker _worker : workerList) {
				if (tvar == _worker.getThreadVar()) {
					workerList.remove(_worker);
					fireWorkerRemoved(_worker);
					break;
				}
			}
		} finally {
			workerListMutex.release();
		}
	}
	public void addTaskManagerListener(TaskManagerListener l) {
		listenerList.add(TaskManagerListener.class, l);
	}
	public void removeTaskManagerListener(TaskManagerListener l) {
		listenerList.remove(TaskManagerListener.class, l);
	}
	protected void fireWorkerAdded(Worker w) {
		TaskManagerEvent e = new TaskManagerEvent(this, w);
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TaskManagerListener.class) {
				((TaskManagerListener) listeners[i + 1]).workerAdded(e);
			}
		}
	}
	protected void fireWorkerRemoved(Worker w) {
		TaskManagerEvent e = new TaskManagerEvent(this, w);
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TaskManagerListener.class) {
				((TaskManagerListener) listeners[i + 1]).workerRemoved(e);
			}
		}
	}
}
