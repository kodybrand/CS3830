
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 This class is the server. It handles incoming requests and also 
 logs connections.
 @author Kody
 */
public class Server
{

   private final static int PORT = 5764;
   private final Logger log = new Logger();
   SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:m:s a z");
   String date = sdf.format(new Date());

   /**
   This method starts creates a ServerThread for each client and logs it.
   */
   public void run()
   {
      try
      {
         ServerSocket servSock = new ServerSocket(PORT);
         while (true)
         {
            Socket sock = servSock.accept();
            log.print("\t" + date + " | Connected from " 
                  + sock.getInetAddress()
                  + " Port: " + sock.getPort());
            ServerThread servThread = new ServerThread(sock, log);
            servThread.start();
         }
      }
      catch (ConnectException e)
      {
         log.print("\tERROR : " + e);
      }
      catch (Exception e)
      {
         log.print("\tERROR : " + e);
      }

   }
}
