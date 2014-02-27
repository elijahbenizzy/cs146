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

		public boolean containsKey(K elem1, K elem2) {
			if(!_map.containsKey(elem1)) {
				return false;
			} else {
				HashMap<K, V> specific = _map.get(elem1);
				if(specific.containsKey(elem2)) {
					return true;
				} else {
					return false;
				}
			}
			
		}

		public V put(K elem1, K elem2, V value) {
			if(!this.containsKey(elem1,elem2)) {
				_size++;
			}
			if(!_map.containsKey(elem1)) {
				_map.put(elem1, new HashMap<K,V>());
				
			}
			HashMap<K, V> specific = _map.get(elem1);
			specific.put(elem2, value);
			return value;
		}
		public int size() {
			return _size;
		}
		
		public Set<Tuple<K>> keySet() {
			HashSet<Tuple<K>> out = new HashSet<Tuple<K>>();
			for(Entry<K,HashMap<K,V>> entry1: _map.entrySet()) {
				for(Entry<K,V> entry2: entry1.getValue().entrySet()) {
					out.add(new Tuple<K>(entry1.getKey(),entry2.getKey()));
				}
			}
			return out;
		}


		@Override
		public Iterable<K> getSecondElements(K elem1) {
			// TODO Auto-generated method stub
			if(!_map.containsKey(elem1)) {
				return new HashSet<K>();
			} else {
				return _map.get(elem1).keySet();
			}
		}
		


		@Override
		public void reverse() {
			Map<K,HashMap<K,V>> oldMap = _map;
			_map = new HashMap<K,HashMap<K,V>>(30000,.5f);
			
			for(K key1: oldMap.keySet()) {
				for(K key2: oldMap.get(key1).keySet()) {
					V value = oldMap.get(key1).get(key2);
					this.put(key2, key1, value);
				}
			}
			
			
		}
		
	}
