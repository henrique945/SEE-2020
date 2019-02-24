
package grafo;

public class Point implements Comparable<Point> {

	public int x;
	public int y;
	public int value;
	public Point prev;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point o) {
		if(this.value>o.value)
			return 1;
		if(this.value<o.value)
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", value=" + value + "]";
	}
	
}
