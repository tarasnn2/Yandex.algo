package ru.yandex.algo.sprint3.c;

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
public class CTimeLimit {
  private static final String SEPARATOR = " ";
  private static final String DESC = "DESK";
  private static final String ASC = "ASC";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/c/input02.txt";
//  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    /*  testCompare();
    testMergeSort();
    testBinaryFind();
    testLinearFind();*/

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
    if (s.length == 0) {
      System.out.println("True");
      return;
    }

    if (s.length > t.length) {
      System.out.println("False");
      return;
    }

    int pos = -1;
    for (String value : s) {
      pos = linearFind(t, pos + 1, t.length - 1, value);
      if (-1 == pos) {
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

  private static int binaryFind(String[] array, int start, int end, String pattern) {
    int mid = (start + end) / 2;
    int compareResult = compare(array[mid], pattern);
    if (0 == compareResult) {
      return mid;
    }
    if (start > end) {
      return -1; // not found
    }

    if (-1 == compareResult) {
      return binaryFind(array, start, mid - 1, pattern);
    } else {
      return binaryFind(array, mid + 1, end, pattern);
    }
  }

  private static int binaryFind(String[] array, String pattern) {
    return binaryFind(array, 0, array.length, pattern);
  }

  private static int binaryFind(String line, String pattern) {
    final String[] array = line.split("");
    return binaryFind(array, 0, array.length, pattern);
  }

  private static int linearFind(String line, String pattern) {
    final String[] array = line.split("");
    return linearFind(array, 0, array.length - 1, pattern);
  }

  private static int linearFind(String[] array, int start, int end, String pattern) {
    for (int i = start; i <= end; i++) {
      if (array[i].equals(pattern)) {
        return i;
      }
    }
    return -1;
  }

  private static void testCompare() {
    System.out.println("Test testCompare start");
    assert compare("a", "z") == 1;
    assert compare("z", "a") == -1;
    assert compare("a", "b") == 1;
    assert compare("c", "b") == -1;
    assert compare("a", "a") == 0;
    assert compare("e", "i") == 1;
    assert compare("t", "i") == -1;
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
    String[] array = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "z"};
    assert binaryFind(array, "d") == 3;
    assert binaryFind(array, "y") == -1;
    assert binaryFind(array, "z") == 8;
    assert binaryFind(array, "a") == 0;
    assert binaryFind(array, 7, 8, "h") == 7;
    assert binaryFind(array, 5, 8, "h") == 7;
    System.out.println("Test testBinaryFind end");
  }

  private static void testLinearFind() {
    System.out.println("Test testLinearFind start");
    String line = "yoytgtshldmogkdburkbcfvoapepjpcuwemusfkfztrzxstytrnarlizjhuoscuzlraezlaweipuuqdgvhwkhhoufexojaps";
    assert linearFind(line, "i") == 54;
    System.out.println("Test testLinearFind stop");
  }
}
