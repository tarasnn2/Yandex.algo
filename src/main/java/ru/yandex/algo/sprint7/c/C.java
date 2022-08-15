package ru.yandex.algo.sprint7.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class C {

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/c/input02.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int capacity = Integer.parseInt(in.readLine());
      final int n = Integer.parseInt(in.readLine());

      Item[] items = new Item[n];

      for (int i = 0; i < n; i++) {
        String[] line = in.readLine().split(" ");
        items[i] = new Item(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
      }
      System.out.println(calc(items, capacity));
    }
  }

  private static long calc(Item[] items, int capacity) {
    Arrays.sort(items, (i1, i2) -> Integer.compare(i2.cost, i1.cost));
    long result = 0;
    for (Item item : items) {
      if (capacity == 0) {
        break;
      }
      int maxWeight = Math.min(capacity, item.weight);
      result += ((long) maxWeight * item.cost);
      capacity -= maxWeight;

    }
    return result;
  }

  static class Item {

    public Item(int cost, int weight) {
      this.cost = cost;
      this.weight = weight;
    }

    int cost;
    int weight;
  }

}

