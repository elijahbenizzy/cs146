package mt;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidCorpusException {
		String trainingEnglish = "/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-0.txt";
		String trainingFrench = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-0.txt";
		String toTranslate = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-2.txt";
		String outfile = "/Users/elijah/Documents/Spring2014/cs146/mt/out.txt";
		ParallelCorpus corpus = new ParallelCorpus("english","/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-0.txt","french","/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-0.txt");
		ParallelCorpusAnalyzer analyzer = new ParallelCorpusAnalyzer(corpus);
		analyzer.runModel();
		BasicDecoder basicDecoder = new BasicDecoder(analyzer);
		basicDecoder.translateFile(toTranslate, outfile);


	}

}
