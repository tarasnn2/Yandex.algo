// 69405356
package ru.yandex.algo.sprint6.final1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Берем любую вершину, убираем ее из множества непросмотренных вершин. Выбираем ребра, инцидентные для нашей вершины и вершин из множества
 * не просмотренных. Из этих ребер выбираем самое весомое (удаляя из источника), если конец ребра инцидентен вершинам из не просмотренных
 * вершин - добавляем это ребро в ответ. Повторяем, пока не закончатся необработанные вершины.
 *
 * <p>Сложность по памяти O(v+e)
 *
 * <p>Сложность по вычислению O(v * e)
 *
 * <p>где v - кол-во вершин, e - кол-во ребер
 */
public class ExpensiveNetwork {

  private static final String SEPARATOR = " ";
  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint6/final1/input12.txt";

  public static void main(String[] args) throws IOException {
    final Graph graph;
    final int vertexCount;
    final int edgeCount;

    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] firstLine = in.readLine().split(SEPARATOR);
      vertexCount = Integer.parseInt(firstLine[0]);
      edgeCount = Integer.parseInt(firstLine[1]);
      if (vertexCount == 1) {
        System.out.println(0);
        return;
      }
      if (edgeCount == 0) {
        System.out.println("Oops! I did it again");
        return;
      }
      graph = buildGraph(vertexCount, edgeCount, in);
    }
    final List<Edge> mst = findMST(graph);
    if (mst.isEmpty()) {
      System.out.println("Oops! I did it again");
      return;
    }
    final int result = mst.stream().mapToInt(Edge::getWeight).sum();
    System.out.println(result);
  }

  private static List<Edge> findMST(Graph graph) {
    final Queue<Edge> edges = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.getWeight(), o1.getWeight()));
    final List<Edge> mst = new ArrayList<>();
    final Set<Vertex> notAdded = new HashSet<>(graph.getVertexes());
    addVertex(graph.getVertexes().stream().findFirst().get(), graph, notAdded, edges);
    while (!notAdded.isEmpty() && !edges.isEmpty()) {
      final Edge edge = edges.poll();
      if (notAdded.contains(edge.getW())) {
        mst.add(edge);
        addVertex(edge.getW(), graph, notAdded, edges);
      }
    }
    if (!notAdded.isEmpty()) {
      return Collections.emptyList();
    }
    return mst;
  }

  private static void addVertex(Vertex v, Graph graph, Set<Vertex> notAdded, Queue<Edge> edges) {
    notAdded.remove(v);
    final Set<Edge> collect = graph.getEdges(v).stream()
        .filter(edge -> v.equals(edge.getV()) && notAdded.contains(edge.getW()))
        .collect(Collectors.toSet());
    edges.addAll(collect);
  }

  private static Graph buildGraph(final int vertexCount, final int edgeCount, final BufferedReader in) throws IOException {
    final Graph graph = new Graph(vertexCount);
    for (int i = 0; i < edgeCount; i++) {
      final String[] line = in.readLine().split(SEPARATOR);
      final Vertex v = new Vertex(Integer.parseInt(line[0]));
      final Vertex w = new Vertex(Integer.parseInt(line[1]));
      graph.getVertexes().add(v);
      graph.getVertexes().add(w);
      final int weight = Integer.parseInt(line[2]);
      graph.put(new Edge(v, w, weight)); // прямой путь
      graph.put(new Edge(w, v, weight)); // обратный путь
    }
    return graph;
  }
}

class Graph {

  private final Set<Vertex> vertexes;
  private final Set<Edge>[] edges;

  @SuppressWarnings("unchecked")
  Graph(int capacity) {
    this.vertexes = new HashSet<>(capacity);
    this.edges = new HashSet[capacity];
  }

  Set<Vertex> getVertexes() {
    return vertexes;
  }

  void put(Edge edge) {
    final int i = edge.getV().getNumber() - 1;
    Set<Edge> e = edges[i];
    if (null == e) {
      e = new HashSet<>();
      edges[i] = e;
    }
    e.add(edge);
  }

  Set<Edge> getEdges(Vertex v) {
    return edges[v.getNumber() - 1];
  }
}

class Vertex {

  private final Integer number;

  Integer getNumber() {
    return number;
  }

  Vertex(Integer number) {
    this.number = number;
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

  @Override
  public String toString() {
    return number + "";
  }
}

class Edge {

  private final Vertex v;
  private final Vertex w;
  private final int weight;

  Edge(Vertex v, Vertex w, int weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  int getWeight() {
    return weight;
  }

  Vertex getV() {
    return v;
  }

  public Vertex getW() {
    return w;
  }

  @Override
  public String toString() {
    return "{" + v + ", " + w + ", " + weight + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Edge edge = (Edge) o;
    return v.equals(edge.v) && w.equals(edge.w) && weight == edge.getWeight();
  }

  @Override
  public int hashCode() {
    return Objects.hash(v, w, weight);
  }
}
