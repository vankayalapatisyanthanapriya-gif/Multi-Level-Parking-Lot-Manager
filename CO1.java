import java.util.Scanner;

public class CO1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Primitive data types & variables
        final int LEVELS = 3; // constant
        final int SPOTS_PER_LEVEL = 10;
        boolean[][] parkingLot = new boolean[LEVELS][SPOTS_PER_LEVEL]; 
        // false = empty, true = occupied

        System.out.println("Welcome to Multi-Level Parking Lot Manager!");
        System.out.println("Total Levels: " + LEVELS + ", Spots per Level: " + SPOTS_PER_LEVEL);

        // Simple I/O
        System.out.print("Enter level (0-" + (LEVELS-1) + "): ");
        int level = sc.nextInt();
        System.out.print("Enter spot (0-" + (SPOTS_PER_LEVEL-1) + "): ");
        int spot = sc.nextInt();

        parkingLot[level][spot] = true; // mark occupied
        System.out.println("Car parked at Level " + level + ", Spot " + spot);
        sc.close();
    }
}

