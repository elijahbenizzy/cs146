package util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;

public class FileWriter {
	public static void writeLines(Vector<String[]> lines,String outFile) throws IOException {
		Writer outputter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
		StringBuilder output = new StringBuilder();
		for(String[] line: lines) {
			output.append(DamnitJava.join(" ", line));
			output.append("\n");
		}
//		System.out.println(output);
		outputter.write(output.toString());
		outputter.close();
	}
}
