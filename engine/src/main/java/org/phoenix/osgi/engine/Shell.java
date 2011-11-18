package org.phoenix.osgi.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class Shell {
	private ServiceTracker shellCommandTracker;
	private List<ServiceReference> shellCommandReferences = new ArrayList<ServiceReference>();
	
	public void start(BundleContext context)  throws Exception {
		System.out.println("Phoenix OSGi shell.");
		shellCommandTracker = new ServiceTracker(context, ShellCommand.class.getName(), null);
		shellCommandTracker.open();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String cmd=null;
		do {
			System.out.print("> ");
			cmd = reader.readLine().trim().toLowerCase();
			if (!cmd.equals("exit")) {
				handleCommand(cmd);
			}
		} while (!cmd.equals("exit"));
	}
	
	public void stop() {
		shellCommandTracker.close();
	}
	
	public void handleCommand(String commandLine) {
		if (shellCommandTracker == null) {
			System.out.println("No command handler found; cannot handle command : "+commandLine);
		} else {
			StringTokenizer tokenizer = new StringTokenizer(commandLine);
			if (tokenizer.hasMoreTokens()) {
                String command = tokenizer.nextToken();
				List<String> args = new ArrayList<String>();
				boolean inQuotedParam = false;
				StringBuilder builder = null;
				if (tokenizer.hasMoreTokens()) {
					while (tokenizer.hasMoreTokens()) {
					    String token = tokenizer.nextToken();
					    if (token.contains("\"") && !inQuotedParam) {
					        inQuotedParam=true;
					        token = token.replaceFirst("\"", "");
					        builder = new StringBuilder();
					        builder.append(token);
					    } else if (inQuotedParam) {
					        if (token.contains("\"")) {
					            inQuotedParam = false;
					            token = token.replaceFirst("\"", "");
					            builder.append(" ");
					            builder.append(token);
					            args.add(builder.toString());
					        } else {
					            builder.append(" ");
					            builder.append(token);
					        }
					    } else {
					        args.add(token);
					    }
					}
				}
				executeCommand(command, args.toArray(new String[args.size()]));
			} else {
				System.out.println("Command line is empty, cannot handle command.");
			}
		}
	}
	
	public void executeCommand(String command, String...args) {
		Object[] shellCommands = shellCommandTracker.getServices();
		if (shellCommands != null) { 
			for (Object object : shellCommands) {
				if (object != null) {
					ShellCommand shellCommand = (ShellCommand)object;
					if (shellCommand.canHandleCommand(command)) {
						shellCommand.execute(command, args);
						return;
					}
				}
			}
		}
		System.out.println("Could not find a command handler able to execute command : "+command);
	}
}
