// package ru.yandex.algo.sprint3.g;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Рита решила оставить у себя одежду только трёх цветов: розового, жёлтого и малинового. После того как вещи других расцветок были убраны,
 * Рита захотела отсортировать свой новый гардероб по цветам. Сначала должны идти вещи розового цвета, потом —– жёлтого, и в конце —–
 * малинового. Помогите Рите справиться с этой задачей.
 *
 * <p>Примечание: попробуйте решить задачу за один проход по массиву!
 */
public class G {
  private static final String SEPARATOR = " ";
  // private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/g/input03.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    execute();
  }

  private static void execute() throws IOException {
    final String[] elements;
    int elementsCount;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      elementsCount = Integer.parseInt(in.readLine());
      if (0 == elementsCount) {
        return;
      }
      elements = in.readLine().split(SEPARATOR);
    }

    final int[] sorted = sort(elements, SortUtility.Direction.ASC, 3);

    StringBuilder result = new StringBuilder(sorted.length);
    for (int s : sorted) {
      result.append(s).append(" ");
    }
    System.out.print(result.toString().trim());
  }

  public static int[] sort(String[] array, SortUtility.Direction direction, int maxValue) {
    final int[] ints = Arrays.stream(array).mapToInt(Integer::parseInt).toArray();
    return SortUtility.countingSort(ints, direction, maxValue);
  }
}

class SortUtility {

  public static int[] countingSort(int[] array, Direction direction, int maxValue) {
    final Integer[] countedArray = new Integer[maxValue];

    for (int value : array) {
      if (null == countedArray[value]) {
        countedArray[value] = 1;
      } else {
        countedArray[value]++;
      }
    }

    final int[] result = new int[array.length];
    int index = 0;
    if (Direction.ASC == direction) {
      for (int i = 0; i < countedArray.length; i++) {
        index = moveCounted(i, index, countedArray, result);
      }
    } else {
      for (int i = countedArray.length - 1; i >= 0; i--) {
        index = moveCounted(i, index, countedArray, result);
      }
    }
    return result;
  }

  private static int moveCounted(int i, int index, Integer[] countedArray, int[] result) {
    if (null == countedArray[i]) {
      return index;
    }
    for (int j = 1; j <= countedArray[i]; j++) {
      result[index] = i;
      index++;
    }
    return index;
  }

  enum Direction {
    DESC,
    ASC;
  }
}
