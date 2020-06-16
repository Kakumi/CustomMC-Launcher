package be.kakumi.custommclauncher.utils;

import be.kakumi.custommclauncher.Config;
import be.kakumi.custommclauncher.LauncherFrame;
import fr.theshark34.supdate.BarAPI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
	public static void openURL(String url) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI(url));
	}
	
    public static void sendMessageInConsole(Object message, boolean error) {
    	LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String str = dateTime.format(timeFormatter);
    	 if(error) {
     		System.err.println("["+str+"] " + "[" + Config.LAUNCHER_NAME + "] " + message);
     	}else {
     		System.out.println("["+str+"] " + "[" + Config.LAUNCHER_NAME + "] " + message);
     	}
    }
    
    public static boolean hasJava64Bits() {
    	if(System.getProperty("sun.arch.data.model").contains("64")) {
    		return true;
    	}else{
        	return false;
    	}
    }
    
    public static void tryToExit() {
		if (BarAPI.getNumberOfDownloadedFiles() != BarAPI.getNumberOfFileToDownload() &&
				JOptionPane.showConfirmDialog(LauncherFrame.getInstance(),
						"Do you really want to interrupt the download in progress?", "Download in progress",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
				) != JOptionPane.YES_OPTION)
			return;
		System.exit(0);
	}
}
