package com.instragram.initiative.like;

import java.io.File;
import java.util.Map;

import com.instragram.initiative.io.IOHandler;
import com.instragram.initiative.selenium.SeleniumUtil;

public class HashTagLiker {

	
	File error;
	
	public static void main(String[] args) {
		HashTagLiker liker = new HashTagLiker();
		// liker.likeHashTags(Arrays.asList("#lineart","#abc"), 2);
	}

	public void process(File login, File hashtags, File error,String destination) throws Exception {
		this.error=error;
		
	 Map<String, String> credentials=IOHandler.getCredentialsFromFile(login,error);
	 likeHashTags(credentials, IOHandler.getHashTagsFromFile(hashtags,error),destination);
	 
	}

	public void login(SeleniumUtil su,Map<String, String> credentials) {
		su.writeTest("//*[@id=\"react-root\"]/section/main/article/div[2]/div[1]/div/form/div[2]/div/label/input",
				credentials.get(IOHandler.USERNAME));
		su.writeTest("//*[@id=\"react-root\"]/section/main/article/div[2]/div[1]/div/form/div[3]/div/label/input",
				credentials.get(IOHandler.PASSWORD));
		su.clickTest("//*[@id=\"react-root\"]/section/main/article/div[2]/div[1]/div/form/div[4]");
	}

	public void likeHashTags(Map<String, String> credentials, Map<String, Integer> hashTags,String destination) {
		SeleniumUtil su = new SeleniumUtil("webdriver.chrome.driver",
				destination+"/chromedriver2.exe");

		openMainPage(su);
		
		login(su, credentials);
		
		try {
			su.clickTest("/html/body/div[4]/div/div/div[3]/button[2]");
		} catch (Exception e) {
			IOHandler.appendErrorsToFile(e, error);
		}
		
		
		for (String hashTag : hashTags.keySet()) {
			openMainPage(su);
			searchHashTagMedia(su, hashTag);// will be in loop
			int counter = 0;
			try {
				if (hashTags.get(hashTag) >= 1 && likeFirstPhoto(su)) {
					counter++;
				}
				su.clickTest("/html/body/div[4]/div[1]/div/div/a");
			} catch (Exception e) {
				IOHandler.appendErrorsToFile(e, error);
				continue;
			}

			while (counter < hashTags.get(hashTag)) {
				try {
					if (likePhoto(su)) {
						counter++;// loop
					}
					su.clickTest("/html/body/div[4]/div[1]/div/div/a[2]");
				} catch (Exception e) {
					IOHandler.appendErrorsToFile(e, error);
					break;
				}

			}
			endCurrentHashTagProcessing(su);
		}

	}

	private void endCurrentHashTagProcessing(SeleniumUtil su) {
		su.clickTest("/html/body/div[4]/div[3]/button");
	}

	private boolean likeFirstPhoto(SeleniumUtil su) {
		su.clickTest("//*[@id=\"react-root\"]/section/main/article/div[1]/div/div/div[1]/div[1]/a/div/div[2]");
		return likePhoto(su);
	}

	public boolean likePhoto(SeleniumUtil su) {
		try {
			String like = su.getElementAttribute(
					"//html/body/div[4]/div[2]/div/article/div[2]/section[1]/span[1]/button/*[name()='svg'][1]",
					"aria-label");
			if (like.equals("Like")) {
				Thread.sleep(2000);
				su.clickTest("/html/body/div[4]/div[2]/div/article/div[2]/section[1]/span[1]/button");
				return true;
			}

		} catch (InterruptedException e) {
			IOHandler.appendErrorsToFile(e, error);
		}
		return false;

	}

	public void searchHashTagMedia(SeleniumUtil su, String hashTag) {

		su.clickTest("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/div/div/span[2]");
		su.writeTest("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/input", hashTag);
		su.clickTest("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a");

	}

	public void openMainPage(SeleniumUtil su) {
		su.openUrl("https://instagram.com/");
	}
}
