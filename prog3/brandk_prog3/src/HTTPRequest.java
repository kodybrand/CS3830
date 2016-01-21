
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 This handles the communication of the interaction.

 @author Kody
 */
public class HTTPRequest extends Thread
{

   private final static String CRLF = "\r\n";
   private final static String VERSION = "HTTP/1.0 ";

   private Socket sock;
   private DataOutputStream writeSock;
   private BufferedReader readSock;
   private HTTP http;

   /**
    Contructor that sets up the reader and writer

    @param sock Assigned Socket
    */
   public HTTPRequest(Socket sock)
   {
      this.sock = sock;
      try
      {
         writeSock = new DataOutputStream(sock.getOutputStream());
         readSock = new BufferedReader(new InputStreamReader(
               sock.getInputStream()));
      }
      catch (Exception e)
      {
         System.out.println("HTTPRequest Exception : " + e);
      }
   }

   /**
    Method that reads the request and them responds to it.
    */
   @Override
   public void run()
   {
      try
      {
         String input = readSock.readLine();
         http = new HTTP(input);
         writeSock.writeBytes(buildStatus()); //Header
         writeSock.writeBytes(buildHeader()); //Status
         writeSock.writeBytes(CRLF); //Blank Line
         sendBody(); //Body
         sock.close();
      }
      catch (Exception e)
      {
         System.out.println("HTTPRequest Exception : " + e);
      }

   }

   /**
    Builds the Status String

    @return
    */
   private String buildStatus()
   {
      String response = VERSION;
      response = response + getStatus() + CRLF;

      return response;
   }

   /**
    Builds the Status String

    @return built status string
    */
   private String getStatus()
   {
      if (http.isFileValid())
      {
         return "200 OK ";
      }
      else
      {
         return "404 Not Found ";
      }
   }

   /**
    Builds the Header String

    @return built header string
    */
   private String buildHeader()
   {
      return "Content-type: " + http.getContentType() + CRLF;
   }

   /**
    Sends the content of the response
    */
   private void sendBody()
   {
      http.sendBody(sock);
   }
}
