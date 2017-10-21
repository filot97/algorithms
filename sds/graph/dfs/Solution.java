package graph.dfs;

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
import java.util.stream.Collectors;

public class Solution {
	static int T;
	static int N;
	static int M;
	
	static int order;

	public static void main(String[] args) throws Exception {
		String file = "C:\\workspace\\algorithms\\sds\\graph\\dfs\\Solution.txt";
		System.setIn(new FileInputStream(file));

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		T = Integer.parseInt(reader.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			int[] discovered = new int[N + 1];
			List[] adjList = new List[N + 1];			
			List<Edge2> cutList = new ArrayList<>();
			
			for (int i = 1; i <= N; i++)
				adjList[i] = new ArrayList<Edge2>();
			
			for (int i = 1; i <= M; i++) {
				st = new StringTokenizer(reader.readLine());
				
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				
				adjList[u].add(new Edge2(u, v, i));
				adjList[v].add(new Edge2(v, u, i));
			}
			
			order = 0;
			
			for (int i = 1; i <= N; i++)
				if (discovered[i] == 0)
					dfs(1, 0, adjList, discovered, cutList);
			
			Collections.sort(cutList, (o1, o2) -> {
				return Integer.compare(o1.n, o2.n);
			});
			
			int count = cutList.size();
			String Answer = "#" + t + " " + count + " " + cutList.stream()
															     .map(p -> p.n + "")
															     .collect(Collectors.joining(" "));			
			
			writer.println(Answer);
		}
		
		writer.close();
		reader.close();
	}
	
	public static int dfs(int u, int parent, List[] adjList, int[] discovered, List<Edge2> cutList) {
		discovered[u] = ++order;
		int num = discovered[u];
		
		List<Edge2> list = adjList[u];
		
		for (Edge2 v : list) {
			if (v.v == parent)
				continue;			
			
			if (discovered[v.v] == 0) {
				int low = dfs(v.v, u, adjList, discovered, cutList);
				
				if (low > discovered[u])
					cutList.add(new Edge2(u, v.v, v.n));
				
				num = Math.min(num, low);				
			}
			else
				num = Math.min(num, discovered[v.v]);
		}
		
		return num;
	}

}

class Edge2 {
	int u;
	int v;
	int n;
	
	public Edge2(int u, int v, int n) {
		this.u = u;
		this.v = v;
		this.n = n;
	}
}
