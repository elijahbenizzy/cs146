package mt;

import util.Bigram;
import java.util.Arrays;

public class Tuple<T> {
		public final T token1;
		public final T token2;

		public Tuple(T elem1, T elem2) {
			this.token1 = elem1;
			this.token2 = elem2;

		}

		@Override
		public int hashCode() {
			int hash = 17;		
			hash = 31 * hash + token1.hashCode();
			hash = 31 * hash + token2.hashCode();
			return hash;
		}
		@Override
		public boolean equals(Object o) {
			Tuple<T> toCompare = (Tuple<T>) o;
			return (this.token1.equals(toCompare.token1)) && (this.token2.equals(toCompare.token2));
		}
		
		@Override
		public String toString() {
			return "{" + token1 + " : " + token2 + "}";
		}
	}
