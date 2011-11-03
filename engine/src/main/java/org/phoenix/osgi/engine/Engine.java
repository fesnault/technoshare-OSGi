package org.phoenix.osgi.engine;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;


/**
 * Phoenix OSGi engine Main class
 *
 */
public class Engine 
{
    private Framework framework;
    private Shell shell;
    
	public static void main(String...args)
    {
        if (args.length != 1) {
        	System.out.println("You must provide the path to the directory containing inital bundles to install.");
        	System.exit(1);
        }
		Engine engine = new Engine();
        engine.setShell(new Shell());
        engine.start(args[0]);
    }
	
	public void start(String initialBundlesPath) {
		System.out.println("Phoenix OSGi engine starting...");
        ServiceLoader<FrameworkFactory> factoryLoader = ServiceLoader.load(FrameworkFactory.class);
        Iterator<FrameworkFactory> iterator = factoryLoader.iterator();
        FrameworkFactory factory = iterator.next();
        Hashtable<String, String> configuration = new Hashtable<String, String>();
        configuration.put("eclipse.consoleLog","true");
        configuration.put("osgi.console","6666");
        configuration.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
        configuration.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "org.phoenix.osgi.engine.interfaces; version=1.0.0");
        framework = factory.newFramework(configuration);
        try {
        	framework.start();
        	System.out.println("Framwork started.");
        } catch (BundleException be) {
        	System.out.println("Could not start framework.");
        }
        installInitialBundles(initialBundlesPath);
        try {
        	shell.start(framework.getBundleContext());
        } catch (Exception e) {
        	System.out.println("Error : "+e.getMessage());
        	e.printStackTrace();
        } finally {
        	stop();
        }
	}
	
	public void stop() {
		try {
			System.out.println("Stopping framework...");
			shell.stop();
			framework.stop();
			framework.waitForStop(0);
			System.out.println("Framework stopped.");
		} catch (Exception e) {
			System.out.println("Error while stopping framework");
		}
		
	}
	
	public void installInitialBundles(String bundlesDirectoryPath) {
		List<Bundle> initialBundles = new ArrayList<Bundle>();
		File directory = new File(bundlesDirectoryPath);
		if (!directory.exists()) {
			System.out.println("Initial bundles directory does not exists : no bundle will be installed.");
			return;
		}
		String[] files = directory.list();
		for (String fileName : files) {
			System.out.println("Installing bundle for jar : "+fileName);
			try {
				Bundle bundle = framework.getBundleContext().installBundle("file://"+directory.getAbsolutePath()+"/"+fileName);
				initialBundles.add(bundle);
			} catch (BundleException be) {
				System.out.println("Unable to install file "+fileName+" : "+be.getMessage());
			}
		}
		for (Bundle bundle : initialBundles) {
			try {
				bundle.start();
				System.out.println("Started bundle "+bundle.getSymbolicName()+"/"+bundle.getVersion());
			} catch (BundleException be) {
				System.out.println("Unable to start bundle "+bundle.getSymbolicName()+"/"+bundle.getVersion()+" : "+be.getMessage());
				be.printStackTrace();
			}
		}
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	
}
