import java.util.Scanner;

public class ReverseString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.next();
        String rev = new StringBuilder(str).reverse().toString();
        System.out.println("Reversed: " + rev);
        sc.close();
    }
}