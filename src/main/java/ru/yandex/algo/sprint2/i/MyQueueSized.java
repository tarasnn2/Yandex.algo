package ru.yandex.algo.sprint2.i;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyQueueSized {
  private static final String REPORT_COMMAND_ERROR = "error";
  private static final String REPORT_COMMAND_NONE = "None";
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String COMMAND_POP = "pop";
  private static final String COMMAND_PEEK = "peek";
  private static final String COMMAND_PUSH = "push";
  private static final String COMMAND_SIZE = "size";

  private static final String FILE = "input.txt";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint2/i/input2.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int commandsCount = Integer.parseInt(in.readLine());
      final int maxQueueSize = Integer.parseInt(in.readLine());
      final Queue queue = new Queue(maxQueueSize);
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

  private static String doCommand(String commandWithArg, Queue queue) {
    final String[] commandWithArgArray = commandWithArg.split(SEPARATOR);
    final String command = commandWithArgArray[0];
    int value;
    String result = null;
    try {
      switch (command) {
        case COMMAND_POP:
          result = String.valueOf(queue.pop());
          break;
        case COMMAND_PEEK:
          result = String.valueOf(queue.peek());
          break;
        case COMMAND_PUSH:
          value = Integer.parseInt(commandWithArgArray[1]);
          queue.push(value);
          break;
        case COMMAND_SIZE:
          result = String.valueOf(queue.size());
          break;
        default:
          throw new IllegalArgumentException("The command doesn't correct : " + command);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      result = REPORT_COMMAND_ERROR;
    } catch (IndexOutOfBoundsException e) {
      result = REPORT_COMMAND_NONE;
    }
    return result;
  }
}

class Queue {

  /** внутренний массив хранения элементов */
  private final int[] innerArray;
  /** указатель на первый элемент */
  private int head = 0;
  /** указатель на последний элемент */
  private int tail = 0;

  /** текущее количество элементов */
  private int size = 0;
  /** максимальное количество элементов, равен размеру массива */
  private final int maxSize;
  /** максимальный индекс в массиве */
  public Queue(int maxSize) {
    this.maxSize = maxSize;
    innerArray = new int[maxSize];
  }

  /** добавить элемент в начало */
  void push(int value) {
    validateSize();
    if (0 != size) {
      head = calcIndexForward(head);
    }
    innerArray[head] = value;
    size++;
  }

  /** вывести первый элемент и удалить его */
  int pop() {
    if (0 == size) {
      throw new IndexOutOfBoundsException();
    }
    final int result = innerArray[tail];
    if (size > 1) {
      tail = calcIndexForward(tail);
    }
    size--;
    return result;
  }
  /** вывести первый элемент */
  int peek() {
    if (0 == size) {
      throw new IndexOutOfBoundsException();
    }
    return innerArray[tail];
  }

  /** вывести кол-во элементов */
  int size() {
    return size;
  }

  private int calcIndexForward(int index) {
    return maxSize - 1 == index ? 0 : ++index;
  }

  private void validateSize() {
    if (size == maxSize) {
      throw new ArrayIndexOutOfBoundsException();
    }
  }
}
