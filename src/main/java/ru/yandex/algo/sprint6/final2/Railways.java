package ru.yandex.algo.sprint6.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Railways {

  private static final String SEPARATOR = " ";
  // private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final2/input01.txt";

  public static void main(String[] args) throws IOException {
    final Pair pair;
    final int vertexCount;
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      pair = buildGraphs(vertexCount, in);
    }
    final RailWayPaths railWayPathsR = buildRailWayPaths(pair.getGraphR());
    final RailWayPaths railWayPathsB = buildRailWayPaths(pair.getGraphB());

    railWayPathsB.getPaths().forEach(railWayPathB -> {
      if (railWayPathsR.getPaths().contains(railWayPathB)) {
        System.out.print("NO");
        System.exit(0);
      }
    });

    System.out.print("YES");
  }

  private static void recursiveDFS(final Graph graph, Vertex vertexA, final Vertex vertexV, final RailWayPaths railWayPaths) {

    final Queue<Edge> edges = graph.getEdges(vertexV);
    while (null != edges && !edges.isEmpty()) {
      final Vertex vertexW = edges.poll().getW();
      recursiveDFS(graph, vertexA, vertexW, railWayPaths);
    }
    if (!vertexA.equals(vertexV)) {
      railWayPaths.addPath(new RailWayPath(vertexA, vertexV));
    }
  }

  private static RailWayPaths buildRailWayPaths(final Graph graph) {
    final RailWayPaths railWayPaths = new RailWayPaths();
    final Vertex startVertex = graph.getFirstVertex();
    recursiveDFS(graph, startVertex, startVertex, railWayPaths);
    return railWayPaths;
  }

  private static Pair buildGraphs(int vertexCount, BufferedReader in) throws IOException {
    final Graph graphR = new Graph(vertexCount);
    final Graph graphB = new Graph(vertexCount);
    for (int i = 1; i < vertexCount; i++) {
      final String[] l = in.readLine().split("");
      for (int j = 1; j <= l.length; j++) {
        final String type = l[j - 1];
        if ("R".equals(type)) {
          graphR.addEdgeToVertex(new Edge(i, i + j));
        } else {
          graphB.addEdgeToVertex(new Edge(i, i + j));
        }

      }
    }
    return new Pair(graphR, graphB);
  }

}

class Pair {

  private final Graph graphR;
  private final Graph graphB;

  public Pair(Graph graphR, Graph graphB) {
    this.graphR = graphR;
    this.graphB = graphB;
  }

  public Graph getGraphR() {
    return graphR;
  }

  public Graph getGraphB() {
    return graphB;
  }
}

class Graph {

  private final Object[] store;

  public Graph(int capacity) {
    store = new Object[capacity];
  }

  void addEdgeToVertex(Edge value) {
    addEdgeToVertex(value.getV(), value);
  }

  void addEdgeToVertex(Vertex key, Edge value) {
    Queue<Edge> edges = getEdges(key);
    if (null == edges) {
      edges = new LinkedList<>();
      store[key.getNumber() - 1] = edges;
    }
    edges.add(value);
  }

  @SuppressWarnings("unchecked")
  Queue<Edge> getEdges(Vertex v) {
    final int i = v.getNumber() - 1;
    return (Queue<Edge>) store[i];
  }

  @SuppressWarnings("unchecked")
  Vertex getFirstVertex() {
    for (Object o : store) {
      if (null != o) {
        return ((Queue<Edge>) o).peek().getV();
      }
    }
    return null;
  }
}

class Vertex {

  public int getNumber() {
    return number;
  }

  private final int number;

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

class RailWayPath {

  private final Vertex a;
  private final Vertex b;

  public RailWayPath(Vertex a, Vertex b) {
    this.a = a;
    this.b = b;
  }

  public Vertex getA() {
    return a;
  }

  public Vertex getB() {
    return b;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RailWayPath railWayPath = (RailWayPath) o;
    return a.equals(railWayPath.a) && b.equals(railWayPath.b);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b);
  }
}

class RailWayPaths {

  //private final Map<RailWayPath, RailWayPath> paths;
  private final Set<RailWayPath> paths;


  public RailWayPaths() {
    paths = new HashSet<>();
  }

  void addPath(RailWayPath p) {
    paths.add(p);
  }

  public Set<RailWayPath> getPaths() {
    return paths;
  }
}