//package ru.yandex.algo.sprint4.final2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class PlayWithHashTable {
  private static final String SEPARATOR = " ";
  private static final String DELIMITER = "\n";
  private static final String COMMAND_DELETE = "delete";
  private static final String COMMAND_PUT = "put";
  private static final String COMMAND_GET = "get";
  private static final String REPORT_COMMAND_NONE = "None";
  private static final HashTable<Integer, Integer> map = new HashTable<>();
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/final2/input13.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int countOfCommand = Integer.parseInt(in.readLine());
      int i = 0;
      StringBuilder sb = new StringBuilder();
      while (i++ < countOfCommand) {
        doCommand(in.readLine(), sb);
      }
      System.out.println(sb);
    }
  }

  private static void doCommand(String commandWithArg, StringBuilder sb) {
    final String[] commandWithArgArray = commandWithArg.split(SEPARATOR);
    final String command = commandWithArgArray[0];

    switch (command) {
      case COMMAND_DELETE:
        {
          final Integer key = Integer.valueOf(commandWithArgArray[1]);
          print(map.delete(key), sb);
          break;
        }
      case COMMAND_PUT:
        {
          final Integer key = Integer.valueOf(commandWithArgArray[1]);
          final Integer value = Integer.valueOf(commandWithArgArray[2]);
          map.put(key, value);
          // System.out.println("----");
          break;
        }
      case COMMAND_GET:
        {
          int key = Integer.parseInt(commandWithArgArray[1]);
          print(map.get(key), sb);
          break;
        }
      default:
        throw new IllegalArgumentException("The command doesn't correct : " + command);
    }
  }

  private static void print(Integer value, StringBuilder sb) {
    if (null == value) {
      sb.append(REPORT_COMMAND_NONE).append(DELIMITER);
    } else {
      sb.append(value).append(DELIMITER);
    }
  }
}

class HashTable<K extends Number, V extends Number> {
  private static final int CAPACITY = 99_999;
  private final Bucket<K, V>[] array = (Bucket<K, V>[]) new Bucket[CAPACITY];

  V put(K key, V value) {
    return getBucket(key)
        .map(
            bucket -> {
              bucket.setValue(value);
              return bucket.getValue();
            })
        .or(
            () -> {
              final Bucket<K, V> newBucket = new Bucket<>(key, value);
              final int index = getIndex(key);
              getHead(key)
                  .ifPresent(
                      head -> {
                        head.setPrev(newBucket);
                        newBucket.setNext(head);
                      });
              array[index] = newBucket;
              return Optional.of(newBucket.getValue());
            })
        .orElse(null);
  }

  V get(K key) {
    return getBucket(key)
        .map(Bucket::getValue)
        .orElse(null);
  }

  V delete(K key) {
    return getBucket(key)
        .map(
            bucketForDelete -> {
              final Bucket<K, V> next = bucketForDelete.getNext();
              final Bucket<K, V> prev = bucketForDelete.getPrev();
              if (null != next) {
                next.setPrev(prev);
              }
              if (null != prev) {
                prev.setNext(next);
              }
              bucketForDelete.setNext(null);
              bucketForDelete.setPrev(null);
              array[getIndex(key)] = prev;
              return bucketForDelete.getValue();
            })
        .orElse(null);
  }

  private Optional<Bucket<K, V>> getBucket(K key) {
    return getHead(key)
        .flatMap(head -> getBucketInChainByKey(head, key));
  }

  private Optional<Bucket<K, V>> getBucketInChainByKey(final Bucket<K, V> head, K key) {
    if (key.equals(head.getKey())) {
      return Optional.of(head);
    }
    Bucket<K, V> next = head.getNext();
    while (null != next) {
      if (key.equals(next.getKey())) {
        return Optional.of(next);
      } else {
        next = next.getNext();
      }
    }
    return Optional.empty();
  }

  private Optional<Bucket<K, V>> getHead(K key) {
    return Optional.ofNullable(array[getIndex(key)]);
  }

  static class Bucket<K, V> {

/*    public static void main(String[] args) {
      test();
    }*/

    public Bucket<K, V> getPrev() {
      return prev;
    }

    public void setPrev(Bucket<K, V> prev) {
      this.prev = prev;
    }

    private Bucket<K, V> prev;

    public Bucket(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public Bucket<K, V> getNext() {
      return next;
    }

    public void setNext(Bucket<K, V> next) {
      this.next = next;
    }

    private Bucket<K, V> next;
    private final K key;

    public K getKey() {
      return key;
    }

    public V getValue() {
      return value;
    }

    public void setValue(V value) {
      this.value = value;
    }

    private V value;
  }

  private int hash(K key) {
    return (int) key;
  }

  private int getIndex(K key) {
    //final int i = Math.abs(hash(key) % CAPACITY);
    // System.out.println("key: " + key + " i:" + i);
    return (int) key % CAPACITY;
  }

  private static void test() {
    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(1, 1);
      map.delete(1);
      assert map.get(1) == null;
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(354, 1);
      map.put(707, 1);
      map.delete(354);
      assert map.get(354) == null;
      assert map.get(707) != null;
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(354, 1);
      map.put(707, 1);
      final Integer delete1 = map.delete(354);
      assert delete1 != null;
      final Integer delete707 = map.delete(707);
      assert delete707 != null;
      assert map.get(354) == null;
      assert map.get(707) == null;
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(0, 1);
      map.put(99999, 2);
      assert map.get(0) == 1;
      assert map.get(99999) == 2;
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(0, 1);
      map.put(99999, 2);
      final Integer delete0 = map.delete(0);
      assert delete0.equals(1);
      assert map.get(0) == null;
      assert map.get(99999) == 2;
      final Integer delete99999 = map.delete(99999);
      assert delete99999.equals(2);
      assert map.get(99999) == null;
    }
    {
      final HashTable<Integer, Integer> map = new HashTable<>();

      map.put(206577450, 27041938);
      assert map.get(206577450).equals(27041938);

      map.put(206577450, 866671225);
      assert map.get(206577450).equals(866671225);

      final Integer delete = map.delete(206577450);
      assert delete.equals(866671225);

      // put 206577450 27041938
      // get 206577450
      // put 206577450 866671225
      // delete 20657745 expect 866671225
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(-1, 1);
      map.delete(-1);
      assert map.get(-1) == null;
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(Integer.MAX_VALUE, Integer.MAX_VALUE);
      map.delete(Integer.MAX_VALUE);
      assert map.get(Integer.MAX_VALUE) == null;
    }

    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      map.put(0, 1);
      map.put(0, 2);
      map.put(0, 3);
      map.put(0, 4);
      map.put(0, 5);
      map.put(99999, 6);
      map.put(99999, 7);
      map.put(99999, 8);
      map.put(99999, 9);
      map.put(99999, 10);
      map.put(-99999, 11);
      assert map.get(0).equals(5);
      assert map.get(99999).equals(10);

      final Integer delete0 = map.delete(0);
      assert delete0.equals(5);

      final Integer delete99999 = map.delete(99999);
      assert delete99999.equals(10);

      assert map.get(-99999).equals(11);
    }

    /*    {
      final HashTable<Integer, Integer> map = new HashTable<>();
      for (int i = 0; i <= 1_000_000; i++) {
        map.put(i, i);
      }
      for (int i = 0; i <= 1_000_000; i++) {
        assert map.get(i).equals(i);
      }

      for (int i = 0; i <= 1_000_000; i++) {
        assert map.delete(i).equals(i);
      }
    }*/
  }
}
