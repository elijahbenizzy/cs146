package mt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import util.DefaultDict;

public class ParallelCorpusAnalyzer {
	private ParallelCorpus _corpus;
	private TupleMap<String,Double> _taus;
	private double _initialTauValue;
	public static final int NUM_STEPS = 10;
	private HashMap<String,String> _mostLikelyTranslations; //map of the argmaxes, from french to english
	public ParallelCorpusAnalyzer(ParallelCorpus corpus) {
		_corpus = corpus;
		_initialTauValue = this.getInitialTauValue();
		_taus = new MapOfMaps<String,Double>(_initialTauValue);
		_mostLikelyTranslations = new HashMap<String,String>();
	}
	
	private double getInitialTauValue() {
		return 1.0/_corpus.getLang1Tokens().size();
	}
	
	public double getTau(String s1, String s2) {
		return _taus.get(s1,s2);
	}
	
	public void setTau(String s1, String s2, double value) {
		_taus.put(s1,s2,value);
	}
	
	private TupleMap<String,Double> EStep(int iternum,boolean reversed) { //returns partial count map
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
	private void filterTaus() {
		
	}
	private void MStep(TupleMap<String,Double> n_e_f) {
		DefaultDict<String,Double> n_e_0 = new DefaultDict<String,Double>(0.0);
		
		for(Tuple<String> pair: n_e_f.keySet()) {
				String english = pair.token1;
				String french = pair.token2;
				n_e_0.put(english, n_e_0.get(english) + this.getTau(english,french));

			}
//		for(String s1: _corpus.getLang1Tokens()) { //initializing n_e_0
//			n_e_0.put(s1,0.0);
//			for(String s2: _corpus.getLang2Tokens()) {
//				n_e_0.put(s1, n_e_0.get(s1) + this.getTau(s1,s2));
//			}
//		}
		for(Tuple<String> pair: n_e_f.keySet()) {
			String english = pair.token1;
			String french = pair.token2;
			double tauValue = n_e_f.get(english,french)/n_e_0.get(english);
			this.setTau(english, french, tauValue);
		}
//		for(String s1: _corpus.getLang1Tokens()) {
//			for(String s2: _corpus.getLang2Tokens()) {
//				double tauValue = n_e_f.get(new WordPair(s1,s2))/n_e_0.get(s1);
//				this.setTau(s1, s2, tauValue);
//			}
//		}
	}
	private void EMIteration(int iternum) {
		System.out.println("doing EM iteration number" + iternum);
		TupleMap<String,Double> estepValue = this.EStep(iternum,false);
		System.out.println("completed e-step");
		this.MStep(estepValue);
		System.out.println("completed M step");
		this.filterTaus();
		System.gc();
		
	}

	private void expectationMaximizer() {
		for(int i = 0;i<NUM_STEPS;i++) {
			this.EMIteration(i);
		}
	}
	
	
	private void findTranslations() {
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
}
