/**
 * @(#)StatsPackage.java
 *
 *
 * @author
 * @version 1.00 2014/1/26
*/
import java.util.*;
import java.io.*;
public class StatsPackage {
	public static ArrayList<Double> DATA;
	public static String ask(String q, Scanner in){
		System.out.print(q);
		return in.nextLine().toLowerCase();
	}
	public static void help(){
		String[] commands = {
			"help", "exit", "load", "print", "list", "clear", "mean", "median", "min", "max", "mode", "percentile", "Q1", "Q2", "Q3", "range", "sample_variance", "variance", "sample_strdev", "strdev",
			"frequency", "rel_frequency"
			};
		String[] desc = {
			"this list",
			"quits the program",
			"loads the data file",
			"pints the data",
			"prints the data in list form",
			"clears the data",
			"finds the average of the data",
			"finds the middle value of the data",
			"finds the smallest value of the data",
			"finds the largest value of the data",
			"finds the mode of the data set",
			"finds the given percentile",
			"finds the 1st quartile",
			"finds the 2nd quartile",
			"finds the 3rd quartile",
			"finds the range on the data",
			"calculates the sample variance",
			"calculates the variance on the data",
			"calculates the sample standard deviation",
			"calculates standard deviation",
			"displays the frequency of each item in the data set",
			"displays the relative frequency of each item in the data set"
		};
		for(int i=0; i<commands.length; i++){
			System.out.print(commands[i]);
			System.out.println(" - " + desc[i]);
		}
	}
	public static ArrayList<Double> load(String filename){
		File f = new File(filename);
		ArrayList list = new ArrayList();
		try{
			Scanner in = new Scanner(f);
			while(in.hasNextDouble()){
				list.add(in.nextDouble());
			}
			System.out.println("Loaded " + list.size() + " numbers.");
			in.close();
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		Collections.sort(list);
		return list;
	}
	public static double percentile(double p){
		double ans = 0.0;
		p = p / (double)100.0;
		double location = p * ((double)DATA.size() + 1);
		if(location == Math.floor(location)){ //whole number
			ans = DATA.get((int)location-1);
		}else{
			double value_1 = DATA.get((int)Math.floor(location) - 1);
			double value_2 = DATA.get((int)Math.ceil(location) - 1);
			ans = (value_1 + value_2) / (double)2.0;
		}
		return ans;
	}
	public static TreeMap<Double, Integer> count(){ //counts how many times a number occurs in the dataset.
		TreeMap<Double, Integer> count = new TreeMap<Double, Integer>();
		for(int i = 0; i<DATA.size(); i++){
			double item = DATA.get(i);
			if(!count.containsKey(item)){
				count.put(item, 1);
			}else{
				count.put(item, count.get(item) + 1); //+1 to value
			}
		}
		return count;
	}
	public static double var(boolean sample){ //variance
		double mean = 0.0;
		for(int i=0; i<DATA.size(); i++){
			mean += DATA.get(i);
		}
		mean = mean / (double)DATA.size();
		double prev = 0.0;
		double current = 0.0;
		for(int i=0; i<DATA.size(); i++){
			double a = DATA.get(i) - mean;
			current = Math.pow(a, 2) + prev;
			prev = current;
		}
		double size = (double)DATA.size();
		if(sample){
			size -= 1;
		}
		return current / size;
	}
	public static void tryCommand(String c, Scanner in){
		if(c.equals("load")){
			System.out.print("Enter Data File Name: " );
			String filename = in.nextLine();
			DATA = load(filename);
		}else if(c.equals("print")){
			if(!DATA.isEmpty()){
				System.out.println(DATA);
			}else{
				System.out.println("[]");
			}
		}else if(c.equals("list")){
			for(int i=0; i<DATA.size(); i++){
				System.out.println((i+1) + ":\t" + DATA.get(i));
			}
		}else if(c.equals("clear")){
			DATA.clear();
			System.out.println("Done.");
		}else if(c.equals("median")){
			boolean oddsize = DATA.size() % 2 == 1;
			if(oddsize){ //odd number size
				double size = DATA.size();
				double location = Math.floor(size / 2.0);

				System.out.println(DATA.get((int)location));
			}else if(!oddsize){
				double size = DATA.size();
				double location = Math.floor(size / 2);
				double a, b;
				a = DATA.get((int)location);
				b = DATA.get((int)location - 1);
				System.out.println("median (" +b + " & " + a + "): " + ((a+b)/2.0) );
			}
		}else if(c.equals("mean")){
			//add up all the numbers first.
			double total = 0.0;
			for(int i=0; i<DATA.size(); i++){
				total += DATA.get(i);
			}
			System.out.println("mean ("+total+"/"+(double)DATA.size()+"): " + Math.round((total/((double)DATA.size()) * 100))/  100d);
		}else if(c.equals("min")){
			System.out.println("min: " + DATA.get(0));
		}else if(c.equals("max")){
			System.out.println("max: " + DATA.get(DATA.size() - 1));
		}else if(c.equals("range")){
			System.out.println("range: " + (DATA.get(DATA.size() - 1) - DATA.get(0)));
		}else if(c.equals("percentile")){
			System.out.print("Enter Percentile as a whole number: ");
//			double p = (double)in.nextLine();
			double p = in.nextDouble();
			double ans = percentile(p);
			System.out.println(p + " percentile: " + ans);
		}else if(c.equals("q1")){
			System.out.println("Q1: " + percentile(25));
		}else if(c.equals("q2")){
			System.out.println("Q2: " + percentile(50));
		}else if(c.equals("q3")){
			System.out.println("Q3: " + percentile(75));
		}else if(c.equals("variance")){
			System.out.println("variance: " + var(false));
		}else if(c.equals("sample_variance")){
			System.out.println("sample variance: " + var(true));
		}else if(c.equals("strdev")){
			System.out.println("standard deviation: " + Math.sqrt(var(false)));
		}else if(c.equals("sample_strdev")){
			System.out.println("sample standard deviation: " + Math.sqrt(var(true)));
		}else if(c.equals("help")){
			help();
		}else if(c.equals("frequency")){
			System.out.println("Frequency of each item: ");
			TreeMap<Double, Integer> count = count();
			for(double key : count.keySet()){
				//System.out.println("For " + key + "\t: " + count.get(key) );
				System.out.format("%.3f\t\t", key);
				System.out.println(count.get(key));
			}
		}else if(c.equals("rel_frequency")){
			System.out.println("Relative Frequency of each item: ");
			TreeMap<Double, Integer> count = count();
			for(double key : count.keySet()){
				//System.out.println("For " + key + "\t\t: " + count.get(key) + "/" + DATA.size());
				System.out.format("%.3f\t\t", key);
				System.out.println(count.get(key) + "/" + DATA.size());

			}
		}else if(c.equals("mode")){
			TreeMap<Double, Integer> count = count();
			ArrayList<Double> modes = new ArrayList<Double>();
			int max = -1;
			for(double key : count.keySet()){ //finding the max occurce
				int value = count.get(key);
				if(value > max){
					max = value;
				}
			}

			for(final Map.Entry<Double, Integer> tuple : count.entrySet()) {
        		if (tuple.getValue() == max){
            		modes.add(tuple.getKey());
        		}
    		}
    		System.out.println("mode: ");
    		for(int i=0; i<modes.size(); i++){
    			System.out.println("\t"+modes.get(i));
    		}
    		System.out.println("occurs " + max + " times.");
		}
	}
    public static void main(String[] args) {
	    String input;
	    boolean end;
	    DATA = new ArrayList<Double>();
	    Scanner in = new Scanner(System.in);
	    System.out.println("Type help for a list of commands or exit to quit.");
	    do{
	    	input = ask("> ", in);
		    end = input.equals("exit");
	    	try{
		    	if(!end){
		    		tryCommand(input, in);
		    	}
	    	}catch(Exception e){
	    		System.out.println("Error: " + e.getMessage());
	    	}
	    }while(!end);
    }
}
