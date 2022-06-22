## Crop Monitoring Cockpit (CMC)##

### MT-AG Support Contact ###
Michael Grüneklee (business),
Sven-Olaf Kelbert, Manuel Klein (technical)

### About ###
- Based on Java 11 and Angular
- The JDK 11 ships with the Application and by default lives together with
    the executable JAR, unless you edit cmc-1.1.0-start.bat in order to put it
    elsewhere.
    
### IMPORTANT ###

     ### No one should ever manually change any data in the Measurement Database;
     ### A Grabbing Job MUST ALWAYS run to the completion;
     If these two rules are observed there should be no duplicate Measurements.

### CONFIGURATION ###
# "application.properties" MUST be in the same directory as cmc-1.1.0.jar

### DANGER ###
## Both
  ### bayer.proxy.status ###
  # and
  ### bayer.cairnew.notification ###
  # must be
  ### ON ###
  # in order to notify CairNew
  
### The Property 
  bayer.cairnew.environment 
# MUST be either 
    DEV
    QA 
    PROD
# example:
  bayer.cairnew.environment=DEV

### EXECUTION ###
### ONLY through the Windows batch file
cmc-1.1.0-start.bat