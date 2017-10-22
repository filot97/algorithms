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
import java.util.Optional;
import java.util.StringTokenizer;

public class Main796 {
	static int N;
	static int K;
	static int order;
	static List<Integer>[] adjList;	
	static int[] discovered;
	static List<Edge796> cutList;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String file = "D:\\gitRepositores\\algorithms\\uva\\graph\\dfs\\796.txt";
		System.setIn(new FileInputStream(file));
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		while (true) {					
			Optional<String> input = Optional.ofNullable(reader.readLine());
			
			if (!input.isPresent())
				break;			
			
			if ("".equals(input.get())) {
				writer.println();
				
				continue;
			}
			
			N = parseInt(input.get());
			
			adjList = new List[N];
			discovered = new int[N];
			cutList = new ArrayList<>();
			order = 0;
			
			for (int i = 0; i < N; i++)
				adjList[i] = new ArrayList<>();
			
			for (int i = 1; i <= N; i++) {
				StringTokenizer st1 = new StringTokenizer(reader.readLine());
				
				int u = parseInt(st1.nextToken());
				int k = parseInt(st1.nextToken());
				
				for (int j = 1; j <= k; j++) {
					int v = parseInt(st1.nextToken());
					
					adjList[u].add(v);
					adjList[v].add(u);
				}
			}
			
			for (int i = 0 ; i < N; i++)				
				dfs(i, -1);
			
			writer.println(cutList.size() + " critical links");				
			
			Collections.sort(cutList, (o1, o2) -> {
				if (o1.u < o2.u)
					return -1;

				if (o1.u == o2.u && o1.v < o2.v)
					return -1;

				if (o1.u == o2.u && o1.v == o2.v)
					return 0;

				return 1;
			});
			
			for (Edge796 edge : cutList)		
				writer.println(edge.u + " - " + edge.v);
			
			writer.flush();
		}
		
		writer.close();
		reader.close();
	}
	
	public static int dfs(int u, int parent) {
		discovered[u] = ++order;
		int num = discovered[u];
		
		List<Integer> list = adjList[u];
		
		for (Integer v : list) {
			if (v == parent)
				continue;
			
			if (discovered[v] == 0) {
				int low = dfs(v, u);
				
				if (low > discovered[u])
					cutList.add(new Edge796(Math.min(u, v), Math.max(u, v)));
				
				num = Math.min(num, low);
			}
			else {
				num = Math.min(num, discovered[v]);
			}
		}
		
		return num;
	}
	
	public static int parseInt(String input) {
		StringBuilder builder = new StringBuilder();
		
		for (Character c : input.toCharArray()) {
			if (c.isDigit(c))
				builder.append(c);
		}
		
		return Integer.parseInt(builder.toString());
	}
}

class Edge796 {
	int u;
	int v;
	
	public Edge796(int u, int v) {
		this.u = u;
		this.v = v;
	}
}
