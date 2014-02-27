package mt;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidCorpusException {
		ParallelCorpus corpus = new ParallelCorpus("english","/course/cs146/data/english-senate-0.txt","french","/course/cs146/data/french-senate-0.txt");
		ParallelCorpusAnalyzer translator = new ParallelCorpusAnalyzer(corpus);
		translator.runModel();
		System.out.println("le : " + translator.getMostLikelyWord("le"));
		System.out.println("pain : " + translator.getMostLikelyWord("pain"));
		System.out.println("il : " + translator.getMostLikelyWord("il"));
		System.out.println("elle: " + traanslator.getMostLikelyWord("elle"));


	}

}
