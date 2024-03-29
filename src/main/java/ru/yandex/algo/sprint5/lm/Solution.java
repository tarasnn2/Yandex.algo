package ru.yandex.algo.sprint5.lm;

public class Solution {
  public static int siftDown(int[] heap, int idx) {
    final int idxLeftSibling = getIdxLeftSibling(heap, idx);
    final int idxRightSibling = getIdxRightSibling(heap, idx);
    if (idxLeftSibling == 0 && idxRightSibling == 0) {
      return idx;
    }
    final int vertexValue = heap[idx];
    if (idxLeftSibling != 0 && idxRightSibling != 0) {
      final int idxPriority = heap[idxLeftSibling] > heap[idxRightSibling] ? idxLeftSibling : idxRightSibling;
      if (heap[idxPriority] > vertexValue) {
        swap(heap, idxPriority, idx);
        return siftDown(heap, idxPriority);
      } else {
        return idx;
      }
    } else if (idxLeftSibling != 0 && heap[idxLeftSibling] > vertexValue) {
      swap(heap, idxLeftSibling, idx);
      return siftDown(heap, idxLeftSibling);
    } else if (idxRightSibling != 0 && heap[idxRightSibling] > vertexValue) {
      swap(heap, idxRightSibling, idx);
      return siftDown(heap, idxRightSibling);
    }
    return idx;
  }

  public static int siftUp(int[] heap, int idx) {
    final int idxParent = getIdxParent(heap, idx);
    if (idxParent == 0) {
      return idx;
    }
    final int vertexValue = heap[idx];
    if (heap[idxParent] < vertexValue) {
      swap(heap, idxParent, idx);
      return siftUp(heap, idxParent);
    }
    return idx;
  }

  private static void swap(int[] array, int idx1, int idx2) {
    final int temp = array[idx1];
    array[idx1] = array[idx2];
    array[idx2] = temp;
  }

  private static int getIdxParent(int[] heap, int idxChildren) {
    int index = idxChildren / 2;
    return (index >= heap.length || index == 0) ? 0 : index;
  }

  private static int getIdxLeftSibling(int[] heap, int idxVertex) {
    int index = idxVertex * 2;
    return index >= heap.length ? 0 : index;
  }

  private static int getIdxRightSibling(int[] heap, int idxVertex) {
    final int index = (idxVertex * 2) + 1;
    return index >= heap.length ? 0 : index;
  }

/*  public static void main(String[] args) {
    testDown();
    testUp();
  }*/

  private static void testDown() {
    int[] sample = {-1, 12, 1, 8, 3, 4, 7};
    final int i = siftDown(sample, 2);
    System.out.println(i);
    assert i == 5;
  }

  private static void testUp() {
    int[] sample = {-1, 12, 6, 8, 3, 15, 7};
    final int i = siftUp(sample, 5);
    System.out.println(i);
    assert i == 1;
  }
}
