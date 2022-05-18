package ru.yandex.algo.sprint3.final2;
// 68483156
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/** Поиск основан на быстрой сортировки, работающей в среднем за O(log n) объемная сложность в среднем O(log n) */
public class ScoringCounter {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/final2/input02.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {

    final Participant[] participants = getParticipants();

    new QuickSortInPlace<Participant>(Direction.DESC).sort(participants);

    print(participants);
  }

  private static void print(Participant[] participants) {
    final StringBuilder sb = new StringBuilder(participants.length * 2);
    final int preLastIndex = participants.length - 1;
    for (int i = 0; i < participants.length; i++) {
      sb.append(participants[i].getName());
      if (i < preLastIndex) {
        sb.append(DELIMITER);
      }
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

class QuickSortInPlace<T extends Comparable<T>> {

  public QuickSortInPlace(Direction direction) {
    this.direction = direction;
  }

  private final Direction direction;

  public void sort(T[] array) {
    sort(array, 0, array.length - 1);
  }

  public void sort(T[] array, int begin, int end) {
    if (begin >= end) {
      return;
    }
    final int partitionIndex = partition_r(array, begin, end);
    sort(array, begin, partitionIndex - 1);
    sort(array, partitionIndex + 1, end);
  }

  private int partition_r(T[] array, int begin, int end) {
    final int random = ThreadLocalRandom.current().nextInt(begin, end + 1);
    swap(array, random, end);
    return partition(array, begin, end);
  }

  private int partition(T[] array, int begin, int end) {

    final T pivot = array[end];
    int i = begin-1;
    final int d1 = (Direction.ASC == direction ? -1 : 1);
    final int d2 = (Direction.ASC == direction ? 0 : 1);
    for (int j = begin; j < end; j++) {
      final int resultCompare = array[j].compareTo(pivot);
      if (d1 == resultCompare || d2 == resultCompare) {
        swap(array, ++i, j);
      }
    }
    final int iNext = i + 1;
    swap(array, iNext, end);
    return iNext;
  }

  private static <T> void swap(T[] array, int i, int j) {
    final T swapTemp = array[i];
    array[i] = array[j];
    array[j] = swapTemp;
  }
}

enum Direction {
  DESC,
  ASC;
}
