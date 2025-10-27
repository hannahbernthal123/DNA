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
    static long slidePower;


    public static int STRCount(String sequence, String STR) {
        // When the length of the STR is greater than
        if (STR.length() > 31) {
            System.out.println("Usage error, STR is too long.");
            return 0;
        }

        int maxReps = 0;
        slidePower = findPower(STR.length() - 1);
        long STRhash = hash(STR);
        long seqHash;
        int numMatches = 0;
        boolean tricky = false;

        for (int i = 0; i < sequence.length() - STR.length(); i++) {
            seqHash = hash(sequence, STR.length(), i);
            if (STRhash == seqHash) {
                numMatches = findMatches(sequence, STR, STRhash, seqHash, i);
                maxReps = Math.max(numMatches, maxReps);
                // i updates to be ahead of the word, but you subtract one because the loop increments itself
                i += (numMatches * STR.length()) - 1;
                if (i != sequence.length() - 1 && STR.charAt(0) != sequence.charAt(i + 1)) {
                    i -= numMatches * STR.length() - 2;
                }
                // Continue because you are skipping ahead in the loop.
                continue;
            }

//            if (i + 1 <= sequence.length() - STR.length()) {
//                seqHash = slide(seqHash, sequence, STR.length(), i);
//            }

        }

        return maxReps;
    }



    public static int findMatches(String sequence, String STR, long STRhash, long seqHash, int index) {
        int count = 0;
        int current = index;
        while (STRhash == seqHash) {
            count++;

            // Note that you must do double the STR length because current is the start of the first occurrence.
            if (current + (2 * STR.length()) <= sequence.length()) {
                current += STR.length();
                seqHash = hash(sequence, STR.length(), current);
            }
            else {
                return count;
            }
        }
        return count;
    }

    // Just made a match, next chunk is not a match, now you need to backup and check the middle of the previous chunk
    // backup to first instance of first character in STR or second character in chunk

    // The hash function takes in a string and converts it to a unique number through Horner's method.
    public static long hash(String str, int length, int index) {
        long hash = 0;
        for (int i = index; i < index + length; i++) {
            hash = RADIX * hash + str.charAt(i);
        }

        return hash;
    }


    public static long hash(String str) {
        return hash(str, str.length(), 0);
    }

    // The slide function takes a previous string's hash and uses the Rabin-Karp fingerprinting algorithm to slide it.
    // Sliding means that you are shifting the current window over to look at the next part of the sequence.
    public static long slide(long oldHash, String seq, int length, int index) {
        // This subtracts the first term (the letter being removed), multiplies by the radix, and adds the new term.
        return (oldHash - seq.charAt(index) * slidePower) * RADIX + seq.charAt(index + length);
    }

    public static long findPower(int power) {
        int count = 1;
        for (int i = 0; i < power; i++) {
            count = count * RADIX;
        }

        return count;
    }


}
