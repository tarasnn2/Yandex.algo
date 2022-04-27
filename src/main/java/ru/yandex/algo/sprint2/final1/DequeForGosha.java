// 67834220
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Операции добавления / удаления в работают за O(1) тк вставка / удаление происходят по индексу в массив.
 *
 * <p>Сложность по памяти O(n) тк зависит от кол-ва элементов
 */
public class DequeForGosha {

  private static final String REPORT_COMMAND_ERROR = "error";
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String COMMAND_POP_FRONT = "pop_front";
  private static final String COMMAND_POP_BACK = "pop_back";
  private static final String COMMAND_PUSH_BACK = "push_back";
  private static final String COMMAND_PUSH_FRONT = "push_front";

  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int commandsCount = Integer.parseInt(in.readLine());
      final int maxDequeSize = Integer.parseInt(in.readLine());
      final Deque deque = new Deque(maxDequeSize);
      final StringBuilder result = new StringBuilder();
      int i = 1;
      while (i++ <= commandsCount) {
        final String commandWithArg = in.readLine().trim();
        final String s = doCommand(commandWithArg, deque);
        if (null != s) {
          result.append(s).append(DELIMITER);
        }
      }
      System.out.println(result);
    }
  }

  private static String doCommand(String commandWithArg, Deque deque) {
    final String[] commandWithArgArray = commandWithArg.split(SEPARATOR);
    final String command = commandWithArgArray[0];
    int value;
    String result = null;
    try {
      switch (command) {
        case COMMAND_POP_FRONT:
          result = String.valueOf(deque.popFront());
          break;
        case COMMAND_POP_BACK:
          result = String.valueOf(deque.popBack());
          break;
        case COMMAND_PUSH_BACK:
          value = Integer.parseInt(commandWithArgArray[1]);
          deque.pushBack(value);
          break;
        case COMMAND_PUSH_FRONT:
          value = Integer.parseInt(commandWithArgArray[1]);
          deque.pushFront(value);
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

class Deque {

  /** внутренний массив хранения элементов */
  private int[] innerArray;
  /** указатель на первый элемент в дек */
  private int head = 0;
  /** указатель на последний элемент в дек */
  private int tail = 0;
  /** текущее количество элементов в дек */
  private int elementsCount = 0;
  /** максимальное количество элементов в дек, равен размеру массива */
  private final int maxDequeSize;
  /** максимальный индекс в массиве */
  public Deque(int maxDequeSize) {
    this.maxDequeSize = maxDequeSize;
    innerArray = new int[maxDequeSize];
  }

  /** добавить элемент в конец дека. */
  void pushBack(int value) {
    validateSize();
    if (0 != elementsCount) {
      tail = calcIndexForward(tail);
    }
    innerArray[tail] = value;
    elementsCount++;
  }

  /** добавить элемент в начало дека. */
  void pushFront(int value) {
    validateSize();
    if (0 != elementsCount) {
      head = calcIndexBackward(head);
    }
    innerArray[head] = value;
    elementsCount++;
  }

  /** вывести первый элемент дека и удалить его */
  int popFront() {
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

  /** вывести последний элемент дека и удалить его */
  int popBack() {
    if (0 == elementsCount) {
      throw new IndexOutOfBoundsException();
    }
    final int result = innerArray[tail];
    if (elementsCount > 1) {
      tail = calcIndexBackward(tail);
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
