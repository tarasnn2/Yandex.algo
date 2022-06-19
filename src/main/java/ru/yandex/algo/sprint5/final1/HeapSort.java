package ru.yandex.algo.sprint5.final1;
// 69040827
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Пирамидальная сортировка.
 *
 * <p>Вначале вставляем элементы в max-heap, благодаря просеиванию вверх на вершине оказывается самый приоритетный элемент.
 *
 * <p>Затем начинаем забирать самый приоритетный элемент, благодаря просеиванию вниз, после каждого изымания на вершине снова оказывается
 * самый приоритетный элемент.
 *
 * <p>Сложность каждого просеивания O(log H), где H - кол-во уровней дерева, сложность по памяти O(1)
 *
 * <p>Гарантируемая худшая сложность сортировки по выполнению O(N log N), по памяти O(log N)
 */
public class HeapSort {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String FILE = "input.txt";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint5/final1/input00.txt";

  public static void main(String[] args) throws IOException {
    final Participant[] participants = getParticipants();
    final MaxHeap<Participant> maxHeap = new MaxHeap<>();
    for (Participant p : participants) {
      maxHeap.push(p);
    }
    StringBuilder sb = new StringBuilder(participants.length);
    for (int i = 0; i < participants.length; i++) {
      sb.append(maxHeap.pop()).append(DELIMITER);
    }
    System.out.println(sb);
  }

  private static Participant[] getParticipants() throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int elementsCount = Integer.parseInt(in.readLine());
      final Participant[] participants = new Participant[elementsCount];
      int i = 0;
      while (i < elementsCount) {
        final String[] s = in.readLine().split(SEPARATOR);
        participants[i] = new Participant(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
        i++;
      }
      return participants;
    }
  }
}

// private static class - не компилится
class Participant implements Comparable<Participant> {
  private final String name;
  private final int score;
  private final int penalty;

  public Participant(String name, int score, int penalty) {
    this.name = name;
    this.score = score;
    this.penalty = penalty;
  }

  @Override
  public String toString() {
    return name;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public int getPenalty() {
    return penalty;
  }

  @Override
  public int compareTo(Participant b) {
    if (null == b) {
      return 1;
    }
    if (this.getScore() > b.getScore()) {
      return 1;
    } else if (this.getScore() < b.getScore()) {
      return -1;
    } else { // очки равны
      if (this.getPenalty() < b.getPenalty()) {
        return 1;
      } else if (this.getPenalty() > b.getPenalty()) {
        return -1;
      } else { // штрафы равны
        return Integer.compare(0, this.getName().compareTo(b.getName()));
      }
    }
  }
}

class MaxHeap<T extends Comparable<T>> {
  private final int CAPACITY = 1_000_000;
  private final Object[] heap = new Object[CAPACITY];
  private int size = 0;

  T pop() {
    final T result = getElement(1);
    heap[1] = heap[size];
    heap[size] = null;
    size--;
    siftDown(1);
    return result;
  }

  void push(T item) {
    size++;
    heap[size] = item;
    siftUp(size);
  }

  @SuppressWarnings("unchecked")
  private T getElement(int idx) {
    return (T) heap[idx];
  }

  private void siftDown(int index) {
    while (true) {
      final int leftIndex = index * 2;
      final int rightIndex = index * 2 + 1;
      if (leftIndex > size) {
        break;
      }
      final int maxChildIndex =
          (rightIndex <= size && getElement(rightIndex).compareTo(getElement(leftIndex)) > -1) ? rightIndex : leftIndex;
      if (getElement(index).compareTo(getElement(maxChildIndex)) > -1) {
        break;
      }
      swap(index, maxChildIndex);
      index = maxChildIndex;
    }
  }

  private void siftUp(int idx) {
    final int idxParent = getIdxParent(idx);
    if (idxParent == -1) {
      return;
    }
    final T vertexValue = getElement(idx);
    if (vertexValue.compareTo(getElement(idxParent)) > -1) {
      swap(idxParent, idx);
      siftUp(idxParent);
    }
  }

  private void swap(int idx1, int idx2) {
    final T temp = getElement(idx1);
    heap[idx1] = heap[idx2];
    heap[idx2] = temp;
  }

  private int getIdxParent(int idxChildren) {
    if (idxChildren == 1) {
      return -1;
    }
    final int index = idxChildren / 2;
    return (index >= heap.length || index == -1) ? -1 : index;
  }
}
