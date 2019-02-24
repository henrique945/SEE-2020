package grafo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

import helper.PositionOfLuHa;
import model.Position;

public class PathFind {

	static int[][] matrix;
	private HashMap<Position, ArrayList<Point>> pathAstro;

	public PathFind(String path) throws IOException {
		matrix = Image.GetMatrix(path);
		pathAstro = new HashMap<Position, ArrayList<Point>>();
	}

	public ArrayList<Position> GetIn(Point from, Point to) throws InterruptedException {
		return Find(from, to);
	}// Method that calls the smallest path between the two points

	public Point convertPoint(Position position) {
		//int x = Convert.toIntValue(PositionOfLuHa.getPosition("center").x + ((position.getX() - PositionOfLuHa.referenceAstro.getX()) * 2));
		//int y = Convert.toIntValue(PositionOfLuHa.getPosition("center").y + ((PositionOfLuHa.referenceAstro.getY() - position.getY()) * 2));
		
		// TAVA - +, - +
		
		int x = (int)((PositionOfLuHa.getPosition("center").x + position.getX() - PositionOfLuHa.referenceAstro.getX()));
		int y = (int) ((PositionOfLuHa.getPosition("center").y - PositionOfLuHa.referenceAstro.getY() + position.getY()));
		
		
		System.out.println("Centro: " + PositionOfLuHa.getPosition("center").x);
		System.out.println("Position x: " + position.getX());
		System.out.println("Reference: " + PositionOfLuHa.referenceAstro.getX());
		System.out.println("POINT: Novo ponto: x: " + x + " y: " + y);
		
		return new Point(x, y);
	}// Method that converts position to point

	public Position convertPosition(Point point) {
		
		System.out.println("CONVERT POSITION");
		System.out.println("Convert: " + point);
		
		double x = (PositionOfLuHa.referenceAstro.getX() + (PositionOfLuHa.getPosition("center").x - point.x) * 5.85);
		double y = (PositionOfLuHa.referenceAstro.getY() - (PositionOfLuHa.getPosition("center").y - point.y) * 3.45);
		double z = PositionOfLuHa.referenceAstro.getZ();
		
		
		System.out.println("PONTO: " + point + "POSITION: Novo ponto: x: " + x + " y: " + y + " REFERENCE  " + PositionOfLuHa.referenceAstro);
		
		return new Position(x, y, z);
	}// Method that converts point to position

	public void clearPath(Position position) {
		position = new Position(position.getX() + 5.85, position.getY() + 3.45, position.getZ());
		
		ArrayList<Point> points = pathAstro.get(position);
		pathAstro.remove(position);
		for (Point point : points) {
			matrix[point.x][point.y] *= (-1);
		}
		Point p = convertPoint(position);
		matrix[p.x][p.y] = -1;
		Image.PrintMatrix(matrix);
	}

	private ArrayList<Position> Find(Point from, Point to) throws InterruptedException {

		PriorityQueue<Point> q = new PriorityQueue<Point>();
		int[][] clone = copyMatriz(matrix);
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
					return getPath(p);

				clone[child.x][child.y] = -1;
			}
			if (p.x > 0 && (clone[p.x - 1][p.y] > 0 || clone[p.x - 1][p.y] == value)) {
				Point child = new Point(p.x - 1, p.y);
				child.prev = p;
				child.value = p.value + clone[p.x - 1][p.y];
				q.add(child);
				if (compare(child, to))
					return getPath(p);

				clone[child.x][child.y] = -1;
			}
			if (p.y < clone[0].length - 1 && (clone[p.x][p.y + 1] > 0 || clone[p.x][p.y + 1] == value)) {
				Point child = new Point(p.x, p.y + 1);
				child.prev = p;
				child.value = p.value + clone[p.x][p.y + 1];
				q.add(child);
				if (compare(child, to))
					return getPath(p);

				clone[child.x][child.y] = -1;
			}
			if (p.y > 0 && (clone[p.x][p.y - 1] > 0 || clone[p.x][p.y - 1] == value)) {
				Point child = new Point(p.x, p.y - 1);
				child.prev = p;
				child.value = p.value + clone[p.x][p.y - 1];
				q.add(child);
				if (compare(child, to))
					return getPath(p);

				clone[child.x][child.y] = -1;
			}

		}
		Image.PrintMatrix(clone);
		return null;
	}// Breadth-First Search
	
	private ArrayList<Position> getPath(Point p) {
		ArrayList<Position> path = new ArrayList<Position>();
		ArrayList<Point> points = new ArrayList<Point>();

		while (p.prev != null) {
			p = p.prev;
			if (matrix[p.x][p.y] >0) {
				path.add(convertPosition(p));
				points.add(p);
				matrix[p.x][p.y]*= (-1);
			}
		}
		pathAstro.put(path.get(0), points);
		Collections.reverse(path);

		Image.PrintMatrix(matrix);

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
		result = prime * result + ((pathAstro == null) ? 0 : pathAstro.hashCode());
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
		if (pathAstro == null) {
			if (other.pathAstro != null)
				return false;
		} else if (!pathAstro.equals(other.pathAstro))
			return false;
		return true;
	}
}
