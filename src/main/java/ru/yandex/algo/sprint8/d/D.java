package ru.yandex.algo.sprint8.d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class D {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/d/input02.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int count = Integer.parseInt(in.readLine());
      if (count == 0) {
        System.out.println(0);
        return;
      }
      if (count == 1) {
        System.out.println(in.readLine().length());
        return;
      }

      final String[] words = new String[count];
      int minLengthIndex = -1;
      int minLength = Integer.MAX_VALUE;
      for (int i = 0; i < count; i++) {
        final String line = in.readLine();
        if (line.length() < minLength) {
          minLengthIndex = i;
          minLength = line.length();
        }
        words[i] = line;
      }

      System.out.println(doCount(words, minLengthIndex));
    }
  }

  private static int doCount(String[] words, int minLengthIndex) {
    int result = 0;
    final String minWord = words[minLengthIndex];
    for (int j = 0; j < minWord.length(); j++) {
      for (int i = 0; i < words.length; i++) {
        if (!minWord.substring(j, j + 1).equals(words[i].substring(j, j + 1))) {
          return result;
        }
      }
      result++;
    }
    return result;
  }
}
