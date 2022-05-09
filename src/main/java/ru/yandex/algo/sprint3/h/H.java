package ru.yandex.algo.sprint3.h;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Вечером ребята решили поиграть в игру «Большое число». Даны числа. Нужно определить, какое самое большое число можно из них составить.
 *
 * <p>Формат ввода В первой строке записано n — количество чисел. Оно не превосходит 100. Во второй строке через пробел записаны n
 * неотрицательных чисел, каждое из которых не превосходит 1000.
 *
 * <p>Формат вывода Нужно вывести самое большое число, которое можно составить из данных чисел.
 */
public class H {
  private static final String DESC = "DESK";
  private static final String ASC = "ASC";

  private static final String SEPARATOR = " ";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/h/input26.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    execute();
  }

  private static void execute() throws IOException {
    final String[] elements;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int elementsCount = Integer.parseInt(in.readLine());
      elements = in.readLine().split(SEPARATOR);
    }
    final String[] sorted = mergeSort(elements, DESC);
    StringBuilder result = new StringBuilder(sorted.length);
    for (String s : sorted) {
      result.append(s);
    }
    System.out.print(result);
  }

  private static String[] mergeSort(String[] array, String directionName) {
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
    return Integer.parseInt(a + b) >= Integer.parseInt(b + a) ? 1 : -1;
  }
}
