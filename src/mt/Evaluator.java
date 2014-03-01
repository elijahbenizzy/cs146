package mt;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

import util.Constants;
import util.FileProcessor;
import util.LineProcessor;
import util.TextProcessor;
import util.WordCounter;

public class Evaluator {
	
	public static void main(String[] args) throws IOException {
		String progoutput = args[0];
		String correct = args[1];
		if(args.length != 2) {
			System.out.println("please use valid arguments");
		}
		System.out.println(Evaluator.getFScore(progoutput, correct));
	}
	
	public static double getFScore(String translatedFile, String correctFile) throws IOException {
		double numExpected = 0;
		double numCorrectResults = 0;
		double numReturnedResults = 0;
		Vector<String[]> translatedLines = new Vector<String[]>(); 
		Vector<String[]> correctLines = new Vector<String[]>();
		FileProcessor.processByLine(translatedFile,new TextProcessor(translatedLines));
		FileProcessor.processByLine(correctFile,new TextProcessor(correctLines));
		if (translatedLines.size() != correctLines.size()) {
			System.out.println("Well shit.");
			System.exit(0);
		}
		
		for(int pos = 0; pos < correctLines.size();pos++) {
			String[] correctSentence = correctLines.get(pos);
			String[] translatedSentence = translatedLines.get(pos);
			if(translatedSentence.length > Constants.MAX_SENTENCE_LENGTH) {
				continue;
			}
			HashSet<String> correct = new HashSet<String>();
			numReturnedResults += translatedSentence.length;
			numExpected += correctSentence.length;
			correct.addAll(Arrays.asList(correctSentence));
			for(String s: translatedSentence) {
				numCorrectResults += correct.contains(s) ? 1 : 0;
			}
		}
		
		

		double precision = numCorrectResults/numReturnedResults;
		double recall = numCorrectResults/numExpected;
		return (2*(precision*recall))/(precision+recall);
		
	}
	
	
}
