package ru.yandex.algo.sprint1.h;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class H {
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/h/input1.txt";
  // private static final String FILE = "input.txt";

  public static void main(String[] args) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      final String binary1 = in.readLine();
      final String binary2 = in.readLine();
      if (binary1.length() >= binary2.length()) {
        log(sum(binary1, binary2));
      } else {
        log(sum(binary2, binary1));
      }
    } catch (IOException e) {
      log(e.getMessage());
    }
  }

  private static String sum(String binaryMore, String binaryLess) {
    StringBuilder result = new StringBuilder();

    boolean overload = false;
    for (int i = binaryMore.length() - 1; i >= 0; i--) {
      int next = i - 1;
      String b1 = binaryMore.substring(next, i);
      String b2 = binaryLess.substring(next, i);
      if ("0".equals(b1) && "0".equals(b2)) {
        if (overload) {
          result.append("1");
        } else {
          result.append("0");
        }
      }
      if ("1".equals(b1) && "1".equals(b2)) {
        result.append("0");
        overload = true;
      }
      if (("0".equals(b1) && "1".equals(b2) || "1".equals(b1) && "0".equals(b2))) {
        result.append("1");
      }
    }

    return result.reverse().toString();
  }

  private static void log(String message) {
    System.out.println(message);
  }
}
