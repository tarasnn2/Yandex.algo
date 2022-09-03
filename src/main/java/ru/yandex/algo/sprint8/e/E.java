package ru.yandex.algo.sprint8.e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class E {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/e/input01.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String s = in.readLine();
      final int n = Integer.parseInt(in.readLine());
      final Insertion[] insertions = new Insertion[n];

      for (int i = 0; i < n; i++) {
        String[] line = in.readLine().split(" ");
        insertions[i] = new Insertion(Integer.parseInt(line[1]), line[0]);
      }
      Arrays.sort(insertions, Comparator.comparingInt(i -> i.pos));
      System.out.println(build(s, insertions));
    }
  }

  private static String build(String s, Insertion[] insertions) {
    StringBuilder result = new StringBuilder();
    int current = 0;
    for (Insertion insertion : insertions) {
      while (current != insertion.pos) {
        result.append(s.charAt(current));
        current++;
      }
      result.append(insertion.str);
    }

    if (current < (s.length())) {
      result.append(s.substring(current));
    }
    return result.toString();
  }
}


class Insertion {

  public Insertion(int pos, String str) {
    this.pos = pos;
    this.str = str;
  }

  int pos;
  String str;
}