package org.parking;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

public class ParkingSystem {

    private Hashtable<Integer, ParkingSlot> slots;
    private Stack<String> activityLog;
    private EntryQueue entryQueue;

    public ParkingSystem() {
        slots = new Hashtable<>();
        activityLog = new Stack<>();
        entryQueue = new EntryQueue();
    }

    // ─── ADD SLOT ───
    public void addSlot(ParkingSlot slot) {
        slots.put(slot.getSlotId(), slot);
        System.out.println("✅ Slot " + slot.getSlotId()
                + " added for " + slot.getVehicleType()
                + " on level " + slot.getLevel());
    }

    // ─── ADD VEHICLE TO QUEUE ───
    public void addVehicleToQueue(Vehicle vehicle) {
        entryQueue.addVehicleToQueue(vehicle);
    }

    // ─── ALLOCATE SLOT ───
    public void allocateSlot() {
        Vehicle vehicle = entryQueue.getNextVehicle();

        if (vehicle == null) {
            System.out.println("❌ No vehicles in queue.");
            return;
        }

        Enumeration<Integer> keys = slots.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            ParkingSlot slot = slots.get(key);

            if (!slot.isOccupied() &&
                    slot.getVehicleType() == vehicle.getVehicleType()) {

                slot.setOccupied(true);
                slot.setParkedVehicle(vehicle);      // ← store vehicle in slot
                String log = "Vehicle " + vehicle.getVehicleNo()
                        + " parked at slot " + slot.getSlotId();
                activityLog.push(log);
                System.out.println("✅ " + log);
                return;
            }
        }
        System.out.println("❌ No available slot for "
                + vehicle.getVehicleType());
    }

    // ─── RELEASE SLOT ───
    public void releaseSlot(int slotId) {
        ParkingSlot slot = slots.get(slotId);

        if (slot == null) {
            System.out.println("❌ Slot not found.");
            return;
        }

        if (!slot.isOccupied()) {
            System.out.println("❌ Slot " + slotId + " is already empty.");
            return;
        }

        Vehicle vehicle = slot.getParkedVehicle();   // ← get vehicle from slot
        slot.setOccupied(false);
        slot.setParkedVehicle(null);                 // ← clear vehicle from slot

        String log = "Vehicle " + vehicle.getVehicleNo()
                + " left slot " + slotId;
        activityLog.push(log);
        System.out.println("✅ " + log);

        calculateFee(vehicle);
    }

    // ─── CALCULATE FEE ───
    public void calculateFee(Vehicle vehicle) {
        Calendar exitTime = Calendar.getInstance();
        long diff = exitTime.getTimeInMillis()
                - vehicle.getEntryTime().getTimeInMillis();

        long hours = diff / (1000 * 60 * 60);
        if (hours < 1) hours = 1;                   // minimum 1 hour

        int feePerHour;
        switch (vehicle.getVehicleType()) {
            case BIKE:  feePerHour = 10; break;
            case CAR:   feePerHour = 20; break;
            default:    feePerHour = 30; break;     // TRUCK, BUS, VAN
        }

        long totalFee = hours * feePerHour;
        System.out.println("💰 Fee for " + vehicle.getVehicleNo()
                + ": ₹" + totalFee
                + " (" + hours + " hour(s) x ₹" + feePerHour + ")");
    }

    // ─── DISPLAY LAST ACTIVITY ───
    public void displayLastActivity() {
        if (activityLog.isEmpty()) {
            System.out.println("❌ No activity yet.");
            return;
        }
        System.out.println("🕐 Last activity: " + activityLog.peek());
    }

    // ─── DISPLAY ALL SLOTS ───
    public void displayAllSlots() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║           ALL PARKING SLOTS              ║");
        System.out.println("╠══════════════════════════════════════════╣");
        Enumeration<Integer> keys = slots.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            ParkingSlot slot = slots.get(key);
            String status = slot.isOccupied() ? "OCCUPIED" : "FREE";
            System.out.println("║ Slot " + slot.getSlotId()
                    + " | Level " + slot.getLevel()
                    + " | " + slot.getVehicleType()
                    + " | " + status + "         ║");
        }
        System.out.println("║ Total Slots: " + ParkingSlot.getTotalSlots()
                + "                            ║");
        System.out.println("╚══════════════════════════════════════════╝\n");
    }

    // ─── DISPLAY OCCUPIED SLOTS ← NEW ───
    public void displayOccupiedSlots() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║         OCCUPIED SLOTS DETAILS           ║");
        System.out.println("╠══════════════════════════════════════════╣");

        boolean anyOccupied = false;
        Enumeration<Integer> keys = slots.keys();

        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            ParkingSlot slot = slots.get(key);

            if (slot.isOccupied() && slot.getParkedVehicle() != null) {
                anyOccupied = true;
                Vehicle v = slot.getParkedVehicle();

                // calculate time spent
                Calendar now = Calendar.getInstance();
                long diff = now.getTimeInMillis()
                        - v.getEntryTime().getTimeInMillis();
                long minutes = diff / (1000 * 60);

                System.out.println("║ Slot     : " + slot.getSlotId() + "                             ║");
                System.out.println("║ Level    : " + slot.getLevel() + "                             ║");
                System.out.println("║ Type     : " + v.getVehicleType() + "                           ║");
                System.out.println("║ Vehicle  : " + v.getVehicleNo() + "                        ║");
                System.out.println("║ Time     : " + minutes + " minute(s) ago               ║");
                System.out.println("╠══════════════════════════════════════════╣");
            }
        }

        if (!anyOccupied) {
            System.out.println("║        No occupied slots right now       ║");
            System.out.println("╚══════════════════════════════════════════╝");
        } else {
            System.out.println("╚══════════════════════════════════════════╝\n");
        }
    }
}