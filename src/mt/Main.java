package mt;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidCorpusException {
		Main.runNoisyChannelDecoder();


	}
	public static void runBasicDecoder() throws IOException, InvalidCorpusException {
		String trainingEnglish = "/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-0.txt";
		String trainingFrench = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-0.txt";
		String toTranslate = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-2.txt";
		String outfile = "/Users/elijah/Documents/Spring2014/cs146/mt/out.txt";
		ParallelCorpus corpus = new ParallelCorpus("english",trainingEnglish,"french",trainingFrench);
		ParallelCorpusAnalyzer analyzer = new ParallelCorpusAnalyzer(corpus);
		analyzer.runModel();
		BasicDecoder basicDecoder = new BasicDecoder(analyzer);
		basicDecoder.translateFile(toTranslate, outfile);
		System.out.println("F-Score is :"  + Evaluator.getFScore("/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-2.txt","/Users/elijah/Documents/Spring2014/cs146/mt/out.txt"));
	}
	public static void runNoisyChannelDecoder() throws IOException, InvalidCorpusException {
		String trainingEnglish = "/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-0.txt";
		String trainingFrench = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-0.txt";
		String toTranslate = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-2.txt";
		String outfile = "/Users/elijah/Documents/Spring2014/cs146/mt/outNoisyChannel.txt";
		ParallelCorpus corpus = new ParallelCorpus("english",trainingFrench,"french",trainingEnglish);
		NoisyChannelAnalyzer analyzer = new NoisyChannelAnalyzer(corpus);
		analyzer.runModel();
		NoisyChannelDecoder decoder = new NoisyChannelDecoder(analyzer);
		decoder.translateFile(toTranslate, outfile);
		System.out.println("F-Score is :"  + Evaluator.getFScore("/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-2.txt",outfile));
	}

}
