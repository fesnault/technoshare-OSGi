package org.phoenix.osgi.engine.interfaces;

import java.util.Enumeration;
import java.util.Map;

public interface PocInterface {
    Map<String,String> doIt(Enumeration entries);
}
