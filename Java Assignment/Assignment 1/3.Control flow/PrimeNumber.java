import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt(), flag = 1;
        if (n < 2) flag = 0;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                flag = 0;
                break;
            }
        }
        System.out.println(n + (flag == 1 ? " is Prime" : " is not Prime"));
        sc.close();
    }
}