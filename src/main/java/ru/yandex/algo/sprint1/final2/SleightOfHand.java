// 67442835
// Сложность по времени O(n)
// Сложность по памяти O(n)


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SleightOfHand {
  private static final String FILE = "input.txt";
  private static final int LINE_COUNT = 4;
  private static final int PLAYER_COUNT = 2;
  private static final String NIL_SYMBOL = ".";
  private static final String LINE_DELIMITER = "";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      final int k = Integer.parseInt(in.readLine()) * PLAYER_COUNT;
      final Map<Integer, Integer> sumValues = new HashMap<>();
      for (int i = 1; i <= LINE_COUNT; i++) {
        buildSumValues(in.readLine(), sumValues);
      }
      final int maxScore = calcMaxScore(sumValues, k);
      System.out.println(maxScore);
    }
  }

  private static int calcMaxScore(final Map<Integer, Integer> sumValues, final int k) {
    return (int) sumValues.values().stream().filter(sumValue -> sumValue <= k).count();
  }

  private static Map<Integer, Integer> buildSumValues(final String line, final Map<Integer, Integer> sumValues) {
    for (String item : line.split(LINE_DELIMITER)) {
      if (NIL_SYMBOL.equals(item)) {
        continue;
      }
      sumValues.merge(Integer.parseInt(item), 1, Integer::sum);
    }
    return sumValues;
  }
}
