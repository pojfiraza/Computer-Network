

/*
 * 
 * The user will enter the server hostname as a command line argument when the client program 
 * is invoked. 
 * 
 * If there is no command line argument then the program will print an error message and exit. 
 * 
 * The client program then enters a loop until told to quit where it will:



The menu will provide the following choices to the user:
1.	Host current Date and Time
2.	Host uptime
3.	Host memory use
4.	Host Netstat
5.	Host current users
6.	Host running processes
7.	Quit

 * */
import java.io.BufferedReader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.*;
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner; 
public class Client
{
 
	public static int countLines(String str) {
	    if(str == null || str.isEmpty())
	    {
	        return 0;
	    }
	    int lines = 1;
	    int pos = 0;
	    while ((pos = str.indexOf("\n", pos) + 1) != 0) {
	        lines++;
	    }
	    return lines;
	
}
    public static Socket socket;
 
    public static void main(String args[])
    {
    	 String number = null;
         
         
        try
        {
        	while (true)
        	{
            String host = "192.168.100.104";
            int port = 2002;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
           
            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
           
            
            BufferedWriter bw = new BufferedWriter(osw);
 
           
            System.out.println("\n*************************\nEnter a number:\n\n"
            		+ "Press 0 for Multithreading\n\n"
            		+ "press 1 for Date and Time\n\n"
            		+ "press 2 for Uptime\n\n"
            		+ "press 3 for Memory Use\n\n"
            		+ "press 4 for Netstat\n\n"
            		+ "press 5 for Current Users\n\n"
            		+ "press 6 for Running processes\n\n"
            		+ "press 7 to quit \n\n");
            Scanner input = new Scanner (System.in);
            number = input.next();
            String requestType = null;
            String verb = "is";
     
            try
           {
            	 if (Integer.parseInt(number) == 0)
                 {
                    	requestType = "Response Time";
                    	//Begin Threading here
                 }
        	   
            if (Integer.parseInt(number) == 1)
            {
            	requestType = "Date and Time";
            }
            else if (Integer.parseInt(number) == 2)
            {
            	requestType = "Uptime";
            }
            else if (Integer.parseInt(number) == 3)
            {
            	requestType = "Free System Memory";
            }
            else if (Integer.parseInt(number) == 4)
            {
            	requestType = "Netstat";
            }
            else if (Integer.parseInt(number) == 5)
            {
            	requestType = "Current Users";
            	verb = "are";
            }
            else if (Integer.parseInt(number) == 6)
            {
            	requestType = "Running Processes";
            	verb = "are";
            }
            else if (Integer.parseInt(number) == 7)
            {
            	System.out.println("Application has terminated.");
            	break;
            }
            else {
            	
            	System.out.println("Write a number between 1 and 7!\n");
            	requestType = "Error";
            	
            }
           } catch (NumberFormatException e)
           {
        	   System.out.println("Write a number between 1 and 7!\n");
        	   requestType = "Error";
           }
            
            String sendMessage = number + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : \n\n"+sendMessage);
 
            
            
            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            String message = "";
            int linecount=0;
          //  message=br.readLine()+"\n";
            message=br.readLine();
            
           
           while(br.ready()){
            		//message += message + "\n";
        	   s = br.readLine()+"\n";
        	   message += s;
            linecount++;
            
            		}//end while
            
            System.out.println("Server " + requestType + " " + verb + ": \n\n" +message);
        	}
        }//end try
        catch (Exception exception)
        {
            exception.printStackTrace();
        }//end catch
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }//end try
            catch(Exception e)
            {
                e.printStackTrace();
            }//end catch
        }// end finally
        	
        	
    }//end main
}//end class client