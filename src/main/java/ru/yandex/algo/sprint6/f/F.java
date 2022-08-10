package ru.yandex.algo.sprint6.f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;

public class F {

  private static final String SEPARATOR = " ";
  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/f/input01.txt";

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    final int edgeCount;
    final int startVertex;
    final int stopVertex;

    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {



      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      edgeCount = Integer.parseInt(firstLine[1]);

      graph = buildGraphs(vertexCount, edgeCount, in);

      final String[] lastLine = in.readLine().split(SEPARATOR);
      startVertex = Integer.parseInt(lastLine[0]);
      stopVertex = Integer.parseInt(lastLine[1]);
    }
    final int i = traversGraphByStackBFS(graph, startVertex, stopVertex, vertexCount);
    System.out.print(i);
  }

  private static int traversGraphByStackBFS(final Graph graph, final int startVertex, final int stopVertex, final int vertexCount) {
    class Item {

      public Item(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
      }

      int vertex;
      int weight;
    }

    boolean[] visited = new boolean[vertexCount + 1];
    visited[startVertex] = true;
    Queue<Item> stack = new ArrayDeque<>();
    stack.add(new Item(startVertex, 0));
    while (!stack.isEmpty()) {
      final Item item = stack.poll();
      int vertex = item.vertex;
      if (vertex == stopVertex) {
        return item.weight;
      }
      final Queue<Integer> edges = graph.getWs(vertex);
      while (null != edges && !edges.isEmpty()) {
        final int w = edges.poll();
        if (!visited[w]) {
          visited[w] = true;
          stack.add(new Item(w, item.weight + 1));
        }
      }
    }
    return -1;
  }



  private static Graph buildGraphs(int vertexCount, int edgeCount, BufferedReader in) throws IOException {
    final Graph graph = new Graph(vertexCount);
    for (int i = 0; i < edgeCount; i++) {
      final String[] l = in.readLine().split(" ");
      final int v = Integer.parseInt(l[0]);
      final int w = Integer.parseInt(l[1]);
      graph.addWToV(v, w);
      graph.addWToV(w, v);
    }
    return graph;
  }
}

class Graph {

  private final Queue<Integer>[] store;

  @SuppressWarnings("unchecked")
  public Graph(int capacity) {
    store = new ArrayDeque[capacity];
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

}