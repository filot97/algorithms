package graph.mst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main10048 {
	static boolean isFound;
	static int Answer;

	public static void main(String[] args) throws Exception {
		String file = "C:\\workspace\\algorithms\\uva\\graph\\mst\\10048.txt";
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

			int[] PARENT = new int[C + 1];
			int[] RANK = new int[C + 1];
			Edge10048[] Edges = new Edge10048[S + 1];
			Edges[0] = new Edge10048(0, 0, Integer.MIN_VALUE);

			for (int i = 1; i <= C; i++)
				PARENT[i] = i;

			for (int i = 1; i <= S; i++) {
				StringTokenizer st = new StringTokenizer(reader.readLine());

				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());

				Edges[i] = new Edge10048(u, v, w);
			}

			Arrays.sort(Edges, (a, b) -> {				
				return Integer.compare(a.w, b.w);
			});
			
			List[] mstEdges = kruscal(C, S, PARENT, RANK, Edges);

			if (T > 1)
				writer.println();
			
			writer.println("Case #" + T++);

			for (int i = 1; i <= Q; i++) {
				StringTokenizer st = new StringTokenizer(reader.readLine());

				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());				
								
				Answer = 0;
				isFound = false;
				boolean[] visited = new boolean[C + 1];
				dfs(u, v, 0, mstEdges, visited);
				
				writer.println(!isFound ? "no path" : Answer);				
			}
		}		
		
		writer.close();
		reader.close();
	}

	public static void dfs(int u, int dest, int max, List[] mstEdges, boolean[] visited) {
		if (u == dest) {
			isFound = true;
			Answer = max;
			
			return;
		}
		
		List<Edge10048> list = mstEdges[u];
		
		for (Edge10048 v : list) {
			if (visited[v.v])
				continue;
			
			visited[v.v] = true;			
			dfs(v.v, dest, Math.max(max, v.w), mstEdges, visited);
		}
	}
	
	public static List[] kruscal(int C, int S, int[] PARENT, int[] RANK, Edge10048[] Edges) {
		List[] mstEdges = new List[C + 1];
		
		for (int i = 1; i <= C; i++)
			mstEdges[i] = new ArrayList<Edge10048>();
		
		for (int i = 1; i <= S; i++) {
			int u = Edges[i].u;
			int v = Edges[i].v;
			int w = Edges[i].w;
			
			if (union(u, v, PARENT, RANK)) {
				mstEdges[u].add(new Edge10048(u, v, w));
				mstEdges[v].add(new Edge10048(v, u, w));
			}
		}
		
		return mstEdges;
	}	

	public static int find(int u, int[] PARENT) {
		if (PARENT[u] == u)
			return u;

		return PARENT[u] = find(PARENT[u], PARENT);
	}

	public static boolean union(int u, int v, int[] PARENT, int[] RANK) {
		int pu = find(u, PARENT);
		int pv = find(v, PARENT);

		if (pu == pv)
			return false;

		if (RANK[pu] < RANK[pv])
			PARENT[pu] = pv;
		else {
			PARENT[pv] = pu;

			if (RANK[pu] == RANK[pv])
				RANK[pu]++;
		}

		return true;
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