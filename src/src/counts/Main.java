package counts;

import java.io.IOException;

import util.FileProcessor;

public class Main {
	public static void main(String[] argv) {
		try {
			String fileSource = argv[0];
			String outputFile = argv[1];
			FileOutputter outputter = new FileOutputter();
			FileProcessor processor = new FileProcessor();
			WordCounter counter = new WordCounter();
			processor.processByLine(fileSource, counter);
			outputter.writeToFile(outputFile, counter.getOutput());
			System.out.println("got here");
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
