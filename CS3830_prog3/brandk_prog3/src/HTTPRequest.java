
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 
 @author Kody
 */
public class HTTPRequest 
{

   private Socket sock;
   private PrintWriter writeSock;
   private BufferedReader readSock;
   
   
   public HTTPRequest() {
      
   }
   
   public HTTPRequest(Socket sock) {
      this.sock = sock;
      try {
         writeSock = new PrintWriter(sock.getOutputStream(), true);
         readSock = new BufferedReader(new InputStreamReader(
               sock.getInputStream()));
      } catch (Exception e) {
         System.out.println("Error : " + e);
      }
   }
   
   public void start() {
      
   }

}
