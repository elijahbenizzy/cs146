package langmod;

import util.Constants;
import util.Corpus;
import util.LineProcessor;

public class UnigramLineProcessor extends FrequencyLineProcessor { 
	public UnigramLineProcessor(Corpus corpus) {
		super(corpus);
	}
	public void processLine(String line) {
		super.processLine(line);
		_corpus.addUnigram(Constants.STOP_TOKEN); //start and stop
		for(String s: _currentLine) {
			_corpus.addUnigram(s);
		}
		_corpus.addUnigram(Constants.STOP_TOKEN);
		
	}

}
