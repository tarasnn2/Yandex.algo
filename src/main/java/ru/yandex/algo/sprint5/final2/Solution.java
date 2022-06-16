package ru.yandex.algo.sprint5.final2;

import java.util.Deque;
import java.util.LinkedList;

public class Solution {
  public static Node remove(Node root, int key) {
    if (null == root) {
      return null;
    }

    final Deque<Node> result = findByKey(root, key, new LinkedList<>());
    final Node deleted = result.pollLast();

    // удаляемый узел не найден
    if (null == deleted) {
      return root;
    }

    final Node parent = result.pollLast();

    // удаляемый узел - единственный
    if (null == parent && null == root.getRight() && null == root.getLeft()) {
      return null;
    }

    // удаляемый узел - корень
    if (null == parent) {
      // удаляемый узел имеет обе ветки
      if (null != deleted.getLeft() && null != deleted.getRight()) {
        return removeVertexWithBothShoulders(deleted);
      }
      // удаляемый узел имеет только правую ветку
      else if (null != deleted.getRight()) {
        final Node newRoot = deleted.getRight();
        deleted.setRight(null);
        return newRoot;
      }
      // удаляемый узел имеет только левую ветку
      else {
        final Node newRoot = deleted.getLeft();
        deleted.setLeft(null);
        return newRoot;
      }
    }

    final boolean isRightChildren = isRightChildren(deleted, parent);
    final boolean isLeftChildren = isLeftChildren(deleted, parent);

    // удаляемый узел - лист
    if (null == deleted.getRight() && null == deleted.getLeft()) {
      if (isRightChildren) {
        parent.setRight(null);
        return root;
      }
      if (isLeftChildren) {
        parent.setLeft(null);
        return root;
      }
    }

    // удаляемый узел - правый потомок родителя
    if (isRightChildren) {
      // удаляемый узел имеет только левую ветку
      if (null == deleted.getRight()) {
        parent.setRight(deleted.getLeft());
        deleted.setLeft(null);
        return root;
      }
      // удаляемый узел имеет только правую ветку
      if (null == deleted.getLeft()) {
        parent.setRight(deleted.getRight());
        deleted.setRight(null);
        return root;
      }
      final Node newVertex = removeVertexWithBothShoulders(deleted);
      parent.setRight(newVertex);
    }
    // удаляемый узел - левый потомок родителя
    if (isLeftChildren) {
      // удаляемый узел имеет только левую ветку
      if (null == deleted.getRight()) {
        parent.setLeft(deleted.getLeft());
        deleted.setLeft(null);
        return root;
      }
      // удаляемый узел имеет только правую ветку
      if (null == deleted.getLeft()) {
        parent.setLeft(deleted.getRight());
        deleted.setRight(null);
        return root;
      }
      final Node newVertex = removeVertexWithBothShoulders(deleted);
      parent.setLeft(newVertex);
    }

    return root;
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
    if (null == node.getLeft()) {
      return node;
    }
    return findMostLeft(node.getLeft());
  }

  private static boolean isRightChildren(Node children, Node parent) {
    return children.equals(parent.getRight());
  }

  private static boolean isLeftChildren(Node children, Node parent) {
    return children.equals(parent.getLeft());
  }

  private static Deque<Node> findByKey(Node root, int key, Deque<Node> result) {
    if (null == root) {
      return new LinkedList<>();
    }
    result.add(root);
    if (key == root.getValue()) {
      return result;
    }
    if (key > root.getValue()) {
      return findByKey(root.getRight(), key, result);
    } else {
      return findByKey(root.getLeft(), key, result);
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

  private static void test() {
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
