package selfDrivingCars;

import java.util.ArrayList;

public class Vehicle {

	public int vehicleNumber;
	public Point vehiclePosition;
	public int numOfRidesDone;
	public int availableTime;
	public ArrayList<Ride> ridesDone = new ArrayList<>();
	
	public Vehicle(int vehicleNumber) {
		this.vehiclePosition = new Point(0,0);
		this.vehicleNumber = vehicleNumber;
	}
	
	public void addRide(Ride ride) {
		this.numOfRidesDone++;
		this.ridesDone.add(ride);
	}
	
}
