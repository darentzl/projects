import java.util.*;
import java.io.*;

/*
 * Daren Tan
 * This program utilizes reads in an Adjacency List, converts it to EdgeList to compute Minimum spanning tree, and outputs the edge with the highest weight in the MST.
 * input: num of test cases (2), num of vertexes (8), for vertex(i) - number of neighbours, neighbour index, neighbour weight (2 1 1 3 3), repeats for all vertex
 * followed by number of queries (2), from vertex i to j (3 to 5)
2

8
2   1 1   3 3
2   0 1   2 4
3   1 4   3 6   7 3
2   0 3   2 6
2   5 5   7 7
2   4 5   6 2
2   5 2   7 1
3   2 3   4 7   6 1
2
3 5
3 5

8
2   1 1   3 3
2   0 1   2 4
3   1 4   3 6   7 3
2   0 3   2 6
2   5 5   7 7
2   4 5   6 2
2   5 2   7 1
3   2 3   4 7   6 1
1
3 5
 * output:
 */

class OutForAWalk {
	private static int V;
	private static Vector<Vector<IntegerPair>> AdjList;
	private static Vector<IntegerTriple> EdgeList;
	private static Vector<IntegerTriple> MSTEdgeList;

	private static int[] visited;
	private static int[] parent;
	
	private static int[][] weightFromTo;

	static void convertAdjListToEdgeList() { // to convert Adjacency List into
												// EdgeList {
		EdgeList = new Vector<IntegerTriple>();

		for (int i = 0; i < V; i++) {
			for (int e = 0; e < AdjList.get(i).size(); e++) {
				IntegerTriple edge = new IntegerTriple(AdjList.get(i).get(e)
						.second(), i, AdjList.get(i).get(e).first());
				EdgeList.add(edge);

				// delete this edge from the adjList to prevent duplicate
				int vert = AdjList.get(i).get(e).first();

				for (int j = 0; j < AdjList.get(vert).size(); j++) {
					if (AdjList.get(vert).get(j).first() == i)
						AdjList.get(vert).remove(j);
				}
			}
		}
		Collections.sort(EdgeList);
	}

	static void computeMST(Vector<IntegerTriple> list) {

		MSTEdgeList = new Vector<IntegerTriple>();

		UnionFind UF = new UnionFind(V);
		for (int i = 0; i < list.size(); i++) {
			IntegerTriple e = list.get(i);
			int u = e.second(), v = e.third();
			if (!UF.isSameSet(u, v)) {
				MSTEdgeList.add(list.get(i));
				UF.unionSet(u, v);
			}
		}
	}

	static void convertEdgeToAdj(Vector<IntegerTriple> list) {
		AdjList = new Vector<Vector<IntegerPair>>();
		for (int i = 0; i < V; i++)
			AdjList.add(new Vector<IntegerPair>());

		for (int i = 0; i < list.size(); i++) {
			IntegerTriple e = list.get(i);
			int w = e.first(), u = e.second(), v = e.third();
			AdjList.get(u).add(new IntegerPair(v, w));
			AdjList.get(v).add(new IntegerPair(u, w));
		}
	}

	void PreProcess() {
		convertAdjListToEdgeList();
		computeMST(EdgeList);
		convertEdgeToAdj(MSTEdgeList);
		weightFromTo = new int[10][V];
	}

	static void initDFS(int start) {
		visited = new int[V];
		parent = new int[V];
		int maxWeight = 0;
		DFSrec(start, maxWeight, start, 0);
	}

	static void DFSrec(int current, int maxWeight, int start, int prevWeight) {
		visited[current] = 1;
		//add weight between me and my parent
		if(maxWeight < prevWeight)	
			maxWeight = prevWeight;
		//System.out.print("current visited pt:" + current);
		//System.out.println(" current max weight:" + maxWeight);

		for (int j = 0; j < AdjList.get(current).size(); j++) {
			IntegerPair v = AdjList.get(current).get(j);

			if (visited[v.first()] == 0) {
				parent[v.first()] = current;
				
				DFSrec(v.first(), maxWeight, start, v.second());

			} else {
				//input max weight so far into array
				//System.out.println("From "+start+" to "+current+":" + maxWeight);
				weightFromTo[start][current] = maxWeight;
			}
		}
	}

	int Query(int source, int destination) {

		if(weightFromTo[source][destination] > 0) {
			return weightFromTo[source][destination];
		} else {
			initDFS(source);
			return weightFromTo[source][destination];
		}
	}

	void run() throws Exception {
		// do not alter this method
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector<Vector<IntegerPair>>();
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector<IntegerPair>());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w));
				}
			}

			PreProcess(); // you may want to use this function or leave it empty
							// if you do not need it

			int Q = sc.nextInt();
			while (Q-- > 0)
				pr.println(Query(sc.nextInt(), sc.nextInt()));
			pr.println(); // separate the answer between two different graphs
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		OutForAWalk ps4 = new OutForAWalk();
		ps4.run();
	}
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not
						// recommended
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
		} catch (IOException ioe) {
			return -1;
		}
	}
}

class IntegerPair implements Comparable<IntegerPair> {
	int _first, _second;

	public IntegerPair(int f, int s) {
		_first = f;
		_second = s;
	}

	public int compareTo(IntegerPair o) {
		if (this.first() != (o.first()))
			return this.first() - o.first();
		else
			return this.second() - o.second();
	}

	int first() {
		return _first;
	}

	int second() {
		return _second;
	}

	public String toString() {
		return "[" + _first + ", " + _second + "]";
	}
}

class IntegerTriple implements Comparable<IntegerTriple> {
	int _first, _second, _third;

	public IntegerTriple(int f, int s, int t) {
		_first = f;
		_second = s;
		_third = t;
	}

	public int compareTo(IntegerTriple o) {
		if (this.first() != (o.first()))
			return this.first() - o.first();
		else if (this.second() != (o.second()))
			return this.second() - o.second();
		else
			return this.third() - o.third();
	}

	int first() {
		return _first;
	}

	int second() {
		return _second;
	}

	int third() {
		return _third;
	}

	public String toString() {
		return "[" + _first + ", " + _second + ", "
				+ _third + "]";
	}
}

class UnionFind { // OOP style
	private Vector<Integer> p, rank, setSize;
	private int numSets;

	public UnionFind(int N) {
		p = new Vector<Integer>(N);
		rank = new Vector<Integer>(N);
		setSize = new Vector<Integer>(N);
		numSets = N;
		for (int i = 0; i < N; i++) {
			p.add(i);
			rank.add(0);
			setSize.add(1);
		}
	}

	public int findSet(int i) {
		if (p.get(i) == i)
			return i;
		else {
			int ret = findSet(p.get(i));
			p.set(i, ret);
			return ret;
		}
	}

	public Boolean isSameSet(int i, int j) {
		return findSet(i) == findSet(j);
	}

	public void unionSet(int i, int j) {
		if (!isSameSet(i, j)) {
			numSets--;
			int x = findSet(i), y = findSet(j);
			// rank is used to keep the tree short
			if (rank.get(x) > rank.get(y)) {
				p.set(y, x);
				setSize.set(x, setSize.get(x) + setSize.get(y));
			} else {
				p.set(x, y);
				setSize.set(y, setSize.get(y) + setSize.get(x));
				if (rank.get(x) == rank.get(y))
					rank.set(y, rank.get(y) + 1);
			}
		}
	}

	public int numDisjointSets() {
		return numSets;
	}

	public int sizeOfSet(int i) {
		return setSize.get(findSet(i));
	}
}