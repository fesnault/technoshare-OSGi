#!/bin/bash

java -classpath target/engine-1.0-SNAPSHOT.jar:../classpath/org.eclipse.osgi-3.6.0.v20100517.jar:../classpath/cm-3.2.0-v20070116.jar org.phoenix.osgi.engine.Engine /Users/phoenix/data/osgi/technoshare-OSGi/initialBundles
