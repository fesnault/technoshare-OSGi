package org.phoenix.osgi.engine.commands.install;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class Activator implements BundleActivator{

	public void start(BundleContext context) throws Exception {
		try {
		System.out.println("Install command bundle started.");
		InstallCommand installCommand = new InstallCommand(context);
		UninstallCommand uninstallCommand = new UninstallCommand(context);
		context.registerService(ShellCommand.class.getName(), installCommand, null);
		context.registerService(ShellCommand.class.getName(), uninstallCommand, null);
		} catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
			e.printStackTrace();
		}
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Install command bundle stopped.");
	}

}
