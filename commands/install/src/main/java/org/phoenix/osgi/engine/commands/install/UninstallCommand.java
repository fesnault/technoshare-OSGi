package org.phoenix.osgi.engine.commands.install;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class UninstallCommand implements ShellCommand {

	private static final String UNINSTALL = "uninstall";
	private BundleContext context;
	
	public UninstallCommand(BundleContext context) {
		this.context=context;
	}

	@Override
	public boolean canHandleCommand(String command) {
		return command.equalsIgnoreCase(UNINSTALL);
	}

	@Override
	public void execute(String command, String...args) {
		if (args.length != 1) {
			System.out.println("Usage: uninstall <bundle-id>");
			return;
		}
		long bundleId = Long.parseLong(args[0]);
		Bundle bundle = context.getBundle(bundleId);
		if (bundle == null) {
			System.out.println("No bundle found for bundle id : "+bundleId);
			return;
		}
		try {
			bundle.uninstall();
			System.out.println("Bundle "+bundle.getSymbolicName()+" ("+bundle.getVersion()+") uninstalled.");
		} catch (BundleException e) {
			System.out.println("Unable to uninstall bundle "+bundle.getSymbolicName()+" ("+bundle.getVersion()+").");
			System.out.println("Error message : "+e.getMessage());
			e.printStackTrace();
		}
	}

}
