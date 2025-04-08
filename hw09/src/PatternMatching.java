import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Riyan Patel
 * @version 1.0
 * @userid rpatel816
 * @GTID 903978548
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * 
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, this homework will receive a zero.
 * 
 * Agree Here: i agree
 */
public class PatternMatching {

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern can't be null or have length 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text and comparator can't be null");
        }
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int patternLength = pattern.length();
        int textLength = text.length();
        int i = 0;
        while (i <= (textLength - patternLength)) {
            int j = patternLength - 1;
            while ((j >= 0) && (comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0)) {
                j--;
            }
            if (j == -1) {
                result.add(i);
                i = i + 1;
            } else {
                char misMatchedChar = text.charAt(i + j);
                int shiftIndex = lastTable.getOrDefault(misMatchedChar, -1);
                int shift = Math.max(1, j - shiftIndex);
                i = i + shift;
            }
        }
        return result;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern can't be null");
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            lastTable.put(c, i);
        }
        return lastTable;
    }

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table 
     * (also called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern can't be null or empty");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator can't be null");
        }

        List<Integer> resultList = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return resultList;
        }

        int[] failureTable = buildFailureTable(pattern, comparator);

        int patternIndex = 0;
        int textIndex = 0;

        while (textIndex + pattern.length() <= text.length()) {
            while (patternIndex < pattern.length()
                    && comparator.compare(pattern.charAt(patternIndex), text.charAt(textIndex + patternIndex)) == 0) {
                patternIndex++;
            }
            if (patternIndex == 0) {
                textIndex++;
            } else {
                if (patternIndex == pattern.length()) {
                    resultList.add(textIndex);
                }
                textIndex += patternIndex - failureTable[patternIndex - 1];
                patternIndex = failureTable[patternIndex - 1];
            }
        }
        return resultList;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("pattern or comaprator can't be null");
        }
        int[] table = new int[pattern.length()];

        if (pattern.length() != 0) {
            table[0] = 0;
        }

        int prefix = 0;
        int current = 1;

        while (current < pattern.length()) {
            if (comparator.compare(pattern.charAt(current), pattern.charAt(prefix)) == 0) {
                table[current++] = ++prefix;
            } else {
                if (prefix != 0) {
                    prefix = table[prefix - 1];
                } else {
                    table[current++] = prefix;
                }
            }
        }
        return table;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern can't be null or empty");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator can't be null");
        }

        List<Integer> result = new ArrayList<>();

        if (pattern.length() > text.length()) {
            return result;
        }
        int patternHash = hash(pattern);
        int windowHash = hash(text.subSequence(0, pattern.length()));

        if (patternHash == windowHash && check(pattern,
                text.subSequence(0, pattern.length()), comparator)) {
            result.add(0);
        }
        for (int i = 1; i < text.length() - pattern.length() + 1; i++) {
            windowHash = (windowHash - (text.charAt(i - 1)
                    * power(BASE, pattern.length() - 1)))
                    * BASE + text.charAt(i + pattern.length() - 1);
            if (windowHash == patternHash && check(pattern,
                    text.subSequence(i, i + pattern.length()), comparator)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * helps the rabinKarp method to calulate the hash
     *
     * @param text string that needs to be calculated
     * @return hash value of a string passed
     */
    private static int hash(CharSequence text) {
        int temp = 0;
        for (int i = 0; i < text.length(); i++) {
            temp += text.charAt(i) * power(BASE, text.length() - 1 - i);
        }
        return temp;
    }

    /**
     * Alternative options of Math.pow()
     *
     * @param a number that will get multiplied
     * @param b times that 'a' gets multiplied
     * @return resulting value of a^b
     */
    private static int power(int a, int b) {
        if (b == 0) {
            return 1;
        } else {
            return a * power(a, b - 1);
        }
    }

    /**
     *  helps the rabinKarp method and checks if text and pattern matches
     *
     * @param pattern a string being searched for in a text
     * @param text the body of text to search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return boolean if pattern and text matches
     */
    private static boolean check(CharSequence pattern, CharSequence text,
                                 CharacterComparator comparator) {
        for (int i = 0; i < pattern.length(); i++) {
            if (comparator.compare(pattern.charAt(i), text.charAt(i)) != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern can't be null or empty");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator can't be null");
        }
        List<Integer> result = new ArrayList<>();

        if (pattern.length() > text.length()) {
            return result;
        }

        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int[] failureTable = buildFailureTable(pattern, comparator);

        int patternLength = pattern.length();
        int textLength = text.length();
        int shift = 0;
        int galilSkip = 0;

        while (shift <= textLength - patternLength) {
            int patternIndex = patternLength - 1;

            while (patternIndex >= galilSkip
                    && comparator.compare(pattern.charAt(patternIndex),
                    text.charAt(shift + patternIndex)) == 0) {
                patternIndex--;
            }

            if (patternIndex < galilSkip) {
                result.add(shift);

                int skipLength = failureTable[patternLength - 1];
                galilSkip = skipLength;
                shift += patternLength - skipLength;
            } else {
                char mismatchedChar = text.charAt(shift + patternIndex);
                int lastOccurrence = lastTable.getOrDefault(mismatchedChar, -1);
                int shiftAmount = Math.max(1, patternIndex - lastOccurrence);

                shift += shiftAmount;
                galilSkip = 0;
            }
        }

        return result;

    }
}
