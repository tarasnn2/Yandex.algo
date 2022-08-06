package ru.yandex.algo.sprint5.a;


public class Solution {

  public static int treeSolution(Node head) {
    return inOrderTravers(head, new WrapValue()).value;
  }

  private static WrapValue inOrderTravers(Node node, WrapValue wrapValue) {

    if (null != node) {
      inOrderTravers(node.left, wrapValue);
      if (wrapValue.value < node.value) {
        wrapValue.value = node.value;
      }
      inOrderTravers(node.right, wrapValue);
    }
    return wrapValue;
  }

  static class WrapValue {

    int value;
  }

  private static void test() {
    Node node1 = new Node(1);
    Node nodeMinus5 = new Node(-5);
    Node node3 = new Node(3);
    Node node2 = new Node(2);

    node3.left = node1;
    node3.right = nodeMinus5;

    node2.left = node3;
    assert treeSolution(node2) == 3;
  }

  private static class Node {

    int value;
    Node left;
    Node right;

    Node(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  public static void main(String[] args) {
    test();
  }
}
