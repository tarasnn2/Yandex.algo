//package ru.yandex.algo.sprint1.e;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class E {

  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/e/input1.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) {
    String result = null;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      int textLength = Integer.parseInt(in.readLine());
      String[] text = parseText(in, textLength);
      result = findMaxLengthWord(text);
    } catch (IOException e) {
      log(e.getMessage());
    }
    log(result + DELIMITER + result.length());
  }

  private static String findMaxLengthWord(String[] text) {
    String result = "";
    for (String s : text) {
      if (s.length() > result.length()) {
        result = s;
      }
    }
    return result;
  }

  private static String[] parseText(BufferedReader in, int textLength) throws IOException {
    String str = in.readLine().trim();
    return str.split(SEPARATOR, textLength);
  }

  private static void log(String message) {
    System.out.println(message);
  }
}
