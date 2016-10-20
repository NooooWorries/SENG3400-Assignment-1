/*
 * Server side protocol
 * Author: Zixin CHENG
 * Course: SENG3400
 * Student number: 3218124
 * */

public class ConverterProtocol 
{
	// Define the state number
	private static final int ASCII = 0; // ASCII: Listen client's request
	private static final int CA    = 1; // CA:    Convert character to ASCII
	private static final int AC    = 2; // AC:    Convert ASCII to character
	private static final int BYE   = 3; // BYE:   Disconnect the current session
	private static final int END   = 4; // END:   Shut down the server
	
	private int state = ASCII;          // Initialize current state: Listening client's request
	
	String matches = "[a-zA-Z_0-9]";    // Regex, only accept "0-9, A-Z, a-z" to convert
    public String processInput (String input)
    {
    	String output = "ERR";
    	switch (input)
    	{
    	    case "ASCII":
    	    	state = CA; // change the status to CA
        		output = "ASCII: OK"; // return OK
        		break;
    	    case "CA":
    	    	state = CA; // change the status to CA
        		output = "CHANGE: OK\n" + "ASCII: OK"; // return OK
        		break;
    	    case "AC":
    	    	state = AC; // change the status to AC
    	    	output = "CHANGE: OK\n" + "ASCII: OK"; // return OK
    	    	break;
    	    case "BYE":
    	    	state = BYE; // change the status to BYE
    	    	output = "BYE: OK\n"; // return OK
    	    	break;
    	    case "END":
    	    	state = END; // change the status to END
    	    	output = "END: OK\n"; // return OK
    	    	break;
    	}
    	
    	// Convert character to ASCII
    	if (state == CA)
    	{
    		if (input.length() == 1 && input.matches(matches)) // Only accept one character, Only accept 0-9, a-z, A-Z
    		{
    		    char character = input.charAt(0);              // Convert string to character
    		    int result = (int)character;                   // Convert character to ASCII
    		    return result + "\n" + "ASCII: OK"; // Return result
    		}
    	}
    	
    	// Convert ASCII to character
    	else if (state == AC)
    	{
    		try
    		{
    			int ascii = Integer.parseInt(input); // Convert input string to int
    			if ( (ascii >= 48 && ascii <= 57) || 
    				 (ascii >= 65 && ascii <= 90) ||
    				 (ascii >= 97 && ascii <= 122) ) // Only accept 0-9, A-Z, a-z
    			{
    			    char result = (char)ascii;       // Convert ASCII to character
    			    return result + "\nASCII: OK";   // Return result
    			}   			
    		}
    		catch (Exception ex) // Catch exception
    		{
    			
    		}
    	}
    	return output; // If return default value - EER, means an error happened
    }
    
    // Return the current states
    // Precondition:  Server connected and request to get status
    // Postcondition: Return back the current status
    public int getState ()
    {
    	return state;
    }
}
