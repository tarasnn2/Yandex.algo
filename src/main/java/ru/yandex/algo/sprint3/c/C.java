package ru.yandex.algo.sprint3.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Гоша любит играть в игру «Подпоследовательность»: даны 2 строки, и нужно понять, является ли первая из них подпоследовательностью второй.
 * Когда строки достаточно длинные, очень трудно получить ответ на этот вопрос, просто посмотрев на них. Помогите Гоше написать функцию,
 * которая решает эту задачу.
 *
 * <p>Формат ввода В первой строке записана строка s.
 *
 * <p>Во второй —- строка t.
 *
 * <p>Обе строки состоят из маленьких латинских букв, длины строк не превосходят 150000. Строки могут быть пустыми.
 *
 * <p>Формат вывода Выведите True, если s является подпоследовательностью t, иначе —– False.
 */
public class C {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/c/input13.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    final String s;
    final String t;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      s = in.readLine();
      t = in.readLine();
    }
    if (s.isEmpty()) {
      System.out.println("True");
      return;
    }

    if (s.length() > t.length()) {
      System.out.println("False");
      return;
    }

    int pos = -1;
    for (int i = 0; i < s.length(); i++) {
      pos = t.indexOf(s.substring(i, i + 1), pos + 1);
      if (-1 == pos) {
        System.out.println("False");
        return;
      }
    }
    System.out.println("True");
  }
}
