package com.instragram.initiative.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class IOHandler {
	
	public static final String USERNAME="username";
	public static final String PASSWORD="password";

	public static File[] filesInFolder(String directoryLocation) {
		final File folder = new File(directoryLocation);
		return folder != null && folder.listFiles().length > 0 ? folder.listFiles() : new File[0];
	}

	public static File fileRecognizer(String fileName, File[] files) {

		for (File file : files) {
			if (file.getName().equalsIgnoreCase(fileName)) {
				return file;
			}
		}
		return null;
	}

	public static Map<String, Integer> getHashTagsFromFile(File file, File error) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] content = line.trim().split(",");
				map.put(content[0], Integer.parseInt(content[1].trim()));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			IOHandler.appendErrorsToFile(e, error);
		}
		return map;
	}

	public static void appendErrorsToFile(Exception exc, File file) {
		try {
			
			
			StringWriter sw = new StringWriter();
            exc.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            
			// Open given file in append mode.
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(exceptionAsString);
			out.close();
		} catch (IOException e) {
			//eat
		}
	}

	public static Map<String, String> getCredentialsFromFile(File file, File error) {
		Map<String, String> credentials = new HashMap<String, String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			credentials.put(USERNAME, line.trim());
			line = reader.readLine();
			credentials.put(PASSWORD, line.trim());
			reader.close();
			
		} catch (IOException e) {
			IOHandler.appendErrorsToFile(e, error);
		}
		return credentials;
	}

}
