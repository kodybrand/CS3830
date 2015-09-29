
/**

 @author Kody
 */
public class PolyAlphabet
{

   private int [] cyperArray = {5,19,19,1,19};
   
   public PolyAlphabet()
   {
   }

   public String encode(String enc, int offset)
   {
      int k = 0;
      offset = offset % 26 + 26;
      StringBuilder encoded = new StringBuilder();
      for (char i : enc.toCharArray())
      {
         if (Character.isLetter(i))
         {
            if (Character.isUpperCase(i))
            {
               encoded.append((char) ('A' + (i - 'A' + offset) % 26));
            }
            else
            {
               encoded.append((char) ('a' + (i - 'a' + offset) % 26));
            }
         }
         else
         {
            encoded.append(i);
         }
      }
      return encoded.toString();
   }
}
