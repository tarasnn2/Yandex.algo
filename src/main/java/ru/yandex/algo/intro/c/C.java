package ru.yandex.algo.intro.c;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class C {

  private static List<Double> movingAverage(int n, List<Integer> arr, int windowSize) {
    List<Double> result = new ArrayList<>(n - windowSize + 1);
    double current_sum = arr.stream().limit(windowSize).mapToInt(Integer::intValue).sum();
    result.add(current_sum / windowSize);
    int limit = n - windowSize;
    for (int i = 0; i < limit; i++) {
      current_sum -= arr.get(i);
      current_sum += arr.get(i + windowSize);
      result.add(current_sum / windowSize);
    }
    return result;
  }

/*  private static void test1() {
    final ArrayList<Integer> arr = new ArrayList<>();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.add(5);
    arr.add(6);
    arr.add(7);
    System.out.println(movingAverage(7, arr, 4));
  }

  private static void test2() {
    final ArrayList<Integer> arr = new ArrayList<>();
    arr.add(9);
    arr.add(3);
    arr.add(2);
    arr.add(0);
    arr.add(1);
    arr.add(5);
    arr.add(1);
    arr.add(0);
    arr.add(0);
    System.out.println(movingAverage(9, arr, 3));
  }

  private static void test3() {
    final ArrayList<Integer> arr = new ArrayList<>();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.add(5);
    System.out.println(movingAverage(5, arr, 5));
  }*/

  public static void main(String[] args) throws IOException {
/*    test1();
    test2();
    test3();*/
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
      int n = readInt(reader);
      List<Integer> arr = readList(reader);
      int windowSize = readInt(reader);
      List<Double> result = movingAverage(n, arr, windowSize);
      for (double elem : result) {
        writer.write(elem + " ");
      }
    }
  }


  private static int readInt(BufferedReader reader) throws IOException {
    return Integer.parseInt(reader.readLine());
  }

  private static List<Integer> readList(BufferedReader reader) throws IOException {
    return Arrays.asList(reader.readLine().split(" "))
        .stream()
        .map(elem -> Integer.parseInt(elem))
        .collect(Collectors.toList());
  }
}
