package ru.yandex.algo.sprint5.j;

public class Solution {

  public static Node insert(Node root, int key) {
    if (null == root) {
      return new Node(null, null, key);
    }
    if (key < root.getValue()) {
      if (null == root.getLeft()) {
        root.setLeft(new Node(null, null, key));
      } else {
        insert(root.getLeft(), key);
      }
    } else {
      if (null == root.getRight()) {
        root.setRight(new Node(null, null, key));
      } else {
        insert(root.getRight(), key);
      }
    }
    return root;
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
    {
      Node node1 = new Node(null, null, 7);
      Node node2 = new Node(node1, null, 8);
      Node node3 = new Node(null, node2, 7);
      Node newHead = insert(node3, 6);
      assert newHead == node3;
      assert newHead.getLeft().getValue() == 6;
    }
    {
      Node node3 = new Node(null, null, 7);
      Node node2 = new Node(node3, null, 9);
      Node node1 = new Node(null, node2, 7);
      final Node newHead = insert(node1, 7);
      assert newHead.equals(node1);
      assert node3.getRight().getValue() == 7;

    }
  }
  public static void main(String[] args) {
    test();
  }
}
