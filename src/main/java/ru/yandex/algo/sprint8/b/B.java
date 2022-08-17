package ru.yandex.algo.sprint8.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class B {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/b/input04.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      String line1 = in.readLine();
      String line2 = in.readLine();
      if (equals(line1, line2)) {
        System.out.println("OK");
      } else {
        System.out.println("FAIL");
      }
    }
  }

  private static boolean equals(String line1, String line2) {
    if (Math.abs(line1.length() - line2.length()) > 1) {
      return false;
    }
    if (line1.length() > line2.length()) {
      return equals(line2, line1);
    }
    int line1Pos = 0;
    int line2Pos = 0;
    boolean oneDiff = false;
    while (line1Pos < line1.length() && line2Pos < line2.length()) {
      if (!line1.substring(line1Pos, line1Pos + 1).equals(line2.substring(line2Pos, line2Pos + 1))) {
        if (oneDiff) {
          return false;
        }
        oneDiff = true;
        if (line1.length() == line2.length()) {
          line1Pos++;
        }
      } else {
        line1Pos++;
      }
      line2Pos++;
    }
    return true;
  }
}
