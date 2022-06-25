package ru.yandex.algo.sprint6.h;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class H {
  private static final String SEPARATOR = " ";
  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/h/input00.txt";

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    final int edgeCount;

    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      edgeCount = Integer.parseInt(firstLine[1]);
      graph = buildGraph(vertexCount, edgeCount, in, (o1, o2) -> Integer.compare(o2.getW().getNumber(), o1.getW().getNumber()));
    }
    final Vertex startVertex = new Vertex(1);
    final StringBuilder sb = traversByDFS(graph, startVertex, vertexCount);
    System.out.print(sb);
  }

  private static StringBuilder traversByDFS(final Graph graph, final Vertex startVertex, final int vertexCount) {
    final TimePairs timePairs = new TimePairs(vertexCount);
    final Deque<Vertex> stack = new LinkedList<>();
    final Colors colors = new Colors(vertexCount);
    int time = 0;
    stack.push(startVertex);

    while (!stack.isEmpty()) {
      Vertex vertex = stack.pop();
      if (colors.isWhite(vertex)) {
        colors.markColor(vertex, Color.GRAY);
        timePairs.addTimeIn(vertex, time++);
        stack.push(vertex);
        final Queue<Edge> edges = graph.getEdges(vertex);
        // final Queue<Edge> edgesCopy = new PriorityQueue<>(edges);
        while (!edges.isEmpty()) {
          final Edge edge = edges.poll();
          final Vertex w = edge.getW();
          if (colors.isWhite(w)) {
            stack.push(w);
          }
        }
      } else if (colors.isGray(vertex)) {
        colors.markColor(vertex, Color.BLACK);
        timePairs.addTimeOut(vertex, time++);
      }
    }
    return timePairs.toStringBuilder();
  }

  private static Graph buildGraph(final int vertexCount, final int edgeCount, BufferedReader in, Comparator<Edge> traversDirect)
      throws IOException {
    final Graph graph = new Graph(vertexCount, traversDirect);
    for (int i = 0; i < edgeCount; i++) {
      final String[] line = in.readLine().split(SEPARATOR);
      final Vertex v = new Vertex(Integer.parseInt(line[0]));
      final Vertex w = new Vertex(Integer.parseInt(line[1]));
      graph.addEdgeToVertex(v, new Edge(v, w));
    }
    return graph;
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

  boolean isBlack(Vertex v) {
    return color[v.getNumber() - 1] == Color.BLACK;
  }
}

class Graph {

  private final Map<Vertex, Queue<Edge>> store;

  private final Comparator<Edge> traversDirect;

  public Graph(int capacity, Comparator<Edge> traversDirect) {
    this.traversDirect = traversDirect;
    store = new HashMap<>(capacity);
  }

  void addEdgeToVertex(Vertex key, Edge value) {
    final Queue<Edge> edges = store.getOrDefault(key, new PriorityQueue<>(traversDirect));
    edges.add(value);
    store.putIfAbsent(key, edges);
  }

  Queue<Edge> getEdges(Vertex key) {
    final Queue<Edge> edges = store.getOrDefault(key, new PriorityQueue<>());
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

class TimePair {
  private int timeIn;
  private int timeOut;

  public int getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(int timeIn) {
    this.timeIn = timeIn;
  }

  public int getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(int timeOut) {
    this.timeOut = timeOut;
  }

  @Override
  public String toString() {
    return toStringBuilder().toString();
  }

  public StringBuilder toStringBuilder() {
    return new StringBuilder(3).append(timeIn).append(" ").append(timeOut);
  }
}

class TimePairs {
  private final Map<Vertex, TimePair> store;

  TimePairs(int capacity) {
    this.store = new HashMap<>(capacity);
  }

  void addTimeIn(Vertex v, int t) {
    final TimePair timePair = store.getOrDefault(v, new TimePair());
    timePair.setTimeIn(t);
    store.putIfAbsent(v, timePair);
  }

  void addTimeOut(Vertex v, int t) {
    final TimePair timePair = store.getOrDefault(v, new TimePair());
    timePair.setTimeOut(t);
    store.putIfAbsent(v, timePair);
  }

  @Override
  public String toString() {
    return toStringBuilder().toString();
  }

  public StringBuilder toStringBuilder() {
    final StringBuilder sb = new StringBuilder(store.size() * 4);
    final TreeMap<Vertex, TimePair> treeMap = new TreeMap<>(Comparator.comparingInt(Vertex::getNumber));
    treeMap.putAll(store);
    for (Map.Entry<Vertex, TimePair> entry : treeMap.entrySet()) {
      sb.append(entry.getValue().toStringBuilder()).append("\n");
    }
    return sb;
  }
}
