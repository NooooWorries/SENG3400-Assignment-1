/*
 * Server side program
 * Author: Zixin CHENG
 * Course: SENG3400
 * Student number: 3218124
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class CodeConverter 
{

	static int     state  = 0;     // Server state
	static int     port   = 0;     // Port number
	public static void main(String[] args) 
	{
		boolean status = false;    // Server status: true - on, false - off
		 
		if (args.length == 1)
		    port = Integer.parseInt(args[0]); // Get port number which specified by user
		
		// If user do not provide port number, set port as default value - 12345
		if (args.length != 1)
		{
			port = 12345;
		}
				
		// If port number is less than 1023, a warning message will send to user.
		if (port <= 1023)
		{
			System.err.println("Port number must be greater than 1023.");
			System.exit(1);
	    }
        // Inforom user that program are trying to start server
        // If no error message shows further means server successfully started
		System.out.println ("Server try to start on port " + port + "\n");
		status = true;
		do
		{
		    status = processing(); // Call serving function
		} while (status == true);  // If status = false (User ask to shut down the server), exit the server program
		System.exit(1);            // Exit the server program (END)
	}
	
	private static boolean processing()
	{
		boolean runningStatus = true;
		
		// Create server/client socket, message reader/receiver
		try (
				ServerSocket serverSocket = new ServerSocket(port); // 	Create a new server socket
				Socket clientSocket = serverSocket.accept();        // Listen socket request from client
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Create message sender
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Create message receiver
	    )
		{
			String inputLine, outputLine;
			ConverterProtocol protocol = new ConverterProtocol(); // Create new protocol object
	    	System.out.println ("Client connected on port " + port + "\n");
					
			// Read the incoming message
			while ((inputLine = in.readLine()) != null && runningStatus == true)
			{
				System.out.println("REQUEST: " + inputLine);   // Print the incoming message
		        outputLine = protocol.processInput(inputLine); // Send the incoming message to the protocol and get the return message
		              
		        if (outputLine == "ERR")
		        {
		            System.out.println("RESPONSE: ERR\n" + "ASCII: OK\n"); // Print the incoming message
                    outputLine = "ERR\nASCII: OK";  // Send the incoming message to the protocol and get the return message
		        }
		        else
		            System.out.println("RESPONSE: " + outputLine + "\n");  // Send the response from protocol
		        if (outputLine != null)
		        {
		            out.println(outputLine);                   // Return message
		            if (outputLine.equals("BYE: OK\n") || outputLine.equals("END: OK\n"))
		            {
		              	state = protocol.getState(); // Get the state of server
		      		    // If server state = 3 (BYE), disconnect the current session
		            	// Client side will disconnect the current session
		            	if (state == 3) 
		            	{
		            		System.out.println("REQUEST: BYE\nRESPONSE: BYE: OK"); // Print if client ask "BYE"
		            		outputLine = "BYE";
		            		runningStatus = true;
		            		break;
		            	}
		            	// If server state = 4 (END), shut down the server
		            	if (state == 4)
		            	{
		            		outputLine = "END";
		            		System.out.println("REQUEST: END\nRESPONSE: END: OK");
		            		runningStatus = false; // Set status to false
 		            		clientSocket.close();  // Close client socket
		            		serverSocket.close();  // Close server socket
		            		System.exit(0);        // Exit the server program
		            	}
		            }
		        }
		    }	
		}
    	// Catch exception and return error message
		catch (IOException e)
		{
			System.err.println("Unable to connect to the server:\n" + e.getMessage());
			System.exit(1);
		}
		return runningStatus;
	}
}
