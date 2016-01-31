import java.util.*;
import java.io.*;

/*
 * Daren Tan
 * This program utilizes bellman ford or djikstra to find the single-source shortest path from 1 point to another.
 * 
 * input: number of graphs, number of n vertex, vertex 0 has 2 neighbours, vertex 2 with directed edge weight 8 and vertex 3 with edge weight 3. 
1

7
2   2 8   3 3
0
0
1   4 6
1   5 2
1   6 7
1   1 5
 * 
 * output: shortest path
23
 */

class DijkstraExample {
	private static int V; // number of vertices in the graph (number of junctions in Singapore map)
	private static Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge

	private static final int INF = 1000000000;
	private static Vector<Integer> D = new Vector<Integer>();
	private static Vector<Integer> p = new Vector<Integer>();

	private static PriorityQueue<IntegerPair> PQ = new PriorityQueue<IntegerPair>();


	private static void initSSSP(int s) {
		D.clear();
		D.addAll(Collections.nCopies(V, INF));
		p.clear();
		p.addAll(Collections.nCopies(V, -1));
		D.set(s, 0);
	}


	private static void relax(int u, int v, int w_u_v) {
		if (D.get(v) > D.get(u) + w_u_v) {
			D.set(v, D.get(u) + w_u_v);
			p.set(v, u);
		}
	}

	private static void dijkstra() {
		initSSSP(0);

		PQ.add(new IntegerPair(0,0));
		while(!PQ.isEmpty()) {
			IntegerPair current = PQ.poll();
			if(D.get(current.first()) == current.second()) {
				for(IntegerPair v: AdjList.get(current.first())) {
					//relax 
					if(D.get(v.first()) > D.get(current.first()) + v.second()) {
						D.set(v.first(), D.get(current.first()) + v.second());
						p.set(v.first(), current.first());
						PQ.add(new IntegerPair(v.first(), D.get(v.first())));
					}
				}
			}
		}

	}


	/* assumes weight in graph is identical.
	private static void bellF() {
      
		//for every vertex
		for(int i=0; i<V-1; i++)
			//for each edge
			for(int u=0; u<V; u++)
				for(IntegerPair e: AdjList.get(u))
					relax(u, e.first(), e.second());
	} */

	int Query() {

		// You have to report the shortest path from Steven and Grace's home (vertex 0)
		// to reach their chosen hospital (vertex 1)
		dijkstra();
		int ans = D.get(1);

		return ans;
	}

	void run() throws Exception {
		// you can alter this method if you need to do so
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector < Vector < IntegerPair > >();
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector<IntegerPair>());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
				}
			}

			pr.println(Query());
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		DijkstraExample ps5 = new DijkstraExample();
		ps5.run();
	}
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
	BufferedInputStream bis;
	IntegerScanner(InputStream is) {
		bis = new BufferedInputStream(is, 1000000);
	}

	public int nextInt() {
		int result = 0;
		try {
			int cur = bis.read();
			if (cur == -1)
				return -1;

			while ((cur < 48 || cur > 57) && cur != 45) {
				cur = bis.read();
			}

			boolean negate = false;
			if (cur == 45) {
				negate = true;
				cur = bis.read();
			}

			while (cur >= 48 && cur <= 57) {
				result = result * 10 + (cur - 48);
				cur = bis.read();
			}

			if (negate) {
				return -result;
			}
			return result;
		}
		catch (IOException ioe) {
			return -1;
		}
	}
}


class IntegerPair implements Comparable<IntegerPair> {
	Integer _first, _second;

	public IntegerPair(Integer f, Integer s) {
		_first = f;
		_second = s;
	}

	public int compareTo(IntegerPair o) {
		if (!this.first().equals(o.first()))
			return this.first() - o.first();
		else
			return this.second() - o.second();
	}

	Integer first() { return _first; }
	Integer second() { return _second; }
}
