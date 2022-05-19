package ru.yandex.algo.sprint3.final1;
// 68487205
/**
 * Разделяем и властвуем. п.1: если массив возрастает, ищем с помощью бинарного поиска O(log N). п.2: если массив не возрастает, делим его
 * на две части и к п.1.
 *
 * <p>память используется для рекурсии, сложность по памяти O(log N)
 */
public class Solution {

  public static void main(String[] args) {
    test();
  }

  public static int brokenSearch(int[] arr, int k) {
    return fixSearch(arr, 0, arr.length - 1, k);
  }

  private static int fixSearch(int[] arr, int minIndex, int maxIndex, int k) {
    final int valueMinIndex = arr[minIndex];
    final int valueMaxIndex = arr[maxIndex];

    if (minIndex == maxIndex && valueMinIndex == k) {
      return minIndex;
    }

    if (valueMinIndex > valueMaxIndex) {
      final int mid = (minIndex + maxIndex) / 2;
      final int result = fixSearch(arr, minIndex, mid, k);
      if (result == -1) {
        return fixSearch(arr, mid + 1, maxIndex, k);
      }
      return result;
    }
    return binarySearch(arr, minIndex, maxIndex, k);
  }

  private static int binarySearch(int[] arr, int minIndex, int maxIndex, int k) {
    final int mid = (minIndex + maxIndex) / 2;
    if (arr[mid] == k) {
      return mid;
    }
    if (minIndex > maxIndex) {
      return -1; // not found
    }

    if (Integer.compare(arr[mid], k) == -1) {
      return binarySearch(arr, mid + 1, maxIndex, k);
    }
    return binarySearch(arr, minIndex, mid - 1, k);
  }

  private static void test() {
    {
      final int[] arr = {19, 21, 100, 101, 1, 4, 5, 7, 12};
      final int result = brokenSearch(arr, 5);
      assert 6 == result;
    }
    {
      final int[] arr = {5, 1};
      final int result = brokenSearch(arr, 1);
      assert 1 == result;
    }

    {
      final int[] arr = {12, 41, 122, 411, 412, 1222, 3000, 12222, 122222};
      final int result = brokenSearch(arr, 3000);
      assert 6 == result;
    }
    {
      final int[] arr = {1, 5};
      final int result = brokenSearch(arr, 1);
      assert 0 == result;
    }
  }
}
