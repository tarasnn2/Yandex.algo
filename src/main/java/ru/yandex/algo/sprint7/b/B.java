package ru.yandex.algo.sprint7.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B {

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/b/input03.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int count = Integer.parseInt(in.readLine());
      Lesson[] lessons = new Lesson[count];

      for (int i = 0; i < count; i++) {
        String[] line = in.readLine().split(" ");
        lessons[i] = new Lesson(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
      }
      print(calc(lessons));
    }
  }

  private static void print(List<Lesson> list) {
    StringBuilder sb = new StringBuilder(list.size() * 2);
    sb.append(list.size()).append("\n");
    list.forEach(lesson -> {
      sb.append(lesson).append("\n");
    });
    System.out.println(sb);
  }

  private static List<Lesson> calc(Lesson[] lessons) {
    Arrays.sort(lessons, (l1, l2) -> {
      if (l1.end == l2.end) {
        return Double.compare(l1.start, l2.start);
      }
      return Double.compare(l1.end, l2.end);
    });

    List<Lesson> result = new ArrayList<>();
    Lesson prev = new Lesson(0, 0);
    for (Lesson lesson : lessons) {
      if (lesson.start >= prev.end) {
        prev = lesson;
        result.add(prev);
      }
    }
    return result;
  }

  static class Lesson {

    public Lesson(double start, double end) {
      this.start = start;
      this.end = end;
    }

    double start;
    double end;

    @Override
    public String toString() {
      return (start + "").replace(".0", "").concat(" ").concat((end + "").replace(".0", ""));
    }
  }

}

