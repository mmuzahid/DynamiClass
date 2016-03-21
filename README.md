# DynamiClass
A Java project for Dynamically Class Loading.

Goal of this project is to load java classes from different sources like String variable, DB, XML etc.
Initially, 'String loaded as Class' feature is developed.
We can load string content as a java class simply calling 'StrToClazz.load()' method.
following is a simple example:

``StrToClazz.load("package org.my.test;\r\n" + 
			"public class Hello {\r\n" + 
			"	static{System.out.println(\"'Hello' class is loading from string source ... :)\");}\r\n" + 
				"}\r\n" + 
				"");``
