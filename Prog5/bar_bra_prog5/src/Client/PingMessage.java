package Client;
import java.net.*;

/**
 * This class creates a ping message to be sent to the Server.
 * @author Trevor Bartel and Kody Brand
 */
public class PingMessage 
{
   InetAddress IPAddress;
   int portNum;
   String dataPayload;
   
   /**
    * This is a constructor for the PingMessage class.
    * @param addr is the Destination IP Address.
    * @param port is the Destination Port Number.
    * @param payload is the datagram's payload. 
    */
   public PingMessage(InetAddress addr, int port, String payload)
   {
      IPAddress = addr;
      portNum = port;
      dataPayload = payload;
   }
   
   /**
    * Returns the Server's IP address.
    * @return Server's IP address
    */
   public InetAddress getIP()
   {
      return IPAddress;
   }
   
   /**
    * Returns the Server's Port Number;
    * @return Server's Port Number; 
    */
   public int getPort()
   {
      return portNum;
   }
   
   /**
    * Returns the Payload of the Client.
    * @return Payload of the Client 
    */
   public String getPayload()
   {
      return dataPayload;
   }
}
