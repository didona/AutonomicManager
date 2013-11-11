#!/bin/bash

mvn install:install-file -Dfile=lib/WPMConnector.jar -DgroupId=eu.cloudtm -DartifactId=WPMConnector -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/client-0.2.0-SNAPSHOT.jar -DgroupId=org.apache.deltacloud -DartifactId=client -Dversion=0.2.0-SNAPSHOT -Dpackaging=jar
mvn install:install-file -Dfile=lib/dap-framework_22_08_2013_LDA_Auto_Online_Optimize_With_Data_Placement.jar -DgroupId=stoyan -DartifactId=dap -Dversion=1.0.0 -Dpackaging=jar

mvn install
