//package ru.yandex.algo.sprint3.e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.BiFunction;

public class E {
  private static final String SEPARATOR = " ";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/e/input02.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    execute();
  }

  private static void execute() throws IOException {
    final Integer[] houseCosts;
    final int houseCount;
    final int budget;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] l = in.readLine().split(SEPARATOR);
      //houseCount = Integer.parseInt(l[0]);
      budget = Integer.parseInt(l[1]);
      houseCosts = getArray(in.readLine().split(SEPARATOR));
    }

    final Integer[] sorted =
        new SortMerge<Integer>(Integer.class).mergeSort(houseCosts, SortMerge.Direction.ASC, SortMerge.twoDigitComparator);
    int result = 0;
    int costChoose = 0;
    for (Integer cost : sorted) {
      if (costChoose + cost <= budget) {
        costChoose = costChoose + cost;
        result++;
      } else {
        break;
      }
    }
    System.out.print(result);
  }

  private static Integer[] getArray(String[] array) {
    return Arrays.stream(array).map(Integer::parseInt).toArray(Integer[]::new);
  }
}

class SortMerge<T> {
  private Class<T> clazz;

  static final BiFunction<Integer, Integer, Integer> twoDigitComparator = Integer::compare;

  public SortMerge(Class<T> clazz) {
    this.clazz = clazz; // TODO replace by reflection
  }

  public T[] mergeSort(T[] array, SortMerge.Direction direction, BiFunction<T, T, Integer> comparator) {
    if (1 == array.length) {
      return array;
    }
    final int mid = array.length / 2;
    final T[] left = mergeSort(Arrays.copyOfRange(array, 0, mid), direction, comparator);
    final T[] right = mergeSort(Arrays.copyOfRange(array, mid, array.length), direction, comparator);
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

  enum Direction {
    DESC,
    ASC;
  }
}
