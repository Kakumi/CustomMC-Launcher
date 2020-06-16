package be.kakumi.custommclauncher;

import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;

import java.io.File;

public class Config {
    //Launcher options
    public static final String LAUNCHER_NAME = "Custom MC Launcher";
    public static final GameVersion VERSION = new GameVersion("1.12", GameType.V1_8_HIGHER);
    public static int WINDOW_WIDTH = 975;
    public static int WINDOW_HEIGHT = 685;
    public static final String RESOURCES_PATH = "/be/kakumi/custommclauncher/ressources/";

    //Game Directory options
    public static final GameInfos INFOS = new GameInfos(LAUNCHER_NAME.replaceAll(" ", "-").toLowerCase(), VERSION, new GameTweak[] {GameTweak.FORGE});
    public static final File DIR = INFOS.getGameDir();
    public static final File DIR_CRASH = new File(DIR, "crash-reports");

    //Update options
    public static final String SITE_WEB_UPDATER = "Your S-Update link";

    //Discord options
    public static final String DISCORD_LINK = "Your discord link";
    public static final boolean USE_DISCORD_INTEGRATION = true;
    public static final String DISCORD_LARGE_IMG_KEY = "logo";
    public static final String DISCORD_APP_ID = "Your Discord App ID";

    //Encryption options
    public static final boolean AUTO_LOAD_PASSWORD = true;
    public static final String ENCRYPTION_KEY = "gg1rGR564eg";
}
