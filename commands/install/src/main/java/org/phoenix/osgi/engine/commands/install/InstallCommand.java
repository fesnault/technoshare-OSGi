package org.phoenix.osgi.engine.commands.install;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class InstallCommand implements ShellCommand {

	private static final String INSTALL = "install";
	private BundleContext context;
	
	public InstallCommand(BundleContext context) {
		this.context=context;
	}

	@Override
	public boolean canHandleCommand(String command) {
		return command.equalsIgnoreCase(INSTALL);
	}

	@Override
	public void execute(String command, String...args) {
		if (args.length < 2) {
			System.out.println("Usage: install <relative path|absolute path> <bundle jar name> [-list|-start]");
			return;
		}
		String directory=args[0];
		String jarNameFilter=args[1];
		String modifier=args.length>2 ? args[2] : null;
		File dir=new File(directory);
		if (!dir.exists() && !dir.isDirectory()) {
			System.out.println("Path does not exists or is not a directory : "+dir.getAbsolutePath());
			return;
		}
		List<String> jarPathes = searchJars(dir, jarNameFilter);
		if (modifier != null && modifier.trim().toLowerCase().equals("-list")) {
			for (String path : jarPathes) {
				System.out.println(" -> "+path);
			}
		} if (modifier != null && modifier.trim().toLowerCase().equals("-start")) {
		    for (String path : jarPathes) {
                installBundle(path, true);
            }
		}else {
			for (String path : jarPathes) {
				installBundle(path);
			}
		}
	}
	
	private void installBundle(String path) {
	    installBundle(path, false);
	}
	
	private void installBundle(String path, boolean start) {
		try {
			Bundle bundle=context.installBundle("file://"+path);
			if (start) {
			    bundle.start();
			}
		    System.out.println("Bundle "+bundle.getSymbolicName()+" ("+bundle.getVersion()+") installed" + (start?" and started.":"."));
		} catch (BundleException e) {
			System.out.println("Could not install and/or start bundle with path :"+path);
			System.out.println("Error message : "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private List<String> searchJars(File directory, String filter) {
		List<String> jarPathes = new ArrayList<String>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				jarPathes.addAll(searchJars(file, filter));
			} else {
				if (file.getName().toLowerCase().endsWith(".jar") && (file.getName().toLowerCase().contains(filter) || filter.equals("*"))) {
					jarPathes.add(file.getAbsolutePath());
				}
			}
		}
		return jarPathes;
	}

}
