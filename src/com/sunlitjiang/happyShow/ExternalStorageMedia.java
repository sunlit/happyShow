package com.sunlitjiang.happyShow;

import java.io.File;
import java.io.FilenameFilter;

import android.os.Environment;

public class ExternalStorageMedia {
	private boolean bFolderIsAvailable;
	private File directory;
	private FilenameFilter pictureFilter;
	private FilenameFilter videoFilter;

	ExternalStorageMedia(String sFolderName) {
		bFolderIsAvailable = false;
		directory = null;
		
		String storageState = Environment.getExternalStorageState();

	    if ( Environment.MEDIA_MOUNTED.equals(storageState) || 
	          Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState) )  {
		    File rootPath = Environment.getExternalStorageDirectory();
		    directory = new File(rootPath, sFolderName); 
		    if ( directory.exists() ) {
		    	bFolderIsAvailable = true;
		    }	        
	    } // if MEDIA_MOUNTED
	    
	    pictureFilter = new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	        return (name.endsWith(".jpeg") || 
	        		name.endsWith(".JEPG") ||
	                name.endsWith(".jpg") || 
	                name.endsWith(".JPG") || 
	                name.endsWith(".png") ||
	                name.endsWith(".PNG") );
	        }
	    };
	    
	    videoFilter = new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	        return (name.endsWith(".mp4") || 
	        		name.endsWith(".MP4") );
	        }
	    };

	} // constructor end
	
	public String[] getPictureArray() {
		return getFileArray(pictureFilter);
	}
	
	public String[] getVideoArray() {
		return getFileArray(videoFilter);
	}
	
	private String[] getFileArray(FilenameFilter fileFilter) {
		String[] sNamelist = null;
		if ( bFolderIsAvailable && (directory != null) ) {
			sNamelist = directory.list(fileFilter);
			String sPath = directory.getPath();			
			for (int i = 0; i < sNamelist.length; i++) {
				sNamelist[i] = sPath + '/' + sNamelist[i];
			}
		}
		return sNamelist;
	} // public String[] getFileArray

}
