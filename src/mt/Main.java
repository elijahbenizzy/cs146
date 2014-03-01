package mt;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidCorpusException {
		if(args.length != 4) {
			return;
		}
		String frenchtrain = args[0];
		String englishtrain = args[1];
		String frenchtest = args[2];
		String noisyOrBasic = args[3];
		if(noisyOrBasic.equals("noisy")) {
			Main.runNoisyChannelDecoder(englishtrain, frenchtrain, frenchtest);
		} else if (noisyOrBasic.equals("basic")) {
			Main.runBasicDecoder(englishtrain, frenchtrain, frenchtest);
		}
//		String trainingEnglish = "/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-0.txt";
//		String trainingFrench = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-0.txt";
//		String toTranslate = "/Users/elijah/Documents/Spring2014/cs146/mt/data/french-senate-2.txt";
//		String outFile = "/Users/elijah/Documents/Spring2014/cs146/mt/out.txt";
	}
	public static void runBasicDecoder(String trainingEnglish, String trainingFrench,String toTranslate) throws IOException, InvalidCorpusException {
		
		ParallelCorpus corpus = new ParallelCorpus("english",trainingEnglish,"french",trainingFrench);
		ParallelCorpusAnalyzer analyzer = new ParallelCorpusAnalyzer(corpus);
		analyzer.runModel();
		BasicDecoder basicDecoder = new BasicDecoder(analyzer);
		basicDecoder.printTranslation(toTranslate);
		System.out.println("F-Score is :"  + Evaluator.getFScore("/Users/elijah/Documents/Spring2014/cs146/mt/data/english-senate-2.txt","/Users/elijah/Documents/Spring2014/cs146/mt/out.txt"));
	}
	public static void runNoisyChannelDecoder(String trainingEnglish, String trainingFrench,String toTranslate) throws IOException, InvalidCorpusException {
		ParallelCorpus corpus = new ParallelCorpus("english",trainingFrench,"french",trainingEnglish);
		NoisyChannelAnalyzer analyzer = new NoisyChannelAnalyzer(corpus);
		analyzer.runModel();
		NoisyChannelDecoder decoder = new NoisyChannelDecoder(analyzer);
		decoder.printTranslation(toTranslate);
	}

}
