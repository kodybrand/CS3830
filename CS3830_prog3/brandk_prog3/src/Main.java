
/**

 @author Kody
 */
public class Main
{

   public static void main(String[] args)
   {
      try
      {
         WebServer web = new WebServer();
      }
      catch (Exception e)
      {
         System.out.println("Error starting server :" + e);
      }

   }
}
