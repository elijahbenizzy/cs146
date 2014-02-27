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
	public static final int NUM_STEPS = 13;
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
	
	private boolean converged() {
		return (_previousLikelihood != 0) && Math.abs(_currentLikelihood-_previousLikelihood) < Math.log(Constants.CONVERGANCE_THRESHOLD);
	}
	
	private double getInitialTauValue() {
		System.out.println(1.0/_corpus.getLang1Tokens().size());
		return 1.0/_corpus.getLang2Tokens().size();
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
		
//		long prevTime = System.currentTimeMillis();
//		int pairsProcessed = 0;
//		System.out.println(_corpus.getLineArrayLang1().size());
		
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
					p_k+=tauValue; // Set pk =Plj=0 τej,fk, where j are the positions of the English words in the same sentence pair as fk
				}
				
				for(int j = 0; j < englishSentence.length;j++) {  //incrementing n_k
					String french = frenchSentence[k];
					String english = englishSentence[j];
					double tauValue = _taus.get(english,french);
					n_e_f.put(english,french,n_e_f.get(english,french)+tauValue/p_k);
				}
			}
//			if(pos %100 == 0) {
//				long t2 = System.currentTimeMillis();
//				double timeTaken = (t2-prevTime);
//				System.out.println("================");
//				System.out.println("position #" + pos + " took " + timeTaken + " milliseconds to process " + pairsProcessed + "pairs");
//				System.out.println("total used memory is:" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1000000 + "m");
//				System.out.println("total possible size is:" + _corpus.getLang1Tokens().size()*_corpus.getLang2Tokens().size());
//				System.out.println("Iteration number is: " + iternum);
//				System.out.println("================");
//				pairsProcessed = 0;
//				prevTime = t2;
//			}
		}
		return n_e_f;
	}
	
		
	//TODO - filter taus by a certain threshold?
//	protected void filterTaus() {
//		for(Tuple<String> key: _taus.keySet()) {
//			
//		}
//	}
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
		System.out.println("doing EM iteration number" + iternum);
		TupleMap<String,Double> estepValue = this.EStep(iternum,false);
		System.out.println("completed e-step");
		this.MStep(estepValue);
		System.out.println("completed M step");
		System.gc();
		
		
	}

	protected void expectationMaximizer() {
		int i = 0;
		while(i<Constants.NUM_STEPS) {
			System.out.println("Previous likelihood was " + _previousLikelihood);
			System.out.println("Likelihood is " + _currentLikelihood);
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
//		for(WordPair w: _taus.keySet()) {
//			System.out.println(w + " <-----> " + _taus.get(w.token1,w.token2));
//		}
//		for(Entry<String,String> e: _mostLikelyTranslations.entrySet()) {
//			System.out.println(e.getKey() + " <-----> " + e.getValue());
//		}
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
		
		// TODO - 1) for the previous english word, loop through every english word, and select the one with maximum bigramProb(previousEnglish, word) * tau(french,english) 
		// TODO - 2) cache the result, return it
	}
}
