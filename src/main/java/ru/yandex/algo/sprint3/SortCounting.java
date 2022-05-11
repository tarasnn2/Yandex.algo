package ru.yandex.algo.sprint3;

import java.util.Arrays;
import java.util.function.BiFunction;

public class SortCounting {
  private static final BiFunction<Integer, Integer, Integer> twoDigitComparator = Integer::compare;

  public static void main(String[] args) {
    test();
  }


  public static int[] sort(int[] array, Direction direction, BiFunction<Integer, Integer, Integer> comparator, int maxValue) {
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

  private static void test() {
    System.out.println("Test start");
    {
      int maxValue = 6;
      final int[] source = new int[] {4, 5, maxValue, 3, 1};
      final int[] expect = new int[] {1, 3, 4, 5, 6};
      final int[] sorted = sort(source, Direction.ASC, twoDigitComparator, ++maxValue);
      assert Arrays.equals(sorted, expect);
    }

    {
      int maxValue = 6;
      final int[] source = new int[] {4, 4, 5, 5, 5, maxValue, 3, 1};
      final int[] expect = new int[] {6, 5, 5, 5, 4, 4, 3, 1};
      final int[] sorted = sort(source, Direction.DESC, twoDigitComparator, ++maxValue);
      assert Arrays.equals(sorted, expect);
    }

    System.out.println("Test end");
  }
}
