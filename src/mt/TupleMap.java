package mt;

import java.util.Set;

public interface TupleMap<K,V> {
	public V get(K elem1, K elem2);
	public V put(K elem11, K elem2, V value);
	public boolean containsKey(K elem1, K elem2);
	public Set<Tuple<K>> keySet();
	public Iterable<K> getSecondElements(K elem1);
	public void reverse(); //reverses the order of every tuple
}
