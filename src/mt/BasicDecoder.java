package mt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import util.FileProcessor;
import util.FileWriter;
import util.LineProcessor;
import util.TextProcessor;

public class BasicDecoder implements Translator, LineProcessor{
	private ParallelCorpusAnalyzer _model;
	private Vector<String[]> _toTranslate;
	private Vector<String[]> _translatedText;
	
	public BasicDecoder(ParallelCorpusAnalyzer model) {
		_toTranslate = new Vector<String[]>();
		_translatedText = new Vector<String[]>();
		_model = model;
	}

	@Override
	public String[] translate(String[] sentence) {
		// TODO Auto-generated method stub
		ArrayList<String> out = new ArrayList<String>();
		for(String toTranslate: sentence) {
			String translation = _model.getMostLikelyWord(toTranslate);
			if(translation != null) {
				out.add(translation);
			} else {
				out.add(toTranslate);
			}
		}
		return out.toArray(new String[out.size()]);
	}

	@Override
	public void translateFile(String inFile, String outFile) throws IOException {
		FileProcessor.processByLine(inFile,this);
		//------ for test
		Vector<String[]> bothLines = new Vector<String[]>();
		for(int i = 0;i < _toTranslate.size();i++) {
			bothLines.add(_toTranslate.get(i));
			bothLines.add(_translatedText.get(i));
		}
		//------ for test
		FileWriter.writeLines(bothLines, outFile);
	}

	@Override
	public double getFScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void processLine(String line) {
		String[] lineSplit = line.split(" ");
		_toTranslate.add(lineSplit);
		_translatedText.add(this.translate(lineSplit));
	}
	
}
