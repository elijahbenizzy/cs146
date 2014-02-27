package mt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class WordTupleMap implements StringTupleMap{

		private double _defaultValue;
		private Map<String,HashMap<String,Double>> _map;
		private int _size;
		
		public WordTupleMap(double defaultValue){
			_defaultValue = defaultValue;
			_map = new HashMap<String,HashMap<String,Double>>(30000,.5f);
			_size = 0;
		}
		
		
		public Map<String,HashMap<String,Double>> getUnderlyingTable() {
			return _map;
		}

		public Double get(String word1, String word2) {
			if(!_map.containsKey(word1)) {
				return _defaultValue;
			} else {
				HashMap<String, Double> specific = _map.get(word1);
				if(specific.containsKey(word2)) {
					return specific.get(word2);
				} else {
					return _defaultValue;
				}
			}
		}

		public boolean containsKey(String word1, String word2) {
			if(!_map.containsKey(word1)) {
				return false;
			} else {
				HashMap<String, Double> specific = _map.get(word1);
				if(specific.containsKey(word2)) {
					return true;
				} else {
					return false;
				}
			}
			
		}

		public Double put(String word1, String word2, Double value) {
			if(!this.containsKey(word1,word2)) {
				_size++;
			}
			if(!_map.containsKey(word1)) {
				_map.put(word1, new HashMap<String,Double>());
				
			}
			HashMap<String, Double> specific = _map.get(word1);
			specific.put(word2, value);
			return value;
		}
		public int size() {
			return _size;
		}
		
		public Set<WordPair> keySet() {
			HashSet<WordPair> out = new HashSet<WordPair>();
			for(Entry<String,HashMap<String,Double>> entry1: _map.entrySet()) {
				for(Entry<String,Double> entry2: entry1.getValue().entrySet()) {
					out.add(new WordPair(entry1.getKey(),entry2.getKey()));
				}
			}
			return out;
		}
		
	}
