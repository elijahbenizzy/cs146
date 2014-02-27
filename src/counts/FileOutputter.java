package counts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputter {
	public FileOutputter() {
		
	}
	
	public void writeToFile(String filePath, String toWrite) throws IOException {
		FileWriter writer = new FileWriter(new File(filePath));
		writer.write(toWrite);
		writer.close();
	}

}
