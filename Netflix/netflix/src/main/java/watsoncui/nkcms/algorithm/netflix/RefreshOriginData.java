package watsoncui.nkcms.algorithm.netflix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefreshOriginData {
	
	
	
	public static void getProbeSet() {
		
		try {
			BufferedReader probe = null;
			probe = new BufferedReader(new FileReader(new File(Constant.DIR + "probe.txt")));
			if(null != probe) {
				String line = null;
				String last = probe.readLine();
				List<String> strList = new ArrayList<String>();
				Map<String, List<String>> movieMap = new HashMap<String, List<String>>();
				while(null != (line = probe.readLine())) {
					if(line.contains(":")) {
						movieMap.put(last, strList);
						strList = new ArrayList<String>();
						last = line;
					} else {
						strList.add(line);
					}
				}
				movieMap.put(last, strList);
				
				File[] files = Utils.getFiles(Constant.TRAININGSETDIR);
				BufferedReader br = null;
				PrintWriter pwProbe = null;
				PrintWriter pwTraining = null;
				for(File file:files) {
					String fileName = file.getName();
					System.out.println(fileName);
					br = new BufferedReader(new FileReader(file));
					pwProbe = new PrintWriter(new File(Constant.PROBESETDIR + fileName));
					pwTraining = new PrintWriter(new File(Constant.PURETRAININGSETDIR + fileName));
					String tempLine = br.readLine();
					System.out.println(tempLine);
					List<String> probes = movieMap.get(tempLine);
					if(null != probes) {
						System.out.println(probes.size());
						while(null != (tempLine = br.readLine())) {
							boolean isProbe = false;
							for(String str:probes) {
								if(tempLine.subSequence(0, tempLine.indexOf(',')).equals(str)) {
									isProbe = true;
									break;
								}
							}
							if(isProbe) {
								System.out.println(tempLine);
								pwProbe.println(tempLine);
							} else {
								pwTraining.println(tempLine);
							}
						}
					} else {
						while(null != (tempLine = br.readLine())) {
								pwTraining.println(tempLine);
						}
					}
					
					pwProbe.flush();
					pwTraining.flush();
					pwProbe.close();
					pwTraining.close();
				}	
				br.close();
				probe.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		getProbeSet();
		
	}

}
