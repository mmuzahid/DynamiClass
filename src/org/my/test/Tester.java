package org.my.test;

import java.io.IOException;

import org.my.main.stoc.StrToClazz;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StrToClazz.load("package org.my.test;\r\n" + 
					"public class Hello {\r\n" + 
					"	static{System.out.println(\"'Hello' class is loading from string source ... :)\");}\r\n" + 
					"}\r\n" + 
					"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
