package ru.yandex.algo.sprint6.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class C_ML {
  private static final String SEPARATOR = " ";
  private static final String ONE = "1";
  private static final String GRAY = "gray";
  private static final String BLACK = "black";
  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/c/input00.txt";

  public static void main(String[] args) throws IOException {
    final boolean[][] matrix;
    final int n;
    final int m;
    final int s;
    final String[] color;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      n = Integer.parseInt(firstLine[0]);
      m = Integer.parseInt(firstLine[1]);
      matrix = buildMatrixNonOriented(n, m, in);
      s = Integer.parseInt(in.readLine());
    }
    color = new String[n];
    StringBuilder sb = new StringBuilder(n * 2);
    sb.append(s).append(SEPARATOR);
    recursiveDFS(matrix, color, n, s, sb);
    System.out.print(sb);
  }
  private static void recursiveDFS(boolean[][] matrix, String[] color, final int n, final int v, StringBuilder sb) {
    final int indexV = v - 1;
    if (BLACK.equals(color[indexV])) {
      return;
    }
    color[indexV] = GRAY;
    for (int indexW = 0; indexW < n; indexW++) {
      final boolean v2IsVertex = matrix[indexV][indexW];
      if (v2IsVertex && null == color[indexW]) {
        color[indexW] = GRAY;
        final int w = indexW + 1;
        sb.append(w).append(SEPARATOR);
        recursiveDFS(matrix, color, n, w, sb);
      }
    }
    color[indexV] = BLACK;
  }

  private static boolean[][] buildMatrixNonOriented(final int n, final int m, BufferedReader in) throws IOException {
    final boolean[][] matrix = new boolean[n][n];
    for (int i = 1; i <= m; i++) {
      final String[] line = in.readLine().split(SEPARATOR);
      final int vertexFrom = Integer.parseInt(line[0]);
      final int vertexTo = Integer.parseInt(line[1]);
      matrix[vertexFrom - 1][vertexTo - 1] = true;
      matrix[vertexTo - 1][vertexFrom - 1] = true;
    }
    return matrix;
  }
}
