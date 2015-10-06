
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;

/**

 @author Kody
 */
public class HTTP
{

   private String method;
   private String url;
   private String version;

   private boolean validMethod;
   private boolean validFile;
   private boolean validVersion;

   public HTTP(String request)
   {
      System.out.println("HERE! HTTP");
      parseRequest(request);
      System.out.println("request info ::" + request);
   }

   public boolean isValid()
   {
      if (!validRequest())
      {
         return false;
      }
      if (!validUrl())
      {
         return false;
      }
      return validVersion();
   }

   private void parseRequest(String request)
   {
      StringTokenizer st = new StringTokenizer(request);
      if (st.hasMoreTokens())
      {
         this.method = st.nextToken();
      }
      if (st.hasMoreTokens())
      {
         this.url = "." + st.nextToken();
      }
      if (st.hasMoreTokens())
      {
         this.version = st.nextToken();
      }
   }

   private boolean validRequest()
   {
      return method.equalsIgnoreCase("GET");
   }

   private boolean validUrl()
   {
      try
      {
         FileInputStream fi = new FileInputStream(url);
      }
      catch (FileNotFoundException e)
      {
         return false;
      }
      return true;
   }

   private boolean validVersion()
   {
      return version.equalsIgnoreCase("HTTP/1.1");
   }

   private boolean validMethod()
   {
      return method.equalsIgnoreCase("GET");
   }

   public String getMethod()
   {
      return method;
   }

   public String getUrl()
   {
      return url;
   }

   public String getVersion()
   {
      return version;
   }

   public boolean isValidMethod()
   {
      return validMethod;
   }

   public boolean isValidFile()
   {
      return validFile;
   }

   public boolean isValidVersion()
   {
      return validVersion;
   }
   
   

}
