package codewars;

import java.util.Arrays;

public class 超市排队结账 {
    public static void main(String[] args) {
//        int[] ints = {1,2,4};
//        solveSuperMarketQueue(ints,2);

        int[] ints = flattenAndSort(new int[][]{{3, 2, 1}, {7, 9, 8}, {6, 4, 5}});
        System.out.println(ints);
    }

    /**
     * @param customers  元素值=时间，size=人数
     * @param n   一共几个结账口
     * @return
     */
    public static int solveSuperMarketQueue(int[] customers, int n) {
        int[] result = new int[n];
        for(int i = 0; i < customers.length; i++){
            result[0] += customers[i];
            Arrays.sort(result);
        }
        return result[n-1];
    }

    public static int[] flattenAndSort(int[][] array) {
        int[] aa = new int[]{};
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            int[] ints = array[i];
            for (int j = 0; j <ints.length; j++) {
            aa[k] = ints[j];
            }
        }
        Arrays.sort(aa);
        return aa;
    }
}
