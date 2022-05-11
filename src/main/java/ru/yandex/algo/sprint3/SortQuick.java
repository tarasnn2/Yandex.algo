package ru.yandex.algo.sprint3;

import java.util.Arrays;
import java.util.function.BiFunction;

public class SortQuick<T> {

  public SortQuick(BiFunction<T, T, Integer> comparator, Direction direction) {
    this.comparator = comparator;
    this.direction = direction;
  }

  private final BiFunction<T, T, Integer> comparator;

  private final Direction direction;

  public static void main(String[] args) {
    test();
  }

  public void sort(T[] array) {
    sort(array, 0, array.length - 1);
  }

  private void sort(T[] array, int begin, int end) {
    if (begin >= end) {
      return;
    }
    final int partitionIndex = partition(array, begin, end);
    sort(array, begin, partitionIndex - 1);
    sort(array, partitionIndex + 1, end);
  }

  private int partition(T[] array, int begin, int end) {
    final T pivot = array[end];
    int i = begin - 1;
    final int d1 = (Direction.ASC == direction ? -1 : 1);
    final int d2 = (Direction.ASC == direction ? 0 : 1);
    for (int j = begin; j < end; j++) {
      final Integer resultCompare = comparator.apply(array[j], pivot);
      if (d1 == resultCompare || d2 == resultCompare) {
        i++;
        T swapTemp = array[i];
        array[i] = array[j];
        array[j] = swapTemp;
      }
    }

    final int iNext = i + 1;
    T swapTemp = array[iNext];
    array[iNext] = array[end];
    array[end] = swapTemp;

    return iNext;
  }

  private enum Direction {
    DESC,
    ASC;
  }

  private static void test() {
    System.out.println("Test start");

    {
      final Integer[] source = new Integer[] {4, 4, 5, 5, 5, 6, 3, 1};
      final Integer[] expect = new Integer[] {1, 3, 4, 4, 5, 5, 5, 6};
      new SortQuick<Integer>(Integer::compare, Direction.ASC).sort(source);
      assert Arrays.equals(source, expect);
    }

    {
      final Integer[] source = new Integer[] {4, 4, 5, 5, 5, 6, 3, 1};
      final Integer[] expect = new Integer[] {6, 5, 5, 5, 4, 4, 3, 1};
      new SortQuick<Integer>(Integer::compare, Direction.DESC).sort(source);
      assert Arrays.equals(source, expect);
    }

    {
      final BiFunction<String, String, Integer> lexicographicComparator = (String a, String b) -> Integer.compare(0, a.compareTo(b));
      final String[] source = new String[] {"b", "a", "c"};
      new SortQuick<String>(lexicographicComparator, Direction.DESC).sort(source);
      final String[] expected = new String[] {"a", "b", "c"};
      assert Arrays.equals(source, expected);
    }

    {
      final BiFunction<String, String, Integer> lexicographicComparator = (String a, String b) -> Integer.compare(0, a.compareTo(b));
      final String[] source = new String[] {"c", "a", "b"};
      new SortQuick<String>(lexicographicComparator, Direction.ASC).sort(source);
      final String[] expected = new String[] {"c", "b", "a"};
      assert Arrays.equals(source, expected);
    }

    System.out.println("Test end");
  }
}
