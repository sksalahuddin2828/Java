import java.util.Arrays;

class Solution {
    public int minDifficulty(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if (n < d) {
            return -1;
        }

        int[][] memo = new int[n][d + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dp(jobDifficulty, 0, d, memo);
    }

    private int dp(int[] jobDifficulty, int i, int d, int[][] memo) {
        if (d == 1) {
            int maxDifficulty = -1;
            for (int j = i; j < jobDifficulty.length; j++) {
                maxDifficulty = Math.max(maxDifficulty, jobDifficulty[j]);
            }
            return maxDifficulty;
        }

        if (i == jobDifficulty.length - 1) {
            return Integer.MAX_VALUE;
        }

        if (memo[i][d] != -1) {
            return memo[i][d];
        }

        int curDifficulty = jobDifficulty[i];
        int minDifficulty = Integer.MAX_VALUE;

        for (int j = i; j < jobDifficulty.length - d + 1; j++) {
            curDifficulty = Math.max(curDifficulty, jobDifficulty[j]);
            int change = curDifficulty + dp(jobDifficulty, j + 1, d - 1, memo);
            minDifficulty = Math.min(minDifficulty, change);
        }

        memo[i][d] = minDifficulty;
        return minDifficulty;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] jobDifficulty = {6, 5, 4, 3, 2, 1};
        int days = 2;

        Solution solution = new Solution();
        int result = solution.minDifficulty(jobDifficulty, days);

        System.out.println("Minimum difficulty: " + result);
    }
}
