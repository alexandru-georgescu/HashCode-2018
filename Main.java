package selfDrivingCars;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
        Scanner scanf = new Scanner(new BufferedReader(new FileReader(args[0] + ".txt")));
        PrintWriter printf = new PrintWriter(new BufferedWriter(new FileWriter(args[0] + "out.txt")));
		
		final int rows = scanf.nextInt(); //unused
		final int columns = scanf.nextInt(); //unused
		final int numOfVehicles = scanf.nextInt();
		final int numOfRides = scanf.nextInt();
		final int bonus = scanf.nextInt(); //unused
		final int steps = scanf.nextInt(); //unused
		scanf.nextLine();
		
		ArrayList<Ride> rides = new ArrayList<>();
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		
		for (int i = 0; i < numOfRides; i++) { //read all rides
			rides.add(new Ride(i, new Point(scanf.nextInt(), scanf.nextInt()), 
					new Point(scanf.nextInt(), scanf.nextInt()), scanf.nextInt(),
					scanf.nextInt()));
			scanf.nextLine();
		}
	
		for (int i = 0; i < numOfVehicles; i++) { //read all vehicles
			vehicles.add(new Vehicle(i));
		}
		
		Point initialPosition = new Point(0, 0);
		
		Comparator<Ride> initialComparator = new Comparator<Ride>() {

			@Override
			public int compare(Ride one, Ride two) {

				  int first;
	              int second;
	               
	              if (one.earlyTime - Point.getDistance(initialPosition, one.startPosition) >= 0)
	                   first = Point.getDistance(initialPosition, one.finishPosition)
	                   + (one.earlyTime - Point.getDistance(initialPosition, one.startPosition));
	               else 
	                   first = 0;
	             
	               if (two.earlyTime - Point.getDistance(initialPosition, two.startPosition) >= 0)
	                   second = Point.getDistance(initialPosition, two.finishPosition)
	                   + (two.earlyTime - Point.getDistance(initialPosition, two.startPosition));
	               else 
	                   second = 0;
	               
	               if (first >= second) return 1;
	               else return -1;
	               
			}
		};
		
		Collections.sort(rides, initialComparator);
		Iterator<Ride> ridesIterator = rides.iterator();
		Iterator<Vehicle> vehiclesIterator = vehicles.iterator();

	       while (vehiclesIterator.hasNext()) {
	    	   
	           Vehicle currentVehicle = vehiclesIterator.next();
	           
	           while (ridesIterator.hasNext()) {
	        	   
	               Ride currentRide = ridesIterator.next();
	               int timeLimit = currentVehicle.availableTime 
	            		   + Point.getDistance(initialPosition, currentRide.startPosition)
	               + Point.getDistance(initialPosition, currentRide.finishPosition);
	               
	               if (timeLimit <= currentRide.latestTime) {
	               currentVehicle.addRide(currentRide);
	               
	               if (currentRide.earlyTime > Point.getDistance(initialPosition, currentRide.startPosition)) {
	            	   currentVehicle.availableTime =
	            			    Point.getDistance(currentRide.startPosition, currentRide.finishPosition)
	            			   + Point.getDistance(initialPosition, currentRide.startPosition); 
	              } else {
	            	   currentVehicle.availableTime = 
	            			    currentRide.earlyTime 
	            			    + Point.getDistance(currentRide.startPosition, currentRide.finishPosition);
	               }
	               currentVehicle.vehiclePosition = currentRide.finishPosition;
	               ridesIterator.remove();
	               break;
	               }
	           }
	           
	           if (!ridesIterator.hasNext()) {
	               break;
	           }
	       }
	       
		while (ridesIterator.hasNext()) {
			
			Vehicle container = null;
			Ride currentRide = ridesIterator.next();
			int timeLimit = (int) Math.pow(2, 32) - 1;
			
			for (int i = 0; i < numOfVehicles; i++) {
				Vehicle currentVehicle = vehicles.get(i);
				int currentTimeLimit = currentVehicle.availableTime
						+ Point.getDistance(currentVehicle.vehiclePosition, currentRide.startPosition)
						+ Point.getDistance(currentRide.startPosition, currentRide.finishPosition);
				if (currentTimeLimit <= timeLimit && currentTimeLimit <= currentRide.latestTime) {
					container = currentVehicle;
					timeLimit = currentTimeLimit;
				}
			}
			
			if (container != null) {
				container.addRide(currentRide);
				
				if (currentRide.earlyTime > container.availableTime
						+ Point.getDistance(container.vehiclePosition, currentRide.startPosition)) {
					container.availableTime = 2 * currentRide.earlyTime - container.availableTime
							+ Point.getDistance(currentRide.startPosition, currentRide.finishPosition)
							- Point.getDistance(container.vehiclePosition, currentRide.startPosition);
				} else {
					container.availableTime = currentRide.earlyTime
							+ Point.getDistance(currentRide.startPosition, currentRide.finishPosition);
				}
				container.vehiclePosition = currentRide.finishPosition;
			}
			else {
				ridesIterator.remove();
			}
		}
		
		for (int i = 0; i < numOfVehicles; i++) {
			printf.print(vehicles.get(i).numOfRidesDone + " ");
			for (int j = 0; j < vehicles.get(i).numOfRidesDone; j++) {
				printf.print(vehicles.get(i).ridesDone.get(j).rideNumber + " ");
			}
			printf.println();
		}
		printf.close();
		scanf.close();
	}
}
