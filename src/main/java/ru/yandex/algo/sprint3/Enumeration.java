package ru.yandex.algo.sprint3;

public class Enumeration {

  public static void main(String[] args) {
    genBinary(1, "a");
  }

  private static void genBinary(int n, String prefix) {
    if (n == 0) {
      System.out.println(prefix);
    } else {
      //genBinary(n - 1, prefix + 'a');
      //genBinary(n - 1, prefix + 'b');
      //genBinary(n - 1, prefix + 'c');
      genBinary(n - 1, prefix + 'd');
      genBinary(n - 1, prefix + 'e');
      genBinary(n - 1, prefix + 'f');
    }
  }
}
