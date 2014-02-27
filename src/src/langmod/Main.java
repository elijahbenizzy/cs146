package langmod;

import java.io.IOException;

import util.Corpus;

public class Main {
	public static void main(String[] argv) {
		if (argv.length != 5) {
			String message = "Please use the following arguments: \n\t <training corpus> <development corpus> <testCorpus> <goodbad file> <\"unigram\"/\"bigram\"";
			fail(message);
		}
		try {
			String trainingData = argv[0];
			String devData = argv[1];
			String testData = argv[2];
			String goodBad = argv[3];
			boolean unigram = argv[4].equals("unigram");
			
			UnigramAnalyzer unigramModel = new UnigramAnalyzer(trainingData,devData,1);
			if(unigram) {
				double fixedAlphaCorpusProbability = unigramModel.setAlpha(1).getCorpusProbability(testData);
				double optimalCorpusProbability = unigramModel.setOptimalAlpha().getCorpusProbability(testData);
				System.out.println(fixedAlphaCorpusProbability);
				System.out.println(optimalCorpusProbability);
				System.out.println(GoodBadTester.testFile(goodBad, unigramModel));
				System.out.println(unigramModel.getAlpha());
			} else {
				unigramModel.setAlpha(1);
				BigramAnalyzer bigramModel = new BigramAnalyzer(unigramModel,1);
				double fixedBetaCorpusProbability = bigramModel.getCorpusProbability(testData);
				unigramModel.setOptimalAlpha();
				double optimalCorpusProbability = bigramModel.setOptimalBeta().getCorpusProbability(testData);
				System.out.println(fixedBetaCorpusProbability);
				System.out.println(optimalCorpusProbability);
				System.out.println(GoodBadTester.testFile(goodBad, bigramModel));
				System.out.println(unigramModel.getAlpha());
				System.out.println(bigramModel.getBeta());
			}
			
		} catch (IOException e) {
			fail("files are not valid");
		}
		
		
	}
	
	public static void fail(String message) {
		System.out.println(message);
		System.exit(1);
	}
}
