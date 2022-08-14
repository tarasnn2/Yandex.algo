//package ru.yandex.algo.sprint8.final1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

public class PackedPrefix {

  private static final String FILE = "input.txt";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/final1/input7.txt";

  private static final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");


  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      System.out.println(maxPrefix(in));
    }
  }

  private static String maxPrefix(final BufferedReader in) throws IOException {
    final int count = Integer.parseInt(in.readLine());
    if (count == 0) {
      return "";
    }
    final String[] strings = unpackAll(in, count);
    String prefix = strings[0];
    for (int i = 1; i < count; i++) {
      final String s = strings[i];
      while (!prefix.isEmpty() && !s.startsWith(prefix)) {
        prefix = prefix.substring(0, prefix.length() - 1);
      }
    }
    return prefix;
  }


  private static String[] unpackAll(BufferedReader in, int count) throws IOException {
    String[] result = new String[count];
    for (int i = 0; i < count; i++) {
      result[i] = unpack(in.readLine());
    }
    Arrays.sort(result, Comparator.comparingInt(String::length));
    return result;
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
          final int mult = multiply.removeLast();
          String s1 = String.join("", symbol.removeLast());
          for (int j = 0; j < mult; j++) {
            result.append(s1);
          }
          //result.append(String.join("", symbol.removeLast()).repeat(multiply.removeLast()));
          continue;
        }
        final String previous = String.join("", symbol.removeLast());
        final int mult = multiply.removeLast();
        for (int j = 0; j < mult; j++) {
          symbol.getLast().add(previous);
        }
        //symbol.getLast().add(previous.repeat(multiply.removeLast()));
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
