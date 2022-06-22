package ru.yandex.algo.sprint6.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class B {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  // private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/b/input00.txt";

  public static void main(String[] args) throws IOException {
    final String[][] matrix;
    final int n;
    final int m;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      n = Integer.parseInt(firstLine[0]);
      m = Integer.parseInt(firstLine[1]);
      matrix = buildMatrix(n, m, in);
    }
    System.out.println(buildReport(matrix, n));
  }

  private static StringBuilder buildReport(String[][] matrix, final int n) {
    final StringBuilder sb = new StringBuilder(n);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(matrix[i][j] == null ? "0" : matrix[i][j]).append(SEPARATOR);
      }
      sb.append(DELIMITER);
    }
    return sb;
  }

  private static String[][] buildMatrix(final int n, final int m, BufferedReader in) throws IOException {
    final String[][] matrix = new String[n][n];
    for (int i = 1; i <= m; i++) {
      final String[] line = in.readLine().split(SEPARATOR);
      final int vertexFrom = Integer.parseInt(line[0]);
      final int vertexTo = Integer.parseInt(line[1]);
      matrix[vertexFrom - 1][vertexTo - 1] = "1";
    }
    return matrix;
  }
}
