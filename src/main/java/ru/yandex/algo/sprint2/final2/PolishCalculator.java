// 67834265

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Операции добавления / удаления работают за O(1) тк вставка / удаление происходят по индексу в массив.
 *
 * <p>Сложность по памяти O(n) тк массив будет заполнен примерно на n/2 (одна командна на две цифры), что асимптотически составляет n
 */
public class PolishCalculator {
  private static final String SEPARATOR = " ";
  private static final String OPERATION_PLUS = "+";
  private static final String OPERATION_MINUS = "-";
  private static final String OPERATION_DIVIDE = "/";
  private static final String OPERATION_MULTIPLE = "*";

  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] line = in.readLine().split(SEPARATOR);
      final Stack stack = new Stack(line.length);
      for (String item : line) {
        execute(item, stack);
      }
      System.out.println(stack.pop());
    }
  }

  private static void execute(String commandOrDigit, Stack stack) {
    int digit1, digit2;
    switch (commandOrDigit) {
      case OPERATION_PLUS:
        digit2 = stack.pop();
        digit1 = stack.pop();
        stack.push(digit1 + digit2);
        break;
      case OPERATION_MINUS:
        digit2 = stack.pop();
        digit1 = stack.pop();
        stack.push(digit1 - digit2);
        break;
      case OPERATION_DIVIDE:
        digit2 = stack.pop();
        digit1 = stack.pop();
        stack.push(divide(digit1, digit2));
        break;
      case OPERATION_MULTIPLE:
        digit2 = stack.pop();
        digit1 = stack.pop();
        stack.push(digit1 * digit2);
        break;
      default:
        stack.push(Integer.parseInt(commandOrDigit));
    }
  }

  private static int divide(int digit1, int digit2) {
    return (int) Math.floor((double) digit1 / (double) digit2);
  }
}

class Stack {

  /** внутренний массив хранения элементов */
  private int[] innerArray;
  /** указатель на первый элемент в дек */
  private int head = 0;

  /** текущее количество элементов в дек */
  private int elementsCount = 0;
  /** максимальное количество элементов в дек, равен размеру массива */
  private final int maxDequeSize;
  /** максимальный индекс в массиве */
  public Stack(int maxDequeSize) {
    this.maxDequeSize = maxDequeSize;
    innerArray = new int[maxDequeSize];
  }

  /** добавить элемент в начало */
  void push(int value) {
    validateSize();
    if (0 != elementsCount) {
      head = calcIndexBackward(head);
    }
    innerArray[head] = value;
    elementsCount++;
  }

  /** вывести первый элемент и удалить его */
  int pop() {
    if (0 == elementsCount) {
      throw new IndexOutOfBoundsException();
    }
    final int result = innerArray[head];
    if (elementsCount > 1) {
      head = calcIndexForward(head);
    }
    elementsCount--;
    return result;
  }

  private int calcIndexBackward(int index) {
    return (index - 1 + maxDequeSize) % maxDequeSize;
  }

  private int calcIndexForward(int index) {
    return (index + 1) % maxDequeSize;
  }

  private void validateSize() {
    if (elementsCount == maxDequeSize) {
      throw new IndexOutOfBoundsException();
    }
  }
}
