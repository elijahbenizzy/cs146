package util;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import langmod.UnigramLineProcessor;

public class Corpus {
	private Vector<String> _tokenPositions;
	private Vector<Integer> _unigramFrequencies;
	private Hashtable<String, Integer> _positionMap;
	private int _numWords;
	private String _sourceFile;
	public Corpus(String sourceFile) throws IOException {
		
		(_positionMap = new Hashtable<String, Integer>()).put(Constants.STOP_TOKEN, 0);
		_sourceFile = sourceFile;
		_numWords = 0;
		(_tokenPositions = new Vector<String>()).add(Constants.STOP_TOKEN);
		(_unigramFrequencies = new Vector<Integer>(10000)).add(0);
		UnigramLineProcessor processor = new UnigramLineProcessor(this);
		FileProcessor.processByLine(sourceFile, processor);
	}
	
	public int getFrequency(String s) {
		if(_positionMap.containsKey(s)) {
			return _unigramFrequencies.get(_positionMap.get(s));
		} else {
			return 0;
		}
	}
	
	public Vector<String> getAllTokens() {
		return _tokenPositions;
	}

	public void addUnigram(String s) {
		_numWords++;
		int position;
		if (_positionMap.containsKey(s))  {
			position = _positionMap.get(s);
			_unigramFrequencies.set(position,_unigramFrequencies.get(_positionMap.get(s))+1);
			
		} else {
			position = _tokenPositions.size();
			_tokenPositions.add(s);
			_unigramFrequencies.add(1);
			_positionMap.put(s, position);
		}
	}
	
	
	public int getNumTokens() {
		return _positionMap.size();
	}
	
	public int getNumWords() {
		return _numWords;
	}
	

}
