This is the readme for langmod.

I. Running it
	To run it, use the files unigram, and bigram, with the arguments <training data> <held out data> <test data> <good/bad data>

II.
	Design
	I used a smooth unigram/bigram model to determine the probability of texts, and wrote the project in java. The main classes are:
		Corpus:
			reads a file and provides frequencies
		BigramCorpus:
			reads a file and provides bigram frequencies
		Analyzer:
			Abstract superclass for a probability analyzer for text
		UnigramAnalyzer:
			Gives the probability of a line or of a text, calculates smoothing parameters using a unigram model
		BigramAnalyzer:
			Gives the probability of a line using a bigram model
	I used a ternary search to maximize the parameters, but I'm considering upgrading it to a golden-section search. I got the pseudocode from wikipedia.

III. known bugs
	There are no known bugs in my project.
