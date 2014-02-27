package mt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MapOfMaps<K,V> implements TupleMap<K,V>{

		private V _defaultValue;
		private Map<K,HashMap<K,V>> _map;
		private int _size;
		
		public MapOfMaps(V defaultValue){
			_defaultValue = defaultValue;
			_map = new HashMap<K,HashMap<K,V>>(30000,.5f);
			_size = 0;
		}
		
		
		public Map<K,HashMap<K,V>> getUnderlyingTable() {
			return _map;
		}

		public V get(K elem1, K elem2) {
			if(!_map.containsKey(elem1)) {
				return _defaultValue;
			} else {
				HashMap<K, V> specific = _map.get(elem1);
				if(specific.containsKey(elem2)) {
					return specific.get(elem2);
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
		
		public Set<Tuple> keySet() {
			HashSet<Tuple> out = new HashSet<Tuple>();
			for(Entry<String,HashMap<String,Double>> entry1: _map.entrySet()) {
				for(Entry<String,Double> entry2: entry1.getValue().entrySet()) {
					out.add(new Tuple(entry1.getKey(),entry2.getKey()));
				}
			}
			return out;
		}
		
	}
