package org.parking;

public class ParkingSlot {

    private int slotId;
    private int level;
    private VehicleType vehicleType;
    private boolean isOccupied;
    private static int totalSlots = 0;
    private Vehicle parkedVehicle;        // ← NEW: stores parked vehicle

    public ParkingSlot(int slotId, int level, VehicleType vehicleType) {
        this.slotId = slotId;
        this.level = level;
        this.vehicleType = vehicleType;
        this.isOccupied = false;
        this.parkedVehicle = null;        // ← starts empty
        totalSlots++;
    }

    public int getSlotId() { return slotId; }
    public int getLevel() { return level; }
    public VehicleType getVehicleType() { return vehicleType; }
    public boolean isOccupied() { return isOccupied; }
    public static int getTotalSlots() { return totalSlots; }
    public Vehicle getParkedVehicle() { return parkedVehicle; } // ← NEW

    public void setOccupied(boolean occupied) { isOccupied = occupied; }
    public void setParkedVehicle(Vehicle vehicle) {             // ← NEW
        this.parkedVehicle = vehicle;
    }
}