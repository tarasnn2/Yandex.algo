//package ru.yandex.algo.sprint3.k;

import java.util.Arrays;

/**
 * K. Сортировка слиянием
 *
 * <p>Ограничение времени 2 секунды Ограничение памяти 64Mb Ввод стандартный ввод или input.txt Вывод стандартный вывод или output.txt Гоше
 * дали задание написать красивую сортировку слиянием. Поэтому Гоше обязательно надо реализовать отдельно функцию merge и функцию
 * merge_sort. Функция merge принимает два отсортированных массива, сливает их в один отсортированный массив и возвращает его. Если
 * требуемая сигнатура имеет вид merge(array, left, mid, right), то первый массив задаётся полуинтервалом [ l e f t , m i d ) массива array,
 * а второй – полуинтервалом [ m i d , r i g h t ) массива array. Функция merge_sort принимает некоторый подмассив, который нужно
 * отсортировать. Подмассив задаётся полуинтервалом — его началом и концом. Функция должна отсортировать передаваемый в неё подмассив, она
 * ничего не возвращает. Функция merge_sort разбивает полуинтервал на две половинки и рекурсивно вызывает сортировку отдельно для каждой.
 * Затем два отсортированных массива сливаются в один с помощью merge. Заметьте, что в функции передаются именно полуинтервалы [ b e g i n ,
 * e n d ) , то есть правый конец не включается. Например, если вызвать merge_sort(arr, 0, 4), где a r r = [ 4 , 5 , 3 , 0 , 1 , 2 ] , то
 * будут отсортированы только первые четыре элемента, изменённый массив будет выглядеть как a r r = [ 0 , 3 , 4 , 5 , 1 , 2 ] . Реализуйте
 * эти две функции. Мы рекомендуем воспользоваться заготовками кода для данной задачи, расположенными по ссылке.
 */
public class Solution {
  public static int[] merge(int[] arr, int left, int mid, int right) {
    // [left,mid)
    // [mid,right)

    int it1 = 0;
    int it2 = 0;
    int[] result = new int[right - left];

    while (left + it1 < mid && mid + it2 < right) {
      if (arr[left + it1] < arr[mid + it2]) {
        result[it1 + it2] = arr[left + it1];
        it1++;
      } else {
        result[it1 + it2] = arr[mid + it2];
        it2++;
      }
    }

    while ((left + it1) < mid) {
      result[it1 + it2] = arr[left + it1];
      it1++;
    }

    while ((mid + it2) < right) {
      result[it1 + it2] = arr[mid + it2];
      it2++;
    }

    for (int i = 0; i < it1 + it2; i++) {
      arr[left + i] = result[i];
    }
    return arr;
  }

  public static void merge_sort(int[] arr, int left, int right) {
    // [begin,end)

    if ((left + 1) >= right) {
      return;
    }
    int mid = (left + right) / 2;
    merge_sort(arr, left, mid);
    merge_sort(arr, mid, right);
    merge(arr, left, mid, right);
  }

  public static void main(String[] args) {
    int[] a = {1, 4, 9, 2, 10, 11};
    int[] b = merge(a, 0, 3, 6);
    int[] expected = {1, 2, 4, 9, 10, 11};
    assert Arrays.equals(b, expected);
    int[] c = {1, 4, 2, 10, 1, 2};
    merge_sort(c, 0, 6);
    int[] expected2 = {1, 1, 2, 2, 4, 10};
    assert Arrays.equals(c, expected2);
  }
}
