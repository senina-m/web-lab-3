# Readme
### __How to use this project?__

To use code from the project with **Wildfly (25.0.1.Final)** application server and **Oracle Database (11g EE Release 11.2.0.3.0 - 64bit Production)** you need to create confiurations of standalone.xml and persistence.xml.

#### **standalone.xml**
Find file ```/docker/standalone.sample``` and change the text is written in uppercase with your database url, login and password:

```xml
<connection-url>DATABASE-URL</connection-url>
<driver-class>oracle.jdbc.driver.OracleDriver</driver-class>
<driver>oracle</driver>
...
<security>
    <user-name>LOGIN</user-name>
    <password>PASSWORD</password>
</security>
```
Put this file to ```wildfly-25.0.1.Final/standalone/configuration``` folder.

#### **persistence.xml**
The same story with it. Find file ```/src/main/resources/META-INF/persisnence.sample``` and change the text is written in uppercase with your database url, login and password:
```xml
<property name="javax.persistence.jdbc.url"value="DATABASE-URL"/>  <!-- BD Url-->
<property name="javax.persistence.jdbc.user" value="LOGIN"/> <!-- DB User -->
<property name="javax.persistence.jdbc.password" value="PASSWORD"/> <!-- DB Password -->
```
Rename file to persistence.xml and put it to folder ```/src/main/resources/META-INF/```.

#### **How to run project?**
You can build your project with Gradle task ```build```, put it to folder ```/wildfly-25.0.1.Final/standalone/deployments/``` and create there a file with name: ```YOUR-WAR-NAME.dodeploy```.

##### **Manually**
Create directory ```/wildfly-25.0.1.Final/modules/system/layers/base/com/oracle/main/``` and put there files ```module.xml```, ```orai18n.jar```, ```ojdbc8.jar``` from ```/docker/``` folder.

Now you can run application server.
Firstly, run ```/wildfly-25.0.1.Final/bin/add-user.sh``` to create a manager user. With login and password you set you will be able to connect to wildfly web interface.

Then you can run server with command ```/wildfly-25.0.1.Final/bin/standalone.sh```.

##### **Using Docker**
Go to folder ```/docker/``` and run there command ```docker compose up```. It will build image if you have no yet and run a container based on it. 

Remember that if you run app in docker you need to create a ssh channel from your DB to you computer port (YOUR-PORT) and use ```host.docker.internal:YOUR-PORT``` as a host of database.

To create ssh channel you can use command:
``` ssh -p SERVER-PORT SERVER-USERNAME@SERVER-HOST -L LOCALPORT:DB-HOST:DB-SERVER-PORT```.