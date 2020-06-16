package be.kakumi.custommclauncher.utils;

import be.kakumi.custommclauncher.Config;
import be.kakumi.custommclauncher.LauncherFrame;
import be.kakumi.custommclauncher.ram.RamManager;
import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;

import java.io.File;
import java.util.Arrays;

public class LauncherManager {
    private static Thread updateThread;
    private static CrashReporter crashReporter = new CrashReporter("Error", Config.DIR_CRASH);
    private static File launcherProp = new File(Config.DIR, "launcher.properties");
    private static AuthInfos authInfos;

    public static void update()throws Exception {
        SUpdate su = new SUpdate(Config.SITE_WEB_UPDATER, Config.DIR);

        su.getServerRequester().setRewriteEnabled(true);
        su.addApplication(new FileDeleter());

        updateThread = new Thread() {
            private int val;
            private int max;
            private double pourcentage;

            public void run() {
                while (!isInterrupted()) {
                    if (BarAPI.getNumberOfFileToDownload() == 0) {
                        ProgressBarLoading.loadingPoint();
                    }
                    else {
                        LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setVisible(true);
                        this.val = ((int)(BarAPI.getNumberOfTotalDownloadedBytes() / 1000L));
                        this.max = ((int)(BarAPI.getNumberOfTotalBytesToDownload() / 1000L));
                        LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(this.max);
                        LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(this.val);
                        pourcentage = Swinger.percentage(this.val, this.max);
                        if (pourcentage > 100) pourcentage = 100;
                        LauncherFrame.getInstance().getLauncherPanel().setTextProgressBar("Telechargement en cours.. (" + pourcentage + "%)");

                    }
                }
            }
        };
        updateThread.start();
        su.start();
        updateThread.interrupt();
    }

    public static void launch() throws LaunchException {
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(Config.INFOS, GameFolder.BASIC, authInfos);
        //profile.getVmArgs().addAll(ram.exists() ? Integer.parseInt(RamManager.getRam()) > 0 ? Arrays.asList("-Xms1G","-Xmx"+RamManager.getRam()+"G") : Arrays.asList("-Xms1G","-Xmx2G") : Arrays.asList("-Xms1G","-Xmx2G"));
        profile.getVmArgs().addAll(Utils.hasJava64Bits() ? launcherProp.exists() ? Integer.parseInt(RamManager.getRam()) > 0 ? Arrays.asList("-Xms1G","-Xmx"+RamManager.getRam()+"G") : Arrays.asList("-Xms1G","-Xmx2G") : Arrays.asList("-Xms1G","-Xmx2G") : Arrays.asList("-Xms1G","-Xmx1G"));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        Process p = launcher.launch();
        if (Config.USE_DISCORD_INTEGRATION) {
            LauncherFrame.getInstance().getDiscord().setDiscordDetails("Playing (" + authInfos.getUsername() + ")");
        }
        LauncherFrame.getInstance().setVisible(false);

        try {
            //Thread.sleep(5000L);
            //System.exit(0);
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void auth(String username, String password) throws AuthenticationException {
        Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
        System.out.println("Starting authentification : " + username);
        AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
        authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());
    }

    public static void interruptThread()
    {
        updateThread.interrupt();
    }

    public static CrashReporter getCrashReporter() {
        return crashReporter;
    }

    public static File getLauncherProp() {
        return launcherProp;
    }
}
