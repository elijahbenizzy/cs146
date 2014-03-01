package util;

import langmod.MaximizeableFunction;

public class Util {
	public static double ternarySearch(MaximizeableFunction function, double start, double end, double precision) {
		if (end-start < precision) 
			return (start+end)/2;

		double thirdLength = (end-start)/3;
		double left = start+thirdLength;
		double right = start + 2*thirdLength;
		double f_left = function.function(left);
		double f_right = function.function(right);
		if(f_left < f_right) 
			return ternarySearch(function,left,end,precision);
		else
			return ternarySearch(function,start,right,precision);
				
		
	}
	public static String join(String link, String[] stringArray) {
		if(stringArray.length == 0) {
			return "";
		}
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< stringArray.length -1; i++) {
			s.append(stringArray[i] + link);
		}
		s.append(stringArray[stringArray.length-1]);
		return s.toString();
	}
}
