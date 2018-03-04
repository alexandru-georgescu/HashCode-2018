package selfDrivingCars;

public class Point {

	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static int getDistance(Point first, Point second) {
		return Math.abs(first.x - second.x) + Math.abs(first.y - second.y);
	}
}
