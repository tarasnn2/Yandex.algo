package ru.yandex.algo.sprint1.g;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class G {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/g/input2.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      log(convert(Integer.parseInt(in.readLine()), 2));
    } catch (IOException e) {
      log(e.getMessage());
    }
  }

  private static String convert(int digit, int base) {
    StringBuilder result = new StringBuilder();
    while (digit >= base) {
      result.append(digit % base);
      digit = digit / base;
      if (digit < base) {
        result.append(digit);
      }
    }
    return result.reverse().toString();
  }

  private static void log(String message) {
    System.out.println(message);
  }
}
