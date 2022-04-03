package ru.yandex.algo.intro.d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class D2 {

  // Если ответ существует, верните список из двух элементов
  // Если нет - то верните пустой список
  private static List<Integer> twoSum(List<Integer> arr, int targetSum) {
    int arrSize = arr.size();
    Set<Integer> integerSet = new HashSet<>(arrSize);
    List<Integer> result = new ArrayList<>(2);
    for (int i = 0; i < arrSize; i++) {
      final int sub = targetSum - arr.get(i);
      if (integerSet.contains(sub)) {
        result.add(arr.get(i));
        result.add(sub);
        break;
      } else {
        integerSet.add(arr.get(i));
      }
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
/*    System.out.println(test1());
    System.out.println(test2());
    System.out.println(test3());*/

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      int n = readInt(reader);
      List<Integer> arr = readList(reader);
      int targetSum = readInt(reader);
      List<Integer> result = twoSum(arr, targetSum);
      if (result.isEmpty()) {
        System.out.println("None");
      } else {
        System.out.println(result.get(0) + " " + result.get(1));
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

  private static List<Integer> test1() {
    List<Integer> arr = new ArrayList<>();
    arr.add(-1);
    arr.add(-1);
    arr.add(-9);
    arr.add(-7);
    arr.add(3);
    arr.add(-6);
    return twoSum(arr, 2);
  }

  private static List<Integer> test2() {
    List<Integer> arr = new ArrayList<>();
    arr.add(6);
    arr.add(2);
    arr.add(8);
    arr.add(-3);
    arr.add(1);
    arr.add(1);
    arr.add(6);
    arr.add(10);
    return twoSum(arr, 100);
  }

  private static List<Integer> test3() {
    List<Integer> arr = new ArrayList<>();
    //-93 67 84 -30 -96 22 40 -11 -39 11
    arr.add(-93);
    arr.add(67);
    arr.add(84);
    arr.add(-30);
    arr.add(-96);
    arr.add(22);
    arr.add(40);
    arr.add(-11);
    arr.add(-39);
    arr.add(-11);
    return twoSum(arr, -186);
  }
}
