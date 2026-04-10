package org.parking;                           // 1

import java.util.Calendar;                     // 2

public class Vehicle {                         // 3

    private String vehicleNo;                  // 4
    private VehicleType vehicleType;           // 5
    private Calendar entryTime;               // 6

    public Vehicle(String vehicleNo, VehicleType vehicleType) {   // 7
        this.vehicleNo = vehicleNo;            // 8
        this.vehicleType = vehicleType;        // 9
        this.entryTime = Calendar.getInstance();  // 10
    }

    public String getVehicleNo() {             // 11
        return vehicleNo;                      // 12
    }

    public VehicleType getVehicleType() {      // 13
        return vehicleType;                    // 14
    }

    public Calendar getEntryTime() {           // 15
        return entryTime;                      // 16
    }
}