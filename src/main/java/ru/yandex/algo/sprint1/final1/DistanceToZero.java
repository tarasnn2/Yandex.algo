// 67442507
// Сложность по времени O(n)
// Сложность по памяти O(n)


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistanceToZero {

  private static final String SEPARATOR = " ";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      final int streetLength = Integer.parseInt(in.readLine());
      final int[] numbers = parseNumbers(in);
      final List<Integer> distanceToZero = getDistanceToZero(numbers, streetLength);
      print(distanceToZero);
    }
  }

  private static int[] parseNumbers(final BufferedReader in) throws IOException {
    return Arrays.stream(in.readLine().trim().split(SEPARATOR))
        .mapToInt(Integer::parseInt)
        .toArray();
  }

  private static List<Integer> getDistanceToZero(final int[] numbers, final int streetLength) {
    final List<Integer> result = new ArrayList<>(streetLength);
    final List<Integer> zeroIndex = fromLeftToRight(numbers, streetLength, result);
    return fromRightToLeft(zeroIndex, result);
  }

  private static List<Integer> fromRightToLeft(final List<Integer> zeroIndex, final List<Integer> result) {
    for (int zIndex : zeroIndex) {
      int counter = 1;
      for (int i = zIndex - 1; i >= 0; i--) {
        if (null == result.get(i) || result.get(i) > 0) {
          result.set(i, counter++);
        }
        if (result.get(i) == 0 || isLastBeforePeak(result, i)) {
          break;
        }
      }
    }
    return result;
  }

  private static boolean isLastBeforePeak(final List<Integer> result, final int currentPosition) {
    final int nextPosition = currentPosition - 1;
    if (nextPosition < 0) {
      return true;
    }
    final int nextNextPosition = currentPosition - 2;
    if (nextNextPosition < 0) {
      return result.get(currentPosition).equals(result.get(nextPosition));
    }
    return result.get(currentPosition).equals(result.get(nextNextPosition)) || result.get(currentPosition).equals(result.get(nextPosition));
  }

  private static List<Integer> fromLeftToRight(final int[] numbers, final int streetLength, final List<Integer> result) {
    int counter = 0;
    boolean stepOverZero = false;
    final List<Integer> zeroIndex = new ArrayList<>();
    for (int i = 0; i < streetLength; i++) {
      if (numbers[i] == 0) {
        zeroIndex.add(i);
        counter = 0;
        result.add(i, counter++);
        stepOverZero = true;
      } else if (stepOverZero) {
        result.add(i, counter++);
      } else {
        result.add(null);
      }
    }
    return zeroIndex;
  }

  private static void print(final List<Integer> lengthToZero) {
    final StringBuilder result = new StringBuilder(lengthToZero.size() * 2);
    lengthToZero.forEach(integer -> result.append(integer).append(SEPARATOR));
    System.out.println(result);
  }
}
