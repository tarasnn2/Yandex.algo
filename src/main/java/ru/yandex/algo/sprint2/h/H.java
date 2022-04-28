//package ru.yandex.algo.sprint2.h;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;

public class H {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint2/h/input6.txt";
  private static final String FILE = "input.txt";
  private static final String LEFT_BRACKET_TYPE_1 = "{";
  private static final String LEFT_BRACKET_TYPE_2 = "(";
  private static final String LEFT_BRACKET_TYPE_3 = "[";
  private static final String RIGHT_BRACKET_TYPE_1 = "}";
  private static final String RIGHT_BRACKET_TYPE_2 = ")";
  private static final String RIGHT_BRACKET_TYPE_3 = "]";

  public static void main(String[] args) throws IOException {
    final String line;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      line = in.readLine();
    }
    if (isRightSequence(line)) {
      System.out.println("True");
    } else {
      System.out.println("False");
    }
  }

  private static boolean isRightSequence(String line) {
    if (line.isEmpty()) {
      return true;
    }
    boolean result = true;
    final Deque<String> queue = new LinkedList<>();
    for (String s : line.split("")) {
      if (s.equals(LEFT_BRACKET_TYPE_1) || s.equals(LEFT_BRACKET_TYPE_2) || s.equals(LEFT_BRACKET_TYPE_3)) {
        queue.push(s);
      } else {
        if (queue.isEmpty()) {
          result = false;
          break;
        }
        final String fromQueue = queue.pop();
        if (!((LEFT_BRACKET_TYPE_1.equals(fromQueue) && RIGHT_BRACKET_TYPE_1.equals(s))
            || (LEFT_BRACKET_TYPE_2.equals(fromQueue) && RIGHT_BRACKET_TYPE_2.equals(s))
            || (LEFT_BRACKET_TYPE_3.equals(fromQueue) && RIGHT_BRACKET_TYPE_3.equals(s)))) {
          result = false;
          break;
        }
      }
    }
    if (!queue.isEmpty()) {
      return false;
    }

    return result;
  }
}
