
/**
 This class starts up the Web server.

 @author Kody
 */
public class Main
{

   /**
    Starts up the web server.

    @param args
    */
   public static void main(String[] args)
   {
      try
      {
         WebServer web = new WebServer();
         web.run();
      }
      catch (Exception e)
      {
         System.out.println("Error starting server :" + e);
      }

   }
}
