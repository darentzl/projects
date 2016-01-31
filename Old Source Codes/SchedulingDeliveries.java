import java.util.*;
import java.io.*;

/*
 * Daren Tan
 * Given the names of N pregnant women that are about to give birth and
 * their initial dilation (in millimeters), determine which woman that the only doctor on duty has to
 * give his/her most attention to. A woman with higher dilation status has higher priority. If there
 * are more than one woman with the same highest dilation status, this only doctor will give priority
 * to the woman who arrived at the hospital earlier.
 *
 * input: case 0:ArriveAtHospital, case 1:UpdateDilation, case 2:GiveBirth,	case 3: returns highest priority woman
15
0 GRACE 31
0 ASTRID 55
0 MARIA 42
3
0 CINDY 77
3
1 GRACE 24
2 CINDY
3
2 MARIA
3
2 GRACE
3
2 ASTRID
3
 * output:
ASTRID
CINDY
GRACE
GRACE
ASTRID
The delivery suite is empty
 */

class SchedulingDeliveries {

	TreeMap<String, WomanInfo> namesAsKey = new TreeMap<String, WomanInfo>();
	TreeMap<WomanInfo, String> namesAsValue = new TreeMap<WomanInfo, String>();
	int currentQueue = 0;

	class WomanInfo implements Comparable<WomanInfo> {
		private int queueNo;
		private int dilation;

		public WomanInfo(int q, int d) {
			this.queueNo = q;
			this.dilation = d;
		}

		int getQueue() {
			return queueNo;
		}

		int getDilation() {
			return dilation;
		}

		// Override
		public int compareTo(WomanInfo w) {
			if (this.dilation < w.getDilation()) {
				return -1;
			} else if (this.dilation > w.getDilation()) {
				return 1;
			} else {
				// dilation identical, compare queueNo
				if (this.queueNo > w.getQueue()) {
					return -1;
				} else if (this.queueNo < w.getQueue()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	//Constructor
	public SchedulingDeliveries() {

		namesAsKey = new TreeMap<String, WomanInfo>();	
		namesAsValue = new TreeMap<WomanInfo, String>();
	}

	void ArriveAtHospital(String womanName, int dilation) {

		WomanInfo w = new WomanInfo(currentQueue, dilation);
		
		namesAsKey.put(womanName, w);
		namesAsValue.put(w, womanName);
		
		currentQueue++;
	}
	
	// update dilation of woman using womanName
	void UpdateDilation(String womanName, int increaseDilation) {

		WomanInfo woman = namesAsKey.get(womanName);
		int sameQueue = woman.getQueue();
		int newDilation = woman.getDilation() + increaseDilation;
		
		WomanInfo updatedWoman = new WomanInfo(sameQueue, newDilation);
		
		//replace both tree
		namesAsValue.remove(woman);
		namesAsKey.remove(womanName);
		
		namesAsValue.put(updatedWoman, womanName);
		namesAsKey.put(womanName, updatedWoman);
	}

	//removes woman from from data structure
	void GiveBirth(String womanName) {

		WomanInfo w = namesAsKey.get(womanName);
		
		namesAsValue.remove(w);
		namesAsKey.remove(womanName);

	}
	
	// Return the name of the woman that the doctor
	// has to give the most attention to. If there is no more woman to
	// be taken care of, return a String "The delivery suite is empty"
	String Query() {

		String ans = "The delivery suite is empty";

		if(!namesAsValue.isEmpty())
			ans = namesAsValue.get(namesAsValue.lastKey());
		return ans;
	}

	void run() throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));
		int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >=
														// N
		while (numCMD-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			switch (command) {
			case 0:
				ArriveAtHospital(st.nextToken(),
						Integer.parseInt(st.nextToken()));
				break;
			case 1:
				UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken()));
				break;
			case 2:
				GiveBirth(st.nextToken());
				break;
			case 3:
				pr.println(Query());
				break;
			}
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		SchedulingDeliveries ps2 = new SchedulingDeliveries();
		ps2.run();
	}
}
