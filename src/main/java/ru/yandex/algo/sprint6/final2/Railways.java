// 69405381
package ru.yandex.algo.sprint6.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Ищем цикличность в графе через dfs. Если цикличность есть - карта не оптимальная.
 * <p>Сложность по памяти O(v+e)
 * <p>Сложность по вычислению O(v + e)
 * <p>где v - кол-во вершин, e - кол-во ребер
 */
public class Railways {

  private static final String R = "R";

  //private static final String FILE = "input.txt";

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final2/input15.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int vertexCount = Integer.parseInt(in.readLine());
      final Graph graph = buildGraphs(vertexCount, in);
      if (isMapOptimal(graph)) {
        System.out.print("YES");
      } else {
        System.out.print("NO");
      }
    }

  }

  private static void recursiveDFS(int vertexV, Graph graph, Colors colors) throws NoOptimalMap {
    colors.markColor(vertexV, Colors.GRAY);
    final Queue<Integer> edges = graph.getWs(vertexV);
    while (null != edges && !edges.isEmpty()) {
      final int vertexW = edges.poll();
      if (colors.isGray(vertexW)) {
        throw new NoOptimalMap("The map isn't optimal: " + vertexW);
      } else if (colors.isWhite(vertexW)) {
        recursiveDFS(vertexW, graph, colors);
      }
    }
    colors.markColor(vertexV, Colors.BLACK);
  }

  private static boolean isMapOptimal(Graph graph) {
    final Colors colors = new Colors(graph.getVertexCount());
    int startVertex = colors.getNextWhiteVertex(1);
    while (-1 != startVertex) {
      try {
        recursiveDFS(startVertex, graph, colors);
      } catch (NoOptimalMap e) {
        return false;
      }
      startVertex = colors.getNextWhiteVertex(startVertex + 1);
    }
    return true;
  }

  private static Graph buildGraphs(int intCount, BufferedReader in) throws IOException {
    final Graph graph = new Graph(intCount);
    for (int v = 1; v < intCount; v++) {
      final String[] l = in.readLine().split("");
      for (int j = 1; j <= l.length; j++) {
        final String type = l[j - 1];
        if (R.equals(type)) {
          graph.addWToV(v + j, v);
        } else {
          graph.addWToV(v, v + j);
        }
      }
    }
    return graph;
  }

  private static class NoOptimalMap extends Exception {

    public NoOptimalMap(String message) {
      super(message);
    }
  }

}

class Colors {

  static final int WHITE = 0;
  static final int GRAY = 1;
  static final int BLACK = 2;
  private final int[] color;

  public Colors(int capacity) {
    color = new int[capacity];
  }

  void markColor(int v, int c) {
    color[v - 1] = c;
  }

  boolean isWhite(int v) {
    return color[v - 1] == WHITE;
  }

  boolean isGray(int v) {
    return color[v - 1] == GRAY;
  }

  int getNextWhiteVertex(int start) {
    for (int i = start; i <= color.length; i++) {
      if (isWhite(i)) {
        return i;
      }
    }
    return -1;
  }
}

class Graph {

  private final int vertexCount;
  private final Queue<Integer>[] store;

  @SuppressWarnings("unchecked")
  public Graph(int capacity) {
    store = new ArrayDeque[capacity];
    this.vertexCount = capacity;
  }

  void addWToV(int v, int w) {
    Queue<Integer> edges = getWs(v);
    if (null == edges) {
      edges = new ArrayDeque<>();
      store[v - 1] = edges;
    }
    edges.add(w);
  }

  Queue<Integer> getWs(int v) {
    final int i = v - 1;
    return store[i];
  }

  public int getVertexCount() {
    return vertexCount;
  }
}