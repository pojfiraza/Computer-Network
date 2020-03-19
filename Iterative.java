import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.Process;
import java.lang.Runtime;
import java.net.*;
import java.io.*;

public class Iterative{
   public static void main(String[] argc) throws IOException{
      int portN;    
      if(argc.length != 1){
         System.err.println("Usage: Java Server<Port Number>");
         System.exit(1);
      }
      portN = Integer.parseInt(argc[0]);
      try{
         ServerSocket server = new ServerSocket(portN,100);
         System.out.println(" Type Ctrl+C to exit...");
         while(true){
            try(
               Socket client = server.accept();
               PrintWriter outz = new PrintWriter(client.getOutputStream(),true);
               BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            ){
               System.out.println("User Connection Successful: "  + client.getInetAddress());
               String com = in.readLine();
               int menu = com.charAt(0) - 48;
               switch(menu){
                  case 1:
                     DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                     Calendar cal = Calendar.getInstance();
                     String time = date.format(cal.getTime());
                     outz.println(time);
                     System.out.println("Date/Time has been processed.");
                     break;
                  case 2:
                     Process uptime = Runtime.getRuntime().exec("uptime");
                     BufferedReader obama = new BufferedReader(new InputStreamReader(uptime.getInputStream()));
                     String uptimeS = obama.readLine();
                     outz.println(uptimeS);
                     System.out.println("Uptime has been processed.");
                     break;
                  case 3:
                     Runtime mem = Runtime.getRuntime();
                     outz.println("Total Memory: " + mem.maxMemory()/1000000.0 + " MB");
                     outz.println("Free Memory: " + mem.freeMemory()/1000000.0 + " MB");
                     outz.println("Used Memory: " + ((mem.maxMemory()/1000000.0) -(mem.maxMemory()/1000000.0))+ " MB");
                     System.out.println("Memory has been processed");
                     break;
                  case 4:
                     Process netStat = Runtime.getRuntime().exec("netstat -a:");
                     BufferedReader netR = new BufferedReader(new InputStreamReader(netStat.getInputStream()));
                     String netS;
                     while ((netS = netR.readLine()) != null)outz.println(netS);
                     System.out.println("NetStat has been processed.");
                     break;
                  case 5:
                     Process userP = Runtime.getRuntime().exec("who");
                     BufferedReader userR = new BufferedReader(new InputStreamReader(userP.getInputStream()));
                     String userS;
                     while((userS = userR.readLine()) != null)outz.println(userS);
                     System.out.println("Current USers has been processed");
                     break;
                  case 6:
                     Process runP = Runtime.getRuntime().exec("ps -aux");
                     BufferedReader hillary = new BufferedReader(new InputStreamReader(runP.getInputStream()));
                     String w;
                     while( (w= hillary.readLine()) != null)outz.println(w);
                     System.out.println("Running Processed has been processed");
                     break;
                  case 7:break;       
               }
               System.out.println("Closing connections..." + client.getInetAddress());
               client.close();
               in.close();
               outz.close();
         }catch(IOException e){
            System.out.println("IOException Error during connection...");
            System.out.println(e.getMessage());
         }
         System.out.println("Waiting for next connection...");
      }}catch(IOException e){
         System.out.println("IOException Error while listening on port#" + portN);
         System.out.println(e.getMessage());
      }
   }
}

  