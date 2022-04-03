package ru.yandex.algo.intro.d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class D1 {

  // Если ответ существует, верните список из двух элементов
  // Если нет - то верните пустой список
  private static List<Integer> twoSum(List<Integer> arr, int targetSum) {
    int arrSize = arr.size();
    List<Integer> result = new ArrayList<>(2);
    Collections.sort(arr);
    int left = 0;
    int right = arrSize - 1;

    while (left < arrSize && right > 0 && left != right) {
      int sum = arr.get(left) + arr.get(right);
      if (sum == targetSum) {
        result.add(arr.get(left));
        result.add(arr.get(right));
        break;
      } else if (sum < targetSum) {
        left++;
      } else if (sum > targetSum) {
        right--;
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
