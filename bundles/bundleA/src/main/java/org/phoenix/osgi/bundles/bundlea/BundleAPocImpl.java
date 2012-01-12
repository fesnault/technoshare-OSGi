package org.phoenix.osgi.bundles.bundlea;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import org.phoenix.osgi.engine.interfaces.PocInterface;
import org.phoenix.osgi.engine.interfaces.ResolutionEnum;

public class BundleAPocImpl implements PocInterface {

   
    public Map<String,String> doIt(Enumeration entries) {
        long start = System.currentTimeMillis();

        // sort according to the keys (labels of the enums)
        TreeMap<String,String> resolutions = new TreeMap<String,String>();
        if (entries == null) {
            return resolutions;
        }
        while (entries.hasMoreElements()) {
            String path = ((URL)entries.nextElement()).getFile();

            try {
                path = URLDecoder.decode(path, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // UTF-8 is a required encoding
            }
            if (path.startsWith("/")) { path = path.substring(1); }
            String externalName = path.substring(0, path.indexOf('.')).replace('/', '.');
            addIfAssignable(resolutions, path);
        }


        return resolutions;
    }
    
    private void addIfAssignable(Map<String, String> resolutions, String packageOrClass) {
        try {
                System.out.println("Checking to see if class '" + packageOrClass + "' implements " + ResolutionEnum.class);
            ClassLoader loader = getClass().getClassLoader();
            String externalName = packageOrClass.substring(0, packageOrClass.indexOf('.')).replace('/', '.');

            Class type = loader.loadClass(externalName);
            if (ResolutionEnum.class.isAssignableFrom(type)
                    && Enum.class.isAssignableFrom(type)) {
                    System.out.println(ResolutionEnum.class.getName() + " is isAssignableFrom " + type.getName());
                EnumSet<? extends Enum> set = EnumSet.allOf(type);
                for(Enum e : set){
                    ResolutionEnum re = (ResolutionEnum) e;
                    System.out.println("Found : " + e.name()  + " with path " + re.getPath());
                        resolutions.put(e.name(), re.getPath());
                }
            } else {
                    System.out.println(ResolutionEnum.class.getName() + " is not isAssignableFrom " + type.getName());
           }

               System.out.println("Found resolutions : " + resolutions);

        }
        catch (Throwable t) {
                System.out.println("Could not examine class '" + packageOrClass + "'" + " due to a " +
                     t.getClass().getName() + " with message: " + t.getMessage());
        }
    }
}
