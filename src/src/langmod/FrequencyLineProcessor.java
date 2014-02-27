package langmod;

import util.Corpus;
import util.LineProcessor;

public class FrequencyLineProcessor implements LineProcessor{
	protected Corpus _corpus;
	protected String[] _currentLine;
	public FrequencyLineProcessor(Corpus corpus) {
		_corpus = corpus;
	}
	@Override
	public void processLine(String line) {
		_currentLine = line.split(" ");
	}
}
