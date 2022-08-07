package ru.yandex.algo.sprint5.k;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Solution {


  public static void printRange(Node root, int L, int R, BufferedWriter writer) throws IOException {
    final List<Node> result = new ArrayList<>();
    travers123(root, L, R, result);

    result.sort(Comparator.comparingInt(n -> n.value));

    StringBuilder sb = new StringBuilder(result.size() * 2);
    result.forEach(node -> sb.append(node.value).append("\n"));

    //sb.append("7").append("\n").append("11");

    writer.write(sb.toString());
  }

  private static void travers123(Node node, int left, int right, List<Node> result) {
    if (null != node) {
      travers123(node.left, left, right, result);
      if (left <= node.value && node.value <= right) {
        result.add(node);
      }
      travers123(node.right, left, right, result);
    }
  }

  private static class Node {

    private int value;
    private Node left;
    private Node right;

    Node(Node left, Node right, int value) {
      this.left = left;
      this.right = right;
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public Node getRight() {
      return right;
    }

    public void setRight(Node right) {
      this.right = right;
    }

    public Node getLeft() {
      return left;
    }

    public void setLeft(Node left) {
      this.left = left;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }
  private static void test() throws IOException {
    Node node1 = new Node(null, null, 2);
    Node node2 = new Node(null, node1, 1);
    Node node3 = new Node(null, null, 8);
    Node node4 = new Node(null, node3, 8);
    Node node5 = new Node(node4, null, 9);
    Node node6 = new Node(node5, null, 10);
    Node node7 = new Node(node2, node6, 5);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    printRange(node7, 2, 8, writer);
    writer.flush();
    // expected output: 2 5 8 8
  }
  public static void main(String[] args) throws IOException {
    test();
  }
}
