import java.io.*;
import java.util.*;

// A0111855J
// Daren Tan Zong Loong

class Caesarean {
	private static int V; // number of vertices in the graph (steps of a Caesarean section surgery)
	private static int E; // number of edges in the graph (dependency information between various steps of a Caesarean section surgery)
	private static Vector < IntegerPair > EL; // the unweighted graph, an edge (u, v) in EL implies that step u must be performed before step v
	private static Vector < Integer > estT; // the estimated time to complete each step

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------
	private static Vector<Integer> T = new Vector<Integer>();
	private static Vector<Integer> p = new Vector<Integer>();

	private static Vector<Vector<IntegerPair>> AdjList;
	private static Vector<Integer> toposort = new Vector<Integer>();

	private static Vector<Integer> criticalVertices = new Vector<Integer>();
	private static Vector<Integer> possibleVertices = new Vector<Integer>();
	private static int steps;
	// --------------------------------------------



	private static void initSSLP() {
		T.clear();
		T.addAll(Collections.nCopies(V, 0));
		p.clear();
		p.addAll(Collections.nCopies(V, -1));
	}


	private static void ELtoAdjL() {
		AdjList = new Vector<Vector<IntegerPair>>();
		for (int i = 0; i < V; i++)
			AdjList.add(new Vector<IntegerPair>());

		for (int i = 0; i < EL.size(); i++) {
			IntegerPair e = EL.get(i);
			int t = estT.get(e.first()), u = e.first(), v = e.second();
			AdjList.get(u).add(new IntegerPair(v, t));
		}
	}

	private static void DFSrec(int s) {
		T.set(s,1);
		for(int i=0; i<AdjList.get(s).size(); i++) {
			IntegerPair v = AdjList.get(s).get(i);
			if(T.get(v.first()) == 0) {
				p.set(v.first(), s);
				DFSrec(v.first());
			}
		}
		toposort.add(s);
	}

	private static void toposort() {
		initSSLP();
		toposort.clear();
		for(int i=0; i<V; i++)
			if(T.get(i)==0)
				DFSrec(i);
		Collections.reverse(toposort);
		//System.out.println(toposort);
	}

	private static void bellF() {

		//for each edge
		Integer topoV = null;
		for(int i=0; i<V; i++) {
			topoV = toposort.get(i);
			//System.out.print(topoV);
			for(IntegerPair e: AdjList.get(topoV)) {
				stretch(topoV, e.first(), e.second());
			}
		} 
	}

	private static void stretch(int u, int v, int t_u_v) {
		if (T.get(v) < T.get(u) + t_u_v) {
			T.set(v, T.get(u) + t_u_v);
			p.set(v, u);
		}
	}

	/*private static void countV(int u, int total, int max, Vector<Integer> vertices) {

		//if reached end of path
		if(u == V-1) {
			//all these v are critical
			if(total == max) { 
				//for(Integer i: vertices) {
					//if(!criticalVertices.contains(i))
						//criticalVertices.add(i);
				criticalVertices.addAll(vertices);
				//}
				//System.out.println("critical:" + criticalVertices);
			} else {
				// not critical vertices
				//for(Integer i: vertices) {
					//if(!possibleVertices.contains(i))
						//possibleVertices.add(i);
				possibleVertices.addAll(vertices);
				//}
				//System.out.println("possible:" + possibleVertices);
			} 
		} else {
			//not end of path yet
			Vector<Integer> v = new Vector<Integer>(vertices);

			for(IntegerPair e: AdjList.get(u)) {
				v.add(u);
				countV(e.first(), total + estT.get(u) , max, v);
			}
		} 
	}

	int Query() {
		
		ELtoAdjL();
		toposort();

		initSSLP();
		bellF();

		int maxTime = T.get(V-1);

		criticalVertices.clear();
		possibleVertices.clear();

		steps=0;
		Vector<Integer> vertices = new Vector<Integer>();
		countV(0, 0, maxTime, vertices);

		//System.out.println("possible:" + possibleVertices);
		for(Integer i: possibleVertices) {
			if(!criticalVertices.contains(i)) {
				//System.out.println(i);
				if(T.get(i) + 1 <= maxTime) {
					steps++;
				}
			}
		}

		return steps;
	}*/

	int Query() {
		steps = 0;
		
		ELtoAdjL();
		toposort();

		initSSLP();
		bellF();
		
		Collections.reverse(toposort);
		//for each vertex
		for(int i=1; i<V-1; i++) {
			int u= toposort.get(i);
			//get min(T of all its child)
			int min = 10000000;
			for(IntegerPair e: AdjList.get(u)) {
				if(T.get(e.first()) < min) {
					min = T.get(e.first());
				}
			}
					
				
			if(T.get(u) + estT.get(u) < min) {
				steps++;
				T.set(u, min-estT.get(u));
			}
		}

		return steps;
	}
	
	void run() throws Exception {
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt(); E = sc.nextInt(); // read V and then E

			estT = new Vector < Integer > ();
			for (int i = 0; i < V; i++)
				estT.add(sc.nextInt());

			// clear the graph and read in a new graph as an unweighted Edge List (only using IntegerPair, not IntegerTriple)
			EL = new Vector < IntegerPair > ();
			for (int i = 0; i < E; i++)
				EL.add(new IntegerPair(sc.nextInt(), sc.nextInt())); // just directed edge (u -> v)

			pr.println(Query());
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		Caesarean ps6 = new Caesarean();
		ps6.run();
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
	int _first, _second;

	public IntegerPair(int f, int s) {
		_first = f;
		_second = s;
	}

	public int compareTo(IntegerPair o) {
		if (this.first() != o.first())
			return this.first() - o.first();
		else
			return this.second() - o.second();
	}

	int first() { return _first; }
	int second() { return _second; }
}
