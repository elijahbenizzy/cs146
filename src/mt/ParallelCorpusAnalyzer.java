package mt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import langmod.BigramAnalyzer;
import util.Constants;
import util.DefaultDict;

public class ParallelCorpusAnalyzer {
	private ParallelCorpus _corpus;
	protected TupleMap<String,Double> _taus;
	private double _initialTauValue;
	private double _previousLikelihood;
	private double _currentLikelihood;
	private TupleMap<String,String> _cachedTranslations; //stores the cached translations given a preveious englishword and a french word
	private BigramAnalyzer _bigramModel;
	
	private HashMap<String,String> _mostLikelyTranslations; //map of the argmaxes, from french to english
	public ParallelCorpusAnalyzer(ParallelCorpus corpus) {
		_previousLikelihood = 0;
		_currentLikelihood = 0;
		_corpus = corpus;
		_initialTauValue = this.getInitialTauValue();
		_taus = new MapOfMaps<String,Double>(_initialTauValue);
		_mostLikelyTranslations = new HashMap<String,String>();
	}

	
	private double getInitialTauValue() {
		return 1.0;
	}
	
	public double getTau(String s1, String s2) {
		
		return _taus.get(s1,s2);
	}
	
	public void setTau(String s1, String s2, double value) {
		_taus.put(s1,s2,value);
	}
	
	private TupleMap<String,Double> EStep(int iternum,boolean reversed) { //returns partial count map
		_previousLikelihood = _currentLikelihood;
		_currentLikelihood = 0;
		TupleMap<String,Double> n_e_f = new MapOfMaps<String,Double>(0.0);
		for(int pos = 0; pos< _corpus.getLineArrayLang1().size();pos++) {
			String[] frenchSentence;
			String[] englishSentence;
			
			if(reversed) {
				frenchSentence = _corpus.getLineArrayLang2().elementAt(pos);
				englishSentence = _corpus.getLineArrayLang1().elementAt(pos);
			} else {
				englishSentence = _corpus.getLineArrayLang1().elementAt(pos); //french
				frenchSentence = _corpus.getLineArrayLang2().elementAt(pos);
			}
				
		
			for(int k = 0;k<frenchSentence.length;k++) { //for each french word position k = 1,..m, do
				
				double p_k = 0;
				
				
				for(int j = 0; j <englishSentence.length;j++) { //getting p_k
//					pairsProcessed++;
					double tauValue = this.getTau(englishSentence[j],frenchSentence[k]);
					p_k+=tauValue; 
				}
				
				for(int j = 0; j < englishSentence.length;j++) {  //incrementing n_k
					String french = frenchSentence[k];
					String english = englishSentence[j];
					double tauValue = _taus.get(english,french);
					n_e_f.put(english,french,n_e_f.get(english,french)+tauValue/p_k);
				}
			}

		}
		return n_e_f;
	}
	private void MStep(TupleMap<String,Double> n_e_f) {
		DefaultDict<String,Double> n_e_0 = new DefaultDict<String,Double>(0.0);
		
		for(Tuple<String> pair: n_e_f.keySet()) {
				String english = pair.token1;
				String french = pair.token2;
				n_e_0.put(english, n_e_0.get(english) + this.getTau(english,french));

			}
		for(Tuple<String> pair: n_e_f.keySet()) {
			String english = pair.token1;
			String french = pair.token2;
			double tauValue = n_e_f.get(english,french)/n_e_0.get(english);
			_currentLikelihood += Math.log(tauValue);
			this.setTau(english, french, tauValue);
		}
	}
	private void EMIteration(int iternum) {
		this.MStep(this.EStep(iternum,false));
		System.gc();
		
		
	}

	protected void expectationMaximizer() {
		int i = 0;
		while(i<Constants.NUM_STEPS) {
			this.EMIteration(i++);
		}
	}
	
	
	protected void findTranslations() {
		DefaultDict<String,Double> _maximumTau = new DefaultDict<String,Double>(0.0);
		for(Tuple<String> pair: _taus.keySet()) {
			String english = pair.token1;
			String french = pair.token2;
				if(_maximumTau.get(english) < _taus.get(english,french)) {
					_maximumTau.put(english, _taus.get(english,french));
					_mostLikelyTranslations.put(english,french);
				}
			}
		}
	
	
	public void runModel() {
		this.expectationMaximizer();
		this.findTranslations();
	}
	
	public String getMostLikelyWord(String s) {
		return _mostLikelyTranslations.get(s);
	}
	public String getBestTranslation(String prevEnglish, String frenchWord) { 
		String translation = _cachedTranslations.get(prevEnglish, frenchWord);
		if( translation == null) {
			double currentBest = 0;
			for(String englishWord: _taus.getSecondElements(frenchWord)) {
				double EGivenE = _bigramModel.getTheta(prevEnglish,englishWord);
				double FGivenE = _taus.get(frenchWord,englishWord);
				double product = FGivenE*EGivenE;
				if(product > currentBest) {
					translation = englishWord;
					currentBest = product;
				}
				
				
			}
		}
		_cachedTranslations.put(prevEnglish, frenchWord, translation);
		return translation;
	}
}
