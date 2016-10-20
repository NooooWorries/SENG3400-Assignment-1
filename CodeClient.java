/*
 * Client program
 * Author: Zixin CHENG
 * Course: SENG3400
 * Student number: 3218124
 * */

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class CodeClient 
{
	public static void main(String[] args)  throws IOException
	{	
    	String host = "";    // Server address that client hope to connect
		int port = 0;        // Client port number
		
	    if (args.length == 2)
	    {   	
	    	host = args[0];                    // Get host address from terminal (or cmd)
	    	port = Integer.parseInt(args[1]);  // Get port number from terminal (or cmd)
	    }
	    
	    // If user do not provide port number and host, set port as default value - 127.0.0.1:12345
	 	if (args.length != 2)
	 	{
	 		System.out.println("Full socket not provided, using the default socket 127.0.0.1:12345");
	 		host = "127.0.0.1";
	 		port = 12345;
	 	}
	 	
	    // If port number is less than 1023, a warning message will send to user and the program will terminate.
	 	if (port <= 1023)
	 	{
	 		System.err.println("Port number must be greater than 1023.");
	 		System.exit(1);
	    }
	 	
	 	// Create client socket, printer writer and buffered reader
	 	try (
	 			Socket socket = new Socket(host, port);  // Create a new client socket
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  // Create new server writer
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Create new server message reader
	 	)
	 	{
	 		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));  // Read message from server
	 		String fromServer = "";    // Message from server
            String fromUser   = "";    // Message from user
            boolean serving   = false; // Serving status. Once client send BYE or END, serving status change to false.
            out.println("ASCII");      // Send "ASCII" to server: start service
            System.out.println ("CLIENT: ASCII");
            
            // Waiting for response from server
            while ((fromServer = in.readLine()) == null)
            {
            	
            }
            System.out.println ("SERVER: "+ fromServer);  // Print message from server
            serving = true;                   // Change serving status to start
            while (serving == true)
            {
            	System.out.print("Enter command to send to server [AC,CA,BYE,END, or something to convert]: ");
            	fromUser = stdIn.readLine();  // Read message from client
            	if (fromUser != null) 
            	{   
                    System.out.println("CLIENT: " + fromUser); 
                    out.println(fromUser);
                }
   	
            	// Do not accept ASCII: OK from server
            	while (!(fromServer = in.readLine()).contains("ASCII: OK"))
            	{
            		if (fromServer.equals("BYE: OK"))
                    {
                 	    socket.close(); // Close socket. This sentence will also close I/O Stream
                 	    System.out.println("Disconnected");
                 	    break;   
                    }
            		else if (fromServer.equals("END: OK"))
            		{
            		    socket.close(); // Close socket. This sentence will also close I/O Stream
                   	    System.out.println("Ended"); 
                   	    break;   
            		}
            		else
            		{
                        System.out.println("SERVER: " + fromServer + "\nASCII: OK");	
            		}
            	}
            	if (fromServer.equals("BYE: OK") || fromServer.equals("END: OK"))
            		break;
            }
	 	}
	 	// Catch exception: server not found
	 	catch (UnknownHostException e) 
	 	{
            System.err.println("Server " + host + " not found.");
            System.exit(1);
        } 
	 	// Catch exception: failed connect to server
	 	catch (IOException e) 
        {
            System.err.println("Couldn't get I/O for the connection to " + host);
            System.exit(1);
        }
        // Catch exception: other general error
	 	catch (Exception e)
	 	{
	 		 System.err.println("General Error:\n" + e.getMessage());
	            System.exit(1);
	 	}
	 	return;
	 }

}
