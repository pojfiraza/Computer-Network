import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; 
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Server {

	public static Socket socket;
	
	public static void main(String[] args) 
	{

		   try
	        {

                
	            int port = 2002;
	            ServerSocket serverSocket = new ServerSocket(port);
	            System.out.println("Server Started and listening to the port " +String.valueOf(port));
	 
	            //Server is running always. This is done using this while(true) loop
	            while(true)
	            {
	                //Reading the message from the client
	                socket = serverSocket.accept(); //Stops program 
	                /// 
	                InputStream is = socket.getInputStream(); // Waits
	                InputStreamReader isr = new InputStreamReader(is); //Saves input into "isr"
	                BufferedReader br = new BufferedReader(isr); //Breaks up isr
	                String number = br.readLine(); //reads first line
	                System.out.println("Message received from client is "+number);
	 
	                //Multiplying the number by 2 and forming the return message
	                String returnMessage = "Bad Input\n";
	              
	                try
	                {
	                    int numberInIntFormat = Integer.parseInt(number);
	                    //gets Date and Time if client types 1
	                    if (numberInIntFormat == 1){
	                    	Date now = new Date();
	                        SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
	                    	returnMessage = dateFormatter.format(now) +"\n";
	                    	
	                    }
	                   //gets uptime if client types 2
	                    if (numberInIntFormat == 2){
	                    RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		                
		        		long upTime = runtimeBean.getUptime();
		                returnMessage = upTime +"ms\n";
	                   }
	                   if (numberInIntFormat == 3)
	                   {
	                	   //long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
	                	   //long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
	                	   //long actualMemUsed=afterUsedMem-beforeUsedMem;
	                	   long freeMem =  Runtime.getRuntime().freeMemory();
			               returnMessage = freeMem +" bytes\n";
		                }
	                   if (numberInIntFormat == 4)
	                   {
	                	   Process process = Runtime.getRuntime().exec("cmd /c netstat");
	              		 
	              		 String s;
	              		 String netstat="";
	              		 System.out.println(process.getOutputStream());
	              		 BufferedReader stdInput = new BufferedReader(
	              				 new InputStreamReader(process.getInputStream()));
	              		 while ((s=stdInput.readLine())!=null){
	              			 netstat += s +"\n";
	              		 }
	              		 
	                	   returnMessage = netstat + "\n";
	                   }// end if 4
	                   if (numberInIntFormat == 5)
	                   {
	                	   Process process = Runtime.getRuntime().exec("cmd /c net user");
	              		 
	              		 String s;
	              		 String netusers="";
	              		 System.out.println(process.getOutputStream());
	              		 BufferedReader stdInput = new BufferedReader(
	              				 new InputStreamReader(process.getInputStream()));
	              		 while ((s=stdInput.readLine())!=null){
	              			 netusers += s +"\n";
	              		 }
	              		 
	                	   returnMessage = netusers + "\n";
	                   }// end if 5
	                   if (numberInIntFormat == 6)
	                   {
	                	   Process process = Runtime.getRuntime().exec("cmd /c tasklist");
	              		 
	              		 String s;
	              		 String tasklist="";
	              		 System.out.println(process.getOutputStream());
	              		 BufferedReader stdInput = new BufferedReader(
	              				 new InputStreamReader(process.getInputStream()));
	              		 while ((s=stdInput.readLine())!=null){
	              			 tasklist += s +"\n";
	              		 }
	              		 
	                	   returnMessage = tasklist + "\n";
	                   }// end if 6
	                }
	                catch(NumberFormatException e)
	                {
	                    //Input was not a number. Sending proper message back to client.
	                    returnMessage = "Please send a proper number\n";
	                }
	             
	               
	                //Sending the response back to the client.
	                
	                OutputStream os = socket.getOutputStream();
	                OutputStreamWriter osw = new OutputStreamWriter(os);
	                BufferedWriter bw = new BufferedWriter(osw);
	                bw.write(returnMessage);
	               
	                
	                System.out.println("Message sent to the client is "+returnMessage);
	                bw.flush();
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            try
	            {
	                socket.close();
	            }
	            catch(Exception e){}
	        }
}
}

