package org.phoenix.osgi.engine.commands.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.phoenix.osgi.engine.interfaces.ShellCommand;

public class ConfigCommand implements ShellCommand {

	private static final String CONFIG = "config";
	private BundleContext context;
	
	public ConfigCommand(BundleContext context) {
		this.context=context;
	}

	@Override
	public boolean canHandleCommand(String command) {
		return command.equalsIgnoreCase(CONFIG);
	}

	@Override
	public void execute(String command, String...args) {
		if (args.length < 2) {
			System.out.println("Usage: config <service.pid> <prop=value[,prop=value]*>");
			return;
		}
		ServiceReference ref = context.getServiceReference("org.osgi.service.cm.ConfigurationAdmin");
		if (ref == null) {
		    System.out.println("ConfigurationAdmin reference could not be obtained.");
		}
		ConfigurationAdmin configAdmin = (ConfigurationAdmin)context.getService(ref);
		if (configAdmin == null) {
		    System.out.println("Could not get ConfigurationAdmin service from its reference. Returned service is null");
		}
		try {
		    Configuration configuration = configAdmin.getConfiguration(args[0], null);
		    Dictionary<Object, Object> props = new Properties(); 
		    StringTokenizer tokenizer = new StringTokenizer(args[1],",");
		    while (tokenizer.hasMoreTokens()) {
		        StringTokenizer pvTokenizer = new StringTokenizer(tokenizer.nextToken(),"=");
		        String propName = pvTokenizer.nextToken();
		        String value = pvTokenizer.nextToken();
		        props.put(propName, value);
		    }
		    if (props.size() > 0) {
		        configuration.update(props); 
		        System.out.println("Updating configuration for service pid : "+configuration.getPid());
		        Enumeration keys = configuration.getProperties().keys();
		        while (keys.hasMoreElements()) {
		            String key = (String)keys.nextElement();
		            System.out.println(key+"="+configuration.getProperties().get(key));
		        }
		    }
		} catch (IOException ioe) {
		    System.out.println("Oops, could not get configuration for pid "+args[0]+" : "+ioe.getMessage());
		}
	}
}
