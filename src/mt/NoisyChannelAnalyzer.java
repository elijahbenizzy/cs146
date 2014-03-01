package mt;

import java.io.IOException;
import java.util.HashMap;

import util.Constants;
import langmod.BigramAnalyzer;

public class NoisyChannelAnalyzer extends ParallelCorpusAnalyzer {
	private TupleMap<String,String> _cachedTranslations; //stores the cached translations given a preveious englishword and a french word
	private BigramAnalyzer _bigramModel;
	
	public NoisyChannelAnalyzer(ParallelCorpus corpus) throws IOException {
		super(corpus);
		_bigramModel = new BigramAnalyzer(corpus.getLang1Data(),Constants.ALPHA,Constants.BETA);
		_cachedTranslations = new MapOfMaps<String,String>(null);
	}
	@Override
	public void runModel() {
		super.expectationMaximizer();
		this.filterTaus();
		_taus.reverse();
	}
	
	private void filterTaus() {
		for(Tuple<String> pair: _taus.keySet()) {
			if (_taus.get(pair.token1, pair.token2) < Constants.EPSILON) {
				_taus.remove(pair.token1,pair.token2);
			}
		}
	}

	public String getBestTranslation(String prevEnglish, String frenchWord) { 
		String translation = _cachedTranslations.get(prevEnglish, frenchWord);
		if( translation == null) {
			double currentBest = Double.MIN_VALUE;
			for(String englishWord: _taus.getSecondElements(frenchWord)) {
				
				if(englishWord.equals(Constants.NO_TRANSLATION)) {
					continue;
				}
				
				double EGivenE = _bigramModel.getTheta(prevEnglish,englishWord);
				double FGivenE = _taus.get(frenchWord,englishWord);
				double product = FGivenE*EGivenE;
				if(product > currentBest) {
					translation = englishWord;
					currentBest = product;
				}
				
				
			}
		}
		translation = translation == null ? frenchWord : translation;
		_cachedTranslations.put(prevEnglish, frenchWord, translation);
		return translation;
		
		// TODO - 1) for the previous english word, loop through every english word, and select the one with maximum bigramProb(previousEnglish, word) * tau(french,english) 
		// TODO - 2) cache the result, return it
	}
	
	

}
