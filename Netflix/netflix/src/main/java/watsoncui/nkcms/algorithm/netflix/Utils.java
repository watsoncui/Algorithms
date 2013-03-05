package watsoncui.nkcms.algorithm.netflix;

import java.io.File;

public class Utils {

	public static File[] getFiles(String dirName) {
		File dir = new File(dirName);
		return dir.listFiles();
	}
}
