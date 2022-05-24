package ru.yandex.algo.sprint4.final1;

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

public class SearchSystem {

  private static final String DELIMITER = "\n";
  private static final String SEPARATOR = " ";
  private static final Map<String, Map<Integer, Integer>> index = new HashMap<>();
  private static final StringBuilder report = new StringBuilder();
  private static final int LIMIT = 5;

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/final1/input11.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int documentCount = Integer.parseInt(in.readLine());
      int documentNumber = 0;
      while (documentNumber++ < documentCount) {
        documentIndexing(in.readLine(), documentNumber);
      }

      final int requestCount = Integer.parseInt(in.readLine());
      int requestNumber = 0;
      while (requestNumber++ < requestCount) {
        final Map<Integer, Integer> statistics = buildStatistic(in.readLine());
        makeReport(new ArrayList<>(statistics.entrySet()));
      }

      System.out.println(report);
    }
  }

  private static void documentIndexing(String document, Integer documentNumber) {
    // if (documentNumber == 16 || documentNumber == 83) System.out.println("documentNumber:" + documentNumber + " document:" + document);

    Arrays.stream(document.split(SEPARATOR))
        .forEach(
            word -> {
              final Map<Integer, Integer> wordStats = index.computeIfAbsent(word, k -> new HashMap<>());
              Integer count = wordStats.getOrDefault(documentNumber, 0);
              wordStats.put(documentNumber, ++count);
            });
  }

  private static Map<Integer, Integer> buildStatistic(String request) {
    final Map<Integer, Integer> statistics = new HashMap<>();
    //System.out.println();
    //System.out.println("Запрос " + request);

    Arrays.stream(request.split(SEPARATOR))
        .collect(Collectors.toCollection(HashSet::new))
        .forEach(
            word -> {
              final Map<Integer, Integer> wordStats = index.get(word);
              if (null != wordStats) {
                wordStats.forEach(
                    (documentNumber, count) -> {
                      //System.out.println("Слово " + word + " есть в документе " + documentNumber);

/*                      if (documentNumber == 16 && (word.equals("dz") || word.equals("vnebtz"))) {
                        wordStats.get(word);
                      }*/

                      Integer relevance = statistics.get(documentNumber);
                      final Integer countInDocument = wordStats.get(documentNumber);
                      if (null == relevance) {
                        statistics.put(documentNumber, countInDocument);
                      } else {
                        statistics.put(documentNumber, relevance + countInDocument);
                      }
                      //System.out.println("Релевантность документа " + documentNumber + " стала " + statistics.get(documentNumber));
                    });
              }
            });
    //statistics.forEach((integer, integer2) -> System.out.println("Документ " + integer + " релевантность " + integer2));
    return statistics;
  }

  private static void makeReport(List<Entry<Integer, Integer>> list) {
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
