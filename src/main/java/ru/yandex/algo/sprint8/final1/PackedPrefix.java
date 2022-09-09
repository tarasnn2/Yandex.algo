// 70230307
package ru.yandex.algo.sprint8.final1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

/**
 * Распаковка требует O(n) по сложности и памяти. Во время распаковки определяемся с самой короткой строкой, берем ее за основу для
 * вычисления maxPrefix. Поиск maxPrefix требует O(n^2) по сложности и O(n) по памяти. Самую короткую строку сравниваем с каждой строкой
 * уменьшая ее на один символ для каждого неуспеха. В худшем случае мы получим пустую строку, в лучшем - стартовую.
 */
public class PackedPrefix {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/final1/input7.txt";

  private static final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");


  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int count = Integer.parseInt(in.readLine());
      if (count == 0) {
        System.out.println("");
        return;
      }
      final String[] words = new String[count];
      final int minLengthIndex = unpackAll(in, count, words);
      final String result = maxPrefix(words, minLengthIndex);
      System.out.println(result);
    }
  }

  private static String maxPrefix(String[] words, int minLengthIndex) {
    String minWord = words[minLengthIndex];
    for (int i = 0; i < words.length; i++) {
      final String s = words[i];
      while (!minWord.isEmpty() && !s.startsWith(minWord)) {
        minWord = minWord.substring(0, minWord.length() - 1);
      }
    }
    return minWord;
  }

  private static int unpackAll(BufferedReader in, int count, String[] words) throws IOException {
    int minLength = Integer.MAX_VALUE;
    int minLengthIndex = -1;
    for (int i = 0; i < count; i++) {
      words[i] = unpack(in.readLine());
      if (minLength > words[i].length()) {
        minLength = words[i].length();
        minLengthIndex = i;
      }
    }
    return minLengthIndex;
  }

  private static String unpack(String line) {
    final StringBuilder result = new StringBuilder();
    final Deque<Integer> multiply = new ArrayDeque<>();
    final Deque<StringBuilder> symbol = new ArrayDeque<>();
    for (int i = 0; i < line.length(); i++) {

      String s = String.valueOf(line.charAt(i));

      if (isNumeric(s)) {
        multiply.add(Integer.parseInt(s));
        continue;
      }

      if ("[".equals(s)) {
        symbol.add(new StringBuilder());
        continue;
      }

      if ("]".equals(s)) {
        if (symbol.size() == 1) {
          int m = multiply.removeLast();
          final StringBuilder last = symbol.removeLast();
          for (int j = 1; j <= m; j++) {
            result.append(last);
          }
          continue;
        }
        StringBuilder last = symbol.removeLast();
        final int m = multiply.removeLast();
        for (int j = 1; j <= m; j++) {
          symbol.getLast().append(last);
        }
        continue;
      }

      if (symbol.isEmpty()) {
        result.append(s);
        continue;
      }
      symbol.getLast().append(s);
    }
    return result.toString();
  }

  private static boolean isNumeric(String s) {
    return numericPattern.matcher(s).matches();
  }

}
