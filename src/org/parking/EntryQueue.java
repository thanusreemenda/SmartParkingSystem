package org.parking;

import java.util.LinkedList;
import java.util.Queue;

public class EntryQueue {

    private Queue<Vehicle> queue;              // the waiting line

    public EntryQueue() {
        queue = new LinkedList<>();            // initialize the queue
    }

    // Add vehicle to the back of the queue
    public void addVehicleToQueue(Vehicle vehicle) {
        queue.offer(vehicle);
        System.out.println("Vehicle " + vehicle.getVehicleNo()
                + " added to queue.");
    }

    // Remove and return vehicle from front of queue
    public Vehicle getNextVehicle() {
        return queue.poll();
    }

    // Check how many vehicles are waiting
    public int getQueueSize() {
        return queue.size();
    }
}