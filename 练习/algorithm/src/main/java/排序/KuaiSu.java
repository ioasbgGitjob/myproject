package 排序;

import org.junit.Test;

import java.util.Arrays;

/**
 * 快速排序
 */
public class KuaiSu {

    @Test
    public void test1() throws Exception {
        int[] ii = new int[]{
                1, 324, 46, 5, 6, 457, 6, 7, 2321, 3, 5
        };
        System.out.println(ii.length);
        quickSort(ii, 0, ii.length - 1);

        Arrays.stream(ii).forEach(System.out::println);
    }

    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
