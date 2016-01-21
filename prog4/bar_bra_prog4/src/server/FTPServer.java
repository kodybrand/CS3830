package server;
import java.net.*;
/**
 * This class will handle each incoming request and each request will take 
 * place inside a separate thread of execution. 
 * @author Trevor Bartel
 */
public class FTPServer
{
   final int CTRL_PORT = 5721;
   
   /**
    * This is the main method of the FTPServer class. It will create a new 
    * FTPServer object and call the run() method on it.
    * @param argv are arguments if the program needs it. 
    */
   public static void main(String argv[])
   {
      try
      {
         System.out.println("FTP Server running...");
         FTPServer server = new FTPServer();
         server.run();
      }
      catch (Exception ex)
      {
         System.out.println("FTPServer Exception: " + ex);
      }
   }
   
   /**
    * This function will create a new FTPThread and accept new connections.
    */
   public void run()
   {
      try
      {
         ServerSocket servSock = new ServerSocket(CTRL_PORT);
         while(true)
         {
            Socket sock = servSock.accept();
            FTPThread ftpthread = new FTPThread(sock);
            System.out.println("Got a connection: " 
               + sock.getInetAddress() + " on port# " + sock.getLocalPort() 
               + " remote port# " + sock.getPort());
            ftpthread.start();
         }
      }
      catch (Exception ex)
      {
         System.out.println("FTPServer Exception: " + ex);
      }
   }
   
}
