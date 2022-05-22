package ru.yandex.algo.sprint4.g;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class G {
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/g/input0n.txt";
  // private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int resultCount = Integer.parseInt(in.readLine());

      if (resultCount <= 1) {
        System.out.println(0);
        return;
      }

      final int[] results = getResults(in, resultCount);

      calcForDraw(results);
      calcForDrawTL(results);
    }
  }

  private static void calcForDrawTL(int[] results) {
    int maxLength = 0;
    for (int i = 0; i < results.length; i++) {
      int value = results[i];
      int iNext = i + 1;
      for (int j = iNext; j < results.length; j++) {
        value += results[j];
        int length = j - i + 1;
        if (value == 0 && length > maxLength) {
          maxLength = length;
          System.out.println(i + " " + j +" = "+length);
        }
        if (maxLength >= results.length - i) {
          break;
        }
      }
      if (maxLength >= results.length - i) {
        break;
      }
    }
    System.out.println(maxLength);
  }

  private static void calcForDraw(int[] results) {
    Map<Integer, List<Integer>> score = new HashMap<>();
    score.put(2, new ArrayList<>());
    score.put(-2, new ArrayList<>());
    score.put(0, new ArrayList<>());

    for (int i = 0; i < results.length - 1; i++) {
      final int iNext = i + 1;
      final int sumsScore = results[i] + results[iNext];
      score.get(sumsScore).add(iNext);
    }
    if (score.get(-2).size() > score.get(2).size()) {
      calc(score.get(-2), score.get(2));
    } else {
      calc(score.get(2), score.get(-2));
    }
  }

  private static void calc(List<Integer> max, List<Integer> min) {
    int range = 0;
    int endIndexPrev = 0;
    for (int i = 0; i < min.size(); i++) {

      final Integer indexFromMin = min.get(i);
      final Integer indexFromMax = max.get(i);
      int startIndex = indexFromMin < indexFromMax ? indexFromMin : indexFromMax;
      int endIndex = indexFromMax >= indexFromMin ? indexFromMax : indexFromMin;

      int newRange = (endIndex - startIndex) + 1;

      if (startIndex - endIndexPrev == 1) {
        range += newRange;
      } else {
        range = newRange;
      }
      endIndexPrev = endIndex;
    }

    System.out.println(range);
  }

  private static int[] getResults(BufferedReader in, int resultCount) throws IOException {
    /*      final Integer[] results =
    Arrays.stream(in.readLine().split(" "))
        .map(s -> s.equals("1") ? 1 : -1)
        .collect(Collectors.toList())
        .toArray(new Integer[resultCount]);*/

    int[] result = new int[resultCount];
    final String line = in.readLine();
    int start = 0;
    String pattern = " ";
    for (int i = 0; i < resultCount; i++) {
      final int o = line.indexOf(pattern, start);
      final int off = o > -1 ? o : line.length();
      final String digit = line.substring(start, off);
      result[i] = digit.equals("1") ? 1 : -1;
      start += digit.length() + pattern.length();
    }
    return result;
  }
}
