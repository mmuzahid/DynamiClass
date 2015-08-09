/*
 * Developer:                MD. MUZAHIDUL ISLAM
 * Email:                    CV.MUZAHID@GMAIL.COM  
 * Environment:  	     JDK 1.6
 * Date:                     14-MAR-2015 
 *  */
package org.my.main.stoc;

import java.io.*;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class StrToClazz {

	/**
	 * compile class
	 * @param packageName - package name
	 * @param className - class name
	 * @param classBody - content of class
	 * */
	private static void compileClass(String packageName, String className,
			String classBody) throws IOException {
		String path = packageName;
		path = path.replace(".", File.separator);

		String absSrcPath = StrToClazz.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();

		path = absSrcPath + File.separator + path;

		// create sub-directories and the source file
		String dirName = absSrcPath;
		for (String subdirName : packageName.split("\\.")) {
			dirName += File.separator + subdirName.trim();
			File dir = new File(dirName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		File sourceFile = new File(path + File.separator + className + ".java");
		sourceFile.createNewFile();

		FileWriter writer = new FileWriter(sourceFile);
		writer.write(classBody);
		writer.close();

		JavaCompiler jCompiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = jCompiler.getStandardFileManager(
				null, null, null);

		fileManager.setLocation(StandardLocation.SOURCE_PATH,
				Arrays.asList(new File("" + StandardLocation.SOURCE_PATH)));
		// Compile the file
		jCompiler.getTask(
				null,
				fileManager,
				null,
				null,
				null,
				fileManager.getJavaFileObjectsFromFiles(Arrays
						.asList(sourceFile))).call();
		fileManager.close();

		// delete the source file
		sourceFile.deleteOnExit();

	}

	/**
	 * load String as Class file
	 * */
	public static void load(String classBody) throws IOException {
		String className = parseClassName(classBody);
		String packageName = parsePackageName(classBody);
		compileClass(packageName, className, classBody);
		loadClass(packageName + (packageName.isEmpty() ? "" : ".") + className);
	}
	
	/**
	 * parse package name
	 * */
	private static String parsePackageName(String classBody) {
		String pkgName = "";
		for (String pkgKeyword : new String[] { "package" }) {
			int index = classBody.indexOf(pkgKeyword);
			if (index != -1) {
				pkgName = classBody.substring(index + pkgKeyword.length(),
						classBody.indexOf(";", index)).trim();
			}
		}		
		return pkgName;
	}
	
	/**
	 * parse class name
	 * */
	private static String parseClassName(String classBody) {
		String className = null;
		for (String classKeyword : new String[] { "class", "interface", "enum" }) {
			int index = classBody.indexOf(classKeyword);
			if (index != -1) {
				className = classBody.substring(index + classKeyword.length(),
						classBody.indexOf("{", index));
				className = className.trim().split("\\s+")[0];
				break;
			}
		}		
		return className;
	}

	/**
	 * load named class by class loader 
	 * */
	private static void loadClass(String className) {
		try {
			Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
