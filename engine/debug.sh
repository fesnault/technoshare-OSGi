#!/bin/bash

java -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -classpath target/engine-1.0-SNAPSHOT.jar:../classpath/org.eclipse.osgi-3.6.0.v20100517.jar org.phoenix.osgi.engine.Engine /Users/phoenix/data/osgi/technoshare-osgi/initialBundles