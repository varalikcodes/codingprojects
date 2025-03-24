/*************************************************************************
 *  Compilation:  javac RunLengthEncoding.java
 *  Execution:    java RunLengthEncoding
 *
 *  @author:
 *
 *************************************************************************/

public class RunLengthEncoding {

    /* 
     * Encode the original string by finding sequences in the string
     * where the same character repeats. Replace each such sequence
     * by a token consisting of: the number of characters in the sequence
     * followed by the repeating character.
     * Return the encoded string.
     */
  public static String encode(String input) {
        if (input.isEmpty()) {
            return "";
        }

        char currentChar = input.charAt(0);
        int count = 1;
        int i = 1;
        while (i < input.length() && input.charAt(i) == currentChar) {
            count++;
            i++;
        }

        return buildToken(count, currentChar) + encode(input.substring(i));
    }

    private static String buildToken(int count, char character) {
        if (count == 1) {
            return Character.toString(character);
        }
        return Integer.toString(count) + character;
    }

    public static String decode(String input) {
        if (input.isEmpty()) {
            return "";
        }

        char currentChar = input.charAt(0);

        if (Character.isDigit(currentChar)) {
            int count = currentChar - '0';
            return buildDecodedToken(input.charAt(1), count) + decode(input.substring(2));
        } else {
            return currentChar + decode(input.substring(1));
        }
    }

    private static String buildDecodedToken(char character, int count) {
        if (count == 0) {
            return "";
        }
        return character + buildDecodedToken(character, count - 1);
    }
    public static void main(String[] args) {
        String input =  "3a";
        String encodedString = encode(input);
        System.out.println("Encoded string: " + encodedString);

        String decodedString = decode(encodedString);
        System.out.println("Decoded string: " + decodedString);
    }
}

/* Test cases
Test 14: encode: "3a" decode: "aaa" 
test 16: encode:"5b2c" decode: "bbbbbcc" 
test 17: encode:"3x4y" decode: "xxxyyyy" 
test 18: encode:"2p3q3r2s" decode: "ppqqqrrrss" 
test 19: encode:"2m3n1o2p" decode: "mmnnnoppp" fail: "mmnnnopp"
test 20: encode:"3a2b4c1d2e3f"; decode: "aaabbccccdefff" fail: aaabbccccdeefff
test 21: encode: "1x3y2z4a" decode: "xyyyzzaaa" fail: xyyyzzaaaa
test 22: encode:"2u3v4w2x" decode: "uuvvvwwwwxx" 
*/

