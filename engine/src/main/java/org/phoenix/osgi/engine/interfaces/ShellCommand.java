package org.phoenix.osgi.engine.interfaces;

public interface ShellCommand {
	boolean canHandleCommand(String command);
	void execute(String command, String...args);
}
