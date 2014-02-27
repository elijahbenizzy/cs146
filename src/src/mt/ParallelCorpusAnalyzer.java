package mt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import util.DefaultDict;

public class ParallelCorpusAnalyzer {
	private ParallelCorpus _corpus;
	private StringTupleMap _taus;
	private double _initialTauValue;
	public static final int NUM_STEPS = 10;
	private HashMap<String,String> _mostLikelyTranslations; //map of the argmaxes, from french to english
	public ParallelCorpusAnalyzer(ParallelCorpus corpus) {
		_corpus = corpus;
		_initialTauValue = this.getInitialTauValue();
		_taus = new MapOfMaps(_initialTauValue);
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
//	private static void initializeWordPairMap(HashMap<WordPair,Double> map, Set<String> set1, Set<String> set2,double value) {
//		for(String token1: set1) {
//			for(String token2: set2) {
//				WordPair toAdd = new WordPair(token1,token2);
//				map.put(toAdd, value);
//			}
//		}
//	}
//	private void initialIteration() { //performs an initial iteration of the EM algorithm, setting all the taus to a first, equal value
//		ParallelCorpusAnalyzer.initializeWordPairMap(_taus,_corpus.getLang1Tokens(),_corpus.getLang2Tokens(),_initialTauValue);
//	}
	private StringTupleMap EStep(int iternum) { //returns partial count map
		StringTupleMap n_e_f = new MapOfMaps(0.0);
		long prevTime = System.currentTimeMillis();
		int pairsProcessed = 0;
		System.out.println(_corpus.getLineArrayLang1().size());
		for(int pos = 0; pos< _corpus.getLineArrayLang1().size();pos++) {
			String[] sentence1 = _corpus.getLineArrayLang1().elementAt(pos);
			String[] sentence2 = _corpus.getLineArrayLang2().elementAt(pos);
			for(int j = 0;j<sentence1.length;j++) { //for each french word position k = 1,..m, do
				double p_k = 0;
				
				for(int k = 0; k <sentence2.length;k++) { //getting p_k
					pairsProcessed++;
					double tauValue = this.getTau(sentence1[j],sentence2[k]);
					p_k+=tauValue; // Set pk =Plj=0 τej,fk, where j are the positions of the English words in the same sentence pair as fk
				}
				for(int k = 0; k < sentence2.length;k++) {  //incrementing n_k
					String word1 = sentence1[j];
					String word2 = sentence2[k];
					double tauValue = _taus.get(word1,word2);
					n_e_f.put(word1,word2,n_e_f.get(word1,word2)+tauValue/p_k);
				}
			}
			if(pos %100 == 0) {
				long t2 = System.currentTimeMillis();
				double timeTaken = (t2-prevTime);
//				System.out.println("================");
//				System.out.println("position #" + pos + " took " + timeTaken + " milliseconds to process " + pairsProcessed + "pairs");
////				System.out.println("size of n_e_f is " + n_e_f.size());
//				System.out.println("total memory is:" + Runtime.getRuntime().totalMemory());
//				System.out.println("total free memory is" + Runtime.getRuntime().freeMemory());
//				System.out.println("total possible size is:" + _corpus.getLang1Tokens().size()*_corpus.getLang2Tokens().size());
//				System.out.println("Iteration number is: " + iternum);
//				System.out.println("================");
				pairsProcessed = 0;
				prevTime = t2;
			}
		}
		return n_e_f;
	}
	//TODO - filter taus by a certain threshold?
	private void filterTaus() {
		
	}
	private void MStep(StringTupleMap n_e_f) {
		DefaultDict<String,Double> n_e_0 = new DefaultDict<String,Double>(0.0);
		
		for(WordPair pair: n_e_f.keySet()) {
				String word1 = pair.token1;
				String word2 = pair.token2;
				n_e_0.put(word1, n_e_0.get(word1) + this.getTau(word1,word2));

			}
//		for(String s1: _corpus.getLang1Tokens()) { //initializing n_e_0
//			n_e_0.put(s1,0.0);
//			for(String s2: _corpus.getLang2Tokens()) {
//				n_e_0.put(s1, n_e_0.get(s1) + this.getTau(s1,s2));
//			}
//		}
		for(WordPair pair: n_e_f.keySet()) {
			String word1 = pair.token1;
			String word2 = pair.token2;
			double tauValue = n_e_f.get(word1,word2)/n_e_0.get(word1);
			this.setTau(word1, word2, tauValue);
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
		StringTupleMap estepValue = this.EStep(iternum);
		System.out.println("completed e-step");
		this.MStep(estepValue);
		System.out.println("completed M step");
		this.filterTaus();
		
	}

	private void expectationMaximizer() {
		for(int i = 0;i<NUM_STEPS;i++) {
			this.EMIteration(i);
		}
	}
	
	
	private void findTranslations() {
		DefaultDict<String,Double> _maximumTau = new DefaultDict<String,Double>(0.0);
		for(WordPair pair: _taus.keySet()) {
			String word1 = pair.token1;
			String word2 = pair.token2;
				if(_maximumTau.get(word2) < _taus.get(word1,word2)) {
					_maximumTau.put(word2, _taus.get(word1,word2));
					_mostLikelyTranslations.put(word2,word1);
				}
			}
		}
	
	public void runModel() {
		this.expectationMaximizer();
		this.findTranslations();
//		for(WordPair w: _taus.keySet()) {
//			System.out.println(w + " <-----> " + _taus.get(w.token1,w.token2));
//		}
		for(Entry<String,String> e: _mostLikelyTranslations.entrySet()) {
			System.out.println(e.getKey() + " <-----> " + e.getValue());
		}
	}
	public String getMostLikelyWord(String s) {
		return _mostLikelyTranslations.get(s);
		
	}
}
