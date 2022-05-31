// 68586205
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Индексация работает за O(n) и требует O(n) памяти где n - кол-во слов в документе.
 *
 * <p>Сбор статистики O(m) и требует O(m) памяти, где m - кол-во слов в запросе.
 *
 * <p>Для каждого запроса выполняется сортировка за O(s*log s) где s - кол-во релевантных документов
 *
 * <p>Таким образом, асимптотическая сложность вычисления и сортировки релевантных документов для одного запроса: O(n+m+k+s*log s) по
 * вычислению и O(n+m) памяти
 */
public class SearchSystem {

  private static final String DELIMITER = "\n";
  private static final String SEPARATOR = " ";
  private static final int LIMIT = 5;
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int documentCount = Integer.parseInt(in.readLine());
      int documentNumber = 0;
      final Map<String, Map<Integer, Integer>> index = new HashMap<>();
      while (documentNumber++ < documentCount) {
        buildIndexDocument(in.readLine(), documentNumber, index);
      }

      final int requestCount = Integer.parseInt(in.readLine());
      int requestNumber = 0;
      final StringBuilder report = new StringBuilder();
      while (requestNumber++ < requestCount) {
        final Map<Integer, Integer> statistics = buildStatistic(in.readLine(), index);
        makeReport(new ArrayList<>(statistics.entrySet()), report);
      }

      System.out.println(report);
    }
  }

  private static void buildIndexDocument(String document, Integer documentNumber, Map<String, Map<Integer, Integer>> index) {
    Arrays.stream(document.split(SEPARATOR))
        .forEach(
            word -> {
              final Map<Integer, Integer> wordStats = index.computeIfAbsent(word, k -> new HashMap<>());
              final Integer count = wordStats.getOrDefault(documentNumber, 0);
              wordStats.put(documentNumber, count + 1);
            });
  }

  private static Map<Integer, Integer> buildStatistic(String request, Map<String, Map<Integer, Integer>> index) {
    final Map<Integer, Integer> statistics = new HashMap<>();
    Arrays.stream(request.split(SEPARATOR))
        .collect(Collectors.toCollection(HashSet::new))
        .forEach(
            word -> {
              final Map<Integer, Integer> wordStats = index.get(word);
              if (null != wordStats) {
                wordStats.forEach(
                    (documentNumber, count) -> {
                      final Integer relevance = statistics.get(documentNumber);
                      final Integer countInDocument = wordStats.get(documentNumber);
                      if (null == relevance) {
                        statistics.put(documentNumber, countInDocument);
                      } else {
                        statistics.put(documentNumber, relevance + countInDocument);
                      }
                    });
              }
            });
    return statistics;
  }

  private static void makeReport(List<Entry<Integer, Integer>> list, StringBuilder report) {
    list.stream()
        .sorted(
            (o1, o2) -> {
              if (o1.getValue() > o2.getValue()) {
                return -1;
              } else if (o1.getValue() < o2.getValue()) {
                return 1;
              } else {
                return o1.getKey().compareTo(o2.getKey());
              }
            })
        .limit(LIMIT)
        .forEachOrdered(entry -> report.append(entry.getKey()).append(SEPARATOR));
    report.append(DELIMITER);
  }
}
