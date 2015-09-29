
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 This class handles all of the logging for the program
 @author Kody
 */
public class Logger
{

   private static final String FILE_NAME = "serverLog.log";
   
   /**
   Default Constuctor
   */
   public Logger()
   {
      print("\n\nLogger has been started " );
   }

   /**
   This method prints the message to the log file
   @param message text to be logged.
   */
   public void print(String message)
   {
      try (PrintWriter out = new PrintWriter(new BufferedWriter(
                           new FileWriter(FILE_NAME, true))))
      {
         out.println(message);
      }
      catch (Exception e)
      {
         System.out.println("Error : " + e);
      }
   }
}
