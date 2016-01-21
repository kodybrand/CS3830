package Server;
import java.io.*; 
import java.net.*;
import java.util.*;

/**
 * This class implements a server for UDP service. It sits in an infinite loop
 * listening for incoming UDP packets. When a packet comes in, the Server simply 
 * retrieves the payload, encapsulates the payload in a UDP packet, and sends
 * the packet to the Client.
 * @author Trevor Bartel and Kody Brand
 */
public class PingServer 
{
   final int PACKET_SIZE = 512;
   final int PORT_NUMBER = 5701;
   final double LOSS_RATE = 0.3;
   final int AVG_DELAY = 100;
   final int DOUBLE = 2;
   
   /**
    * This is the main method of the PingServer class. It will create a new 
    * PingServer object and call the run() method on it.
    * @param args are the arguments if the program needs it. 
    */
   public static void main(String args[]) 
   {
      try
      {
         System.out.println("Ping Server running....");
         PingServer server = new PingServer();
         server.run();
      }
      catch (Exception ex)
      {
         System.out.println("PingServer Exception: " + ex);
      }
   }
   
   /**
    * This function will create a new PingThread and accept new connections.
    */
   public void run()
   {
      try
      {
         Random random = new Random();
         DatagramSocket udpSocket = new DatagramSocket(PORT_NUMBER);
         byte[] buff = new byte[PACKET_SIZE];
         while (true)
         {
            System.out.println("Waiting for UDP packet....");
            DatagramPacket inpacket = new DatagramPacket(buff, PACKET_SIZE);
            udpSocket.receive(inpacket);
            printData(inpacket);
            if(random.nextDouble() < LOSS_RATE)
               System.out.println("Packet loss...., packet not sent.");
            else
            {
               Thread.sleep((int)(random.nextDouble() * DOUBLE * AVG_DELAY));
               InetAddress clientHost = inpacket.getAddress();
               int clientPort = inpacket.getPort();
               byte[] buf = inpacket.getData();
               DatagramPacket reply = 
                  new DatagramPacket(buf, buf.length, clientHost, clientPort);
               udpSocket.send(reply);
               System.out.println("Reply sent.");
            }
         }
      }
      catch (Exception ex)
      {
         System.out.println("PingServer Exception: " + ex);
      }
   }
      
   /**
    * This function prints the ping data to the standard output stream.
    * @param request is the datagram packet from the client. 
    */
   private void printData(DatagramPacket request)
   {
      try
      {
         byte[] data = request.getData();
         ByteArrayInputStream bais = new ByteArrayInputStream(data);
         InputStreamReader isr = new InputStreamReader(bais);
         BufferedReader br = new BufferedReader(isr);
         String line = br.readLine();
         System.out.println("Received from: " + 
            request.getAddress().getHostAddress() + " " + new String(line));
      }
      catch (Exception ex)
      {
         System.out.println("PingServer Exception: " + ex);
      }  
   }
}
