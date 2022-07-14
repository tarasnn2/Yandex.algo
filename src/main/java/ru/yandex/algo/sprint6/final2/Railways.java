package ru.yandex.algo.sprint6.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Railways {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final2/input15.txt";

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      vertexCount = Integer.parseInt(in.readLine());
      graph = buildGraphs(vertexCount, in);
    }
    if (isMapOptimal(graph, vertexCount)) {
      System.out.print("YES");
    } else {
      System.out.print("NO");
    }
  }

  private static void recursiveDFS(Graph graph, Vertex vertexV, Colors colors) {
    colors.markColor(vertexV, Color.GRAY);
    final Queue<Edge> edges = graph.getEdges(vertexV);
    while (null != edges && !edges.isEmpty()) {
      final Vertex vertexW = edges.poll().getW();
      if (colors.isGray(vertexW)) {
        throw new NoOptimalMap("The map isn't optimal: " + vertexW);
      } else if (colors.isWhite(vertexW)) {
        recursiveDFS(graph, vertexW, colors);
      }
    }
    colors.markColor(vertexV, Color.BLACK);
  }

  private static boolean isMapOptimal(final Graph graph, int vertexCount) {
    final Colors colors = new Colors(vertexCount);
    Vertex startVertex = graph.getVertexByNumber(colors.getFirstWhiteVertexNumber());
    try {
      while (null != startVertex) {
        recursiveDFS(graph, startVertex, colors);
        startVertex = graph.getVertexByNumber(colors.getFirstWhiteVertexNumber());
      }
      return true;
    } catch (NoOptimalMap e) {
      return false;
    }
  }

  private static Graph buildGraphs(int vertexCount, BufferedReader in) throws IOException {
    final Graph graph = new Graph(vertexCount);
    for (int i = 1; i < vertexCount; i++) {
      final String[] l = in.readLine().split("");
      for (int j = 1; j <= l.length; j++) {
        final String type = l[j - 1];
        final Edge edge;
        if ("R".equals(type)) {
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

  private final Color[] color;

  public Colors(int capacity) {
    color = new Color[capacity];
  }

  void markColor(Vertex v, Color c) {
    color[v.getNumber() - 1] = c;
  }

  boolean isWhite(Vertex v) {
    return isWhite(v.getNumber());
  }

  boolean isWhite(int vertexNumber) {
    return color[vertexNumber - 1] == null || color[vertexNumber - 1] == Color.WHILE;
  }

  boolean isGray(Vertex v) {
    return color[v.getNumber() - 1] == Color.GRAY;
  }

  int getFirstWhiteVertexNumber() {
    for (int i = 1; i <= color.length; i++) {
      if (isWhite(i)) {
        return i;
      }
    }
    return -1;
  }
}

enum Color {
  WHILE,
  GRAY,
  BLACK;
}

class Graph {

  private final Queue<Edge>[] store;

  @SuppressWarnings("unchecked")
  public Graph(int capacity) {
    store = new LinkedList[capacity];
  }

  void addEdgeToVertex(Edge edge) {
    addEdgeToVertex(edge.getV(), edge);
  }

  void addEdgeToVertex(Vertex v, Edge edge) {
    Queue<Edge> edges = getEdges(v);
    if (null == edges) {
      edges = new LinkedList<>();
      store[v.getNumber() - 1] = edges;
    }
    edges.add(edge);
  }


  Queue<Edge> getEdges(Vertex v) {
    final int i = v.getNumber() - 1;
    return store[i];
  }

  Vertex getVertexByNumber(int vertexNumber) {
    if (0 > vertexNumber) {
      return null;
    }
    final Queue<Edge> o = store[vertexNumber - 1];
    if (Objects.isNull(o)) {
      return null;
    }
    return o.peek().getV();
  }
}

class Vertex {

  private final int number;

  public int getNumber() {
    return number;
  }

  public Vertex(int number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return number + "";
  }

}

class Edge {

  private final Vertex v;
  private final Vertex w;

  public Edge(Vertex v, Vertex w) {
    this.v = v;
    this.w = w;
  }

  public Edge(int v, int w) {
    this(new Vertex(v), new Vertex(w));
  }

  public Vertex getV() {
    return v;
  }

  public Vertex getW() {
    return w;
  }
}