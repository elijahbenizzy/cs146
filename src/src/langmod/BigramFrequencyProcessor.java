package langmod;

import util.BigramCorpus;
import util.Constants;
import util.Corpus;
import util.LineProcessor;

public class BigramFrequencyProcessor extends FrequencyLineProcessor {
	private BigramCorpus _corpus;
	public BigramFrequencyProcessor(BigramCorpus corpus) {
		super(corpus);
		_corpus = corpus;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processLine(String line) {
		super.processLine(line);

		_corpus.addBigram(Constants.STOP_TOKEN,super._currentLine[0]);
		for (int i = 0;i < _currentLine.length-1; i++) { //if the strings, s aren't registered as tokens then that blows
			String current = _currentLine[i];
			String next = _currentLine[i+1];
			_corpus.addBigram(current,next);
		}
		_corpus.addBigram(_currentLine[_currentLine.length-1],Constants.STOP_TOKEN);
		// TODO Auto-generated method stub
		
	}
	

}
