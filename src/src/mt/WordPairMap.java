package mt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordPairMap implements StringTupleMap { 
	protected double _defaultValue;
	private Map<WordPair,Double> _map;
	
	public WordPairMap(double defaultValue){
		super();
		_defaultValue = defaultValue;
		_map = new HashMap<WordPair,Double>();
	}

	public Double get(String word1, String word2) {
		WordPair toGet = new WordPair(word1,word2);
		Double out = _map.get(toGet);
		if(out == null)
			return _defaultValue;
		return out;
	} 

	public boolean containsKey(String word1, String word2) {
		WordPair key = new WordPair(word1,word2);
		return _map.containsKey(key);

	}

	public Double put(String word1, String word2, Double value) {
		WordPair key = new WordPair(word1,word2);
		_map.put(key, value);
		return value;
	}

	@Override
	public Set<WordPair> keySet() {
		return _map.keySet();
	}
	
}
