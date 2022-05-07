package ru.yandex.algo.sprint3.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;

/**
 * https://contest.yandex.ru/contest/23638/problems/A/
 *
 * <p>Рита по поручению Тимофея наводит порядок в правильных скобочных последовательностях (ПСП), состоящих только из круглых скобок (). Для
 * этого ей надо сгенерировать все ПСП длины 2n в алфавитном порядке —– алфавит состоит из ( и ) и открывающая скобка идёт раньше
 * закрывающей.
 *
 * <p>Помогите Рите —– напишите программу, которая по заданному n выведет все ПСП в нужном порядке.
 *
 * <p>Рассмотрим второй пример. Надо вывести ПСП из четырёх символов. Таких всего две:
 *
 * <p>(()) ()() (()) идёт раньше ()(), так как первый символ у них одинаковый, а на второй позиции у первой ПСП стоит (, который идёт раньше
 * )
 */
public class A {

  private static final String LEFT_BRACKET = "(";
  private static final String RIGHT_BRACKET = ")";
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/a/input01.txt";
  private static final String FILE = "input.txt";
  private static final String DELIMITER = "\n";

  public static void main(String[] args) throws IOException {
    int n;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      n = Integer.parseInt(in.readLine());
    }
    StringBuilder sb = genBinary(n * 2, "", new StringBuilder());
    System.out.println(sb);
  }

  private static StringBuilder genBinary(int n, String prefix, StringBuilder sb) {
    if (n == 0) {
      if (isRightSequence(prefix)) {
        sb.append(prefix).append(DELIMITER);
      }
    } else {
      genBinary(n - 1, prefix + "(", sb);
      genBinary(n - 1, prefix + ")", sb);
    }
    return sb;
  }

  private static boolean isRightSequence(String line) {
    if (line.isEmpty()) {
      return true;
    }
    boolean result = true;
    final Deque<String> queue = new LinkedList<>();
    for (String s : line.split("")) {
      if (s.equals(LEFT_BRACKET)) {
        queue.push(s);
      } else {
        if (queue.isEmpty()) {
          result = false;
          break;
        }
        final String fromQueue = queue.pop();
        if (!(LEFT_BRACKET.equals(fromQueue) && RIGHT_BRACKET.equals(s))) {
          result = false;
          break;
        }
      }
    }
    if (!queue.isEmpty()) {
      return false;
    }

    return result;
  }
}
