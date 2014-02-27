//package mt;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
//public class PairMap<K,V> implements TupleMap<K,V> { 
//	protected V _defaultValue;
//	private Map<Tuple<K>,V> _map;
//	
//	public PairMap(V defaultValue){
//		super();
//		_defaultValue = defaultValue;
//		_map = new HashMap<Tuple<K>,V>();
//	}
//
//	public V get(K elem1, K elem2) {
//		Tuple<K> toGet = new Tuple<K>(elem1,elem2);
//		V out = _map.get(toGet);
//		if(out == null)
//			return _defaultValue;
//		return out;
//	} 
//
//	public boolean containsKey(K elem1, K elem2) {
//		Tuple<K> key = new Tuple<K>(elem1,elem2);
//		return _map.containsKey(key);
//
//	}
//
//	public V put(K elem1, K elem2, V value) {
//		Tuple<K> key = new Tuple<K>(elem1,elem2);
//		_map.put(key, value);
//		return value;
//	}
//
//	@Override
//	public Set<Tuple<K>> keySet() {
//		return _map.keySet();
//	}
//
//	@Override
//	public Iterable<K> getSecondElements(K elem1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void reverse() {
//		Map<Tuple<K>,V> newMap = new HashMap<Tuple<K>,V>();
//		for(Tuple<K,V> key: )
//		// TODO Auto-generated method stub
//		
//	}
//	
//	
//}
