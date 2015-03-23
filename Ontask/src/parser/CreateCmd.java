package parser;

import util.Task;

public class CreateCmd {
	public Command createNewCommand(String str){
		assert str.equals(new String("clear")) || str.equals(new String(""));
		Command backCommand = new Command(str);
		return backCommand;
	}
	
	public Command createBackCommand(){
		Command backCommand = new Command("");
		return backCommand;
	}
	
	public Command createDirCommand(String str){
		Command dirCommand = new Command("changedir");
		dirCommand.setContent(str);
		return dirCommand;
	}

	public Command createAddCommand(Task t) {
		Command addCommand = new Command("add");
		addCommand.setTask(t);
		return addCommand;
	}

	public Command createEditCommand(String f, String t, int i) {
		Command editCommand = new Command("edit");
		editCommand.setIndex(i);
		editCommand.setContent(f);
		editCommand.setModifiedString(t);

		return editCommand;
	}

	public Command createDeleteCommand(int i) {
		Command deleteCommand = new Command("delete");
		deleteCommand.setIndex(i);

		return deleteCommand;
	}

	public Command createSearchCommand(String s) {
		Command searchCommand = new Command("search");
		searchCommand.setContent(s);

		return searchCommand;
	}

	public Command createUndoCommand() {
		Command undoCommand = new Command("undo");

		return undoCommand;
	}
}
