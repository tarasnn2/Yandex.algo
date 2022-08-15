package ru.yandex.algo.sprint8.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class A {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/a/input03.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      String[] words = in.readLine().split(" ");
      StringBuilder sb = new StringBuilder(words.length * 2);
      for (int i = words.length-1; i >= 0; i--) {
        sb.append(words[i]).append(" ");
      }
      System.out.println(sb);
    }
  }
}
