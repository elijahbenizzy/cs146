package util;

import java.util.Arrays;

public class Bigram {
	public final String token1;
	public final String token2;

	public Bigram(String token1, String token2) {
		this.token1 = token1;
		this.token2 = token2;

	}

	@Override
	public int hashCode() {
//		return token1.hashCode()^token2.hashCode();
		int hash = 17;		
		hash = 31 * hash + token1.hashCode();
		hash = 31 * hash + token2.hashCode();
		return hash;
	}
	@Override
	public boolean equals(Object o) {
		Bigram toCompare = (Bigram) o;
		return (this.token1.equals(toCompare.token1)) && (this.token2.equals(toCompare.token2));
	}
	
	@Override
	public String toString() {
		return "{" + token1 + " : " + token2 + "}";
	}
}
