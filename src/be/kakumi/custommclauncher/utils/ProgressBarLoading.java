package be.kakumi.custommclauncher.utils;

import be.kakumi.custommclauncher.LauncherFrame;

public class ProgressBarLoading {

	private static int nbPoints = 0;

	public static void loadingPoint() {
		new Thread();
		try {
			Thread.sleep(370L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		StringBuilder points = new StringBuilder();
		for (int i = 0; i < nbPoints; i++) {
			points.append(".");
		}
		nbPoints++;
		if (nbPoints > 3) nbPoints = 0;

		LauncherFrame.getInstance().getLauncherPanel().setTextProgressBar("File scanning" + points);
	}
	
}
