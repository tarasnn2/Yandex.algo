package ru.yandex.algo.sprint6.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  // private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/a/input00.txt";

  public static void main(String[] args) throws IOException {
    final Map<String, List<String>> map;
    final int n;
    final int m;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      n = Integer.parseInt(firstLine[0]);
      m = Integer.parseInt(firstLine[1]);
      map = buildMap(m, in);
    }
    System.out.println(buildAdjacencyList(map, n));
  }

  private static StringBuilder buildAdjacencyList(final Map<String, List<String>> map, final int n) {
    final StringBuilder sb = new StringBuilder(n);
    for (int vertexFrom = 1; vertexFrom <= n; vertexFrom++) {
      final List<String> vertexesTo = map.getOrDefault(vertexFrom + "", List.of());
      sb.append(vertexesTo.size());
      if (!vertexesTo.isEmpty()) {
        sb.append(SEPARATOR).append(String.join(SEPARATOR, vertexesTo));
      }
      sb.append(DELIMITER);
    }
    return sb;
  }

  private static Map<String, List<String>> buildMap(final int m, BufferedReader in) throws IOException {
    final Map<String, List<String>> map = new HashMap<>(m);
    for (int i = 1; i <= m; i++) {
      final String[] line = in.readLine().split(SEPARATOR);
      final String vertexFrom = line[0];
      final String vertexTo = line[1];
      final List<String> vertexesTo = map.getOrDefault(vertexFrom, new ArrayList<>());
      vertexesTo.add(vertexTo);
      map.putIfAbsent(vertexFrom, vertexesTo);
    }
    return map;
  }
}
