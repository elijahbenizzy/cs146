package langmod;

import java.io.IOException;
import java.util.Vector;

public abstract class Analyzer {
	protected String _trainingDataPath;
	protected String _devDataPath;
	public Analyzer(String trainingDataPath, String devDataPath) {
		_trainingDataPath = trainingDataPath;
		_devDataPath = devDataPath;
		
	}
	
	public abstract double getLogProbability(Vector<String[]> text);
	public abstract double getCorpusProbability(String corpusPath) throws IOException;
	public String getTrainingFile() {
		return _trainingDataPath;
	}
	public String getDevDataFile() {
		return _devDataPath;
	}
}
