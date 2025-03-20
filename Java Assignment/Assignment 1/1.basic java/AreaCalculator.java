import java.util.Scanner;

public class AreaCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose shape: circle, rectangle, triangle");
        String shape = sc.nextLine();

        if (shape.equalsIgnoreCase("circle")) {
            System.out.print("Enter radius: ");
            double r = sc.nextDouble();
            System.out.println("Area: " + (3.14 * r * r));
        } else if (shape.equalsIgnoreCase("rectangle")) {
            System.out.print("Enter length and width: ");
            double l = sc.nextDouble(), w = sc.nextDouble();
            System.out.println("Area: " + (l * w));
        } else if (shape.equalsIgnoreCase("triangle")) {
            System.out.print("Enter base and height: ");
            double b = sc.nextDouble(), h = sc.nextDouble();
            System.out.println("Area: " + (0.5 * b * h));
        } else {
            System.out.println("Invalid shape!");
        }
        sc.close();
    }
}