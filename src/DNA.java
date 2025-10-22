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

    // The radix would usually be 256, but we can make it 4 for this because the whole alphabet is A, C, T, G.
    public static int RADIX = 4;

    public static int STRCount(String sequence, String STR) {
        // Horner's method

        return 0;
    }

    // The hash function takes in a string and converts it to a unique number through Horner's method.
    public static long hash(String STR, int length, int index) {
        long hash = 0;
        for (int i = index; i < index + length; i++) {
            hash = RADIX * hash + STR.charAt(i);
        }

        return hash;
    }

    // The slide function takes a previous string's hash and uses the Rabin-Karp fingerprinting algorithm to slide it.
    // Sliding means that you are shifting the current window over to look at the next part of the sequence.
    public static long slide(long oldHash, String STR, int length, int index, long nextPower) {
        long newHash;
        // This subtracts the first term (the letter being removed), multiplies by the radix, and adds the new term.
        newHash = (oldHash - STR.charAt(index) * nextPower) * RADIX + STR.charAt(index + length);
        return newHash;
    }


}
