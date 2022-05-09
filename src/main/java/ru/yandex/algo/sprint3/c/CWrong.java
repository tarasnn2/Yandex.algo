//package ru.yandex.algo.sprint3.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Гоша любит играть в игру «Подпоследовательность»: даны 2 строки, и нужно понять, является ли первая из них подпоследовательностью второй.
 * Когда строки достаточно длинные, очень трудно получить ответ на этот вопрос, просто посмотрев на них. Помогите Гоше написать функцию,
 * которая решает эту задачу.
 *
 * <p>Формат ввода В первой строке записана строка s.
 *
 * <p>Во второй —- строка t.
 *
 * <p>Обе строки состоят из маленьких латинских букв, длины строк не превосходят 150000. Строки могут быть пустыми.
 *
 * <p>Формат вывода Выведите True, если s является подпоследовательностью t, иначе —– False.
 */
public class CWrong {
  private static final String SEPARATOR = " ";
  private static final String DESC = "DESK";
  private static final String ASC = "ASC";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/c/input01.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
/*    testCompare();
    testMergeSort();
    testBinaryFind();*/

    execute();
  }

  private static void execute() throws IOException {
    final String[] s;
    final String[] t;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      s = in.readLine().split("");
      t = in.readLine().split("");
    }
    if (s.length > t.length) {
      System.out.println("False");
      return;
    }

    final String[] sortedT = mergeSort(t, DESC);

    for (String value : s) {
      if (!binaryFind(sortedT, value)) {
        System.out.println("False");
        return;
      }
    }
    System.out.println("True");
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
    final int result = a.compareTo(b);
    // System.out.println(a + ".compareTo(" + b + ") = " + result);
    return Integer.compare(0, result);
  }

  private static boolean binaryFind(String[] line, int start, String pattern) {
    int mid = line.length / 2;
    if (0 == compare(line[mid], pattern)) {
      return true;
    }
    if (line.length == 1) {
      return false; // not found
    }

    String[] subLine;
    if (-1 == compare(line[mid], pattern)) {
      subLine = Arrays.copyOfRange(line, start, line.length / 2);
    } else {
      subLine = Arrays.copyOfRange(line, line.length / 2, line.length);
    }
    return binaryFind(subLine, pattern);
  }
  private static boolean binaryFind(String[] line, String pattern) {
    return binaryFind(line, 0, pattern);
  }

  private static void testCompare() {
    System.out.println("Test testCompare start");
    assert compare("a", "z") == 1;
    assert compare("z", "a") == -1;
    assert compare("a", "b") == 1;
    assert compare("c", "b") == -1;
    assert compare("a", "a") == 0;
    System.out.println("Test testCompare end");
  }

  private static void testMergeSort() {
    System.out.println("Test testMergeSort start");
    final String[] sorted = mergeSort(new String[] {"b", "a", "c"}, DESC);
    assert Arrays.equals(sorted, new String[] {"a", "b", "c"});
    System.out.println("Test testMergeSort end");
  }

  private static void testBinaryFind() {
    System.out.println("Test testBinaryFind start");
    String[] line = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "z"};
    assert binaryFind(line, "d");
    assert !binaryFind(line, "y");
    System.out.println("Test testBinaryFind end");
  }
}
