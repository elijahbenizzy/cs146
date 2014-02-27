package util;

public class DamnitJava { //java should have the following methods.
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
