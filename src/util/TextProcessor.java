package util;

import java.util.Vector;

public class TextProcessor implements LineProcessor {
	private Vector<String[]> _text;
	public TextProcessor(Vector<String[]> v) {
		_text = v;
	}

	@Override
	public void processLine(String line) {
		_text.add(line.split("\\s+"));
		
	}
	public Vector<String[]> getText() {
		return _text;
	}

}
