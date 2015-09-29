
/**
 *
 * @author Kody
 */
public class PolyAlphabet {

    private final int[] offsetArray = {5, 19, 19, 5, 19};
    private int location = 0;

    public String encode(String enc) {
        StringBuilder encoded = new StringBuilder();
        location = 0;
        for (char i : enc.toCharArray()) {
            if (Character.isLetter(i)) {
                if (Character.isUpperCase(i)) {
                    encoded.append((char) ('A' + (i - 'A' + getOffset()) % 26));
                } else {
                    encoded.append((char) ('a' + (i - 'a' + getOffset()) % 26));
                }
            } else {
                encoded.append(i);
            }
        }
        location = 0;
        return encoded.toString();
    }

    private int getOffset() 
    {
        int key = offsetArray[location++];
            if(location == offsetArray.length )
            {
                location = 0;
            }
        System.out.println("Key Set : " + key);
        return key;
    }
}
