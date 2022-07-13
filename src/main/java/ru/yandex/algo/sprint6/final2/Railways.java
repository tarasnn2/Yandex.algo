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

/**
 * Взять первую вершину v, покрасить ее в серый. Взять ребро из B, по нему дойти рекурсией до конца глубины, крася все в серый. На выходе из
 * рекурсии все покрасить в черный. Взять ребро из R, по нему дойти рекурсией до конца глубины, проверяя, не попадается ли черный цвет если
 * попался - NO
 */
public class Railways {

  private static final String SEPARATOR = " ";
  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final2/input02.txt";

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      graph = buildGraphs(vertexCount, in);
    }
    if (isMapOptimal(graph, vertexCount)) {
      System.out.print("YES");
    } else {
      System.out.print("NO");
    }
  }

  private static void recursiveDFS(Graph graph, Vertex vertexV, Colors colors, EdgeType type) {
    if (!colors.isBlackR(vertexV) && !colors.isBlackB(vertexV)) {
      colors.markColor(vertexV, Color.GRAY);
    }
    final Queue<Edge> edges = graph.getEdges(vertexV, type);
    final Color colorType = type == EdgeType.B ? Color.BLACK_B : Color.BLACK_R;
    while (null != edges && !edges.isEmpty()) {
      final Vertex vertexW = edges.poll().getW();
      if (colorType == Color.BLACK_B && colors.isBlackR(vertexW) || colorType == Color.BLACK_R && colors.isBlackB(vertexW)) {
        final String s = "The map isn't optimal: " + vertexV + " " + vertexW + " " + type;
        System.out.println(s);
        throw new NoOptimalMap(s);
      }
      if (colors.isWhite(vertexW)) {
        recursiveDFS(graph, vertexW, colors, type);
      }
    }
    colors.markColor(vertexV, colorType);
  }

  private static boolean isMapOptimal(final Graph graph, int vertexCount) {
    Vertex startVertex = graph.getFirstVertex(EdgeType.B);
    if (null == startVertex) {
      return true;
    }
    final Colors colors = new Colors(vertexCount);
    recursiveDFS(graph, startVertex, colors, EdgeType.B);
    try {
      startVertex = graph.getFirstVertex(EdgeType.R);
      if (null == startVertex) {
        return true;
      }
      recursiveDFS(graph, startVertex, colors, EdgeType.R);
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
        graph.addEdgeToVertex(new Edge(i, i + j, type));
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
    return color[v.getNumber() - 1] == null || color[v.getNumber() - 1] == Color.WHILE;
  }

  boolean isGray(Vertex v) {
    return color[v.getNumber() - 1] == Color.GRAY;
  }

  boolean isBlackR(Vertex v) {
    return color[v.getNumber() - 1] == Color.BLACK_R;
  }

  boolean isBlackB(Vertex v) {
    return color[v.getNumber() - 1] == Color.BLACK_B;
  }
}

enum Color {
  WHILE,
  GRAY,
  BLACK_R,
  BLACK_B;
}

class Graph {

  private final Object[] store;

  public Graph(int capacity) {
    store = new Object[capacity];
  }

  void addEdgeToVertex(Edge edge) {
    addEdgeToVertex(edge.getV(), edge);
  }

  void addEdgeToVertex(Vertex vertex, Edge edge) {
    Queue<Edge> edges;
    if (EdgeType.B == edge.getType()) {
      edges = getEdgesB(vertex);
    } else if (EdgeType.R == edge.getType()) {
      edges = getEdgesR(vertex);
    } else {
      throw new IllegalArgumentException(edge.getType().toString());
    }
    edges.add(edge);
  }

  private Queue<Edge> getEdgesB(Vertex v) {
    return getPair(v).getEdgesB();
  }

  private Queue<Edge> getEdgesR(Vertex v) {
    return getPair(v).getEdgesR();
  }

  private Pair getPair(Vertex v) {
    final int i = v.getNumber() - 1;
    Pair pair = (Pair) store[i];
    if (null == pair) {
      pair = new Pair();
      store[i] = pair;
    }
    return pair;
  }

  Vertex getFirstVertex(EdgeType type) {
    for (Object o : store) {
      if (null != o) {
        final Pair p = (Pair) o;
        if (EdgeType.B == type && !p.getEdgesB().isEmpty()) {
          return p.getEdgesB().peek().getV();
        }
        if (EdgeType.R == type && !p.getEdgesR().isEmpty()) {
          return p.getEdgesR().peek().getV();
        }
      }
    }
    return null;
  }

  Queue<Edge> getEdges(Vertex v, EdgeType type) {
    if (type == EdgeType.B) {
      return getEdgesB(v);
    } else if (type == EdgeType.R) {
      return getEdgesR(v);
    } else {
      throw new IllegalArgumentException(type.toString());
    }
  }

  private static class Pair {

    Queue<Edge> edgesR = new LinkedList<>();
    Queue<Edge> edgesB = new LinkedList<>();

    public Queue<Edge> getEdgesR() {
      return edgesR;
    }

    public Queue<Edge> getEdgesB() {
      return edgesB;
    }
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vertex vertex = (Vertex) o;
    return number == vertex.number;
  }

  @Override
  public int hashCode() {
    return Objects.hash(number);
  }
}

class Edge {

  private final Vertex v;
  private final Vertex w;
  private final EdgeType type;

  public Edge(Vertex v, Vertex w, EdgeType type) {
    this.v = v;
    this.w = w;
    this.type = type;
  }

  public Edge(int v, int w, String type) {
    this(new Vertex(v), new Vertex(w), EdgeType.fromString(type));
  }

  public Vertex getV() {
    return v;
  }

  public Vertex getW() {
    return w;
  }

  public EdgeType getType() {
    return type;
  }
}

enum EdgeType {

  R("R"),
  B("B");

  private final String label;

  EdgeType(String label) {
    this.label = label;
  }

  static EdgeType fromString(String text) {
    for (EdgeType e : EdgeType.values()) {
      if (e.label.equals(text)) {
        return e;
      }
    }
    throw new UnsupportedOperationException("Value doesn't support: " + text);
  }
}
