package graph.floyd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main10048 {

	public static void main(String[] args) throws Exception {
		String file = "C:\\workspace\\algorithms\\uva\\graph\\floyd\\10048.txt";
		System.setIn(new FileInputStream(file));
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	
		int T = 1;
		String inputs = "";
	
		while ((inputs = reader.readLine()) != null) {
			String[] input = inputs.split(" ");
	
			int C = Integer.parseInt(input[0]);
			int S = Integer.parseInt(input[1]);
			int Q = Integer.parseInt(input[2]);
	
			if (C == 0 && S == 0 && Q == 0)
				break;
			
			int[][] adjMatrix = new int[C + 1][C + 1];
			
			for (int i = 1; i <= C; i++)
				Arrays.fill(adjMatrix[i], Integer.MAX_VALUE);
			
			for (int i = 1; i <= S; i++) {
				StringTokenizer st = new StringTokenizer(reader.readLine());

				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());
				
				adjMatrix[u][v] = w;
				adjMatrix[v][u] = w;
			}
			
			floyd(C, adjMatrix);
			
			if (T > 1)
				writer.println();
			
			writer.println("Case #" + T++);

			for (int i = 1; i <= Q; i++) {
				StringTokenizer st = new StringTokenizer(reader.readLine());

				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				
				writer.println(adjMatrix[u][v] == Integer.MAX_VALUE ? "no path" : adjMatrix[u][v]);
			}
		}
		
		writer.close();
		reader.close();
	}
	
	public static void floyd(int N, int[][] adjMatrix) {
		for (int k = 1; k <= N; k++)
			for (int i = 1; i <= N; i++)
				for (int j = 1; j <= N; j++)
					adjMatrix[i][j] = Math.min(adjMatrix[i][j], Math.max(adjMatrix[i][k], adjMatrix[k][j]));
	}
}

class Edge10048 {
	int u;
	int v;
	int w;

	public Edge10048(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}	
}