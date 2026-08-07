import java.util.*;

class Solution {
    // Prime factor counts for single digits 1..9
    // {c2, c3, c5, c7}
    private static final int[][] DIGIT_FACTORS = {
        {0, 0, 0, 0}, // 0 (unused)
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into powers of 2, 3, 5, 7
        int[] tFactors = new int[4]; // c2, c3, c5, c7
        long tempT = t;
        int[] primes = {2, 3, 5, 7};

        for (int i = 0; i < 4; i++) {
            while (tempT % primes[i] == 0) {
                tFactors[i]++;
                tempT /= primes[i];
            }
        }

        // If t has prime factors other than 2, 3, 5, 7, it's impossible
        if (tempT > 1) {
            return "-1";
        }

        int n = num.length();
        
        // Track prefix factor counts and first zero position
        int[][] prefixFactors = new int[n + 1][4];
        int firstZero = -1;

        for (int i = 0; i < n; i++) {
            int digit = num.charAt(i) - '0';
            if (digit == 0) {
                if (firstZero == -1) firstZero = i;
                // Once a 0 is met, prefix factor accumulation stops
                for (int k = 0; k < 4; k++) prefixFactors[i + 1][k] = prefixFactors[i][k];
            } else {
                for (int k = 0; k < 4; k++) {
                    prefixFactors[i + 1][k] = prefixFactors[i][k] + DIGIT_FACTORS[digit][k];
                }
            }
        }

        // Step 2: Try to find a valid number of same length n
        // Check if num itself works (no zero and prefix satisfies tFactors)
        if (firstZero == -1 && isSatisfied(prefixFactors[n], tFactors)) {
            return num;
        }

        // Try modifying digit at index i (from right to left)
        int maxI = (firstZero == -1) ? n - 1 : firstZero;

        for (int i = maxI; i >= 0; i--) {
            int startDigit = num.charAt(i) - '0' + 1;
            
            for (int d = startDigit; d <= 9; d++) {
                int[] currentFactors = new int[4];
                for (int k = 0; k < 4; k++) {
                    currentFactors[k] = prefixFactors[i][k] + DIGIT_FACTORS[d][k];
                }

                int[] neededFactors = getNeededFactors(currentFactors, tFactors);
                List<Integer> minDigits = getMinDigits(neededFactors);

                int remainingLen = n - 1 - i;
                if (minDigits.size() <= remainingLen) {
                    // Valid prefix found! Build the answer
                    StringBuilder sb = new StringBuilder();
                    sb.append(num.substring(0, i));
                    sb.append(d);

                    int numOnes = remainingLen - minDigits.size();
                    for (int j = 0; j < numOnes; j++) sb.append('1');
                    for (int digit : minDigits) sb.append(digit);

                    return sb.toString();
                }
            }
        }

        // Step 3: Same length not possible -> Increase length
        List<Integer> minDigits = getMinDigits(tFactors);
        int targetLen = Math.max(n + 1, minDigits.size());
        
        StringBuilder sb = new StringBuilder();
        int numOnes = targetLen - minDigits.size();
        for (int i = 0; i < numOnes; i++) sb.append('1');
        for (int digit : minDigits) sb.append(digit);

        return sb.toString();
    }

    private boolean isSatisfied(int[] current, int[] target) {
        for (int i = 0; i < 4; i++) {
            if (current[i] < target[i]) return false;
        }
        return true;
    }

    private int[] getNeededFactors(int[] current, int[] target) {
        int[] needed = new int[4];
        for (int i = 0; i < 4; i++) {
            needed[i] = Math.max(0, target[i] - current[i]);
        }
        return needed;
    }

    // Pack required (c2, c3, c5, c7) into minimal sorted digit list
    private List<Integer> getMinDigits(int[] factors) {
        int c2 = factors[0], c3 = factors[1], c5 = factors[2], c7 = factors[3];
        
        List<Integer> bestList = null;

        // Try c6 = 0 and c6 = 1
        for (int c6 = 0; c6 <= Math.min(c2, c3) && c6 <= 1; c6++) {
            int rem2 = c2 - c6;
            int rem3 = c3 - c6;

            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < c7; i++) list.add(7);
            for (int i = 0; i < c5; i++) list.add(5);
            for (int i = 0; i < c6; i++) list.add(6);

            // Pack 3s
            int n9 = rem3 / 2;
            int n3 = rem3 % 2;
            for (int i = 0; i < n9; i++) list.add(9);
            for (int i = 0; i < n3; i++) list.add(3);

            // Pack 2s
            int n8 = rem2 / 3;
            int rem2After8 = rem2 % 3;
            for (int i = 0; i < n8; i++) list.add(8);
            if (rem2After8 == 2) list.add(4);
            else if (rem2After8 == 1) list.add(2);

            Collections.sort(list);

            if (bestList == null || list.size() < bestList.size() || 
               (list.size() == bestList.size() && compareLists(list, bestList) < 0)) {
                bestList = list;
            }
        }

        return bestList;
    }

    private int compareLists(List<Integer> l1, List<Integer> l2) {
        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return l1.get(i) - l2.get(i);
            }
        }
        return 0;
    }
}