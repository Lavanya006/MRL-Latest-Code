AXIS2 STUB GENERATION MANUAL
----------------------------

Generate ImportServiceStub.java from the WSDL file:

1 - Download Apache Axis2 and put its bin directory in your PATH variable;
http://www.apache.org/dyn/closer.lua/axis/axis2/java/core/1.7.9/axis2-1.7.9-bin.zip

2 - Issue this command:

For DEV:
---------
WSDL2Java -uri ImportService_Dev.wsdl -sp -s -p com.mtag.cmc.core.service.notification.generated.dev -l java -t -d adb -Eosv -noBuildXML -noWSDL

Here ImportService_Dev.wsdl must be changed according to the environment: DEV, QA or PROD. Also pay attention to the package name.

DANGER!!!
--------- 
 In DEV you must change the WSDL in order to make the service URL https instead of HTTP like this:
<soap:address location="https://by-twbcs-d.de.bayer.cnb:8080/ImportService/services/ImportService/"/>

For QA:
-------
WSDL2Java -uri ImportService_QA.wsdl -sp -s -p com.mtag.cmc.core.service.notification.generated.qa -l java -t -d adb -Eosv -noBuildXML -noWSDL

For PROD:
-------
WSDL2Java -uri ImportService_Prod.wsdl -sp -s -p com.mtag.cmc.core.service.notification.generated.prod -l java -t -d adb -Eosv -noBuildXML -noWSDL


Also you must download from the Bayer PC the XSD file and put it in your local file system. The
current example assumes that the WSDL and the XSD file are in the same directory and the WSDL2Java command
is run on that same directory. Do no forget to update the URI of the XSD file in the WSDL file like this:
<xsd:import namespace="http://cairnew.bcs.cnb/" schemaLocation="importCairNew.xsd">
Originally was: schemaLocation="ImportService?xsd=importCairNew.xsd"

3 - Copy ImportServiceStub.java to that very same package in the cairnew-stubs Module of the CMC project;
com.mtag.cmc.core.service.notification.generated

Everything should just work if you run and compile the project.