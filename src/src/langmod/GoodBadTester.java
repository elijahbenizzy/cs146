package langmod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class GoodBadTester {
	
	public static double testFile(String filePath, Analyzer analyzer) throws IOException {
		int numPasses = 0;
		int numFails = 0;
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		while(true) {
			Vector<String[]> lineGood;
			Vector<String[]> lineBad;
			String line1 = in.readLine();
			String line2 = in.readLine();
			if (line1==null || line2 == null) {
				break;
			}
			(lineGood = new Vector<String[]>()).add(line1.split(" "));
			(lineBad = new Vector<String[]>()).add(line2.split(" "));	
			double lineGoodProb = analyzer.getLogProbability(lineGood);
			double lineBadProb = analyzer.getLogProbability(lineBad);
			if(lineGoodProb > lineBadProb) {
				numPasses += 1;
			} else {
				numFails +=1;
			}
		}
	in.close();
	return ((double)numPasses)/((double)numPasses+(double)numFails);
	
	}
}
