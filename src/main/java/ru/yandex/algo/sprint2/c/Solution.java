package ru.yandex.algo.sprint2.c;

class Node<V> {
  public V value;
  public Node<V> next;

  public Node(V value, Node<V> next) {
    this.value = value;
    this.next = next;
  }
}



public class Solution {
  public static Node<String> solution(Node<String> head, int idx) {
    // Your code
    if (0 == idx) {
      Node<String> newHead = head.next;
      head.next = null;
      return newHead;
    }
    Node<String> previous = head;
    Node<String> current = head.next;
    for (int i = 1; i <= idx; i++) {
      if (i == idx) {
        previous.next = current.next;
        current.next = null;
        break;
      }
      previous = current;
      current = current.next;
    }
    return head;
  }

/*  private static void test() {
    Node<String> node3 = new Node<>("node3", null);
    Node<String> node2 = new Node<>("node2", node3);
    Node<String> node1 = new Node<>("node1", node2);
    Node<String> node0 = new Node<>("node0", node1);
    Node<String> newHead = solution(node0, 1);
    // result is : node0 -> node2 -> node3
  }

  public static void main(String[] arg) {
    test();
  }*/
}
