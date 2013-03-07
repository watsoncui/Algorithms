package watsoncui.nkcms.algorithm.netflix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ObserveMovieByTime {
	
	public static void timeline(String sourceDir, String targetDir) throws IOException {
		File[] files = Utils.getFiles(sourceDir);
		if(null != files) {
			
			BufferedReader br = null;
			PrintWriter pw = null;
			
			for(File file:files) {
				br = new BufferedReader(new FileReader(file));
				pw = new PrintWriter(new File(targetDir + file.getName()));
				String line = null;
				Map<Long, String> map = new TreeMap<Long, String>();
				int dateId = 1;
				while(null != (line = br.readLine())) {
					String[] strs = line.split(",");
					Date date = Date.valueOf(strs[2]);
					Long key = date.getTime();
					if(map.containsKey(date.getTime())) {
						key += dateId++;
					}
					map.put(key, line);
				
				}
				
				Set<Long> keySet = map.keySet();
				Iterator<Long> iter = keySet.iterator();
				while(iter.hasNext()) {
					Long key = iter.next();
					pw.println(map.get(key));
				}
				
				pw.flush();
				br.close();
				pw.close();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		try {
//			timeline(Constant.PROBESETDIR, Constant.PROBETIMELINE);
//			timeline(Constant.PURETRAININGSETDIR, Constant.TRAININGTIMELINE);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
