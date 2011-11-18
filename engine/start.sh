#!/bin/bash

java -Dfelix.fileinstall.dir=../initialBundles -classpath target/engine-1.0-SNAPSHOT.jar:target/engine-1.0-SNAPSHOT/org.eclipse.osgi-3.6.0.v20100517.jar:target/engine-1.0-SNAPSHOT/org.eclipse.osgi.services-3.2.100.v20100503.jar org.phoenix.osgi.engine.Engine target/engine-1.0-SNAPSHOT 
