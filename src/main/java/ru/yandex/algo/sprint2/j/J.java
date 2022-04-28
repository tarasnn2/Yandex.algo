//package ru.yandex.algo.sprint2.j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class J {
  private static final String REPORT_COMMAND_ERROR = "error";
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String COMMAND_GET = "get";
  private static final String COMMAND_PUT = "put";
  private static final String COMMAND_SIZE = "size";

  private static final String FILE = "input.txt";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint2/j/input3.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int commandsCount = Integer.parseInt(in.readLine());
      final Queue<Integer> queue = new Queue<>();
      final StringBuilder result = new StringBuilder();
      int i = 1;
      while (i++ <= commandsCount) {
        final String commandWithArg = in.readLine().trim();
        final String s = doCommand(commandWithArg, queue);
        if (null != s) {
          result.append(s).append(DELIMITER);
        }
      }
      System.out.println(result);
    }
  }

  private static String doCommand(String commandWithArg, Queue<Integer> queue) {
    final String[] commandWithArgArray = commandWithArg.split(SEPARATOR);
    final String command = commandWithArgArray[0];
    String result = null;
    try {
      switch (command) {
        case COMMAND_GET:
          result = String.valueOf(queue.get());
          break;
        case COMMAND_PUT:
          queue.put(Integer.valueOf(commandWithArgArray[1]));
          break;
        case COMMAND_SIZE:
          result = String.valueOf(queue.size());
          break;
        default:
          throw new IllegalArgumentException("The command doesn't correct : " + command);
      }
    } catch (IndexOutOfBoundsException e) {
      result = REPORT_COMMAND_ERROR;
    }
    return result;
  }
}

class Queue<V> {

  /** указатель на конец очереди */
  private Node<V> last;

  /** указатель на начало очереди */
  private Node<V> first;

  /** текущее количество элементов */
  private int size = 0;

  /** добавить элемент */
  void put(V value) {
    final Node<V> newNode = new Node<>(value);
    if (0 == size) {
      first = newNode;
    } else {
      last.prev = newNode;
      newNode.next = last;
    }
    last = newNode;
    size++;
  }

  /** вывести первый элемент и удалить его */
  V get() {
    if (0 == size) {
      throw new IndexOutOfBoundsException();
    }
    final Node<V> result = first;
    if (size > 1) {
      first = first.prev;
      first.next=null;
    } else {
      first = null;
      last = null;
    }
    result.prev = null;
    size--;
    return result.value;
  }

  /** вывести кол-во элементов */
  int size() {
    return size;
  }

  private class Node<V> {
    public V value;
    public Node<V> next;
    public Node<V> prev;

    public Node(V value) {
      this.value = value;
    }
  }
}
