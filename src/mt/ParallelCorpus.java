package mt;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import counts.WordCounter;
import langmod.UnigramLineProcessor;
import util.Constants;
import util.Corpus;
import util.FileProcessor;

public class ParallelCorpus {
	private String _lang1;
	private String _lang2;
	private Vector<String[]> _lineArray1;
	private Vector<String[]> _lineArray2;
	private Set<String> _lang1Tokens;
	private Set<String> _lang2Tokens;
	private String _lang1Data;
	private String _lang2Data;
	public ParallelCorpus(String lang2, String filePath2, String lang1, String filePath1) throws IOException, InvalidCorpusException {
		_lang1 = lang1;
		_lang2 = lang2;
		_lang1Data = filePath1;
		_lang2Data = filePath2;
		WordCounter lang1Counter = new WordCounter();
		WordCounter lang2Counter = new WordCounter();
		
		FileProcessor.processByLine(filePath1, new BasicLineProcessor(_lineArray1 = new Vector<String[]>(),false),lang1Counter); 
		FileProcessor.processByLine(filePath2, new BasicLineProcessor(_lineArray2 = new Vector<String[]>(),true),lang2Counter); //counts the null word
		if(_lineArray1.size() != _lineArray2.size()) {
			throw new InvalidCorpusException();
		}
		_lang1Tokens = new HashSet<String>(lang1Counter.getAllTokens());
		_lang2Tokens = new HashSet<String>(lang2Counter.getAllTokens());
	}
	
	public String getLang1Data() {
		return _lang1Data;
	}
	public String getLang2Data() {
		return _lang2Data;
	}
	
	public ParallelCorpus reverse() {
		String lang1 = _lang1;
		_lang1 = _lang2;
		_lang2 = lang1;
		Vector<String[]> lineArray1 = _lineArray1;
		_lineArray1 = _lineArray2;
		_lineArray2 = lineArray1;
		return this;
	}
	
	public Set<String> getLang1Tokens() {
		return _lang1Tokens;
	}
	
	public Set<String> getLang2Tokens() {
		return _lang2Tokens;
	}
	
	public String getLang1() {
		return _lang1;
	}
	
	public String getLang2() {
		return _lang2;
	}
	public Vector<String[]> getLineArrayLang1() {
		return _lineArray1;
	}
	public Vector<String[]> getLineArrayLang2() {
		return _lineArray2;
	}

}
