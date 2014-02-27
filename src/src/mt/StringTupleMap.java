package mt;

import java.util.Set;

public interface StringTupleMap {
	public Double get(String word1, String word2);
	public Double put(String word1, String word2, Double value);
	public boolean containsKey(String word1, String word2);
	public Set<WordPair> keySet();
}
