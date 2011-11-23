#!/bin/bash

cd uses-demo-A
mvn clean install
cd ../uses-demo-B
mvn clean install
cd ../uses-demo-D
mvn clean install
cd ../uses-demo-C
mvn clean install
cd ..
