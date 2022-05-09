package ru.yandex.algo.sprint3;

import java.util.Arrays;

public class SortUtility {
  private static final String DESC = "DESK";
  private static final String ASC = "ASC";

  public static void main(String[] args) {
    testMergeSort();
  }

  public static String[] mergeSort(String[] array, String directionName) {
    if (1 == array.length) {
      return array;
    }
    final int mid = array.length / 2;
    final String[] left = mergeSort(Arrays.copyOfRange(array, 0, mid), directionName);
    final String[] right = mergeSort(Arrays.copyOfRange(array, mid, array.length), directionName);
    final String[] result = new String[array.length];

    int l = 0;
    int r = 0;
    int k = 0;

    while (l < left.length && r < right.length) {
      int compareResult = compare(left[l], right[r]);
      if (directionName.equals(ASC)) {
        if (compareResult > 0) {
          result[k] = right[r];
          r++;
        } else {
          result[k] = left[l];
          l++;
        }
      } else {
        if (compareResult > 0) {
          result[k] = left[l];
          l++;
        } else {
          result[k] = right[r];
          r++;
        }
      }
      k++;
    }

    while (l < left.length) {
      result[k] = left[l];
      l++;
      k++;
    }

    while (r < right.length) {
      result[k] = right[r];
      r++;
      k++;
    }

    return result;
  }

  private static int compare(String a, String b) {
    final int result = a.compareTo(b);
    // System.out.println(a + ".compareTo(" + b + ") = " + result);
    return Integer.compare(0, result);
  }

  private static void testMergeSort() {
    System.out.println("Test testMergeSort start");
    final String[] sorted = mergeSort(new String[] {"b", "a", "c"}, DESC);
    assert Arrays.equals(sorted, new String[] {"a", "b", "c"});
    System.out.println("Test testMergeSort end");
  }
}
