package ru.yandex.algo.sprint5.final1;

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
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint5/final1/input01.txt";
  // private static final String FILE = "input.txt";

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

class Participant implements Comparable<Participant> {
  Participant(String name, int score, int penalty) {
    this.name = name;
    this.score = score;
    this.penalty = penalty;
  }

  @Override
  public String toString() {
    return name;
  }

  private final String name;
  private final int score;
  private final int penalty;

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
    final T result = getElement(0);
    heap[0] = heap[size];
    heap[size] = null;
    size--;
    siftDown(0);
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

  private int siftDown(int idx) {
    final int idxLeftSibling = getIdxLeftSibling(idx);
    final int idxRightSibling = getIdxRightSibling(idx);
    if (idxLeftSibling == -1 && idxRightSibling == -1) {
      return idx;
    }
    final T vertexValue = getElement(idx);
    if (idxLeftSibling != -1 && idxRightSibling != -1) {
      final int idxPriority = getIdxPriority(idxLeftSibling, idxRightSibling);
      final T idxPriorityValue = getElement(idxPriority);
      if (null == idxPriorityValue) {
        return idx;
      }
      if (idxPriorityValue.compareTo(vertexValue) > -1) {
        swap(idxPriority, idx);
        return siftDown(idxPriority);
      } else {
        return idx;
      }
    } else if (idxLeftSibling != -1 && getElement(idxLeftSibling).compareTo(vertexValue) > -1) {
      swap(idxLeftSibling, idx);
      return siftDown(idxLeftSibling);
    } else if (idxRightSibling != -1 && getElement(idxRightSibling).compareTo(vertexValue) > -1) {
      swap(idxRightSibling, idx);
      return siftDown(idxRightSibling);
    }
    return idx;
  }

  private int getIdxPriority(int idxLeftSibling, int idxRightSibling) {
    final T leftSiblingValue = getElement(idxLeftSibling);
    if (null == leftSiblingValue) {
      return idxRightSibling;
    }
    final T rightSiblingValue = getElement(idxRightSibling);
    if (null == rightSiblingValue) {
      return idxLeftSibling;
    }
    return leftSiblingValue.compareTo(rightSiblingValue) > -1 ? idxLeftSibling : idxRightSibling;
  }

  private int siftUp(int idx) {
    final int idxParent = getIdxParent(idx);
    if (idxParent == -1) {
      return idx;
    }
    final T vertexValue = getElement(idx);
    if (vertexValue.compareTo(getElement(idxParent)) > -1) {
      swap(idxParent, idx);
      return siftUp(idxParent);
    }
    return idx;
  }

  private void swap(int idx1, int idx2) {
    final T temp = getElement(idx1);
    heap[idx1] = heap[idx2];
    heap[idx2] = temp;
  }

  private int getIdxParent(int idxChildren) {
    if (idxChildren == 0) {
      return -1;
    }
    final int index = idxChildren / 2;
    return (index >= heap.length || index == -1) ? -1 : index;
  }

  private int getIdxLeftSibling(int idxVertex) {
    final int index = idxVertex * 2;
    return index >= heap.length ? -1 : index;
  }

  private int getIdxRightSibling(int idxVertex) {
    final int index = (idxVertex * 2) + 1;
    return index >= heap.length ? -1 : index;
  }
}
