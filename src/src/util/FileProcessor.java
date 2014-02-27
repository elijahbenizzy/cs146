package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
	public FileProcessor() {
		
	}
	
	public static void processByLine(String filepath, LineProcessor... processors) throws IOException {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line;
			while((line = reader.readLine()) != null) {
//				System.out.println(line);
				for (LineProcessor processor: processors) {
					processor.processLine(line);
//					System.out.println(t2-t1);
				}
				
			}
			reader.close();
	}
}
