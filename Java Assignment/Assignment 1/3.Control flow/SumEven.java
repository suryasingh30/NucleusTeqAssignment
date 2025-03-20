public class SumEven {
    public static void main(String[] args) {
        int sum = 0, i = 2;
        while (i <= 10) {
            sum += i;
            i += 2;
        }
        System.out.println("Sum of Even Numbers: " + sum);
    }
}