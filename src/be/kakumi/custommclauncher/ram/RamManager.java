package be.kakumi.custommclauncher.ram;

import be.kakumi.custommclauncher.utils.LauncherManager;
import fr.theshark34.openlauncherlib.util.Saver;


public class RamManager {

	private static Saver saver = new Saver(LauncherManager.getLauncherProp());

	public static String getRam() {
		String ram = saver.get("ram", "1");
		ram = ram.replaceAll("[a-zA-Z]", "");

		return ram;
	}

	public static void setRam(String valeur) {
		saver.set("ram", valeur);
	}

	/*
	public static String getRam() {
		int ram = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(LauncherManager.DIR, "ram.properties")));
			String line;
			while ((line = br.readLine()) != null) {
				ram = Integer.parseInt(line.substring(0, line.length() - 2));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = Integer.toString(ram);
		return str;
	}

	public static void setRam(String val) {
		File ramfile = new File(LauncherManager.DIR, "ram.properties");
		if (!ramfile.exists()) {
			try {
				ramfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter rama = new FileWriter(new File(LauncherManager.DIR, "ram.properties"));
			rama.write(val);
			rama.close();
		} catch (Exception localException) {}
	}
	*/
}
