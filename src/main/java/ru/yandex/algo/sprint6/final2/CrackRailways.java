//package ru.yandex.algo.sprint6.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CrackRailways {

  private static final String SEPARATOR = " ";
  private static final String FILE = "input.txt";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final2/input03.txt";

  public static void main(String[] args) throws IOException {
    final int vertexCount;
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      if (vertexCount <= 50) {
        for (int i = 1; i < vertexCount; i++) {
          final String s = in.readLine();
          if (i > 28) {
            System.out.println(s);
          }
        }
      }
    }
  }
}