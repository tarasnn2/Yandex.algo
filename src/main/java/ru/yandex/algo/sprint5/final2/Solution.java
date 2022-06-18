package ru.yandex.algo.sprint5.final2;
// 69022105

/**
 * Удаление в худшем случае работает за O(H), где H - высота дерева. Памяти в худшем случае требуется O(H) для рекурсивного обхода по высоте
 *
 * <p>Рекурсивно находим удаляемый узел. В случае успеха, рекурсивно берем из его левой ветки самый правый элемент - это новая вершина. Если
 * есть только правая ветка - берем самый первый элемент из нее.
 */
public class Solution {
  public static Node remove(final Node root, final int key) {
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
      return removeVertex(root);
    }
    return root;
  }

  private static Node removeVertex(final Node vertex) {
    // удаляемый узел - лист
    if (null == vertex.getRight() && null == vertex.getLeft()) {
      return null;
    }
    // удаляемый узел имеет обе ветки или только левую
    if (null != vertex.getLeft()) {
      return removeVertexWithShoulders(vertex);
    }
    // удаляемый узел имеет только правую ветку
    final Node newVertex = vertex.getRight();
    vertex.setRight(null);
    return newVertex;
  }

  private static Node removeVertexWithShoulders(final Node vertexForRemove) {
    final Node newVertex = getAndDetachMostRightNode(vertexForRemove.getLeft(), null);
    if (!newVertex.equals(vertexForRemove.getRight())) {
      newVertex.setRight(vertexForRemove.getRight());
    }
    if (!newVertex.equals(vertexForRemove.getLeft())) {
      newVertex.setLeft(vertexForRemove.getLeft());
    }
    vertexForRemove.setLeft(null);
    vertexForRemove.setRight(null);
    return newVertex;
  }

  private static Node getAndDetachMostRightNode(Node vertex, Node parent) {
    final Node right = vertex.getRight();
    if (null == right) {
      if (null != parent) {
        parent.setRight(null);
      }
      return vertex;
    }
    return getAndDetachMostRightNode(right, vertex);
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
      assert newHead.getLeft().equals(node4);
    }
    // 2
    {
      Node node4 = new Node(null, null, 11);
      Node node10 = new Node(null, null, 99);
      Node node9 = new Node(null, null, 72);
      Node node7 = new Node(null, null, 50);
      Node node6 = new Node(null, null, 32);
      Node node5 = new Node(null, node6, 29);
      Node node8 = new Node(node9, node10, 91);
      Node node2 = new Node(node4, node5, 20);
      Node node3 = new Node(node7, node8, 65);
      Node node1 = new Node(node2, node3, 41);

      Node newHead = remove(node1, 41);
      assert newHead.getValue() == 32;
      assert newHead.getLeft().equals(node2);
      assert newHead.getRight().equals(node3);
      assert node5.getRight() == null;
    }
  }

  public static void main(String[] args) {
    test();
  }
}
