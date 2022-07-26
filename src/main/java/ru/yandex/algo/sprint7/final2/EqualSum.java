// 69500190
package ru.yandex.algo.sprint7.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Задача разбиения множества чисел. Пытаемся найти подмножество, сумма которого равна половине суммы данного множества целых чисел 0...N.
 * Если сумма 0...N нечетна - такое невозможно (лучший случай). В dp храним ответы, существует ли такое подмножество, элементы в сумме
 * которого дают i. Ответ находиться в нижней правой ячейке с i=N и j=sum/2
 * <p>
 * Сложность по вычислению O(n*s), сложность по памяти O(n*s), где n - кол.-во элементов, s - сумма этих элементов
 */
public class EqualSum {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/final2/input01.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int numberCount = Integer.parseInt(in.readLine());
      final int[] number = new int[numberCount];
      final int numberSum = buildNumber(in.readLine(), number);
      if (numberSum % 2 != 0) {
        System.out.println("False");
        return;
      }
      if (isExistEqualSum(number, numberSum)) {
        System.out.println("True");
      } else {
        System.out.println("False");
      }
    }
  }

  private static boolean isExistEqualSum(int[] number, int numberSum) {
    final int halfNumberSum = numberSum / 2;
    final boolean[][] dp = new boolean[halfNumberSum + 1][number.length + 1];
    for (int i = 0; i <= number.length; i++) {
      dp[0][i] = true;
    }
    for (int i = 1; i <= halfNumberSum; i++) {
      for (int j = 1; j <= number.length; j++) {
        if (i - number[j - 1] >= 0) {
          dp[i][j] = dp[i][j - 1] || dp[i - number[j - 1]][j - 1];
        } else {
          dp[i][j] = dp[i][j - 1];
        }
      }
    }
    return dp[halfNumberSum][number.length];
  }

  // На задаче c графами накушался с TL и ML.
  // Тут решил перестраховаться, сложная реализация в один проход все делает, а сейчас в два (split + for).
  private static int buildNumber(String line, int[] number) {
    int i = 0;
    int numberSum = 0;
    for (String s : line.split(" ")) {
      number[i] = Integer.parseInt(s);
      numberSum += number[i];
      ++i;
    }
    return numberSum;
  }
}
