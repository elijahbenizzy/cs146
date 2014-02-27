//package tests;
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Hashtable;
//import java.util.Map.Entry;
//import java.util.Vector;
//
//import util.*;
//import langmod.*;
//
//import org.junit.Test;
//
//
//public class UnitTests {
//
////	@Test
//	public void maximizeTest() {
//		MaximizeableFunction xRootx = new MaximizeableFunction() {
//			public double function(double x) {
//				return Math.pow(x, 1/x);
//			}
//				
//		};
//		
//		MaximizeableFunction negxSquared = new MaximizeableFunction() {
//			public double function(double x) {
//				return -x*x;
//			}
//				
//		};
//		
//		double precision = .00001;
//		double result = MathUtil.ternarySearch(xRootx, 0, Math.pow(10,9), precision);
//		if (Math.abs(result - Math.E) > precision)
//			fail("result is " + result + " result should have been " + Math.E);
//		
//		result = MathUtil.ternarySearch(negxSquared, -Math.pow(10,4), Math.pow(10,4), precision);
//		if (Math.abs(result) > precision)
//			fail("result is " + result + " result should have been " + 0);
//		
//	}
////	@Test
//	public void testUnigramReader() {
//		try {
//			Corpus c = new Corpus("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt");
//			int freq_the = c.getFrequency("the");
//			if (freq_the != 28378) {
//				fail("Count of the word \" the \" is wrong, got " + freq_the + " should have gotten " + 28378);
//			}
//		} catch(IOException e) {
//			fail("invalid file");
//		}
//	}
//	
////	@Test
//	public void testUnigramAnalyzerFixedAlpha() {
//		try {
//			Vector<String[]> likely;
//			Vector<String[]> unlikely;
//			UnigramAnalyzer analyzer = new UnigramAnalyzer("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt", "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-1.txt",1);
//			(likely = new Vector<String[]>()).add(new String[]{"senate", "the", "government", "president"});
//			(unlikely = new Vector<String[]>()).add(new String[]{"whythefuck", "amiup", "attwo", "inthemorning"});
//			double probLikely = analyzer.getLogProbability(likely);
//			double probUnlikely = analyzer.getLogProbability((unlikely));
//			System.out.println("Probability of sentence " + likely + " is " + probLikely);
//			System.out.println("Probability of sentence " + unlikely + " is " + probUnlikely);
//			if (probLikely < probUnlikely) {
//				fail("Language machine is all wonky");
//			}
//			String testCorpus = "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-2.txt";
//			double result = analyzer.getCorpusProbability(testCorpus);
//			System.out.println("Probability of corpus " + testCorpus + " is " + result + " with alpha at " + 1);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			fail("invalid file");
//		}
//	}
////	@Test
//	public void testUnigramAnalyzerVariableAlpha() {
//		System.out.println("test");
//		try {
//			UnigramAnalyzer analyzer = new UnigramAnalyzer("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt", "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-1.txt");
//			String[][] likely = new String[][]{new String[]{"senate", "the", "government", "president"}};
//			String[][] unlikely = new String[][]{new String[]{"whythefuck", "amiup", "attwo", "inthemorning"}};
//			double probLikely = analyzer.getLogProbability(new Vector(Arrays.asList(likely)));
//			double probUnlikely = analyzer.getLogProbability(new Vector(Arrays.asList(unlikely)));
//			System.out.println("Probability of sentence " + Arrays.toString(likely[0]) + " is " + probLikely);
//			System.out.println("Probability of sentence " + Arrays.toString(unlikely[0]) + " is " + probUnlikely);
//
//			System.out.println(analyzer.getAlpha());
//			if (probLikely < probUnlikely) { //not sure if alpha is correct
//				fail("Language machine is all wonky");
//			}
//
//			String testCorpus = "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-2.txt";
//			double result = analyzer.getCorpusProbability(testCorpus);
//			System.out.println("Probability of corpus " + testCorpus + " is " + result + " with alpha at " + analyzer.getAlpha());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			fail("invalid file");
//		}
//	}
////	@Test 
//	public void bigramTest() {
//		System.out.println("testing bigrams");
//		Bigram b1 = new Bigram("of","the");
//		Bigram b2 = new Bigram("of","the");
//		Bigram b3 = new Bigram("of","the");
//		if(!b1.equals(b2) || !b2.equals(b1)) {
//			fail("Bigrams should be equal");
//		} else {
//			System.out.println("Bigrams are equal");
//		}
//		if(b1.hashCode() != b2.hashCode()) {
//			fail("Bigrams should hash to the same bucket");
//		} else {
//			System.out.println("bigram codes are equal");
//		}
//		Hashtable<Bigram,Integer> h = new Hashtable<Bigram,Integer>();
//		h.put(b1, 0);
//		h.put(b2, 0);
//	}
////	@Test
//	public void bigramProcessorTest() {
//		System.out.println("running bigram processor test");
//		try {
//			BigramCorpus c = new BigramCorpus("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt");
//			Hashtable<Bigram, Integer> bigramFrequencies = c.getBigramFrequencies();
//			ArrayList<Entry<Bigram,Integer>> freqList = new ArrayList<Entry<Bigram,Integer>>(bigramFrequencies.entrySet());
//			Collections.sort(freqList, new Comparator<Entry<Bigram,Integer>>() {
//
//				@Override
//				public int compare(Entry<Bigram, Integer> o1,
//						Entry<Bigram, Integer> o2) {
//					// TODO Auto-generated method stub
//					return -o2.getValue().compareTo(o1.getValue());
//				}
//
//
//				
//			});
//			System.out.println(freqList.size());
//		} catch (IOException e) {
//			
//			// TODO Auto-generated catch block
//			fail("invalid file");
//		}
//		
//	}
//	@Test
//	public void bigramProbabilityTest() {
//		try {
//			UnigramAnalyzer unigramModel = new UnigramAnalyzer("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt", "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-1.txt");
//			BigramAnalyzer analyzer = new BigramAnalyzer(unigramModel);
//			BigramAnalyzer fixedBeta = new BigramAnalyzer(unigramModel,10);
//			double result = analyzer.getCorpusProbability("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-2.txt");
//			double result2 = fixedBeta.getCorpusProbability("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-2.txt");
//			System.out.println("the probability of senate-2 is " + result);
//			System.out.println("the probability of senate-2 with beta = 10 is " + result2);
//			System.out.println(analyzer.getBeta());
//			
//		} catch (IOException e) {
//			fail("invalid file");
//		}
//	}
//	
////	@Test 
//	public void goodBadTestUnigram() {
//		try {
//			String goodBadPath = "/Users/elijah/Documents/Spring2014/cs146/langmod/data/good-bad.txt";
//			UnigramAnalyzer unigramModel = new UnigramAnalyzer("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt", "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-1.txt",1);
//			double result = GoodBadTester.testFile(goodBadPath, unigramModel);
//			System.out.println("Unigram tester with alpha = " + unigramModel.getAlpha() + "got " + result);
//		} catch (IOException e){
//			
//			fail("invalid file");
//		}
//	}
//	@Test
//	public void goodBadTestBigram() {
//		String goodBadPath = "/Users/elijah/Documents/Spring2014/cs146/langmod/data/good-bad.txt";
//		try {
//			UnigramAnalyzer unigramModel = new UnigramAnalyzer("/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-0.txt", "/Users/elijah/Documents/Spring2014/cs146/langmod/data/english-senate-1.txt");
//			BigramAnalyzer bigramModel = new BigramAnalyzer(unigramModel);
//			double result = GoodBadTester.testFile(goodBadPath, bigramModel);
//			System.out.println("Bigram tester with beta = " + bigramModel.getBeta() + "got " + result);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			fail("invalid file");
//		}
//
//	}
//	
//
//}
