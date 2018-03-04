package selfDrivingCars;

public class Ride {

	public int rideNumber;
	public Point startPosition, finishPosition;
	public int earlyTime, latestTime;
	
	public Ride(int rideNumber, Point startPosition, Point finishPosition, int earlyTime
			, int latestTime) {
		this.rideNumber = rideNumber;
		this.startPosition = startPosition;
		this.finishPosition = finishPosition;
		this.earlyTime = earlyTime;
		this.latestTime = latestTime;
	}
}
