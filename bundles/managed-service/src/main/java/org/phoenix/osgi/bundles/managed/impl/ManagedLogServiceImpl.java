package org.phoenix.osgi.bundles.managed.impl;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.phoenix.osgi.bundles.managed.ManagedLogService;

public class ManagedLogServiceImpl implements ManagedLogService, ManagedService {
	private String valueToPrint="Default";

	@Override
	public void logIt() {
		System.out.println("Logging : "+valueToPrint);

	}

	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		if (properties != null) {			
			String newValueToPrint = (String)properties.get("value.to.print");
			if (newValueToPrint != null && newValueToPrint.trim().length() > 0) {
				this.valueToPrint = newValueToPrint;
			} else {
				System.out.println("The value to print cannot be empty.");
			}
		} else {
			System.out.println("The new configuration is null !");
		}
	}

}
