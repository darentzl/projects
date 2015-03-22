/*
 * Daren Tan
 * 
 * This program compares performance of different process scheduling algorithms
 * FIFO, SJF, SRT, MLF
 * assuming a single processor, it reads n pairs of integers with 
 * arrival time and total service times from input.txt on memory stick 
 * and outputs a text file which displays the average turnaround time and the real time
 * of each process i
 * 
 * 
 * Input: 0 4 0 2 3 1
 * Output:
 * 4.66 4 6 4
 * 4.00 6 2 4
 * 3.33 7 2 1 
 * 4.66 7 6 1
 */

import java.util.Vector;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;

public class ScheduleAlgo {

	static Vector<Process> input = new Vector<Process>();	

	static int numProcesses;
	static int maxTime;
	static double avgTT;

	static String output;

	public static void main(String[] args) throws IOException {

		readFile();
		computeMaxTime();
		//printInput();

		fifo();
		printRealTime(output);

		sjf();
		printRealTime(output);

		srt();
		printRealTime(output);

		mlf();
		printRealTime(output);

		createFile();
	}

	public static void printInput() {
		for(Process p: input) {
			System.out.println(p);
		}
		System.out.println("n = " + numProcesses);
		System.out.println("maxTime = " + maxTime);
	}

	public static void printRealTime(String s) { 
		System.out.println(s);
	}

	public static String stringOutput() {
		String s = String.format("%.2f", avgTT); 
		for(Process p : input) {
			s = s + " " + p.realTime;
		}
		return s;
	}

	public static void computeTT() {
		double totalRealTime =0;
		for(Process p: input) {
			totalRealTime += p.realTime;
		}
		avgTT = (double)totalRealTime/numProcesses;
	}

	public static void readFile() throws IOException {
		BufferedReader file = new BufferedReader(new FileReader("/F:/input2.txt"));
		String s;

		while((s = file.readLine()) != null) {

			String[] strArray = s.split(" ");
			numProcesses = strArray.length/2;
			maxTime = 0;

			for(int i=0; i<strArray.length; i=i+2) {
				int arrival = Integer.parseInt(strArray[i]);
				int totalService = Integer.parseInt(strArray[i+1]);
				Process p = new Process(arrival, totalService);
				input.add(p);

			}
		}

		file.close();
	}

	public static void computeMaxTime() {
		maxTime += input.get(0).totalServiceTime;
		for(int i=1; i<input.size(); i++) {
			if(input.get(i).arrivalTime > maxTime) {
				maxTime = input.get(i).arrivalTime;
				maxTime += input.get(i).totalServiceTime;
			} else {
				maxTime += input.get(i).totalServiceTime;
			}
		}
	}

	public static void createFile() throws IOException {
		File file = new File("/F:/A0111855J.txt");

		if(!file.exists()) {
			file.createNewFile();
		}
	}

	public static void writeToFile() throws IOException {
		File file = new File("/F:/A0111855J.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(output);
		bw.newLine();
		bw.close();
	}

	public static void resetProcesses() {
		for(Process p : input) {
			p.currentTimeRem = p.totalServiceTime;
			p.realTime =0;
		}
	}

	public static void fifo() throws IOException {
		for(int i=0; i<maxTime; i++) {
			boolean occupied = false;
			for(Process p: input) {
				//if arrived and yet to complete, take into consideration
				if((p.arrivalTime <= i) && (p.currentTimeRem != 0)) {
					//if this iteration can take in a process slot
					if(occupied == false) {
						p.currentTimeRem--;
						occupied = true;
					}
					p.realTime++;
				}
			}
		}
		computeTT();
		output = stringOutput();
		writeToFile();
		resetProcesses();
	}

	public static void sjf() throws IOException {
		//Sort according to shortest job first
		for(int i=0; i<maxTime; i++) {
			int workingOnIndex = -1;

			//if hasn't found a shortest process to work on
			if(workingOnIndex == -1) {
				int smallest = Integer.MAX_VALUE;
				//Iterate through Processes to find a suitable process
				for(int index=0; index<input.size(); index++) {
					if(input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
						if(input.get(index).totalServiceTime < smallest) {
							workingOnIndex = index;
							smallest = input.get(index).totalServiceTime;
						}
					}
				}

				if(workingOnIndex == -1) {
					continue;
				}

				//Iterate through, compute real time
				for(int index=0; index<input.size(); index++) {
					if(index == workingOnIndex) {
						input.get(index).currentTimeRem--;
						input.get(index).realTime++;
					} else {
						if(input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
							input.get(index).realTime++;
						}
					}
				}
			} else {
				//Iterate through, compute real time
				for(int index=0; index<input.size(); index++) {
					if(index == workingOnIndex) {
						input.get(index).currentTimeRem--;
						input.get(index).realTime++;
					} else {
						if(input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
							input.get(index).realTime++;
						}
					}
				}

			}

			//Check if this selected process has been done
			if(input.get(workingOnIndex).currentTimeRem == 0) {
				workingOnIndex = -1;
			}
		}
		computeTT();
		output = stringOutput();
		writeToFile();
		resetProcesses();
	}

	public static void srt() throws IOException {


		for(int i=0; i<maxTime; i++) {
			int workingOnIndex = -1;

			//find process with shortest remaining time
			int smallest = Integer.MAX_VALUE;
			for(int index=0; index<input.size(); index++) {
				if(input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
					if(input.get(index).currentTimeRem < smallest) {
						workingOnIndex = index;
						smallest = input.get(index).currentTimeRem;
					}
				}
			}

			if(workingOnIndex == -1) {
				continue;

			} else {

				//Iterate through, compute real time
				for(int index=0; index<input.size(); index++) {
					if(index == workingOnIndex) {
						input.get(index).currentTimeRem--;
						input.get(index).realTime++;
					} else {
						if(input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
							input.get(index).realTime++;
						}
					}
				}

			}

		}
		computeTT();
		output = stringOutput();
		writeToFile();
		resetProcesses();	
	}

	public static void mlf() throws IOException {

		for(int i=0; i<maxTime; i++) {
			int workingOnIndex = -1;

			//Find the most suitable process based on priority
			boolean isFound = false;
			for(int n=5; n>=1; n--) {
				for(int index=0; index<input.size(); index++) {
					if(input.get(index).n == n && input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
						workingOnIndex = index;
						//System.out.println(workingOnIndex);
						isFound = true;
						break;
					}
				}
				if(isFound == true) {
					break;
				}
			}
			if(workingOnIndex == -1) {
				continue;

			} else {
				//Iterate through, compute real time
				for(int index=0; index<input.size(); index++) {
					if(index == workingOnIndex) {
						input.get(index).currentTimeRem--;
						input.get(index).realTime++;
						input.get(index).T--;

						if(input.get(index).T == 0) {
							input.get(index).n--;
							input.get(index).T = (int) Math.pow(2, 5-(input.get(index).n));	
						}

					} else {
						if(input.get(index).arrivalTime <= i && input.get(index).currentTimeRem != 0) {
							input.get(index).realTime++;
						}
					}
				}
			}
		}		
		computeTT();
		output = stringOutput();
		writeToFile();
		resetProcesses();	
	}
}


//Each Process object
class Process {

	public int arrivalTime;
	public int totalServiceTime;
	public int currentTimeRem;
	public int realTime;

	public int n=5;
	public int T=1;

	public Process(int arrivalTime, int totalServiceTime) {
		this.arrivalTime = arrivalTime;
		this.totalServiceTime = totalServiceTime;
		this.currentTimeRem = totalServiceTime;
		this.realTime = 0;
	}

	public String toString() {
		return "(" + arrivalTime + ", " + totalServiceTime + ", " + currentTimeRem + ")";
	}

}
