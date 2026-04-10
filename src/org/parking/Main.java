package org.parking;

import org.parking.ParkingSlot;
import org.parking.ParkingSystem;
import org.parking.VehicleType;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ─────────────────────────────────────────
        // 1. CREATE THE PARKING SYSTEM
        // ─────────────────────────────────────────
        ParkingSystem system = new ParkingSystem();

        // ─────────────────────────────────────────
        // 2. ADD PARKING SLOTS
        // ─────────────────────────────────────────
        system.addSlot(new ParkingSlot(1, 1, VehicleType.CAR));
        system.addSlot(new ParkingSlot(2, 1, VehicleType.CAR));
        system.addSlot(new ParkingSlot(3, 1, VehicleType.BIKE));
        system.addSlot(new ParkingSlot(4, 2, VehicleType.BIKE));
        system.addSlot(new ParkingSlot(5, 2, VehicleType.TRUCK));

        // ─────────────────────────────────────────
        // 3. DISPLAY ALL SLOTS (before any vehicle)
        // ─────────────────────────────────────────
        system.displayAllSlots();

        // ─────────────────────────────────────────
        // 4. VEHICLES ARRIVE — ADD TO QUEUE
        // ─────────────────────────────────────────
        Vehicle car1   = new Vehicle("CAR001",   VehicleType.CAR);
        Vehicle car2   = new Vehicle("CAR002",   VehicleType.CAR);
        Vehicle bike1  = new Vehicle("BIKE001",  VehicleType.BIKE);
        Vehicle truck1 = new Vehicle("TRUCK001", VehicleType.TRUCK);

        system.addVehicleToQueue(car1);
        system.addVehicleToQueue(car2);
        system.addVehicleToQueue(bike1);
        system.addVehicleToQueue(truck1);

        // ─────────────────────────────────────────
        // 5. ALLOCATE SLOTS
        // ─────────────────────────────────────────
        System.out.println("\n─── Allocating Slots ───");
        system.allocateSlot();   // car1  → slot 1
        system.allocateSlot();   // car2  → slot 2
        system.allocateSlot();   // bike1 → slot 3
        system.allocateSlot();   // truck1→ slot 5

        // ─────────────────────────────────────────
        // 6. DISPLAY ALL SLOTS (after allocation)
        // ─────────────────────────────────────────
        system.displayAllSlots();

        // ─────────────────────────────────────────
        // 7. SIMULATE TIME PASSING (2 seconds)
        // ─────────────────────────────────────────
        System.out.println("Simulating time passing...");
        Thread.sleep(2000);

        // ─────────────────────────────────────────
        // 8. RELEASE SLOTS — VEHICLES LEAVE
        // ─────────────────────────────────────────
        System.out.println("\n─── Vehicles Leaving ───");
        system.releaseSlot(1, car1);
        system.releaseSlot(3, bike1);

        // ─────────────────────────────────────────
        // 9. DISPLAY LAST ACTIVITY
        // ─────────────────────────────────────────
        System.out.println("\n─── Last Activity ───");
        system.displayLastActivity();

        // ─────────────────────────────────────────
        // 10. DISPLAY ALL SLOTS (final state)
        // ─────────────────────────────────────────
        system.displayAllSlots();

        // ─────────────────────────────────────────
        // 11. TEST FULL CAPACITY
        // ─────────────────────────────────────────
        System.out.println("─── Testing Full Capacity ───");
        Vehicle car3 = new Vehicle("CAR003", VehicleType.CAR);
        Vehicle car4 = new Vehicle("CAR004", VehicleType.CAR);
        system.addVehicleToQueue(car3);
        system.addVehicleToQueue(car4);
        system.allocateSlot();   // car3 → slot 1 (freed earlier)
        system.allocateSlot();   // car4 → no slot available!
    }
}