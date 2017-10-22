package graph.scc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main2150 {
	static int V;
	static int E;
	
	static List<Integer>[] adjList;
	static List<Integer>[] adjListBack;
	
	static int order;	
	static boolean[] visited;
	static int[] finishTime;
	
	static List<List> sccList;

	public static void main(String[] args) throws Exception {
		String file = "D:\\gitRepositores\\algorithms\\boj\\graph\\scc\\2150.txt";
		System.setIn(new FileInputStream(file));

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st1 = new StringTokenizer(reader.readLine());
		
		V = Integer.parseInt(st1.nextToken());
		E = Integer.parseInt(st1.nextToken());
		
		adjList = new List[V + 1];
		adjListBack = new List[V + 1];
		
		for (int i = 1; i <= V; i++) {
			adjList[i] = new ArrayList<>();
			adjListBack[i] = new ArrayList<>();
		}
		
		for (int i = 1; i <= E; i++) {
			StringTokenizer st2 = new StringTokenizer(reader.readLine());
			
			int A = Integer.parseInt(st2.nextToken());
			int B = Integer.parseInt(st2.nextToken());
			
			adjList[A].add(B);			
			adjListBack[B].add(A);
		}
		
		kosaraju();
		
		writer.println(sccList.size());
		
		sccList.stream().sorted((o1, o2) -> {
							return Integer.compare((int)o1.get(0), (int)o2.get(0));
						})
						.forEach(scc -> {
			String result = (String) scc.stream().map(Object::toString)
						.collect(Collectors.joining(" "));
			
			writer.println(result + " -1");
		});
		
		
		writer.close();
		reader.close();
	}

	private static void kosaraju() {
		visited = new boolean[V + 1];
		finishTime = new int[V + 1];
		
		for (int i = 1; i <= V; i++) {
			if (visited[i])
				continue;
		
			dfs(i);
		}
		
		Arrays.fill(visited, false);
		sccList = new ArrayList<>();
		
		for (int i = V; i >= 1 ; i--) {
			int u = finishTime[i];
			
			if (visited[u])
				continue;
			
			List<Integer> scc = new ArrayList<>();
			
			dfsBack(u, scc);
			
			Collections.sort(scc);
			sccList.add(scc);			
		}			
	}
	
	private static void dfs(int u) {
		visited[u] = true;
		
		List<Integer> list = adjList[u];
		
		for (Integer v : list) {
			if (visited[v])
				continue;			
			
			dfs(v);			
		}
		
		finishTime[++order] = u;
	}
	
	private static void dfsBack(int u, List<Integer> scc) {
		visited[u] = true;
		scc.add(u);
		
		List<Integer> list = adjListBack[u];
		
		for (Integer v : list) {
			if (visited[v])
				continue;
			
			dfsBack(v, scc);			
		}
	}

}