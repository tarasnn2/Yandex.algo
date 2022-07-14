package ru.yandex.algo.sprint6.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class Railways {

  private static final String R = "R";

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final2/input15.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int vertexCount = Integer.parseInt(in.readLine());
      final Graph graph = buildGraphs(vertexCount, in);
      if (isMapOptimal(graph, vertexCount)) {
        System.out.print("YES");
      } else {
        System.out.print("NO");
      }
    }

  }

  private static void recursiveDFS(Graph graph, int vertexV, Colors colors) {
    colors.markColor(vertexV, Colors.GRAY);
    final Queue<Edge> edges = graph.getEdges(vertexV);
    while (null != edges && !edges.isEmpty()) {
      final int vertexW = edges.poll().getW();
      if (colors.isGray(vertexW)) {
        throw new NoOptimalMap("The map isn't optimal: " + vertexW);
      } else if (colors.isWhite(vertexW)) {
        recursiveDFS(graph, vertexW, colors);
      }
    }
    colors.markColor(vertexV, Colors.BLACK);
  }

  private static boolean isMapOptimal(final Graph graph, int vertexCount) {
    final Colors colors = new Colors(vertexCount);
    int startVertex = colors.getFirstWhiteVertex();
    while (-1 != startVertex) {
      try {
        recursiveDFS(graph, startVertex, colors);
      } catch (NoOptimalMap e) {
        return false;
      }
      startVertex = colors.getFirstWhiteVertex();
    }
    return true;
  }

  private static Graph buildGraphs(int intCount, BufferedReader in) throws IOException {
    final Graph graph = new Graph(intCount);
    for (int i = 1; i < intCount; i++) {
      final String[] l = in.readLine().split("");
      for (int j = 1; j <= l.length; j++) {
        final String type = l[j - 1];
        final Edge edge;
        if (R.equals(type)) {
          edge = new Edge(i + j, i);
        } else {
          edge = new Edge(i, i + j);
        }
        graph.addEdgeToVertex(edge);
      }
    }
    return graph;
  }

  private static class NoOptimalMap extends RuntimeException {

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

  int getFirstWhiteVertex() {
    for (int i = 1; i <= color.length; i++) {
      if (isWhite(i)) {
        return i;
      }
    }
    return -1;
  }
}

class Graph {

  private final Queue<Edge>[] store;

  @SuppressWarnings("unchecked")
  public Graph(int capacity) {
    store = new LinkedList[capacity];
  }

  void addEdgeToVertex(Edge edge) {
    final int v = edge.getV();
    Queue<Edge> edges = getEdges(v);
    if (null == edges) {
      edges = new LinkedList<>();
      store[v - 1] = edges;
    }
    edges.add(edge);
  }

  Queue<Edge> getEdges(int v) {
    final int i = v - 1;
    return store[i];
  }
}

class Edge {

  private final int v;
  private final int w;

  public Edge(int v, int w) {
    this.v = v;
    this.w = w;
  }

  public int getV() {
    return v;
  }

  public int getW() {
    return w;
  }
}