package ru.yandex.algo.sprint5.final2;

public class Solution {
  public static Node remove(Node root, int key) {
    if (null == root) {
      return null;
    }
    if (key < root.getValue()) {
      final Node vertex = remove(root.getLeft(), key);
      root.setLeft(vertex);
    } else if (key > root.getValue()) {
      final Node vertex = remove(root.getRight(), key);
      root.setRight(vertex);
    } else {
      return removeInner(root);
    }
    return root;
  }

  private static Node removeInner(Node deleted) {
    // удаляемый узел - лист
    if (null == deleted.getRight() && null == deleted.getLeft()) {
      return null;
    }
    // удаляемый узел имеет обе ветки
    if (null != deleted.getLeft() && null != deleted.getRight()) {
      return removeVertexWithBothShoulders(deleted);
    }
    // удаляемый узел имеет только правую ветку
    else if (null != deleted.getRight()) {
      return removeVertexWithOnlyRightShoulder(deleted);
    }
    // удаляемый узел имеет только левую ветку
    else {
      return removeVertexWithOnlyLeftShoulder(deleted);
    }
  }

  private static Node removeVertexWithOnlyRightShoulder(Node deleted) {
    final Node newVertex = deleted.getRight();
    deleted.setRight(null);
    return newVertex;
  }

  private static Node removeVertexWithOnlyLeftShoulder(Node deleted) {
    final Node newVertex = deleted.getLeft();
    deleted.setLeft(null);
    return newVertex;
  }

  private static Node removeVertexWithBothShoulders(Node deleted) {
    final Node newVertex = findMostLeft(deleted.getRight());
    newVertex.setRight(deleted.getRight());
    newVertex.setLeft(deleted.getLeft());
    deleted.setLeft(null);
    deleted.setRight(null);
    return newVertex;
  }

  private static Node findMostLeft(Node node) {
    final Node left = node.getLeft();
    if (null == left) {
      return node;
    }
    return findMostLeft(left);
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

  private static void test() {
    // 00
    {
      Node node1 = new Node(null, null, 2);
      Node node2 = new Node(node1, null, 3);
      Node node3 = new Node(null, node2, 1);
      Node node4 = new Node(null, null, 6);
      Node node5 = new Node(node4, null, 8);
      Node node6 = new Node(node5, null, 10);
      Node node7 = new Node(node3, node6, 5);
      Node newHead = remove(node7, 10);
      assert newHead.getValue() == 5;
      assert newHead.getRight().equals(node5);
      assert newHead.getRight().getValue() == 8;
    }

    // 21
    {
      Node node4 = new Node(null, null, 1);
      Node node5 = new Node(null, null, 3);
      Node node6 = new Node(null, null, 5);
      Node node7 = new Node(null, null, 7);
      Node node3 = new Node(node6, node7, 6);
      Node node2 = new Node(node4, node5, 2);
      Node node1 = new Node(node2, node3, 4);
      Node newHead = remove(node1, 2);
      assert newHead.getValue() == 4;
      assert newHead.getRight().equals(node3);
      assert newHead.getLeft().equals(node5);
    }
  }

  public static void main(String[] args) {
    test();
  }
}
