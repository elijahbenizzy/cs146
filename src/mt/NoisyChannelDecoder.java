package mt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import util.Constants;

public class NoisyChannelDecoder extends BasicDecoder {
	private NoisyChannelAnalyzer _model;
	private int _lineNum;
	public NoisyChannelDecoder(NoisyChannelAnalyzer model) {
		super(model);
		_model = model;
		_lineNum = 0;
		
	}
	@Override
	public String[] translate(String[] sentence) {
		_lineNum++;
		Vector<String> output = new Vector<String>();
		String prev = Constants.STOP_TOKEN;
		for(String toTranslate: sentence) {
			String curr = _model.getBestTranslation(prev, toTranslate);
			output.add(curr);
			prev = curr;
		}
		return output.toArray(new String[]{});
	}
}
