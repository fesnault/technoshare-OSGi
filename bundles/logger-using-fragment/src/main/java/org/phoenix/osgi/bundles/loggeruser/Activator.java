package org.phoenix.osgi.bundles.loggeruser;

import java.lang.reflect.InvocationTargetException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        logIt("I am a logged string.");
    }

    public void stop(BundleContext context) throws Exception {
        
    }
    
    private void logIt(String value) {
     // Try to get the extended logger.
        try {
            Class loggerClass = Class.forName("org.phoenix.osgi.bundles.logger.Logger");
            Object instance = loggerClass.newInstance();
            loggerClass.getDeclaredMethod("log",String.class).invoke(instance, value);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Default logger : "+value);
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
    }

}
