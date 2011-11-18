package org.phoenix.osgi.engine.commands.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class ListConfigsCommand implements ShellCommand {

	private static final String LIST_CONFIG = "list-configs";
	private BundleContext context;
	
	public ListConfigsCommand(BundleContext context) {
		this.context=context;
	}

	@Override
	public boolean canHandleCommand(String command) {
		return command.equalsIgnoreCase(LIST_CONFIG);
	}

	@Override
	public void execute(String command, String...args) {
		
		ServiceReference ref = context.getServiceReference("org.osgi.service.cm.ConfigurationAdmin");
		if (ref == null) {
		    throw new RuntimeException("ConfigurationAdmin reference could not be obtained.");
		}
		ConfigurationAdmin configAdmin = (ConfigurationAdmin)context.getService(ref);
		if (configAdmin == null) {
		    throw new RuntimeException("Could not get ConfigurationAdmin service from its reference. Returned service is null");
		}
		try {
		    Configuration[] listConfigurations = configAdmin.listConfigurations(null);
		    if (listConfigurations != null) {
    		    for (Configuration conf : listConfigurations) {
    		        System.out.println("Configuration for pid : "+conf.getPid());
    		        Dictionary props = conf.getProperties();
    		        Enumeration keys = props.keys();
    		        while (keys.hasMoreElements()) {
    		            String key = (String)keys.nextElement();
    		            System.out.println(key+"="+props.get(key));
    		        }
    		    }
		    } else {
		        System.out.println("No configuration found.");
		    }
		} catch (IOException ioe) {
		    System.out.println("Oops, could not get configurations : "+ioe.getMessage());
		} catch (InvalidSyntaxException ise) {
            System.out.println("Oops, invalid syntax : "+ise.getMessage());
        }
	}
}
