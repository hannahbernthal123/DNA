/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Hannah Bernthal
 *</p>
 */

public class DNA {

    public static int STRCount(String sequence, String STR) {
        int maxCount = 0;

        for (int i = 0; i < sequence.length(); i++) {
            int count = 0;
            if (STR.charAt(0) == sequence.charAt(i)) {
                while (i + STR.length() <= sequence.length() && sequence.substring(i, i + STR.length()).equals(STR)) {
                    count ++;
                    i += STR.length();
                }
            }

            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }
}
