package ru.yandex.algo.sprint3.l;// package ru.yandex.algo.sprint3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class L {
  private static final String SEPARATOR = " ";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/input7.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    final String[] savings;
    final int bicyclePrice;
    final int daysCount;

    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      daysCount = Integer.parseInt(in.readLine());
      savings = readList(in);
      bicyclePrice = Integer.parseInt(in.readLine());
    }
    final int left = 0;
    final int right = daysCount - 1;

    final int day1 = shiftToRealDay(findBuyingDay(savings, left, right, bicyclePrice));
    final int day2 = shiftToRealDay(findBuyingDay(savings, left, right, bicyclePrice * 2));

    System.out.println(day1 + SEPARATOR + day2);
  }

  private static int shiftToRealDay(int day) {
    if (-1 == day) {
      return -1;
    } else {
      return day + 1;
    }
  }

  private static String[] readList(BufferedReader reader) throws IOException {
    return reader.readLine().split(SEPARATOR);
  }

  private static int findBuyingDay(String[] savings, int left, int right, int bicyclePrice) {
    final List<Integer> buyingDays = findBuyingDays(savings, left, right, bicyclePrice, new ArrayList<>(savings.length));
    if (buyingDays.isEmpty()) {
      return -1;
    } else if (1 == buyingDays.size()) {
      return buyingDays.get(0);
    } else {
      Collections.sort(buyingDays);
      return buyingDays.get(0);
    }
  }

  private static List<Integer> findBuyingDays(String[] savings, int left, int right, int bicyclePrice, List<Integer> buyingDays) {
    if (left > right) {
      return buyingDays;
    }
    final int mid = (left + right) / 2;
    final int saving = Integer.parseInt(savings[mid]);
    if (saving < bicyclePrice) {
      findBuyingDays(savings, mid + 1, right, bicyclePrice, buyingDays);
    } else {
      buyingDays.add(mid);
      findBuyingDays(savings, left, mid - 1, bicyclePrice, buyingDays);
    }
    return buyingDays;
  }
}
