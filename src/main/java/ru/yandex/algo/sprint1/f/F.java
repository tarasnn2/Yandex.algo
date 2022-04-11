//package ru.yandex.algo.sprint1.f;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class F {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String SPECIAL_SYMBOL = "[// , //:, //.]";
  private static final String EMPTY_STRING = "";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/f/input2.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) {
    boolean result = false;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      String text = prepareText(in);
      result = isPalindrome(text);
    } catch (IOException e) {
      log(e.getMessage());
    }
    log(result ? "True" : "False");
  }

  private static boolean isPalindrome(String text) {
    // log(text);
    boolean result = true;
    int limit = text.length() / 2;
    for (int left = 0; left <= limit; left++) {
      int right = text.length() - left;
      final String leftLetter = text.substring(left, left + 1);
      final String rightLetter = text.substring(right - 1, right);
      // log(leftLetter + "==" + rightLetter);
      if (!leftLetter.equals(rightLetter)) {
        result = false;
        break;
      }
    }
    return result;
  }

  private static String prepareText(BufferedReader in) throws IOException {
    return in.readLine().trim().toLowerCase().replaceAll(SPECIAL_SYMBOL, EMPTY_STRING);
  }

  private static void log(String message) {
    System.out.println(message);
  }
}
