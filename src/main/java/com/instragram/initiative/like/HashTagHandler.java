package com.instragram.initiative.like;

import java.io.File;

import com.instragram.initiative.io.IOHandler;

public class HashTagHandler {
	
	public static void handleHashTagFiles(String[] args){
		
		File login= IOHandler.fileRecognizer("login.txt",  IOHandler.filesInFolder(args[0]));
		File hashtags= IOHandler.fileRecognizer("hashtags.txt",  IOHandler.filesInFolder(args[0]));
		File error =IOHandler.fileRecognizer("error.txt",  IOHandler.filesInFolder(args[0]));
		
		try {
			HashTagLiker liker = new HashTagLiker();
			liker.process(login,hashtags,error,args[0]);
		} catch (Exception e) {
			IOHandler.appendErrorsToFile(e, error);
		}
		
	}
}
