# Hotel Manager Tool
Manage your hotels or reservations in an easy way.

<h1>Building the project</h1>
The client can be compiled into a jar to later run it via Java while the server it's directly ran by maven.

To pack the client you can either run the `buildClient.bat` located in the root folder or type the  following command in the Client folder:

>mvn install -DskipTests

To run the project you can run the following command

>java -jar [Client]*/target/Client-0.0.1-SNAPSHOT-jar-with-dependencies

To run the server, you can either run the `runServer.bat` or run the following command in the Server folder:

>mvn compile datanucleus:enhance exec:java -DskipTests

To run the JUnits found in each module of the project you can just run

>mvn test

and you'll find the corresponding result in `[Client/Server]*/target/jacoco.exec`


Or enter the target folder and double clicking the jar file (In the case of the Server it's not recommended as it does not have a GUI, if you want to kill the process afterwards you will have to kill the entire java proccess)

<h2>Datanucleus</h2>

To be able to use the database you will need the next command, included already in the building / running commands from above:

> mvn datanucleus:enhance

If a change affects the deletion or adition of a column, you may need to run the next command in order for the database to work properly

> mvn datanucleus:enhance datanucleus:schema-delete

To create the database, make use of the following SQL code, this project runs on MySQL:
```SQL
DROP SCHEMA IF EXISTS HotelManager;
DROP USER IF EXISTS 'spq'@'localhost';
CREATE SCHEMA IF NOT EXISTS HotelManager;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';
GRANT ALL ON HotelManager.* TO 'spq'@'localhost';
```

If you like to check if all tables will generate correctly you can use

> mvn datanucleus:enhance datanucleus:schema-create
