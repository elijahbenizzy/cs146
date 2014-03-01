package util;

import java.io.IOException;

public class WordCounter implements LineProcessor{
	private int _count;
	
	public WordCounter() {
		_count = 0;
	}

	@Override
	public void processLine(String line) {
		_count += line.split(" ").length;
	}
	
	public int getCount() {
		return _count;
	}
	public static int countWords(String filename) throws IOException {
		WordCounter counter = new WordCounter();
		FileProcessor.processByLine(filename, counter);
		return counter.getCount();
	}

}
