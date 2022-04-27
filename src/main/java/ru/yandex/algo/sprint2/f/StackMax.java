//package ru.yandex.algo.sprint2.f;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class StackMax {
  private static final String SEPARATOR = " ";
  private static final String COMMAND_GET_MAX = "get_max";
  private static final String COMMAND_POP = "pop";
  private static final String COMMAND_PUSH = "push";
  static final String REPORT_COMMAND_ERROR = "error";
  static final String REPORT_COMMAND_NONE = "None";

  private static final String DELIMITER = "\n";
  private static final String EMPTY_STRING = "";
  private static MyStack stack = new MyStack(null, null);
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint2/f/input2.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      final int countOfCommand = Integer.parseInt(in.readLine());
      int i = 1;
      while (i <= countOfCommand) {
        final String commandWithArg = in.readLine().trim();
        doCommand(commandWithArg);
        i++;
      }
    }
  }

  private static void doCommand(String commandWithArg) {
    final String[] commandWithArgArray = commandWithArg.split(SEPARATOR);
    final String command = commandWithArgArray[0];
    if (COMMAND_GET_MAX.equals(command)) {
      final Integer max = stack.getMax();
      if (null == max) {
        System.out.println(REPORT_COMMAND_NONE);
      } else {
        System.out.println(max);
      }
    } else if (COMMAND_POP.equals(command)) {
      final Node node = stack.pop();
      if (null == node) {
        System.out.println(REPORT_COMMAND_ERROR);
      }
    } else if (COMMAND_PUSH.equals(command)) {
      int arg = Integer.parseInt(commandWithArgArray[1]);
      stack.push(arg);
    } else {
      throw new IllegalArgumentException("The command doesn't correct : " + command);
    }
  }
}

class MyStack {

  public MyStack(Node head, Node tail) {
    this.head = head;
    this.tail = tail;
  }

  private Integer maxValue;
  private int size = 0;
  private Node head;
  private Node tail;

  Node pop() {
    if (0 == size) {
      return null;
    }

    Node oldHead = head;
    if (1 == size) {
      head = null;
      tail = null;
      maxValue = null;
      size--;
      return oldHead;
    } else {
      head = head.next;
      oldHead.next = null;
      size--;
    }

    if (oldHead.value.equals(maxValue)) {
      Node current = head;
      maxValue = head.value;
      while (null != current) {
        if (maxValue < current.value) {
          maxValue = current.value;
        }
        current = current.next;
      }
    }
    return oldHead;
  }

  void push(Integer i) {
    final Node newTail = new Node(i, null, null);

    if (null == head && null == tail) {
      head = newTail;
      tail = newTail;
      maxValue = newTail.value;
    } else {
      newTail.next = head;
      head.prev = newTail;
      head = newTail;
      if (maxValue < newTail.value) {
        maxValue = newTail.value;
      }
    }

    size++;
  }

  Integer getMax() {
    return maxValue;
  }
}

class Node {
  public Integer value;
  public Node next;
  public Node prev;

  public Node(Integer value, Node next, Node prev) {
    this.value = value;
    this.next = next;
    this.prev = prev;
  }
}
