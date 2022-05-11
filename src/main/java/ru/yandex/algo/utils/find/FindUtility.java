package ru.yandex.algo.utils.find;

public class FindUtility {
  private static final String DESC = "DESK";
  private static final String ASC = "ASC";

  public static void main(String[] args) {
    testCompare();
    testBinaryFind();
    testLinearFind();
  }

  private static int compare(String a, String b) {
    final int result = a.compareTo(b);
    // System.out.println(a + ".compareTo(" + b + ") = " + result);
    return Integer.compare(0, result);
  }

  public static int binaryFind(String[] array, int start, int end, String pattern) {
    int mid = (start + end) / 2;
    int compareResult = compare(array[mid], pattern);
    if (0 == compareResult) {
      return mid;
    }
    if (start > end) {
      return -1; // not found
    }

    if (-1 == compareResult) {
      return binaryFind(array, start, mid - 1, pattern);
    } else {
      return binaryFind(array, mid + 1, end, pattern);
    }
  }

  public static int binaryFind(String[] array, String pattern) {
    return binaryFind(array, 0, array.length, pattern);
  }

  public static int binaryFind(String line, String pattern) {
    final String[] array = line.split("");
    return binaryFind(array, 0, array.length, pattern);
  }

  public static int linearFind(String line, String pattern) {
    final String[] array = line.split("");
    return linearFind(array, 0, array.length - 1, pattern);
  }

  public static int linearFind(String[] array, int start, int end, String pattern) {
    for (int i = start; i <= end; i++) {
      if (array[i].equals(pattern)) {
        return i;
      }
    }
    return -1;
  }

  private static void testCompare() {
    System.out.println("Test testCompare start");
    assert compare("a", "z") == 1;
    assert compare("z", "a") == -1;
    assert compare("a", "b") == 1;
    assert compare("c", "b") == -1;
    assert compare("a", "a") == 0;
    assert compare("e", "i") == 1;
    assert compare("t", "i") == -1;
    System.out.println("Test testCompare end");
  }

  private static void testBinaryFind() {
    System.out.println("Test testBinaryFind start");
    String[] array = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "z"};
    assert binaryFind(array, "d") == 3;
    assert binaryFind(array, "y") == -1;
    assert binaryFind(array, "z") == 8;
    assert binaryFind(array, "a") == 0;
    assert binaryFind(array, 7, 8, "h") == 7;
    assert binaryFind(array, 5, 8, "h") == 7;
    System.out.println("Test testBinaryFind end");
  }

  private static void testLinearFind() {
    System.out.println("Test testLinearFind start");
    String line = "yoytgtshldmogkdburkbcfvoapepjpcuwemusfkfztrzxstytrnarlizjhuoscuzlraezlaweipuuqdgvhwkhhoufexojaps";
    assert linearFind(line, "i") == 54;
    System.out.println("Test testLinearFind stop");
  }
}
