package ru.yandex.algo.sprint1.d;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class D {
  private static final String SEPARATOR = " ";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/d/input01.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) {
    int result = 0;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      int n = Integer.parseInt(in.readLine());
      if (n == 1) {
        log(String.valueOf(n));
        return;
      }
      int[] weather = parseWeather(in, n);
      result = calcChaos(weather);
    } catch (IOException e) {
      log(e.getMessage());
    }
    log(String.valueOf(result));
  }

  private static int calcChaos(int[] weather) {
    int result = 0;
    for (int current = 0; current < weather.length; current++) {
      int next = current + 1;
      int previous = current - 1;

      if (previous < 0) {
        if (weather[current] > weather[next]) {
          result++;
        }
        continue;
      }

      if (next == weather.length) {
        if (weather[current] > weather[previous]) {
          result++;
        }
        continue;
      }

      if (weather[previous] < weather[current] && weather[current] > weather[next]) {
        result++;
      }
    }

    return result;
  }

  private static int[] parseWeather(BufferedReader in, int n) throws IOException {
    int[] weather = new int[n];
    String[] s = in.readLine().split(SEPARATOR);
    for (int i = 0; i < n; i++) {
      weather[i] = Integer.parseInt(s[i]);
    }
    return weather;
  }

  private static void log(String message) {
    System.out.println(message);
  }
}
