package com.eaf.service.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileService {
	public static int getLineNumber(String filePath) throws IOException{
		File file = new File(filePath);
		LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
		lineNumberReader.skip(Long.MAX_VALUE);
		lineNumberReader.close();
		
		return lineNumberReader.getLineNumber();
	}
	
	public static String[][] getValue(String filePath) throws IOException{
		File file = new File(filePath);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line = null;
		int data = getLineNumber(filePath);
		String[][] value = new String[data][50];
		int i=0;
		while((line = buffer.readLine())!=null){
				value[i++] = line.split("\\|");
		}
		return value;
	}
}
