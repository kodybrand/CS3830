package server;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Random;

/**
 This class extents Thread class and handles the Client socket I/O. It also
 must handle the request and response messages from/to the Client.
 @author Trevor Bartel
 */
class FTPThread extends Thread
{
   Socket sock;
   Socket dataSock;
   PrintWriter writeSock;
   BufferedReader readSock;
   String allFiles = "";
   int totalBytes = 0;
   int DataPort;
   final int BYTES = 1024;
   final int MIN_CON = 5722;
   final int MAX_CON = 5780;

   /**
    This is the constructor for the FTPThread class.
    @param socket is the socket from the client.
    */
   public FTPThread(Socket socket)
   {
      try
      {
         sock = socket;
         writeSock = new PrintWriter(sock.getOutputStream(), true);
         readSock = new BufferedReader
            (new InputStreamReader(sock.getInputStream()));
         Random r = new Random();
         DataPort = r.nextInt(MAX_CON - MIN_CON) + MIN_CON;
      }
      catch (Exception ex)
      {
         System.out.println("FTPThread Exception: " + ex);
      }
   }

   /**
    This method maintains the connection between the server and the client.
    */
   public void run()
   {
      try
      {
         listFiles();
         while (true)
         {
            String stringLine = readSock.readLine();
            if (stringLine != null)
            {
               StringTokenizer st = new StringTokenizer(stringLine);
               String temp = st.nextToken();
               if (temp.equals("GET"))
               {
                  String sendRequest = st.nextToken();
                  ServerSocket servSock = new ServerSocket(DataPort);
                  writeSock.println(DataPort);
                  DataPort++;
                  dataSock = servSock.accept();
                  sendFile(sendRequest);
                  dataSock.close();
                  listFiles();
               } 
               else if(temp.equals("PUT")) 
               {
                  String recRequest = st.nextToken();
                  ServerSocket servSock = new ServerSocket(DataPort);
                  writeSock.println(DataPort);
                  DataPort++;
                  dataSock = servSock.accept();
                  getFile(recRequest);
                  dataSock.close();
                  listFiles();
               } 
            }
         }
      }
      catch (Exception ex)
      {
         System.out.println("FTPThread Exception: " + ex);
      }
   }

   /**
    This function will retrieve all file names (excluding folders) from the
    folder Files and send a list of file names with a single String instance
    to the Client socket.
    */
   private void listFiles()
   {
      try
      {
         File dir = new File("Files");
         File[] files = dir.listFiles();
         for (int i = 0; i < files.length; i++)
         {
            if (files[i].isFile())
            {
               allFiles += files[i].getName() + ",";
            }
         }
         writeSock.println(allFiles);
         allFiles = "";
      }
      catch (Exception ex)
      {
         System.out.println("FTPThread Exception: " + ex);
      }
   }

   /**
    This function will send the file through the data connection.
    @param fileName is the file being sent to the Client.
    */
   private void sendFile(String fileName)
   {
      try
      {
         System.out.println("Data connection local port# "
            + dataSock.getLocalPort() + " remote port# " + dataSock.getPort());
         System.out.println("Sending the file...");
         System.out.println("File: " + fileName);
         DataOutputStream ds = new DataOutputStream(dataSock.getOutputStream());
         FileInputStream fis = new FileInputStream("Files\\" + fileName);
         byte[] buffer = new byte[BYTES];
         while (fis.read(buffer) > 0)
         {
            totalBytes += buffer.length;
            ds.write(buffer);
         }
         System.out.println(totalBytes + " bytes sent.");
         totalBytes = 0;
         ds.close();
         fis.close();
         System.out.println("Connection closed.");
      }
      catch (Exception ex)
      {
         System.out.println("FTPThread Exception: " + ex);
      }
   }

   /**
    This function will receive the file through the data connection.
    @param filename is the file being received from the client.
    */
   private void getFile(String fileName)
   {
      try
      {
         System.out.println("Data connection local port# "
            + dataSock.getLocalPort() + " remote port# " + dataSock.getPort());
         System.out.println("Receiving the file...");
         DataInputStream dis = new DataInputStream(dataSock.getInputStream());
         FileOutputStream fos = new FileOutputStream("Files\\" + fileName);
         byte[] buffer = new byte[BYTES];
         while (dis.read(buffer) > 0)
         {
            totalBytes += buffer.length;
            fos.write(buffer);
         }
         System.out.println("Got the file: " + fileName);
         System.out.println("Size: " + totalBytes + " Bytes.");
         totalBytes = 0;
         fos.close();
         dis.close();
         System.out.println("Connection closed.");
      }
      catch (Exception ex)
      {
         System.out.println("FTPThread Exception: " + ex);
      }
   }
}
