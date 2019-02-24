package graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

import helper.PositionOfOxygenFactory;
import model.Position;

public class PathFind {

	static int[][] matrix;
	private HashMap<String, ArrayList<Point>> pathRover;

	public PathFind(String path) throws IOException {
		matrix = Image.GetMatrix(path);
		pathRover = new HashMap<String, ArrayList<Point>>();
	}

	public ArrayList<Position> GetIn(Point from, Point to, String key) throws InterruptedException {
		ArrayList<Position> new_positions = new ArrayList<>();
		try {
			new_positions = Find(from, to, key);
		} catch (Exception e) {
		}
		return new_positions;
	}// Method that calls the smallest path between the two points

	public Point convertPoint(Position position) {

		int x = (int) (PositionOfOxygenFactory.getPosition("o2fac").x + position.getX()
				- PositionOfOxygenFactory.referenceRover.getX());

		int y = (int) (PositionOfOxygenFactory.getPosition("o2fac").y - position.getY()
				+ PositionOfOxygenFactory.referenceRover.getY());

		System.out.println(position.getX());
		System.out.println(PositionOfOxygenFactory.referenceRover.getX());

		return new Point(x, y);
	}// Method that converts position to point

	public Position convertPosition(Point point) {
		double x = PositionOfOxygenFactory.referenceRover.getX() - PositionOfOxygenFactory.getPosition("o2fac").x
				+ point.x;
		double y = PositionOfOxygenFactory.referenceRover.getY() + PositionOfOxygenFactory.getPosition("o2fac").y
				- point.y;
		double z = PositionOfOxygenFactory.referenceRover.getZ();
		return new Position(x, y, z);
	}// Method that converts point to position

	public void clearPath(String key) {
		System.out.println(key);
		ArrayList<Point> points = pathRover.get(key);
		if (points != null) {
			Point p = points.get(points.size() - 1);
			pathRover.remove(key);
			for (Point point : points) {
				matrix[point.x][point.y] *= (-1);
			}
			System.out.println(p);
			matrix[p.x][p.y] = -1;
			Image.PrintMatrix(matrix);
		} else {
			System.out.println("erro");
		}
	}

	private ArrayList<Position> Find(Point from, Point to, String key) throws InterruptedException {

		PriorityQueue<Point> q = new PriorityQueue<Point>();
		int[][] clone = copyMatriz(matrix);
		System.out.println(from);
		System.out.println(to);
		int value = matrix[to.x][to.y];
		from.value = 0;
		q.add(from);

		while (!q.isEmpty()) {
			Point p = q.poll();
			clone[p.x][p.y] = -1;

			if (p.x < clone.length - 1 && (clone[p.x + 1][p.y] > 0 || clone[p.x + 1][p.y] == value)) {
				Point child = new Point(p.x + 1, p.y);
				child.prev = p;
				child.value = p.value + clone[p.x + 1][p.y];
				q.add(child);
				if (compare(child, to))
					return getPath(key, p);

				clone[child.x][child.y] = -1;
			}
			if (p.x > 0 && (clone[p.x - 1][p.y] > 0 || clone[p.x - 1][p.y] == value)) {
				Point child = new Point(p.x - 1, p.y);
				child.prev = p;
				child.value = p.value + clone[p.x - 1][p.y];
				q.add(child);
				if (compare(child, to))
					return getPath(key, p);

				clone[child.x][child.y] = -1;
			}
			if (p.y < clone[0].length - 1 && (clone[p.x][p.y + 1] > 0 || clone[p.x][p.y + 1] == value)) {
				Point child = new Point(p.x, p.y + 1);
				child.prev = p;
				child.value = p.value + clone[p.x][p.y + 1];
				q.add(child);
				if (compare(child, to))
					return getPath(key, p);

				clone[child.x][child.y] = -1;
			}
			if (p.y > 0 && (clone[p.x][p.y - 1] > 0 || clone[p.x][p.y - 1] == value)) {
				Point child = new Point(p.x, p.y - 1);
				child.prev = p;
				child.value = p.value + clone[p.x][p.y - 1];
				q.add(child);
				if (compare(child, to))
					return getPath(key, p);

				clone[child.x][child.y] = -1;
			}

		}
		System.out.println("--------------------------- not find path ----------------");
//		Image.PrintMatrix(clone);
		return null;
	}// Breadth-First Search

	private ArrayList<Position> getPath(String key, Point p) {
		ArrayList<Position> path = new ArrayList<Position>();
		ArrayList<Point> points = new ArrayList<Point>();

		while (p.prev != null) {
			p = p.prev;
			if (matrix[p.x][p.y] > 0) {
				path.add(convertPosition(p));
				points.add(p);
				matrix[p.x][p.y] *= (-1);
			}
		}
		if(pathRover.containsKey(key))
			clearPath(key);
		pathRover.put(key, points);
		Collections.reverse(path);
		if (path != null)
			Image.PrintMatrix(matrix);
		System.out.println(path);
		return path;
	}// return position of list

	private boolean compare(Point a, Point b) {
		if (a.x == b.x && a.y == b.y) {
			return true;
		}
		return false;
	}// Method two point comparison

	private int[][] copyMatriz(int[][] a) {
		int[][] b = new int[a.length][a.length];
		for (int x = 0; x < a.length; x++)
			for (int y = 0; y < a.length; y++)
				b[x][y] = a[x][y];
		return b;
	}// Method responsible for copying one matrix in the other

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pathRover == null) ? 0 : pathRover.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathFind other = (PathFind) obj;
		if (pathRover == null) {
			if (other.pathRover != null)
				return false;
		} else if (!pathRover.equals(other.pathRover))
			return false;
		return true;
	}

	public HashMap<String, ArrayList<Point>> getpathRover() {
		return pathRover;
	}

	public void setpathRover(HashMap<String, ArrayList<Point>> pathRover) {
		this.pathRover = pathRover;
	}

}
