package ru.yandex.algo.sprint6.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class C {
  private static final String SEPARATOR = " ";
  // private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/c/input00.txt";

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    final int edgeCount;
    final Vertex startVertex;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      edgeCount = Integer.parseInt(firstLine[1]);
      graph = buildGraph(edgeCount, in, (o1, o2) -> Integer.compare(o2.getW().getNumber(), o1.getW().getNumber()));
      startVertex = new Vertex(Integer.parseInt(in.readLine()));
    }
    final StringBuilder sb = traversGraphByStackDFS(graph, startVertex, vertexCount);
    System.out.print(sb);
  }

  private static StringBuilder traversGraphByStackDFS(final Graph graph, final Vertex startVertex, final int vertexCount) {
    final StringBuilder sb = new StringBuilder(vertexCount * 2);
    Deque<Vertex> stack = new LinkedList<>();
    Colors colors = new Colors(vertexCount);
    stack.push(startVertex);
    while (!stack.isEmpty()) {
      Vertex vertex = stack.pop();
      if (colors.isWhite(vertex)) {
        colors.markColor(vertex, Color.GRAY);
        sb.append(vertex).append(SEPARATOR);
        stack.push(vertex);
        final PriorityQueue<Edge> edgesCopy = new PriorityQueue<>(graph.getEdges(vertex));
        while (!edgesCopy.isEmpty()) {
          final Edge edge = edgesCopy.poll();
          final Vertex w = edge.getW();
          if (colors.isWhite(w)) {
            stack.push(w);
          }
        }
      } else if (colors.isGray(vertex)) {
        colors.markColor(vertex, Color.BLACK);
      }
    }
    return sb;
  }

  private static Graph buildGraph(final int edgeCount, BufferedReader in, Comparator<Edge> traversDirect) throws IOException {
    final Graph graph = new Graph(traversDirect);
    for (int i = 1; i <= edgeCount; i++) {
      final String[] line = in.readLine().split(SEPARATOR);
      final Vertex v = new Vertex(Integer.parseInt(line[0]));
      final Vertex w = new Vertex(Integer.parseInt(line[1]));
      graph.addEdgeToVertex(v, new Edge(v, w));
      graph.addEdgeToVertex(w, new Edge(w, v));
    }
    return graph;
  }
}

class Colors {
  private final Color[] color;

  public Colors(int count) {
    color = new Color[count];
    Arrays.fill(color, Color.WHILE);
  }

  void markColor(Vertex v, Color c) {
    color[v.getNumber() - 1] = c;
  }

  boolean isWhite(Vertex v) {
    return color[v.getNumber() - 1] == Color.WHILE;
  }

  boolean isGray(Vertex v) {
    return color[v.getNumber() - 1] == Color.GRAY;
  }

  boolean isBlack(Vertex v) {
    return color[v.getNumber() - 1] == Color.BLACK;
  }
}

class Graph {

  private final Map<Vertex, PriorityQueue<Edge>> store;

  private final Comparator<Edge> traversDirect;

  public Graph(Comparator<Edge> traversDirect) {
    this.traversDirect = traversDirect;
    store = new HashMap<>();
  }

  Edge addEdgeToVertex(Vertex key, Edge value) {
    final PriorityQueue<Edge> edges = store.getOrDefault(key, new PriorityQueue<>(traversDirect));
    edges.add(value);
    store.putIfAbsent(key, edges);
    return value;
  }

  PriorityQueue<Edge> getEdges(Vertex key) {
    final PriorityQueue<Edge> edges = store.getOrDefault(key, new PriorityQueue<>());
    store.putIfAbsent(key, edges);
    return edges;
  }
}

class Vertex {

  public Integer getNumber() {
    return number;
  }

  private final Integer number;

  public Vertex(Integer number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return number.toString();
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
    return number.equals(vertex.number);
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

  public Vertex getV() {
    return v;
  }

  public Vertex getW() {
    return w;
  }
}

enum Color {
  WHILE,
  GRAY,
  BLACK;
}
