package ru.yandex.algo.sprint8.final1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

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
      //final String result = doCount(words, minLengthIndex);
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

  private static String doCount(String[] words, int minLengthIndex) {
    final String minWord = words[minLengthIndex];
    for (int j = 0; j < minWord.length(); j++) {
      for (int i = 0; i < words.length; i++) {
        final String minWordSubstring = minWord.substring(j, j + 1);
        final String wordSubstring = words[i].substring(j, j + 1);
        if (!minWordSubstring.equals(wordSubstring)) {
          return minWord.substring(0, j);
        }
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
    final Deque<List<String>> symbol = new ArrayDeque<>();
    for (int i = 0; i < line.length(); i++) {

      String s = String.valueOf(line.charAt(i));

      if (isNumeric(s)) {
        multiply.add(Integer.parseInt(s));
        continue;
      }

      if ("[".equals(s)) {
        symbol.add(new ArrayList<>());
        continue;
      }

      if ("]".equals(s)) {
        if (symbol.size() == 1) {
          //final int mult = multiply.removeLast();
          //String s1 = String.join("", symbol.removeLast());
/*          for (int j = 0; j < mult; j++) {
            result.append(s1);
          }*/
          result.append(String.join("", symbol.removeLast()).repeat(multiply.removeLast()));
          continue;
        }
        final String previous = String.join("", symbol.removeLast());
        //final int mult = multiply.removeLast();
/*        for (int j = 0; j < mult; j++) {
          symbol.getLast().add(previous);
        }*/
        symbol.getLast().add(previous.repeat(multiply.removeLast()));
        continue;
      }

      if (symbol.isEmpty()) {
        result.append(s);
        continue;
      }
      symbol.getLast().add(s);
    }
    return result.toString();
  }

  private static boolean isNumeric(String s) {
    return numericPattern.matcher(s).matches();
  }

}
