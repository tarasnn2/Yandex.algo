package ru.yandex.algo.sprint4.f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class F1 {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/f/input01.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    final String[] str;
    final int strLength;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.US_ASCII))) {
      strLength = Integer.parseInt(in.readLine());
      str = in.readLine().split(" ");
    }
    String[] words = new String[strLength];
    for (int i = 0; i < strLength; i++) {
      final char[] strArr = str[i].toCharArray();
      Arrays.sort(strArr);
      words[i] = new String(strArr);
    }

    Map<String, List<Integer>> data = new HashMap<>();
    List<String> order = new ArrayList<>();
    for (int i = 0; i < str.length; i++) {
      List<Integer> wordStat = data.get(words[i]);
      if (null == wordStat) {
        order.add(words[i]);
        wordStat = new ArrayList<>();
        data.put(words[i], wordStat);
      }
      wordStat.add(i);
    }

    //int[][] positions = new int[order.size()][];
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < order.size(); i++) {
      final List<Integer> orders = data.get(order.get(i));
      //positions[i] = new int[order.size()];
      for (int j = 0; j < orders.size(); j++) {
        //positions[i][j] = orders.get(j);
        sb.append(orders.get(j)).append(" ");
      }
      sb.append("\n");
    }

    System.out.print(sb);
  }
}
