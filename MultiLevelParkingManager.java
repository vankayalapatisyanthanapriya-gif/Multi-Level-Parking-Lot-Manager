import java.util.Scanner;

public class MultiLevelParkingManager {

    static final int LEVELS = 3;
    static final int SPOTS_PER_LEVEL = 5;

    //  represents parking spaces
    // false = available, true = occupied
    static boolean[][] parkingLot =
            new boolean[LEVELS][SPOTS_PER_LEVEL];

    //  stores number of vehicles on each floor
    static int[] vehiclesPerLevel = new int[LEVELS];


  
    static void parkCar(int level, int spot) {

        if (parkingLot[level][spot]) {
            System.out.println("Parking spot is already occupied.");
        } else {
            parkingLot[level][spot] = true;
            vehiclesPerLevel[level]++;

            System.out.println(
                    "Car parked at Level " + (level + 1)
                    + ", Spot " + (spot + 1)
            );
        }
    }


    static void removeCar(int level, int spot) {

        if (parkingLot[level][spot]) {
            parkingLot[level][spot] = false;
            vehiclesPerLevel[level]--;

            System.out.println(
                    "Car removed from Level " + (level + 1)
                    + ", Spot " + (spot + 1)
            );
        } else {
            System.out.println("Parking spot is already empty.");
        }
    }


    static void displayParking() {

        System.out.println("\n===== PARKING STATUS =====");

        for (int i = 0; i < LEVELS; i++) {

            System.out.print("Level " + (i + 1) + ": ");

            for (int j = 0; j < SPOTS_PER_LEVEL; j++) {

                if (parkingLot[i][j]) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }

            System.out.println();
        }
    }


    static void findEmptySpot() {

        for (int i = 0; i < LEVELS; i++) {

            for (int j = 0; j < SPOTS_PER_LEVEL; j++) {

                if (!parkingLot[i][j]) {

                    System.out.println(
                            "Available spot: Level "
                            + (i + 1)
                            + ", Spot "
                            + (j + 1)
                    );

                    return;
                }
            }
        }

        System.out.println("Parking lot is full.");
    }


    static int totalVehicles() {

        int total = 0;

        for (int count : vehiclesPerLevel) {
            total += count;
        }

        return total;
    }


    static double averageVehicles() {

        return (double) totalVehicles() / LEVELS;
    }


    static void displayStatistics() {

        System.out.println("\n===== PARKING STATISTICS =====");

        for (int i = 0; i < LEVELS; i++) {

            System.out.println(
                    "Level " + (i + 1)
                    + " : "
                    + vehiclesPerLevel[i]
                    + " vehicles"
            );
        }

        System.out.println(
                "Total Vehicles: " + totalVehicles()
        );

        System.out.println(
                "Average Vehicles per Level: "
                + averageVehicles()
        );
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== MULTI-LEVEL PARKING LOT =====");
            System.out.println("1. Park Car");
            System.out.println("2. Remove Car");
            System.out.println("3. Display Parking");
            System.out.println("4. Find Empty Spot");
            System.out.println("5. Display Statistics");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            // CO2: switch statement
            switch (choice) {

                case 1:

                    System.out.print("Enter level (1-3): ");
                    int parkLevel = sc.nextInt();

                    System.out.print("Enter spot (1-5): ");
                    int parkSpot = sc.nextInt();

                    if (parkLevel >= 1 && parkLevel <= LEVELS
                            && parkSpot >= 1
                            && parkSpot <= SPOTS_PER_LEVEL) {

                        parkCar(parkLevel - 1, parkSpot - 1);

                    } else {

                        System.out.println(
                                "Invalid level or parking spot."
                        );
                    }

                    break;


                case 2:

                    System.out.print("Enter level (1-3): ");
                    int removeLevel = sc.nextInt();

                    System.out.print("Enter spot (1-5): ");
                    int removeSpot = sc.nextInt();

                    if (removeLevel >= 1
                            && removeLevel <= LEVELS
                            && removeSpot >= 1
                            && removeSpot <= SPOTS_PER_LEVEL) {

                        removeCar(
                                removeLevel - 1,
                                removeSpot - 1
                        );

                    } else {

                        System.out.println(
                                "Invalid level or parking spot."
                        );
                    }

                    break;


                case 3:

                    displayParking();
                    break;


                case 4:

                    findEmptySpot();
                    break;


                case 5:

                    displayStatistics();
                    break;


                case 6:

                    System.out.println(
                            "Exiting Parking Manager..."
                    );
                    break;


                default:

                    System.out.println(
                            "Invalid choice. Try again."
                    );
            }

        } while (choice != 6);

        sc.close();
    }
}
