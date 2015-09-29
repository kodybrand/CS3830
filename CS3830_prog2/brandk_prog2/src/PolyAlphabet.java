
/**
 This class handles the encryption.

 @author Kody
 */
public class PolyAlphabet
{

   private final int[] offsetArray =
   {
      5, 19, 19, 5, 19
   };
   private int location = 0;

   /**
    This method encodes the string using the offsetArray set above.

    @param enc message to be encoded
    @return encoded message
    */
   public String encode(String enc)
   {
      StringBuilder encoded = new StringBuilder();
      location = 0;
      for (char i : enc.toCharArray())
      {
         if (Character.isLetter(i))
         {
            if (Character.isUpperCase(i))
            {
               encoded.append((char) ('A' + (i - 'A' + getOffset()) % 26));
            }
            else
            {
               encoded.append((char) ('a' + (i - 'a' + getOffset()) % 26));
            }
         }
         else
         {
            encoded.append(i);
         }
      }
      location = 0;
      return encoded.toString();
   }

   /**
    Handles the offset settings

    @return correct offset for encoding
    */
   private int getOffset()
   {
      int key = offsetArray[location++];
      if (location == offsetArray.length)
      {
         location = 0;
      }
      return key;
   }
}
