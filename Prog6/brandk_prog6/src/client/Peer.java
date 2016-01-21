package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 This is for the Peers. It holds the information for each Peer.
 @author Kody
 */
public class Peer
{

   private InetAddress ipaddress;
   private String username;

   /**
    The payload contains only the username, which has “length” characters
    starting at byte 1 of the payload. (Byte 0 is the message type, you can
    ignore it)
    */
   public Peer(InetAddress peerAddr, byte[] payload, int length) throws UnknownHostException
   {
      ipaddress = peerAddr;
      byte[] name = new byte[length + 1];
      for (int i = 0; i < length; i++)
      {
         name[i] = payload[i + 1];
      }
      username = new String(name);
   }

   /**
    The payload contains IP address and the username. Byte 0 is the message
    type, ignore it. Start at byte 1 is the 4-byte IP address, and start at
    byte 1 + IP_SIZE is the username with a length: (length – 1 – IP_SIZE)
    */
   public Peer(byte[] payload, int length) throws UnknownHostException
   {
      byte[] ipAddress = new byte[Message.IP_SIZE];
      for(int i = 0; i < Message.IP_SIZE; i++) {
         ipAddress[i] = payload[i + 1];
      }
      ipaddress = InetAddress.getByAddress(ipAddress);
      
      byte[] name = new byte[length - 1 - Message.IP_SIZE];
      for (int i = 0; i < name.length; i++)
      {
         name[i] = payload[Message.IP_SIZE + 1 + i];
      }
      username = new String(name);
   }

   /**
   Returns the Username
   @return username
   */
   public String getName()
   {
      return username;
   }

   /**
   Returns the ip address
   @return ipaddress
   */
   public InetAddress getAddr()
   {
      return ipaddress;
   } 
   /**
   Returns the username and ip address
   @return username:ipaddress
   */
   public String toString()
   {
      return username + ":" + ipaddress.getHostAddress();
   }
}
