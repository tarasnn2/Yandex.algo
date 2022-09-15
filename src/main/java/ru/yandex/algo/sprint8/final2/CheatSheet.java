// 70463799
package ru.yandex.algo.sprint8.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Сначала строим бор. Сложность по вычислению построения бора составляет O(n), где n - кол-во символов во всех словах. Сложность по памяти
 * O(n). Затем проверяем, можно ли создать строку с данным индексом. Каждый индекс требует обход бора. Сложность по вычислению O(n^2), где n
 * кол-во символов в данной строке, сложность по памяти O(n).
 */

public class CheatSheet {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/Code/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/final2/input03.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final String line = in.readLine();
      final int count = Integer.parseInt(in.readLine());
      if (count == 0) {
        System.out.println("NO");
        return;
      }
      final Node root = buildTree(in, count);
      System.out.println(isSplit(line, root) ? "YES" : "NO");
    }
  }

  private static Node buildTree(BufferedReader in, int count) throws IOException {
    final Node root = new Node();
    for (int j = 1; j <= count; j++) {
      final String word = in.readLine();
      Node node = root;
      for (int i = 0; i < word.length(); i++) {
        final String s = word.substring(i, i + 1);
        if (null == node.next) {
          node.next = new HashMap<>();
        }
        if (null == node.next.get(s)) {
          node.next.put(s, new Node());
        }
        node = node.next.get(s);
      }
      node.terminal = word.length();
    }
    return root;
  }

  private static boolean isSplit(String line, Node root) {
    final boolean[] dp = new boolean[line.length() + 1];
    dp[0] = true;
    for (int i = 0; i < line.length(); i++) {
      Node node = root;
      if (dp[i]) {
        for (int j = i; j < line.length() + 1; j++) {
          if (node.terminal > 0) {
            dp[j] = true;
          }
          if (j == line.length() || (null == node.next)) {
            break;
          }
          final String s = line.substring(j, j + 1);
          if (null == node.next.get(s)) {
            break;
          }
          node = node.next.get(s);
        }
      }
    }
    return dp[line.length()];
  }


}

class Node {

  HashMap<String, Node> next;
  int terminal;
}