package counts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import util.LineProcessor;

public class WordCounter implements LineProcessor {
	private Hashtable<String,Integer> _wordCounts;
	public WordCounter () {
		_wordCounts = new Hashtable<String,Integer>();
	}
	@Override
	public void processLine(String line) {
		String[] lineSplit = line.split(" ");
		for(String word: lineSplit) {
			if(_wordCounts.containsKey(word)) {
				_wordCounts.put(word,_wordCounts.get(word)+1);
			} else {
				_wordCounts.put(word, 1);
			}
		}
		
	}
	
	public Set<String> getAllTokens() {
		return _wordCounts.keySet();
	}
	
	public String getOutput() {
		StringBuilder output = new StringBuilder();
		ArrayList<Entry<String,Integer>> entries = new ArrayList<Entry<String,Integer>>(_wordCounts.entrySet());
		Collections.sort(entries, new Comparator<Entry<String,Integer>>() {

			@Override
			public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
				if (arg0.getValue() > arg1.getValue()) {
					return -1;
				} else if (arg0.getValue() == arg1.getValue()) {
					return 0;
				} else {
					return 1;
				}
			}
			
		}
		);
		for (Entry<String,Integer> entry : entries) {
			output.append(entry.getKey() + " " + entry.getValue() + "\n");
		}
		return output.toString();
	}
	
}
