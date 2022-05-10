package ru.yandex.algo.sprint3;

import java.util.Arrays;
import java.util.function.BiFunction;

public class SortUtility {
  private static final BiFunction<String, String, Integer> lexicographicComparator =
      (String a, String b) -> {
        final int result = a.compareTo(b);
        // System.out.println(a + ".compareTo(" + b + ") = " + result);
        return Integer.compare(0, result);
      };

  private static final BiFunction<Integer, Integer, Integer> twoDigitComparator = Integer::compare;

  public static void main(String[] args) {
    testMergeSort();
    testCountingSort();
  }

  public static String[] mergeSort(String[] array, Direction direction, BiFunction<String, String, Integer> comparator) {
    if (1 == array.length) {
      return array;
    }
    final int mid = array.length / 2;
    final String[] left = mergeSort(Arrays.copyOfRange(array, 0, mid), direction, comparator);
    final String[] right = mergeSort(Arrays.copyOfRange(array, mid, array.length), direction, comparator);
    final String[] result = new String[array.length];

    int l = 0;
    int r = 0;
    int k = 0;

    while (l < left.length && r < right.length) {
      int compareResult = comparator.apply(left[l], right[r]);
      if (Direction.ASC == direction) {
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

  public static int[] countingSort(int[] array, Direction direction, BiFunction<Integer, Integer, Integer> comparator, int maxValue) {
    final Integer[] countedArray = new Integer[maxValue];

    for (int value : array) {
      if (null == countedArray[value]) {
        countedArray[value] = 1;
      } else {
        countedArray[value]++;
      }
    }

    final int[] result = new int[array.length];
    int index = 0;
    if (Direction.ASC == direction) {
      for (int i = 0; i < countedArray.length; i++) {
        index = moveCounted(i, index, countedArray, result);
      }
    } else {
      for (int i = countedArray.length - 1; i >= 0; i--) {
        index = moveCounted(i, index, countedArray, result);
      }
    }
    return result;
  }

  private static int moveCounted(int i, int index, Integer[] countedArray, int[] result) {
    if (null == countedArray[i]) {
      return index;
    }
    for (int j = 1; j <= countedArray[i]; j++) {
      result[index] = i;
      index++;
    }
    return index;
  }

  private enum Direction {
    DESC,
    ASC;
  }

  private static void testMergeSort() {
    System.out.println("Test testMergeSort start");
    final String[] sorted = mergeSort(new String[] {"b", "a", "c"}, Direction.DESC, lexicographicComparator);
    assert Arrays.equals(sorted, new String[] {"a", "b", "c"});
    System.out.println("Test testMergeSort end");
  }

  private static void testCountingSort() {
    System.out.println("Test testCountingSort start");
    {
      int maxValue = 6;
      final int[] source = new int[] {4, 5, maxValue, 3, 1};
      final int[] expect = new int[] {1, 3, 4, 5, 6};
      final int[] sorted = countingSort(source, Direction.ASC, twoDigitComparator, ++maxValue);
      assert Arrays.equals(sorted, expect);
    }

    {
      int maxValue = 6;
      final int[] source = new int[] {4, 4, 5, 5, 5, maxValue, 3, 1};
      final int[] expect = new int[] {6, 5, 5, 5, 4, 4, 3, 1};
      final int[] sorted = countingSort(source, Direction.DESC, twoDigitComparator, ++maxValue);
      assert Arrays.equals(sorted, expect);
    }

    System.out.println("Test testCountingSort end");
  }
}
