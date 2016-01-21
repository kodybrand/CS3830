package Client;
import java.net.*;
import java.util.*;

/**
 * This class will send a ping message and receive the echo message
 * from the Server. 
 * @author Trevor Bartel and Kody Brand
 */
public class UDPPinger 
{
   DatagramSocket socket; 
   final int PACKET_SIZE = 512;
   
   /**
    * Instantiates a new Datagram Socket.
    */
   public void createSocket() 
   {   
      try 
      {   
         socket = new DatagramSocket();   
      } 
      catch (Exception ex) 
      {   
         System.out.println("UDPPinger Exception: " + ex);   
      }   
   } 
   
   /**
    * This method sends a UDP packet with an instance of PingMessage.
    * @param ping is an instance of PingMessage.
    */
   public void sendPing(PingMessage ping)
   {
      InetAddress host = ping.getIP();
      int port = ping.getPort();
      String msg = ping.getPayload();
      try
      {
         DatagramPacket packet = 
            new DatagramPacket(msg.getBytes(), msg.length(), host, port);
         socket.send(packet);
      }  
      catch (Exception ex)
      {
         System.out.println("UDPPinger Exception: " + ex);
      }
   }
   
   /**
    * This method receives the UDP packet from the Server. This method
    * may throw SocketTimeoutException.
    * @return the UDP packet from the Server. 
    */
   public PingMessage receivePing()
   {
      Date date = new Date();
      String dateString = String.format("%tc", date);
      byte recvBuf[] = new byte[PACKET_SIZE];
      DatagramPacket recvPacket = 
	 new DatagramPacket(recvBuf, PACKET_SIZE);
      PingMessage reply = null;
      try
      {
         socket.receive(recvPacket);
         System.out.println("Received packet from " + recvPacket.getAddress()
            + " " + recvPacket.getPort() + " " + dateString);
         String recvMsg = new String(recvPacket.getData());
	 reply = new PingMessage(recvPacket.getAddress(),
            recvPacket.getPort(), recvMsg);
      }
      catch (Exception ex)
      {
         System.out.println("receivePing...java.net.SocketTimeoutException: " +
            "Receive timed out");
      }
      return reply;
   } 
}
