package ru.yandex.algo.sprint2.b;

class Node<V> {
  public V value;
  public Node<V> next;

  public Node(V value, Node<V> next) {
    this.value = value;
    this.next = next;
  }
}



public class Solution {
  public static void solution(Node<String> head) {
    // Your code
    while (null != head) {
      System.out.println(head.value);
      head = head.next;
    }
  }

}
