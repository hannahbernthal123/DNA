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

        // maxReps is the maximum number of times that the STR occurs in sequence.
        int maxReps = 0;

        // Calculates the R^(m - 1) power that is used to remove the first term when sliding.
        slidePower = findPower(STR.length() - 1);

        long STRhash = hash(STR);
        long seqHash;

        // Tracks the current number of STRs in sequence, later to be compared to our max.
        int numMatches = 0;

        // Loops through the whole sequence but stops when it gets to the point no more STRs could fit.
        for (int i = 0; i < sequence.length() - STR.length(); i++) {

            // Takes the hash of the
            seqHash = hash(sequence, STR.length(), i);
            if (STRhash == seqHash) {
                numMatches = findMatches(sequence, STR, STRhash, seqHash, i);
                maxReps = Math.max(numMatches, maxReps);
                // i updates to be ahead of the word, but you subtract one because the loop increments itself
                i += (numMatches * STR.length()) - 1;

                // Check if the next character could start a new STR match but doesn't.
                if (i != sequence.length() - 1 && STR.charAt(0) != sequence.charAt(i + 1)) {
                    // Backtrack to check overlapping possibilities starting at the second index of the prev STR.
                    i -= numMatches * STR.length() - 2;
                }
                // Continue because you are skipping ahead in the loop.
                continue;
            }

            // Slide the hash window forward by one position for the next iteration.
            if (i + 1 <= sequence.length() - STR.length()) {
                seqHash = slide(seqHash, sequence, STR.length(), i);
            }

        }

        return maxReps;
    }


    // Counts how many consecutive STRs occur from a given starting index.
    public static int findMatches(String sequence, String STR, long STRhash, long seqHash, int index) {
        int count = 0;

        // Temporary variable that can update to represent the index.
        int current = index;

        // Keep checking consecutive STRs as long as hashes match.
        while (STRhash == seqHash) {
            count++;

            // Note that you must do the STR length DOUBLED because current is the start of the first occurrence.
            // So you must check for space for another occurrence after.
            if (current + (2 * STR.length()) <= sequence.length()) {
                // If there is space for another STR, increment current and find the new hash there.
                current += STR.length();
                seqHash = hash(sequence, STR.length(), current);
            }

            // If there isn't space, return the current number of consecutive matches.
            else {
                return count;
            }
        }

        // Return the total count of consecutive matches found.
        return count;
    }

    // The hash function takes in a string and converts it to a unique number through Horner's method.
    public static long hash(String str, int length, int index) {
        long hash = 0;
        for (int i = index; i < index + length; i++) {
            hash = RADIX * hash + str.charAt(i);
        }

        return hash;
    }

    // Overwritten hash function for the STR hash.
    public static long hash(String str) {
        return hash(str, str.length(), 0);
    }

    // The slide function takes a previous string's hash and uses the Rabin-Karp fingerprinting algorithm to slide it.
    // Sliding means that you are shifting the current window over to look at the next part of the sequence.
    public static long slide(long oldHash, String seq, int length, int index) {
        // This subtracts the first term (the letter being removed), multiplies by the radix, and adds the new term.
        return (oldHash - seq.charAt(index) * slidePower) * RADIX + seq.charAt(index + length);
    }

    // Same as the POW function just more efficient.
    public static long findPower(int power) {
        int count = 1;
        for (int i = 0; i < power; i++) {
            count = count * RADIX;
        }

        return count;
    }


}
