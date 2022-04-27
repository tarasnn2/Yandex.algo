package ru.yandex.algo.sprint2.d;

class Node<V> {
  public V value;
  public Node<V> next;

  public Node(V value, Node<V> next) {
    this.value = value;
    this.next = next;
  }
}



public class Solution {
  public static int solution(Node<String> head, String elem) {
    Node<String> current = head;
    int i = 0, result = -1;
    while (null != current) {
      if (current.value.equals(elem)) {
        result = i;
        break;
      }
      current = current.next;
      i++;
    }
    return result;
  }

/*  private static void test() {
    Node<String> node3 = new Node<>("node3", null);
    Node<String> node2 = new Node<>("node2", node3);
    Node<String> node1 = new Node<>("node1", node2);
    Node<String> node0 = new Node<>("node0", node1);
    int idx = solution(node0, "node2");
    // result is : idx == 2
    System.out.println(idx);
  }

  public static void main(String[] args) {
    test();
  }*/
}
