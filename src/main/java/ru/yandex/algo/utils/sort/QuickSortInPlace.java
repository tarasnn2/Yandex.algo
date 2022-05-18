package ru.yandex.algo.utils.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;


public class QuickSortInPlace<T extends Comparable<T>> {

  public static void main(String[] args) {
    test();
  }

  public QuickSortInPlace(Direction direction) {
    this.direction = direction;
  }

  private final Direction direction;

  public void sort(T[] array) {
    sort(array, 0, array.length - 1);
  }

  public void sort(T[] array, int begin, int end) {
    if (begin >= end) {
      return;
    }
    final int partitionIndex = partition_r(array, begin, end);
    sort(array, begin, partitionIndex - 1);
    sort(array, partitionIndex + 1, end);
  }

  private int partition_r(T[] array, int begin, int end) {
    final int random = ThreadLocalRandom.current().nextInt(begin, end + 1);
    swap(array, random, end);
    return partition(array, begin, end);
  }

  private int partition(T[] array, int begin, int end) {

    final T pivot = array[end];
    int i = begin-1;
    final int d1 = (Direction.ASC == direction ? -1 : 1);
    final int d2 = (Direction.ASC == direction ? 0 : 1);
    for (int j = begin; j < end; j++) {
      final int resultCompare = array[j].compareTo(pivot);
      if (d1 == resultCompare || d2 == resultCompare) {
        swap(array, ++i, j);
      }
    }
    final int iNext = i + 1;
    swap(array, iNext, end);
    return iNext;
  }

  private static <T> void swap(T[] array, int i, int j) {
    final T swapTemp = array[i];
    array[i] = array[j];
    array[j] = swapTemp;
  }


  private static void test() {
    System.out.println("Test start");

    {
      final Integer[] source = new Integer[] {4, 4, 5, 5, 5, 6, 3, 1};
      final Integer[] expect = new Integer[] {1, 3, 4, 4, 5, 5, 5, 6};
      new QuickSortInPlace<Integer>(Direction.ASC).sort(source);
      assert Arrays.equals(source, expect);
    }

     {
      final Integer[] source = new Integer[] {4, 4, 5, 5, 5, 6, 3, 1};
      final Integer[] expect = new Integer[] {6, 5, 5, 5, 4, 4, 3, 1};
      new QuickSortInPlace<Integer>(Direction.DESC).sort(source);
      assert Arrays.equals(source, expect);
    }

    {
      final String[] source = new String[] {"c", "a", "b"};
      new QuickSortInPlace<String>(Direction.ASC).sort(source);
      final String[] expected = new String[] {"a", "b", "c"};
      assert Arrays.equals(source, expected);
    }

    {
      final String[] source = new String[] {"c", "a", "b"};
      new QuickSortInPlace<String>(Direction.DESC).sort(source);
      final String[] expected = new String[] {"c", "b", "a"};
      assert Arrays.equals(source, expected);
    }

    System.out.println("Test end");
  }
}
enum Direction {
  DESC,
  ASC;
}
