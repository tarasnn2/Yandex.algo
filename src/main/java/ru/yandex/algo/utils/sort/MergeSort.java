package ru.yandex.algo.utils.sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiFunction;

public class MergeSort<T> {
  private Class<T> clazz;
  private static final BiFunction<String, String, Integer> lexicographicComparator =
      (String a, String b) -> {
        final int result = a.compareTo(b);
        // System.out.println(a + ".compareTo(" + b + ") = " + result);
        return Integer.compare(0, result);
      };

  private static final BiFunction<Integer, Integer, Integer> twoDigitComparator = Integer::compare;

  public static void main(String[] args) {
    test();
  }

  public MergeSort(Class<T> clazz) {
    this.clazz = clazz; // TODO replace by reflection
  }

  public T[] sort(T[] array, Direction direction, BiFunction<T, T, Integer> comparator) {
    if (1 == array.length) {
      return array;
    }
    final int mid = array.length / 2;
    final T[] left = sort(Arrays.copyOfRange(array, 0, mid), direction, comparator);
    final T[] right = sort(Arrays.copyOfRange(array, mid, array.length), direction, comparator);
    final T[] result = (T[]) Array.newInstance(clazz, array.length);

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

  private enum Direction {
    DESC,
    ASC;
  }

  private static void test() {
    System.out.println("Test start");
    {
      final String[] source = new String[] {"b", "a", "c"};
      final String[] sorted = new MergeSort<>(String.class).sort(source, Direction.DESC, lexicographicComparator);
      final String[] expected = new String[] {"a", "b", "c"};
      assert Arrays.equals(sorted, expected);
    }
    {
      final Integer[] source = new Integer[] {4, 4, 5, 5, 5, 6, 3, 1};
      final Integer[] expect = new Integer[] {6, 5, 5, 5, 4, 4, 3, 1};
      final Integer[] sorted = new MergeSort<>(Integer.class).sort(source, Direction.DESC, twoDigitComparator);
      assert Arrays.equals(sorted, expect);
    }

    {
      final Integer[] source = new Integer[] {4, 4, 5, 5, 5, 6, 3, 1};
      final Integer[] expect = new Integer[] {1, 3, 4, 4, 5, 5, 5, 6};
      final Integer[] sorted = new MergeSort<>(Integer.class).sort(source, Direction.ASC, twoDigitComparator);
      assert Arrays.equals(sorted, expect);
    }

    System.out.println("Test end");
  }
}
