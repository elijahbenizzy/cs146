package util;

import java.util.HashMap;

public class DefaultDict<K,V> extends HashMap<K,V> { //works like a hashmap, except it gives a default value
	private V _defaultValue;
	
	public DefaultDict(V defaultValue) {
		super();
		_defaultValue = defaultValue;
	}
	
	public DefaultDict(V defaultValue, int initialCapacity) {
		super(initialCapacity);
		_defaultValue = defaultValue;
	}
	
	@Override
	public V get(Object pair) {
		if(this.containsKey(pair)) {
			return super.get(pair);
		} else {
			return _defaultValue;
		}
	}
	
}
