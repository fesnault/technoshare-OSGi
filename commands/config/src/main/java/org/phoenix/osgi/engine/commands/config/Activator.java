package org.phoenix.osgi.engine.commands.config;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class Activator implements BundleActivator{

	public void start(BundleContext context) throws Exception {
		try {
		ConfigCommand configCommand = new ConfigCommand(context);
		ListConfigsCommand listConfigCommand = new ListConfigsCommand(context);
		context.registerService(ShellCommand.class.getName(), configCommand, null);
        context.registerService(ShellCommand.class.getName(), listConfigCommand, null);
		System.out.println("Config command bundle started.");
		} catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
			e.printStackTrace();
		}
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Config command bundle stopped.");
	}

}
