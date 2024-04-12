package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.util.ArrayList;

public class ImageFinder {
	
	public ArrayList<String> getThumbnail(String path){
		ArrayList<String> arrFilePath = new ArrayList<String>();
		
		File mydir = new File(path);
		System.out.println(">>mydir = "+mydir);
		if (mydir != null) {
			String[] filenames = null;
			filenames = mydir.list();

			for (int i = 0; i < filenames.length; i++) {
				String filename = "";
				
				filename = filenames[i];
				
				if(filename != null){				
					arrFilePath.add(filename);
				}else{
					System.out.println("filename is null");
				}
			}
		}		
		return arrFilePath;
	}
	
}
