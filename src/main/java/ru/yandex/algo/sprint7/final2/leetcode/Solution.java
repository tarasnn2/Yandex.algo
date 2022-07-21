package ru.yandex.algo.sprint7.final2.leetcode;

import java.util.Arrays;

class Solution {

  public boolean canPartition(int[] nums) {
    int sum = Arrays.stream(nums).sum();
    final int halfNumberSum = sum / 2;
    final boolean[][] dp = new boolean[halfNumberSum + 1][nums.length + 1];
    for (int i = 0; i <= nums.length; i++) {
      dp[0][i] = true;
    }
    for (int i = 1; i <= halfNumberSum; i++) {
      for (int j = 1; j <= nums.length; j++) {
        if (i - nums[j - 1] >= 0) {
          dp[i][j] = dp[i][j - 1] || dp[i - nums[j - 1]][j - 1];
        } else {
          dp[i][j] = dp[i][j - 1];
        }
      }
    }
    return dp[halfNumberSum][nums.length];
  }
}
