package geometry.convexhull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main1708 {
	static int N;

	public static void main(String[] args) throws Exception {
		String file = "D:\\gitRepositores\\algorithms\\boj\\geometry\\convexhull\\1708.txt";
		System.setIn(new FileInputStream(file));

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		N = Integer.parseInt(reader.readLine());

		List<Point> pointSet = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			pointSet.add(new Point(x, y));
		}

		Point basePoint = findBasePoint(pointSet);
		sort(basePoint, pointSet);		
		
		Stack<Point> convex = new Stack<>();

		convex.push(pointSet.get(0));
		convex.push(pointSet.get(1));

		for (int i = 1; i < N; i++) {
			while (convex.size() > 1 && Point.ccw(convex.peek2(), convex.peek(), pointSet.get(i)) <= 0)				
					convex.pop();			

			convex.push(pointSet.get(i));
		}

		writer.println(convex.size());

		writer.close();
		reader.close();
	}

	private static void sort(Point basePoint, List<Point> pointSet) {
		Collections.sort(pointSet, (a, b) -> {
			if (basePoint.equals(a))
				return -1;
			
			if (basePoint.equals(b))
				return 1;
			
			int ccw = Point.ccw(basePoint, a, b);
			
			if (ccw > 0)
				return -1;

			if (ccw < 0)
				return 1;

			return Point.distance(basePoint, a) < Point.distance(basePoint, b) ? -1 : 1;
		});
	}

	private static Point findBasePoint(List<Point> pointSet) {
		return pointSet.stream().min((a, b) -> {
			if (a.y < b.y)
				return -1;

			if (a.y == b.y && a.x < b.x)
				return -1;

			if (a.y > b.y)
				return 1;

			return 0;
		}).get();
	}

}

class Point {
	int x;
	int y;	

	public Point(int x, int y) {
		this.x = x;
		this.y = y;		
	}

	public static int ccw(Point a, Point b, Point c) {
		long res = ((long)a.x * (long)b.y) + (long)b.x * (long)c.y + (long)c.x * (long)a.y -
					((long)a.x * (long)c.y + (long)b.x * (long)a.y + (long)c.x * (long)b.y);

		if (res > 0)
			return 1;

		if (res < 0)
			return -1;

		return 0;
	}

	public static int distance(Point a, Point b) {
		return Math.abs((a.x - b.x)) + Math.abs((a.y - b.y));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;		
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}

class Stack<T> {
	private List<T> pointSet;

	public Stack() {
		this.pointSet = new ArrayList<>();
	}

	public void push(T point) {
		this.pointSet.add(point);
	}

	public T pop() {
		int lastIndex = this.pointSet.size() - 1;

		T point = this.pointSet.get(lastIndex);
		this.pointSet.remove(lastIndex);

		return point;
	}

	public T peek() {
		return pointSet.get(pointSet.size() - 1);
	}
	
	public T peek2() {
		return pointSet.get(pointSet.size() - 2);
	}
	
	public int size() {
		return pointSet.size();
	}
}