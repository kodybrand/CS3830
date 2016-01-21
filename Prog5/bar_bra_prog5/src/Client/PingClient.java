package Client; 
import java.net.*;
import java.util.*;

/**
 * This is the Client Class. This class extends the UDPPinger class and 
 * implements the Java Runnable Interface. It will create and send 10 datagrams
 * to the Server.
 * @author Trevor Bartel and Kody Brand
 */
public class PingClient extends UDPPinger implements Runnable 
{
   final static String remoteHost = "localhost";;
   final static int remotePort = 5701;
   int numReplies = 0;
   boolean[] replies = new boolean[10];
   long[] rtt = new long[10];
   long[] temps = new long[10];
   final int TIMEOUT = 1000;
   final int REPLY_TIMEOUT = 5000;
   final int NUM_PINGS = 10;
   
   
   /**
    * This is the main function of the Client program.
    * @param args are unused.
    */
   public static void main(String args[]) 
   {   
      String host = remoteHost;   
      int port = remotePort;   
      System.out.println("Contacting host " + host + " at port " + port);   
      PingClient client = new PingClient();   
      client.run(); 
    }  
   
   /**
    * Creates a Datagram Socket and sends 10 ping to and from the Server.
    */
   public void run()
   {
      createSocket();
      try
      { 
         transportPing();
         socket.setSoTimeout(REPLY_TIMEOUT);     
         while (numReplies < NUM_PINGS) 
         {   
            try 
            {   
               PingMessage reply = receivePing();
               handleReply(reply.getPayload());   
            } 
            catch (Exception ex) 
            {   
               numReplies = NUM_PINGS;   
            }   
         }     
         for (int i = 0; i < NUM_PINGS; i++) 
         {   
            System.out.println("PING " + i + ": " + replies[i] + " RTT: "   
               + Long.toString(rtt[i]) + " ms");   
         }
         long min = getMinValue(rtt);
         long max = getMaxValue(rtt);
         double avg = average(rtt);
         System.out.println("Minimum = " + min + "ms, Maximum = " + max 
            + "ms, Average = " + avg + "ms.");
      }
      catch (Exception ex)
      {
         System.out.println("PingClient Exception: " + ex);
      }
   }
   
   /**
    * Sends 10 ping to the Server. 
    */
   private void transportPing()
   {
      try
      {
         socket.setSoTimeout(TIMEOUT);
         for (int i = 0; i < NUM_PINGS; i++)
         {
            Date now = new Date();
            Long temp = now.getTime();
            String message = "PING " + i + " " + temp;
            temps[i] = temp;
            replies[i] = false;
            rtt[i] = TIMEOUT;
            PingMessage ping = null;
            ping = new PingMessage(InetAddress.getByName(remoteHost),   
               remotePort, message); 
            sendPing(ping);
            try
            {
               PingMessage reply = receivePing();
               handleReply(reply.getPayload());
            }
            catch (Exception ex)
            {
            }
         }
      }
      catch (Exception ex)
      {
         System.out.println("PingClient Exception: " + ex);
      }
   }
   
   /**
    * Determines the RTT of the ping message.
    * @param reply is the echoing ping from the Server.
    */
   private void handleReply(String reply) 
   {   
      String[] tmp = reply.split(" ");   
      int pingNumber = Integer.parseInt(tmp[1]);    
      replies[pingNumber] = true;     
      Date now = new Date();      
      rtt[pingNumber] = now.getTime() - temps[pingNumber];    
      numReplies++;   
   }
   
   /**
    * Calculates the maximum value of the array.
    * @param array is the array of elements.
    * @return maximum value of the array
    */
   private static long getMaxValue(long[] array)
   {  
      long maxValue = array[0];  
      for(int i = 1; i < array.length; i++)
      {  
         if(array[i] > maxValue)
         {  
            maxValue = array[i];  
         }  
      }  
      return maxValue;  
   }
   
   /**
    * Calculates the minimum value of the array.
    * @param array is the array of elements.
    * @return the minimum value of the array
    */
   private static long getMinValue(long[] array)
   {  
      long minValue = array[0];  
      for(int i = 1; i < array.length; i++)
      {  
         if(array[i] < minValue)
         {  
            minValue = array[i];  
         }  
      }  
      return minValue;  
   }
   
   /**
    * Calculates the average of the array.
    * @param data is the array of elements.
    * @return the average of the array
    */
   private double average(long[] data) 
   {  
      long sum = 0;
      for(int i = 0; i < data.length; i++)
         sum = sum + data[i]; 
      double average = (double)sum / data.length; 
      return average;
   }  
}
