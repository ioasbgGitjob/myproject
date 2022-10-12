import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {


    }

    public static int solveSuperMarketQueue(int[] customers, int n) {
        int[] result = new int[n];
        for (int i = 0; i < customers.length; i++) {
            result[0] += customers[i];
            Arrays.sort(result);
        }
        return result[n - 1];
    }

}

