package ru.yandex.algo.sprint3.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * https://contest.yandex.ru/contest/23638/problems/B/
 *
 * <p>На клавиатуре старых мобильных телефонов каждой цифре соответствовало несколько букв. Примерно так:
 *
 * <p>2:'abc', 3:'def', 4:'ghi', 5:'jkl', 6:'mno', 7:'pqrs', 8:'tuv', 9:'wxyz'
 *
 * <p>Вам известно в каком порядке были нажаты кнопки телефона, без учета повторов. Напечатайте все комбинации букв, которые можно набрать
 * такой последовательностью нажатий.
 */
public class B {
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/b/input1.txt";
  //private static final String FILE = "input.txt";
  private static final Map<Integer, String[]> alphaBet = new HashMap<>();

  static {
    alphaBet.put(2, new String[] {"a", "b", "c"});
    alphaBet.put(3, new String[] {"d", "e", "f"});
    alphaBet.put(4, new String[] {"g", "h", "i"});
    alphaBet.put(5, new String[] {"j", "k", "l"});
    alphaBet.put(6, new String[] {"m", "n", "o"});
    alphaBet.put(7, new String[] {"p", "q", "r", "s"});
    alphaBet.put(8, new String[] {"t", "u", "v"});
    alphaBet.put(9, new String[] {"w", "x", "y", "z"});
  }

  public static void main(String[] args) throws IOException {
    String line;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      line = in.readLine();
    }
    final String[] digits = line.split("");
    String[] lettersAll = new String[0];
    for (String digit : digits) {
      lettersAll = mergeLetters(lettersAll, getLetters(digit));
    }

    StringBuilder sb = new StringBuilder();
    for (int k = 0; k < digits.length - 1; k++) {
      final String[] letters = getLetters(digits[k]);
      for (int i = 0; i < letters.length; i++) {
        String prefix = letters[i];
        for (int j = letters.length; j < lettersAll.length; j++) {
          sb.append(prefix).append(lettersAll[j]).append(" ");
        }
      }
    }
    System.out.println(sb);

    /*    for (int i = 0; i <lettersAll.length ; i++) {
      for(int j = 0; j < lettersAll.length; j++) {
        System.out.println(lettersAll[i]+lettersAll[]);
      }

    }*/

    /*    String[] lettersAll = new String[0];
    for (String digit : line.split("")) {
      lettersAll = mergeLetters(lettersAll, getLetters(digit));
    }*/

    // genBinary(2, "", lettersAll, 0);
  }

  /*  private static void genBinary(int n, String prefix, String[] sequence, int sequenceStartPoint) {
    if (n == 0) {
      System.out.println(prefix);
    } else {
      for (int i = sequenceStartPoint; i < sequence.length; i++) {
        genBinary(--n, prefix + sequence[sequenceStartPoint], sequence, ++sequenceStartPoint);
      }
    }
  }*/

  private static String[] getLetters(String digit) {
    return alphaBet.get(Integer.valueOf(digit));
  }

  private static String[] mergeLetters(String[] firstArray, String[] secondArray) {
    if (0 == firstArray.length) {
      return secondArray;
    }
    if (0 == secondArray.length) {
      return firstArray;
    }
    int fal = firstArray.length;
    int sal = secondArray.length;
    String[] result = new String[fal + sal];
    System.arraycopy(firstArray, 0, result, 0, fal);
    System.arraycopy(secondArray, 0, result, fal, sal);
    return result;
  }
}
