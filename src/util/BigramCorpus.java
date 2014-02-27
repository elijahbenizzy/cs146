package util;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

import langmod.BigramFrequencyProcessor;

public class BigramCorpus extends Corpus {
	private Hashtable<Bigram,Integer> _bigramFrequencies;
	private int _totalBigrams;

	public BigramCorpus(String sourceFile) throws IOException {
		super(sourceFile);
		_bigramFrequencies = new Hashtable<Bigram,Integer>();
		_totalBigrams = 0;
		FileProcessor.processByLine(sourceFile, new BigramFrequencyProcessor(this));
		
		
		// TODO Auto-generated constructor stub
	}
	
	public void addBigram(String token1, String token2) {
		_totalBigrams++;
		Bigram toAdd = new Bigram(token1,token2);
		if (this.getBigramFrequency(toAdd) != 0) {
			_bigramFrequencies.put(toAdd, _bigramFrequencies.get(toAdd)+1);
		} else {
			_bigramFrequencies.put(toAdd, 1);
			
		}
	}
	public int getBigramFrequency(String s1, String s2) {
		return this.getBigramFrequency(new Bigram(s1,s2));
	}
	
	public int getBigramFrequency(Bigram toGet) {
		if (_bigramFrequencies.containsKey(toGet)) 
			return _bigramFrequencies.get(toGet);
		else {
			return 0;
		}
		
	}
	public Hashtable<Bigram, Integer> getBigramFrequencies() {
		return _bigramFrequencies;
	}
	
	public int totalBigramNumber() {
		return _totalBigrams;
	}
	
	public Set<Bigram> getBigrams() {
		return _bigramFrequencies.keySet();
	}
	
	public int firstTokenFrequency(String w) {
		if(w.equals(Constants.STOP_TOKEN)) //gives the frequency of n_w_o, which is the same as n_w, except that that n_w_0(STOP) equals n_w(STOP)-1, because the last stop can't be the first word in a bigram 
			return super.getFrequency(w)-1;
		else
			return super.getFrequency(w);
	}

}
