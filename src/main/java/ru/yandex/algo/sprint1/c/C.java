//package ru.yandex.algo.sprint1.c;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class C {

  private static final String SEPARATOR = " ";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/c/input01.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) {
    List<Integer> neighbours = null;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      int row = Integer.parseInt(in.readLine());
      int col = Integer.parseInt(in.readLine());
      int[][] matrix = makeMatrix(in, row, col);
      int rowPoint = Integer.parseInt(in.readLine());
      int colPoint = Integer.parseInt(in.readLine());
      neighbours = getNeighbours(matrix, rowPoint, colPoint, row, col);
    } catch (IOException e) {
      log(e.getMessage());
    }
    Collections.sort(neighbours);
    print(neighbours);
  }

  private static void print(List<Integer> list) {
    final StringBuilder builder = new StringBuilder(list.size() * 2);
    list.forEach(integer -> builder.append(integer).append(SEPARATOR));
    log(builder.toString());
  }

  private static List<Integer> getNeighbours(int[][] matrix, int rowPoint, int colPoint, int row, int col) {
    final ArrayList<Integer> result = new ArrayList<>();
    int rowPointNext = rowPoint + 1;
    int rowPointPrevious = rowPoint - 1;
    int colPointNext = colPoint + 1;
    int colPointPrevious = colPoint - 1;
    if (rowPointNext < row) {
      result.add(matrix[rowPointNext][colPoint]);
    }
    if (rowPointPrevious >= 0) {
      result.add(matrix[rowPointPrevious][colPoint]);
    }
    if (colPointNext < col) {
      result.add(matrix[rowPoint][colPointNext]);
    }
    if (colPointPrevious >= 0) {
      result.add(matrix[rowPoint][colPointPrevious]);
    }
    return result;
  }

  private static int[][] makeMatrix(BufferedReader in, int row, int col) throws IOException {
    int[][] matrix = new int[row][col];
    for (int i = 0; i < row; i++) {
      String[] s = in.readLine().split(SEPARATOR);
      for (int j = 0; j < col; j++) {
        matrix[i][j] = Integer.parseInt(s[j]);
      }
    }
    return matrix;
  }

  private static void log(String message) {
    System.out.println(message);
  }
}
