package langmod;

import java.io.IOException;

import util.Constants.*;

import java.util.Iterator;
import java.util.Vector;

import util.Constants;
import util.Corpus;
import util.FileProcessor;
import util.TextProcessor;

public class UnigramAnalyzer extends Analyzer {
	private Corpus _devData;
	private Corpus _trainingData;
	private double _alpha;
	public UnigramAnalyzer(String trainingDataPath, String devDataPath) throws IOException {
		super(trainingDataPath,devDataPath);
		this.initialize(trainingDataPath, devDataPath);
		_alpha = this.bestAlpha();
	}
	public UnigramAnalyzer(String trainingDataPath, double alpha) { //no held out data
		super(trainingDataPath,null);
	}
	
	public UnigramAnalyzer(String trainingDataPath, String devDataPath, double alpha) throws IOException {
		super(trainingDataPath,devDataPath);
		this.initialize(trainingDataPath, devDataPath);
		_alpha = alpha;
	}
	private void initialize(String trainingDataPath, String devDataPath) throws IOException {
		_trainingData = new Corpus(trainingDataPath);
		_devData = new Corpus(devDataPath);
	}
	
	
	public double getAlpha() {
		return _alpha;
	}
	public UnigramAnalyzer setOptimalAlpha() {
		_alpha = this.bestAlpha();
		return this;
	}
	public UnigramAnalyzer setAlpha(double alpha) {
		_alpha = alpha;
		return this;
	}

	
	protected double getTheta(String s, double alpha, Corpus corpus) { //returns the theta from the 
		double n_w = corpus.getFrequency(s);
		double n_0 = corpus.getNumWords();
		double size_w = corpus.getNumTokens();
		double theta = (n_w + alpha)/(n_0 + alpha*size_w);
		return theta;
	}
	
	private class MaximumLikelihoodFunction implements MaximizeableFunction {

		@Override
		public double function(double alpha) {
			Vector<String> tokens = _devData.getAllTokens();
			double currSum = 0.0;
			for(String token : tokens) {
				int frequency = _devData.getFrequency(token);
				double logTheta = Math.log(UnigramAnalyzer.this.getTheta(token, alpha,_trainingData));
				currSum+= frequency*logTheta;
			}
			return currSum;
		}
		
	}
	
	private double bestAlpha() {
		return util.MathUtil.ternarySearch(new MaximumLikelihoodFunction(), Math.pow(10,-6), Math.pow(10,6), Math.pow(10, -9));
	}
	
	@Override
	public double getLogProbability(Vector<String[]> text) {
		double currprob = 0;
		double stopTheta = this.getTheta(Constants.STOP_TOKEN, _alpha,_trainingData);
		currprob+=stopTheta;
		for (String[] line: text) {
			for(String word: line) {
				double theta = Math.log(this.getTheta(word, _alpha,_trainingData));
				currprob += theta;
			}
			
			currprob += stopTheta;
		}
		return currprob;

	}

	@Override
	public double getCorpusProbability(String corpusPath) throws IOException {
		Vector<String[]> text = new Vector<String[]>();
		FileProcessor processor = new FileProcessor();
		processor.processByLine(corpusPath, new TextProcessor(text));
		return this.getLogProbability(text);
			
	}


}
