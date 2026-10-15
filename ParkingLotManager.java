import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

// ============================================================================
// MODULE 4: EXCEPTION HANDLING (Custom Checked Exception)
// ============================================================================
class InvalidSlotException extends Exception {
    public InvalidSlotException(String message) {
        super(message);
    }
}

// ============================================================================
// MODULE 4: INTERFACES & ABSTRACTION
// ============================================================================
interface ParkingOperations {
    double calculateFee();
    void printTicket();
}

// Base Abstract Class (Inheritance "is-a" relationship)
abstract class Vehicle implements ParkingOperations, Comparable<Vehicle> {
    private String plateNo;
    private String ownerName;
    private double hoursParked;
    private int statusFlag; // Module 1: Bitwise status flags (1 = Active, 2 = VIP)

    // Module 4: Default Constructor
    public Vehicle() {
    }

    // Module 4: Parameterized Constructor
    public Vehicle(String plateNo, String ownerName, double hoursParked, int statusFlag) throws InvalidSlotException {
        if (plateNo == null || plateNo.trim().isEmpty()) {
            throw new InvalidSlotException("License plate number cannot be empty!");
        }
        this.plateNo = plateNo.toUpperCase().trim();
        this.ownerName = ownerName.trim();
        setHoursParked(hoursParked); // Call mutator with validation
        this.statusFlag = statusFlag;
    }

    // Encapsulation: Getters and Setters
    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public double getHoursParked() { return hoursParked; }
    public void setHoursParked(double hoursParked) throws InvalidSlotException {
        if (hoursParked <= 0.0) {
            throw new InvalidSlotException("Parking duration must be greater than 0 hours!");
        }
        this.hoursParked = hoursParked;
    }

    public int getStatusFlag() { return statusFlag; }

    // Module 5: Comparable implementation for sorting by hours parked (descending)
    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(other.hoursParked, this.hoursParked);
    }

    // Module 4: toString() overriding
    @Override
    public String toString() {
        return String.format("%s,%s,%.2f,%d", plateNo, ownerName, hoursParked, statusFlag);
    }
}

// Derived Class 1: Car
class Car extends Vehicle {
    private static int totalCars = 0; // Static member
    public static final double RATE_PER_HOUR = 20.0; // Constant

    public Car() {
        super();
        totalCars++;
    }

    public Car(String plateNo, String ownerName, double hoursParked, int statusFlag) throws InvalidSlotException {
        super(plateNo, ownerName, hoursParked, statusFlag);
        totalCars++;
    }

    public static int getTotalCars() { return totalCars; }

    @Override
    public double calculateFee() {
        return getHoursParked() * RATE_PER_HOUR;
    }

    @Override
    public void printTicket() {
        // Module 5: StringBuilder usage
        StringBuilder sb = new StringBuilder();
        sb.append("--- CAR TICKET --- ")
          .append("Plate: ").append(getPlateNo())
          .append(" | Owner: ").append(getOwnerName())
          .append(" | Hours: ").append(getHoursParked())
          .append(" | Total Fee: ₹").append(calculateFee());
        System.out.println(sb.toString());
    }
}

// Derived Class 2: Electric Car
class ElectricCar extends Vehicle {
    private static int totalEVs = 0;
    public static final double EV_RATE_PER_HOUR = 25.0;
    private double chargingFee;

    public ElectricCar(String plateNo, String ownerName, double hoursParked, int statusFlag, double chargingFee) throws InvalidSlotException {
        super(plateNo, ownerName, hoursParked, statusFlag);
        this.chargingFee = chargingFee;
        totalEVs++;
    }

    public static int getTotalEVs() { return totalEVs; }

    @Override
    public double calculateFee() {
        return (getHoursParked() * EV_RATE_PER_HOUR) + chargingFee;
    }

    @Override
    public void printTicket() {
        System.out.printf("--- EV TICKET --- Plate: %s | Owner: %s | Hours: %.1f | Charging Fee: ₹%.2f | Total: ₹%.2f\n",
                getPlateNo(), getOwnerName(), getHoursParked(), chargingFee, calculateFee());
    }
}

// ============================================================================
// MODULE 3: RECURSION, METHODS & MATRIX MATHEMATICS
// ============================================================================
class ParkingUtils {

    // Module 3: Recursion (Base case & Recursive case)
    public static int calculateLoyaltyPoints(int visits) {
        if (visits <= 1) { // Base Case
            return 10;
        }
        return (visits * 5) + calculateLoyaltyPoints(visits - 1); // Recursive Case
    }

    // Module 3: Method Overloading (Average computation)
    public static double computeAverageHours(double[] hours) {
        double sum = 0.0;
        for (double h : hours) { // 1D Array Traversal
            sum += h;
        }
        return hours.length == 0 ? 0.0 : sum / hours.length;
    }

    public static double computeAverageHours(int[] hours) {
        int sum = 0;
        for (int h : hours) {
            sum += h;
        }
        // Module 1: Explicit Typecasting
        return hours.length == 0 ? 0.0 : (double) sum / hours.length;
    }

    // Module 3: 2D Array Matrix Arithmetic (Matrix Addition for shift grids)
    public static int[][] addShiftGrids(int[][] shift1, int[][] shift2) {
        int rows = shift1.length;
        int cols = shift1[0].length;
        int[][] totalGrid = new int[rows][cols];

        // Module 2: Nested loops
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                totalGrid[i][j] = shift1[i][j] + shift2[i][j];
            }
        }
        return totalGrid;
    }
}

// ============================================================================
// MAIN DRIVER CLASS
// ============================================================================
public class ParkingLotManager {

    public static void main(String[] args) {
        // Module 1: Scanner Initialization for Reading Console Input
        Scanner sc = new Scanner(System.in);

        System.out.println("=================================================================");
        System.out.println("   MULTI-LEVEL PARKING LOT MANAGEMENT SYSTEM (INTERACTIVE CONSOLE) ");
        System.out.println("=================================================================\n");

        // ---------------------------------------------------------------------
        // MODULE 1 & 2: SCANNER INPUT, CONTROL FLOW (DO-WHILE & SWITCH-CASE)
        // ---------------------------------------------------------------------
        char floorChoice = ' ';
        boolean validChoice = false;

        // Module 2: do-while loop to validate interactive Scanner input
        do {
            System.out.print("Enter Parking Floor Choice (A: Ground/EV, B: First Floor, C: Second Floor): ");
            String inputFloor = sc.nextLine().trim().toUpperCase();

            // Module 1: Operators & String verification
            if (!inputFloor.isEmpty() && (inputFloor.charAt(0) == 'A' || inputFloor.charAt(0) == 'B' || inputFloor.charAt(0) == 'C')) {
                floorChoice = inputFloor.charAt(0);
                validChoice = true;
            } else {
                System.out.println("❌ Invalid floor choice! Please enter 'A', 'B', or 'C'.\n");
            }
        } while (!validChoice);

        // Module 2: Selection Structure (switch-case)
        String floorName;
        switch (floorChoice) {
            case 'A':
                floorName = "Ground Floor (EV Charging & Disabled Parking)";
                break;
            case 'B':
                floorName = "First Floor (Compact & Medium Cars)";
                break;
            case 'C':
                floorName = "Second Floor (SUVs & Overflow Parking)";
                break;
            default:
                floorName = "General Parking Lot";
                break;
        }
        System.out.println("Selected Floor: " + floorName + "\n");

        // Module 1: Primitives, Bitwise Operators, Expressions
        final double GST_RATE = 0.18; // Constant
        int activeBit = 1; // 0001
        int vipBit = 2;    // 0010
        int statusFlag = activeBit | vipBit; // Bitwise OR (3)

        boolean isVip = (statusFlag & vipBit) == vipBit; // Bitwise AND check
        double baseRate = 50.0 + 20.0 * 2.0 - (isVip ? 10.0 : 0.0);
        double totalEstimate = baseRate + (baseRate * GST_RATE);

        // Formatted Output
        System.out.printf("[Module 1] Base Rate: ₹%.2f | Estimated total with GST: ₹%.2f | Is VIP: %b\n\n", 
                          baseRate, totalEstimate, isVip);

        // Module 2: Iteration loop with break and continue
        System.out.print("[Module 2] Slot Allocation Scan: ");
        for (int slot = 1; slot <= 5; slot++) {
            if (slot == 3) {
                continue; // Skip reserved slot 3
            }
            if (slot == 5) {
                break; // Stop scanning after slot 4
            }
            System.out.print("Slot-" + slot + " ");
        }
        System.out.println("\n");

        // ---------------------------------------------------------------------
        // MODULE 3: METHODS, RECURSION & MATRIX MATH
        // ---------------------------------------------------------------------
        int rewardPoints = ParkingUtils.calculateLoyaltyPoints(4);
        System.out.println("[Module 3] Recursive Loyalty Points (4 Visits): " + rewardPoints);

        // 2D Array Matrix Arithmetic
        int[][] morningShift = {{1, 0, 1}, {0, 1, 0}};
        int[][] eveningShift = {{1, 1, 0}, {0, 1, 1}};
        int[][] totalOccupancy = ParkingUtils.addShiftGrids(morningShift, eveningShift);
        System.out.println("[Module 3] Combined Occupancy at Floor 1, Slot 1: " + totalOccupancy[0][0] + "\n");

        // ---------------------------------------------------------------------
        // MODULE 4 & 6: INTERACTIVE ENTRY VIA SCANNER, OOP & EXCEPTION HANDLING
        // ---------------------------------------------------------------------
        List<Vehicle> parkingList = new ArrayList<>();
        Set<String> ownerSet = new HashSet<>();

        System.out.println("-----------------------------------------------------------------");
        System.out.println("                  VEHICLE REGISTRATION PORTAL                    ");
        System.out.println("-----------------------------------------------------------------");

        System.out.print("How many vehicles do you want to register? (e.g., 2): ");
        int count = 2;
        if (sc.hasNextInt()) {
            count = sc.nextInt();
        }
        sc.nextLine(); // Clear newline buffer

        // Module 2 Loop for vehicle entry
        for (int i = 1; i <= count; i++) {
            System.out.println("\n--- Entering Details for Vehicle #" + i + " ---");
            System.out.print("Select Vehicle Type (1: Standard Car, 2: Electric Car): ");
            int type = 1;
            if (sc.hasNextInt()) {
                type = sc.nextInt();
            }
            sc.nextLine(); // Clear buffer

            System.out.print("Enter License Plate Number (e.g., TS-09-EA-1234): ");
            String plate = sc.nextLine();

            System.out.print("Enter Owner Name: ");
            String owner = sc.nextLine();

            System.out.print("Enter Hours Parked: ");
            double hours = 1.0;
            if (sc.hasNextDouble()) {
                hours = sc.nextDouble();
            }
            sc.nextLine(); // Clear buffer

            // Module 4: Exception handling block
            try {
                if (type == 2) {
                    System.out.print("Enter Charging Fee (₹): ");
                    double cFee = 0.0;
                    if (sc.hasNextDouble()) {
                        cFee = sc.nextDouble();
                    }
                    sc.nextLine(); // Clear buffer

                    ElectricCar ev = new ElectricCar(plate, owner, hours, activeBit, cFee);
                    parkingList.add(ev);
                    ownerSet.add(ev.getOwnerName());
                } else {
                    Car car = new Car(plate, owner, hours, statusFlag);
                    parkingList.add(car);
                    ownerSet.add(car.getOwnerName());
                }
                System.out.println("✔ Vehicle registered successfully!");
            } catch (InvalidSlotException | IllegalArgumentException e) {
                System.err.println("❌ Registration Failed: " + e.getMessage());
            }
        }

        // Fallback default vehicle if list is empty
        if (parkingList.isEmpty()) {
            try {
                parkingList.add(new Car("TS-09-DEFAULT", "Default User", 2.5, statusFlag));
            } catch (InvalidSlotException ignored) {}
        }

        System.out.printf("\n[Module 4] Total Registered -> Standard Cars: %d | EVs: %d\n\n",
                Car.getTotalCars(), ElectricCar.getTotalEVs());

        // ---------------------------------------------------------------------
        // MODULE 5: STRINGS, ALGORITHMS & FILE I/O
        // ---------------------------------------------------------------------
        String p1 = "TS-09-EA-1234";
        String p2 = new String("TS-09-EA-1234");
        System.out.println("[Module 5] String '==' (Reference Check): " + (p1 == p2));
        System.out.println("[Module 5] String 'equals()' (Value Check): " + p1.equals(p2));

        // Tokenization with split(), trim(), join()
        String rawData = "  MH-12-PQ-8888 ,  Vikram Singh , 4.0  ";
        String[] tokens = rawData.split(",");
        List<String> cleanTokens = new ArrayList<>();
        for (String t : tokens) {
            cleanTokens.add(t.trim().toUpperCase());
        }
        System.out.println("[Module 5] Parsed/Cleaned Record: " + String.join(" | ", cleanTokens));

        // Collections.sort using Comparable interface (Sort by hours descending)
        Collections.sort(parkingList);
        System.out.println("[Module 5] Longest Parked Vehicle: " + parkingList.get(0).getPlateNo());

        // Modern File I/O (java.nio.file Path, Files, try-with-resources)
        Path logFile = Paths.get("parking_data.txt");

        // Writing to file
        try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Vehicle v : parkingList) {
                writer.write(v.toString());
                writer.newLine();
            }
            System.out.println("[Module 5] Successfully saved records to: " + logFile.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("File Writing Error: " + e.getMessage());
        }

        // Reading line-by-line from file
        System.out.println("[Module 5] Reading saved data from file:");
        try (BufferedReader reader = Files.newBufferedReader(logFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("   File Record -> " + line);
            }
        } catch (IOException e) {
            System.err.println("File Reading Error: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // MODULE 6: FUNCTIONAL PROGRAMMING & STREAMS PIPELINE
        // ---------------------------------------------------------------------
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("           MODULE 6: STREAMS & DECLARATIVE REPORT                ");
        System.out.println("-----------------------------------------------------------------");

        // Functional Interfaces
        Predicate<Vehicle> longStayFilter = v -> v.getHoursParked() >= 2.0; // Lambda
        Function<Vehicle, String> getPlate = Vehicle::getPlateNo;          // Method Reference
        Consumer<String> displayConsumer = plate -> System.out.println(" Long Stay Vehicle -> Plate: " + plate);
        Supplier<String> headerSupplier = () -> "=== OVERTIME VEHICLE REPORT ===";

        System.out.println(headerSupplier.get());

        // Stream Pipeline 1: filter, sorted, map, forEach
        parkingList.stream()
                   .filter(longStayFilter)
                   .sorted(Comparator.comparing(Vehicle::getPlateNo))
                   .map(getPlate)
                   .forEach(displayConsumer);

        // Stream Pipeline 2: Collectors.groupingBy and counting
        Map<String, Long> vehicleTypeCounts = parkingList.stream()
                .collect(Collectors.groupingBy(v -> v instanceof ElectricCar ? "Electric Car" : "Standard Car", Collectors.counting()));

        System.out.println("\n[Module 6] Vehicle Breakdown Count:");
        vehicleTypeCounts.forEach((type, vCount) -> System.out.printf(" - %s: %d\n", type, vCount));

        // Stream Pipeline 3: Reading File Lines as Stream and Reducing
        try (Stream<String> lines = Files.lines(logFile)) {
            double averageHours = lines
                    .map(line -> line.split(","))
                    .mapToDouble(arr -> Double.parseDouble(arr[2]))
                    .average()
                    .orElse(0.0);

            System.out.printf("\n[Module 6] Average Hours Parked (Stream reduction from file): %.2f hrs\n", averageHours);
        } catch (IOException e) {
            System.err.println("Stream Error: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // SCANNER INPUT FOR DYNAMIC SURCHARGE MULTIPLIER
        // ---------------------------------------------------------------------
        System.out.println("\n=================================================================");
        System.out.print("Enter dynamic surcharge multiplier for peak hours (e.g., 1.5): ");
        if (sc.hasNextDouble()) {
            double penalty = sc.nextDouble();
            double adjustedFee = parkingList.get(0).calculateFee() * penalty;
            System.out.printf("Adjusted Penalty Fee for Longest Parked Vehicle (%s): ₹%.2f\n",
                    parkingList.get(0).getPlateNo(), adjustedFee);
        } else {
            System.out.println("Invalid multiplier entered. Skipping calculation.");
        }

        sc.close(); // Close Scanner

        System.out.println("=================================================================");
        System.out.println("     PROGRAM EXECUTION COMPLETED SUCCESSFULLY                    ");
        System.out.println("=================================================================");
    }
}