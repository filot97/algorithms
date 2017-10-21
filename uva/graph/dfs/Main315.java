package graph.dfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main315 {
	static int N;
	static int order;
	static List[] adjList;	
	static int[] discovered;
	static List<Integer> cutList;

	public static void main(String[] args) throws Exception {
		String file = "C:\\workspace\\algorithms\\uva\\graph\\dfs\\315.txt";
		System.setIn(new FileInputStream(file));
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		String input = "";
		
		while ((input = reader.readLine()) != null) {
			N = Integer.parseInt(input);
			
			if (N == 0)
				break;
			
			order = 0;
			adjList = new List[N + 1];
			discovered = new int[N + 1];			
			cutList = new ArrayList<>();
			
			for (int i = 1; i <= N; i++)
				adjList[i] = new ArrayList<Integer>();
			
			while (true) {
				StringTokenizer st = new StringTokenizer(reader.readLine());
				
				int u = Integer.parseInt(st.nextToken());			
				
				if (u == 0)
					break;
				
				while (st.hasMoreTokens()) {
					int v = Integer.parseInt(st.nextToken());
					
					adjList[u].add(v);
					adjList[v].add(u);
				}			
			}
			
			dfs(1, true);
			
			writer.println(cutList.stream().distinct().count());
		}
		
		writer.close();
		reader.close();
	}
	
	public static int dfs(int u, boolean isRoot) {
		discovered[u] = ++order;
		int num = discovered[u];
		
		int childrenCount = 0;
		List<Integer> list = adjList[u];
		
		for (Integer v : list) {
			if (discovered[v] == 0) {
				++childrenCount;
				int low = dfs(v, false);
				
				if (!isRoot && low >= discovered[u])
					cutList.add(u);
				
				num = Math.min(num, low);
			}
			else {
				num = Math.min(num, discovered[v]);
			}
		}
		
		if (isRoot && childrenCount >= 2)
			cutList.add(u);
			
		return num;
	}

}