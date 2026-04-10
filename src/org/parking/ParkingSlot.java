package org.parking;

public class ParkingSlot {

    private int slotId;                          // unique slot number
    private int level;                           // which floor
    private VehicleType vehicleType;             // what type fits here
    private boolean isOccupied;                  // taken or free
    private static int totalSlots = 0;           // shared count of all slots

    public ParkingSlot(int slotId, int level, VehicleType vehicleType) {
        this.slotId = slotId;
        this.level = level;
        this.vehicleType = vehicleType;
        this.isOccupied = false;                 // starts as free
        totalSlots++;                            // increment shared count
    }

    // Getters
    public int getSlotId() {
        return slotId;
    }

    public int getLevel() {
        return level;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public static int getTotalSlots() {          // static getter
        return totalSlots;
    }

    // Setters
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}