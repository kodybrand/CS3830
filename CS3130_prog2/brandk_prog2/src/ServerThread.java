
import java.io.*;
import java.net.*;

/**
 This class handles the requests for a particular client.

 @author Kody
 */
public class ServerThread extends Thread
{

   private PrintWriter writeSock;
   private BufferedReader readSock;
   private Socket sock;
   private Logger log;

   /**
    Contructor for this class

    @param sock Socket to listen to
    @param log Log to print logs to
    */
   public ServerThread(Socket sock, Logger log)
   {
      this.log = log;
      this.sock = sock;
      try
      {
         writeSock = new PrintWriter(sock.getOutputStream(), true);
         readSock = new BufferedReader(new InputStreamReader(
               sock.getInputStream()));
      }
      catch (Exception e)
      {
         log.print("ERROR : " + e.getClass().toString() + e);
      }
   }

   /**
    This method accepts incoming stream and returns encoded messages.
    */
   @Override
   public void run()
   {
      try
      {
         PolyAlphabet pa = new PolyAlphabet();
         boolean quitTime = false;
         while (!quitTime)
         {
            if (!sock.isClosed())
            {
               String inLine = readSock.readLine();
               if (inLine.equalsIgnoreCase("quit"))
               {
                  quitTime = true;
               }
               else
               {
                  String outLine = pa.encode(inLine);
                  writeSock.println(outLine);
               }
            }
            else
            {
               quitTime = true;
            }
         }
         writeSock.println("Good Bye!");
         sock.close();
         log.print("\tConnection Closed. Port " + sock.getPort());
      }
      catch (Exception e)
      {
         log.print("\tConnection Closed. Port " + sock.getPort());
      }

   }
}
