******************************************
*         SENG3400 Assignment 1          *
*          Author: Zixin Cheng           *
*        Student number: c3218124        *
*              USER MANUAL               *
******************************************

Before start runing, compile all three java classes.

1. Run the server
Run terminal or cmd, type in the command:
java CodeConverter <Port>
For example: java CodeConverter 12345
If you leave address and/or port as blank, the program will use the default settings:
Port: 12345

2. Run the client
Run terminal or cmd, type in the command:
java CodeClient <Server address> <Port>
For example: java CodeConverter 127.0.0.1 12345
If you leave address and/or port as blank, the program will use the default settings: 
Server address: 127.0.0.1 
Port: 12345

3. Convert
The default convert model is CA (Character to ASCII).
You can use the code following on client to switch to another model:
AC - ASCII to Character
CA - Character to ASCII

4. Close the service:
You can type in the code following on client at anytime to close the service:
BYE - Disconnect shut down the client (You can use other clients to connect to the server)
END - Disconnect and shut down both server and client

