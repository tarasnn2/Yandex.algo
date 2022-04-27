package ru.yandex.algo.sprint2.a;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class A {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String EMPTY_STRING = "";

  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint2/a/input2.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      final int row = Integer.parseInt(in.readLine());
      final int col = Integer.parseInt(in.readLine());
      final String[][] income = getIncome(row, col, in);
      final String[][] outcome = transpose(row, col, income);
      print(outcome, row);
    }
  }

  private static void print(final String[][] outcome, final int symbolInRow) {
    final StringBuilder s = new StringBuilder();
    for (int i = 0; i < outcome.length; i++) {
      for (int j = 0; j < outcome[i].length; j++) {
        s.append(outcome[i][j]).append(SEPARATOR);
        if (j == (symbolInRow - 1)) {
          s.append(DELIMITER);
        }
      }
    }
    System.out.println(s);
  }

  private static String[][] getIncome(final int row, final int col, final BufferedReader in) throws IOException {
    final String[][] result = new String[row][col];
    for (int i = 0; i < row; i++) {
      result[i] = in.readLine().split(SEPARATOR);
    }
    return result;
  }

  private static String[][] transpose(final int row, final int col, final String[][] income) {
    final String[][] outcome = new String[col][row];
    for (int i = 0; i < income.length; i++) {
      for (int j = 0; j < income[i].length; j++) {
        outcome[j][i] = income[i][j];
      }
    }
    return outcome;
  }
}
