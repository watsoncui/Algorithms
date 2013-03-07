package watsoncui.nkcms.algorithm.netflix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GlobalEffect {
	private Map<String, Integer> userCount;
	private Map<String, Double> userScoreResidue;
	private Map<String, Double> userParam;
	private Map<String, Integer> movieCount;
	private Map<String, Integer> movieScore;
	private Map<String, Double> movieParam;
	private File[] trainingFiles;
	private File[] probeFiles;
	private double overallMean;
	
	public GlobalEffect() {
		userCount = new HashMap<String, Integer>();
		userScoreResidue = new HashMap<String, Double>();
		userParam = new HashMap<String, Double>();
		movieCount = new HashMap<String, Integer>();
		movieScore = new HashMap<String, Integer>();
		movieParam = new HashMap<String, Double>();
		trainingFiles = Utils.getFiles(Constant.TRAININGTIMELINE);
		probeFiles = Utils.getFiles(Constant.PROBETIMELINE);
		overallMean = 0;
		try {
			theFirstStep();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void clearMaps() {
		userCount.clear();
		userScoreResidue.clear();
		userParam.clear();
		movieCount.clear();
		movieScore.clear();
		movieParam.clear();
	}
	
 	private void theFirstStep() throws IOException {
 		clearMaps();
		int overall = 0;
		int count = 0;
		if(null != trainingFiles) {
			BufferedReader br = null;
			
			for(File file:trainingFiles) {
				br = new BufferedReader(new FileReader(file));
				String fileName = file.getName();
				String line = null;
				int PmCount = 0;
				int PmScore = 0;
				while(null != (line = br.readLine())) {
					String[] strs = line.split(",");
					int score = Integer.valueOf(strs[1]);
					overall += score;
					PmScore += score;
					count++;
					PmCount++;
				}
				br.close();
				movieCount.put(fileName, PmCount);
				movieScore.put(fileName, PmScore); 
			}
			
			if(count != 0) {
				overallMean = ((double)overall)/count;
			}
			
			Set<String> movieKeySet = movieCount.keySet();
			Iterator<String> movieIter = movieKeySet.iterator();
			while(movieIter.hasNext()) {
				String key = movieIter.next();
				int PmCount = movieCount.get(key);
				int PmScore = movieScore.get(key);
				movieParam.put(key, (((double)PmScore) - PmCount * overallMean) / (PmCount + Constant.MovieAlpha));
			}
		}
	}
	
	private void theUserStep() throws IOException {
		if(null != trainingFiles) {
			BufferedReader br = null;
			for(File file:trainingFiles) {
				br = new BufferedReader(new FileReader(file));
				String fileName = file.getName();
				String line = null;
				while(null != (line = br.readLine())) {
					String[] strs = line.split(",");
					int score = Integer.valueOf(strs[1]);
					if(userCount.containsKey(strs[0])) {
						userCount.put(strs[0], userCount.get(strs[0]) + 1);
					} else {
						userCount.put(strs[0], 1);
					}
					double residue = score - overallMean - movieParam.get(fileName);
					if(userScoreResidue.containsKey(strs[0])) {
						userScoreResidue.put(strs[0], userScoreResidue.get(strs[0]) + residue);
					} else {
						userScoreResidue.put(strs[0], residue);
					}
				}
				br.close();
			}
			
			Set<String> userKeySet = userCount.keySet();
			Iterator<String> userIter = userKeySet.iterator();
			while(userIter.hasNext()) {
				String key = userIter.next();
				userParam.put(key, userScoreResidue.get(key)/(userCount.get(key) + Constant.UserAlpha));
			}
		}
	}
	
	private double theLastStep(int step) throws IOException {

		double squaredEpsilon = 0.0;
		double RMSE = 0;
		int probeCount = 0;
		if(null != probeFiles) {
			BufferedReader br = null;
			for(File file:probeFiles) {
				br = new BufferedReader(new FileReader(file));
				String fileName = file.getName();
				String line = null;
				while(null != (line = br.readLine())) {
					String[] strs = line.split(",");
					int score = Integer.valueOf(strs[1]);
					double epsilon;
					switch (step) {
					case 1:
						epsilon = score - overallMean - movieParam.get(fileName);
						break;
					case 2:
						epsilon = score - overallMean - movieParam.get(fileName) - userParam.get(strs[0]);
						break;
					default:
						epsilon = score - overallMean;
					}
					squaredEpsilon += epsilon * epsilon;
					probeCount++;
				}
				br.close();
			}
		}
		
		if(probeCount != 0) {
			RMSE = Math.sqrt(squaredEpsilon/probeCount);
		}
		return RMSE;
	}
	
	public double userAfterMovie() {
		try {
			theUserStep();
			return theLastStep(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Double.NaN;
		}
	}
	
	public double movie() {
		try {
			return theLastStep(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Double.NaN;
		}
	}
	
	public double overallMean() {
		try {
			return theLastStep(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Double.NaN;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GlobalEffect ge = new GlobalEffect();
		System.out.println(ge.overallMean());
		System.out.println(ge.movie());
		System.out.println(ge.userAfterMovie());
	}
}