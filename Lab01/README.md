### COSC 4P14 - Lab exercise #1
###### By: Liam Baillie, Cameron Hutchings, Tamara McDiarmid
***
#### Files Contained in Project:
* ClientApp.java
* GeneralData.java
* main.java
* ServerConnection.java
* ServerController.java
* UserData.java

#### Features Implemented:
1. Basic text chatting
2. Having usernames to identify who's talking
3. More than two participants concurrently
4. Text replacements of some sort (see options for client)
5. More than two concurrent conversations on 
the same server execution. i.e. chat rooms

#### Directions to run Java Chat Program:
* Compile all files (javac *.java)
* Run the main file (java main)
* User presented with choice s/c:
    * 's' for server start
    * 'c' for client start
    * Client presented with a message to enter
    their server IP address to establish connection
##### Options for Client:
* Will be replaced with :) on server side (see below)

~~~
  SMILE_COMMAND = "(smile)";
~~~
* Will be replaced with :D on server side (see below)

~~~
EXCITED_COMMAND = "(excited)";
~~~
* To change chat rooms if user enters (see below)
~~~
"/cr"
~~~




