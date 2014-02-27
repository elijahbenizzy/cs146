package mt;

import java.io.IOException;

public interface Translator {
	public String[] translate(String[] sentence);
	public void translateFile(String inFile, String outFile) throws IOException;
}
