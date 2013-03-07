package watsoncui.nkcms.algorithm.netflix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BasicStatistics {

	public static void getSomeStat(String sourceDir, String target) throws IOException {
		File[] files = Utils.getFiles(sourceDir);
		if(null != files) {
			BufferedReader br = null;
			PrintWriter pw = new PrintWriter(new FileWriter(new File(target)));
			pw.println("@relation netflix_movie");
			pw.println();
			pw.println("@attribute id string");
			pw.println("@attribute mean numeric");
			pw.println("@attribute stdev numeric");
			pw.println("@attribute count numeric");
			pw.println();
			pw.println("@data");
			for(File file:files) {
				String fileName = file.getName();
				String line = null;
				List<Integer> scores = new ArrayList<Integer>();
				br = new BufferedReader(new FileReader(new File(sourceDir + fileName)));
				while(null != (line = br.readLine())) {
					String[] strs = line.split(",");
					scores.add(Integer.valueOf(strs[1]));
				}
				int count = scores.size();
				int sum = 0;
				double mean = 0;
				double dev = 0;
				double stdev = 0;
				for(Integer score:scores) {
					sum += score;
				}
				if(count > 0) {
					mean = ((double)sum)/count;
				}
				for(Integer score:scores) {
					dev += (mean - score) * (mean - score);
				}
				if(count > 1) {
					stdev = Math.sqrt(dev/(count - 1));
				}
				StringBuffer sb = new StringBuffer();
				sb.append(fileName).append(",").append(mean).append(",").append(stdev).append(",").append(count);
				pw.println(sb.toString());
			}
			pw.flush();
			br.close();
			pw.close();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			getSomeStat(Constant.TRAININGTIMELINE, Constant.DIR + "netflix.arff");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
