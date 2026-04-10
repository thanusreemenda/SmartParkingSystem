package org.parking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ParkingSystem system = new ParkingSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     SMART PARKING SYSTEM         ║");
        System.out.println("╚══════════════════════════════════╝");

        // Setup slots
        System.out.print("\nHow many parking slots do you want to add? ");
        int numSlots = scanner.nextInt();

        for (int i = 1; i <= numSlots; i++) {
            System.out.println("\n--- Slot " + i + " ---");
            System.out.print("Enter level (1/2/3...): ");
            int level = scanner.nextInt();

            // ← VALIDATED vehicle type input
            int typeChoice = 0;
            while (typeChoice < 1 || typeChoice > 3) {
                System.out.print("Enter vehicle type (1=CAR, 2=BIKE, 3=TRUCK): ");
                typeChoice = scanner.nextInt();
                if (typeChoice < 1 || typeChoice > 3) {
                    System.out.println("❌ Invalid! Please enter 1, 2 or 3 only.");
                }
            }

            VehicleType type;
            switch (typeChoice) {
                case 2: type = VehicleType.BIKE; break;
                case 3: type = VehicleType.TRUCK; break;
                default: type = VehicleType.CAR; break;
            }
            system.addSlot(new ParkingSlot(i, level, type));
        }

        // Main menu
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║            MAIN MENU             ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Add Vehicle to Queue         ║");
            System.out.println("║  2. Allocate Slot                ║");
            System.out.println("║  3. Release Slot                 ║");
            System.out.println("║  4. Display All Slots            ║");
            System.out.println("║  5. Display Occupied Slots       ║");
            System.out.println("║  6. Display Last Activity        ║");
            System.out.println("║  7. Exit                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                // ─── ADD VEHICLE ───
                case 1: {
                    System.out.print("Enter vehicle number: ");
                    String vehicleNo = scanner.next();

                    // ← VALIDATED vehicle type input
                    int typeChoice = 0;
                    while (typeChoice < 1 || typeChoice > 3) {
                        System.out.print("Enter vehicle type (1=CAR, 2=BIKE, 3=TRUCK): ");
                        typeChoice = scanner.nextInt();
                        if (typeChoice < 1 || typeChoice > 3) {
                            System.out.println("❌ Invalid! Please enter 1, 2 or 3 only.");
                        }
                    }

                    VehicleType type;
                    switch (typeChoice) {
                        case 2: type = VehicleType.BIKE; break;
                        case 3: type = VehicleType.TRUCK; break;
                        default: type = VehicleType.CAR; break;
                    }

                    system.addVehicleToQueue(new Vehicle(vehicleNo, type));
                    break;
                }

                // ─── ALLOCATE SLOT ───
                case 2: {
                    system.allocateSlot();
                    break;
                }

                // ─── RELEASE SLOT ───
                case 3: {
                    System.out.print("Enter slot ID to release: ");
                    int slotId = scanner.nextInt();
                    system.releaseSlot(slotId);  // ← simplified, no need to enter vehicle
                    break;
                }

                // ─── DISPLAY ALL SLOTS ───
                case 4: {
                    system.displayAllSlots();
                    break;
                }

                // ─── DISPLAY OCCUPIED SLOTS ← NEW ───
                case 5: {
                    system.displayOccupiedSlots();
                    break;
                }

                // ─── DISPLAY LAST ACTIVITY ───
                case 6: {
                    system.displayLastActivity();
                    break;
                }

                // ─── EXIT ───
                case 7: {
                    System.out.println("\n✅ Thank you for using Smart Parking System!");
                    System.out.println("   Goodbye! 👋");
                    break;
                }

                default: {
                    System.out.println("❌ Invalid choice! Enter 1-7.");
                }
            }

        } while (choice != 7);

        scanner.close();
    }
}1