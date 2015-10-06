
import java.net.ServerSocket;
import java.net.Socket;

/**
 
 @author Kody
 */
public class WebServer 
{

   private static final int PORT = 5764;
   
   public WebServer() {
      
   }
   
   public void run() {
      try {
         ServerSocket servSock = new ServerSocket(PORT);
         while(true) {
            System.out.println("HERE! WebServer");
            Socket sock = servSock.accept();
            HTTPRequest request = new HTTPRequest(sock);
            request.start();
            System.out.println("HERE! WebServer");
         }
      } catch (Exception e) {
         System.out.println("Error : " + e );
      }
      
   }
}
