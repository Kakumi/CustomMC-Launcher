package be.kakumi.custommclauncher;

import be.kakumi.custommclauncher.panel.LauncherPanel;
import be.kakumi.custommclauncher.utils.Discord;
import be.kakumi.custommclauncher.utils.LauncherManager;
import com.sun.awt.AWTUtilities;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.awt.*;

public class LauncherFrame extends JFrame {
    private static LauncherFrame instance;

    private LauncherPanel launcherPanel;
    private Discord discord;

    public LauncherFrame() {
        this.setTitle(Config.LAUNCHER_NAME);
        this.setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResource("logo.png"));
        launcherPanel = new LauncherPanel();
        this.setContentPane(launcherPanel);
        AWTUtilities.setWindowOpacity(this, 0.0f);

        //Fond transparent
        this.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);

        if (Config.USE_DISCORD_INTEGRATION) {
            discord = new Discord();
        }

        Animator.fadeInFrame(this, 2);
    }

    public static void main(String[] args) {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath(Config.RESOURCES_PATH);
        Config.DIR_CRASH.mkdirs();

        instance = new LauncherFrame();
    }

    public LauncherPanel getLauncherPanel()
    {
        return launcherPanel;
    }

    public static LauncherFrame getInstance() {
        return instance;
    }

    public Discord getDiscord() {
        return discord;
    }
}
