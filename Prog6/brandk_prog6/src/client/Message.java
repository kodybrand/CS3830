package client;

/**
 CS3830 FALL 2015 Program 6
 This class defines shared constants on the message types,  
 the Tracker IP Address and the username for instant messaging. 
 @author  Kody Brand
*/

public class Message 
{
   public static final String TRACKER    = "io.uwplatt.edu"; //Tracker IP address
   public static final String USER_NAME  = "brandk"; //change this to your user name
   public static final int    TRACKER_PORT   = 5764; //Tracker UDP port
   public static final int    IMTHREAD_PORT  = 5702; //Your IMTread UDP port
   public static final int    IP_SIZE    = 4; //IPv4 address size
   public static final byte   PEER_UP    = 1; //type 1: peer going up
   public static final byte   PEER_DOWN  = 2; //type 2: peer going down
   public static final byte   MSG2PEER   = 3; //type 3: message to the peer
   public static final byte   ADD_PEER   = 4; //type 4: peer is up
   public static final byte   DEL_PEER   = 5; //type 5: peer is down
   public static final byte   ACK        = 6; //type 6: ACK in response to type 3
}
