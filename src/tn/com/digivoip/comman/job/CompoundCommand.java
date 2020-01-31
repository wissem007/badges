package tn.com.digivoip.comman.job;

import java.util.List;
import java.util.Vector;

public class CompoundCommand extends Command{

	protected List<ICommand>	commandList;

	public CompoundCommand() {
		super(null);
		commandList = new Vector<ICommand>();
		priority = Command.NORMAL_PRIORITY;
		commandType = Command.NORMAL_OPERATION;
	}
	public void add(ICommand c) {
		commandList.add(c);
	}
	public void execute(IWorkerStatusController worker) throws Exception {
		for (ICommand _command : commandList) {
			_command.execute(worker);
		}
	}
	public boolean canBeProcessed() {
		boolean result = true;
		for (ICommand _command : commandList) {
			result &= _command.canBeProcessed();
		}
		if (!result) {
			releaseAllFolderLocks();
		}
		return result;
	}
	public void releaseAllFolderLocks() {
		for (ICommand _command : commandList) {
			_command.releaseAllFolderLocks();
		}
	}
	public void updateGUI() throws Exception {
		for (ICommand _command : commandList) {
			_command.updateGUI();
		}
	}
}