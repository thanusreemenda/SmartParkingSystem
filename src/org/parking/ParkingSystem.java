package org.parking;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

public class ParkingSystem {

    private Hashtable<Integer, ParkingSlot> slots;   // all parking slots
    private Stack<String> activityLog;               // log of actions
    private EntryQueue entryQueue;                   // waiting vehicles

    public ParkingSystem() {
        slots = new Hashtable<>();
        activityLog = new Stack<>();
        entryQueue = new EntryQueue();
    }

    // ─────────────────────────────────────────
    // 1. ADD A SLOT
    // ─────────────────────────────────────────
    public void addSlot(ParkingSlot slot) {
        slots.put(slot.getSlotId(), slot);
        System.out.println("Slot " + slot.getSlotId()
                + " added for " + slot.getVehicleType());
    }

    // ─────────────────────────────────────────
    // 2. ADD VEHICLE TO QUEUE
    // ─────────────────────────────────────────
    public void addVehicleToQueue(Vehicle vehicle) {
        entryQueue.addVehicleToQueue(vehicle);
    }

    // ─────────────────────────────────────────
    // 3. ALLOCATE SLOT
    // ─────────────────────────────────────────
    public void allocateSlot() {

        // get next vehicle from queue
        Vehicle vehicle = entryQueue.getNextVehicle();

        if (vehicle == null) {
            System.out.println("No vehicles in queue.");
            return;
        }

        // find a matching free slot
        Enumeration<Integer> keys = slots.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            ParkingSlot slot = slots.get(key);

            if (!slot.isOccupied() &&
                    slot.getVehicleType() == vehicle.getVehicleType()) {

                slot.setOccupied(true);
                String log = "Vehicle " + vehicle.getVehicleNo()
                        + " parked at slot " + slot.getSlotId();
                activityLog.push(log);
                System.out.println(log);
                return;
            }
        }

        System.out.println("No available slot for "
                + vehicle.getVehicleType());
    }

    // ─────────────────────────────────────────
    // 4. RELEASE SLOT
    // ─────────────────────────────────────────
    public void releaseSlot(int slotId, Vehicle vehicle) {
        ParkingSlot slot = slots.get(slotId);

        if (slot == null) {
            System.out.println("Slot not found.");
            return;
        }

        slot.setOccupied(false);
        String log = "Vehicle " + vehicle.getVehicleNo()
                + " left slot " + slotId;
        activityLog.push(log);
        System.out.println(log);

        // calculate fee after releasing
        calculateFee(vehicle);
    }

    // ─────────────────────────────────────────
    // 5. CALCULATE FEE
    // ─────────────────────────────────────────
    public void calculateFee(Vehicle vehicle) {
        Calendar exitTime = Calendar.getInstance();
        long diff = exitTime.getTimeInMillis()
                - vehicle.getEntryTime().getTimeInMillis();

        long hours = diff / (1000 * 60 * 60);
        if (hours < 1) hours = 1;                    // minimum 1 hour charge

        int feePerHour;

        switch (vehicle.getVehicleType()) {
            case BIKE:
                feePerHour = 10;
                break;
            case CAR:
                feePerHour = 20;
                break;
            default:                                  // TRUCK, BUS, VAN
                feePerHour = 30;
                break;
        }

        long totalFee = hours * feePerHour;
        System.out.println("Fee for vehicle " + vehicle.getVehicleNo()
                + ": ₹" + totalFee
                + " (" + hours + " hour(s) x ₹" + feePerHour + ")");
    }

    // ─────────────────────────────────────────
    // 6. DISPLAY LAST ACTIVITY
    // ─────────────────────────────────────────
    public void displayLastActivity() {
        if (activityLog.isEmpty()) {
            System.out.println("No activity yet.");
            return;
        }
        System.out.println("Last activity: " + activityLog.peek());
    }

    // ─────────────────────────────────────────
    // 7. DISPLAY ALL SLOTS
    // ─────────────────────────────────────────
    public void displayAllSlots() {
        System.out.println("\n─── Parking Slots Status ───");
        Enumeration<Integer> keys = slots.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            ParkingSlot slot = slots.get(key);
            String status = slot.isOccupied() ? "OCCUPIED" : "FREE";
            System.out.println("Slot " + slot.getSlotId()
                    + " | Level " + slot.getLevel()
                    + " | " + slot.getVehicleType()
                    + " | " + status);
        }
        System.out.println("Total Slots: " + ParkingSlot.getTotalSlots());
        System.out.println("────────────────────────────\n");
    }
}
