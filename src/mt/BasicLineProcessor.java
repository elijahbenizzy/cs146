package mt;

import java.util.Vector;

import util.Constants;
import util.LineProcessor;

public class BasicLineProcessor implements LineProcessor {
	private Vector<String[]> _lineArray;
	private boolean _translateTo;
	public BasicLineProcessor(Vector<String[]> lineArray,boolean translateTo) { // if its toTranslate, it will count the null word, otherwise not
		_lineArray = lineArray;
		_translateTo = translateTo;
	}

	@Override
	public void processLine(String line) {
		String[] lineSplit = line.split("\\s+");
		_lineArray.add(lineSplit);
	}

}
