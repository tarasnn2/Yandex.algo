package ru.yandex.algo.sprint6.h;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class H {
  private static final String SEPARATOR = " ";
  // private static final String FILE = "input.txt";
  private static final String FILE =
      "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/h/input00.txt";

  private static int time = -1;

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    final int edgeCount;

    try (final BufferedReader in =
        new BufferedReader(
            new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      edgeCount = Integer.parseInt(firstLine[1]);
      graph =
          buildGraph(
              vertexCount,
              edgeCount,
              in,
              // (o1, o2) -> Integer.compare(o2.getW().getNumber(), o1.getW().getNumber()));
              Comparator.comparingInt(o -> o.getW().getNumber()));
    }
    final Vertex startVertex = new Vertex(1);
    final StringBuilder sb = callRecursiveDFS(graph, startVertex, vertexCount);
    // final StringBuilder sb = stackDFS(graph, startVertex, vertexCount); // don't accept by TL https://contest.yandex.ru/contest/25069/run-report/69194146/
    System.out.print(sb);
  }

  private static void recursiveDFS(
      final Graph graph, final Vertex vertexV, Colors colors, TimePairs timePairs) {

    ++time;
    colors.markColor(vertexV, Color.GRAY);
    timePairs.addTimeIn(vertexV, time);

    final Queue<Edge> edges = graph.getEdges(vertexV);
    while (null != edges && !edges.isEmpty()) {
      final Vertex vertexW = edges.poll().getW();
      if (colors.isWhite(vertexW)) {
        recursiveDFS(graph, vertexW, colors, timePairs);
      }
    }
    ++time;
    timePairs.addTimeOut(vertexV, time);
    colors.markColor(vertexV, Color.BLACK);
  }

  private static StringBuilder callRecursiveDFS(
      final Graph graph, final Vertex startVertex, final int vertexCount) {

    final TimePairs timePairs = new TimePairs(vertexCount);
    final Colors colors = new Colors(vertexCount);
    recursiveDFS(graph, startVertex, colors, timePairs);
    return timePairs.toStringBuilder();
  }

  private static StringBuilder stackDFS(
      final Graph graph, final Vertex startVertex, final int vertexCount) {
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
        while (null != edges && !edges.isEmpty()) {
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

  private static Graph buildGraph(
      final int vertexCount, final int edgeCount, BufferedReader in, Comparator<Edge> traversDirect)
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

  private final Object[] store;

  private final Comparator<Edge> traversDirect;

  public Graph(int capacity, Comparator<Edge> traversDirect) {
    this.traversDirect = traversDirect;
    store = new Object[capacity];
  }

  void addEdgeToVertex(Vertex key, Edge value) {
    Queue<Edge> edges = getEdges(key);
    if (null == edges) {
      edges = new PriorityQueue<>(traversDirect);
      store[key.getNumber() - 1] = edges;
    }
    edges.add(value);
  }

  @SuppressWarnings("unchecked")
  Queue<Edge> getEdges(Vertex v) {
    final int i = v.getNumber() - 1;
    return (Queue<Edge>) store[i];
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
  private final TimePair[] store;

  TimePairs(int capacity) {
    store = new TimePair[capacity];
  }

  void addTimeIn(Vertex v, int t) {
    final int i = v.getNumber() - 1;
    TimePair timePair = store[i];
    if (null == timePair) {
      timePair = new TimePair();
      store[i] = timePair;
    }
    timePair.setTimeIn(t);
  }

  void addTimeOut(Vertex v, int t) {
    final int i = v.getNumber() - 1;
    TimePair timePair = store[i];
    if (null == timePair) {
      timePair = new TimePair();
      store[i] = timePair;
    }
    timePair.setTimeOut(t);
  }

  @Override
  public String toString() {
    return toStringBuilder().toString();
  }

  public StringBuilder toStringBuilder() {
    final StringBuilder sb = new StringBuilder(store.length * 4);
    for (TimePair timePair : store) {
      sb.append(timePair).append("\n");
    }
    return sb;
  }
}
