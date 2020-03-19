import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.Process;
import java.lang.Runtime;
import java.net.*;
import java.io.*;

public class Concurrent{
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
         while(true) new SThread(server.accept());
      }
      catch(IOException e){
         System.out.println("IOException Error while listening on port#" + portN);
         System.out.println(e.getMessage());
      }
   }
}
class SThread implements Runnable{
   PrintWriter out;
   BufferedReader in;
   Socket user;
   String clientAddress;
   Process command;
   BufferedReader cReader;
   String response;
   int choice;
   SThread(Socket user){
      try {
         this.user = user;
         this.out = new PrintWriter(user.getOutputStream(), true);
         this.in = new BufferedReader(new InputStreamReader(user.getInputStream()));
         this.clientAddress = user.getInetAddress().toString();
         System.out.println("User Connection Successful: "+ this.clientAddress);
         (new Thread(this)).start();
      } catch (IOException e) {
         System.out.println("IOException Error during connection...");
         System.out.println(e.getMessage());
      }
   }
   public void run(){
      try{
         choice = Integer.parseInt(in.readLine());
         switch (choice){
            case 1:
               this.command = Runtime.getRuntime().exec("date");
               break;
            case 2:
               this.command = Runtime.getRuntime().exec("uptime");
               break;
            case 3:
               this.command = Runtime.getRuntime().exec("free");
               break;
            case 4:
               this.command = Runtime.getRuntime().exec("netstat");
               break;
            case 5:
               this.command = Runtime.getRuntime().exec("who");
               break;
            case 6:
               this.command = Runtime.getRuntime().exec("ps -aux");
               break;
            default:
               System.out.println("Invalid Request");
               break;       
         }
         
         // Send data to client
         this.cReader = new BufferedReader(new InputStreamReader(command.getInputStream()));
         while ((response = cReader.readLine()) != null)out.println(choice);
         // Close I/O and socket
         System.out.println("User's Connection closed.. " + this.clientAddress);   
         this.in.close();
         this.out.close();
         this.user.close();
      }catch (IOException e){
         System.err.println("I/O error with the connection");
         System.exit(1);
      }   
   }
}


  