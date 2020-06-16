package be.kakumi.custommclauncher.panel;

import be.kakumi.custommclauncher.Config;
import be.kakumi.custommclauncher.LauncherFrame;
import be.kakumi.custommclauncher.ram.RamFrame;
import be.kakumi.custommclauncher.utils.LauncherManager;
import be.kakumi.custommclauncher.utils.Utils;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import org.jasypt.util.text.BasicTextEncryptor;
import sun.misc.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class LauncherPanel extends JPanel implements SwingerEventListener {
    private Image background = Swinger.getResource("background.png");
    public static Font customTextFont;

    File launcherProp = new File(Config.DIR, "launcher.properties");
    private Saver saver = new Saver(launcherProp);
    private JTextField usernameField = new JTextField(saver.get("username", ""));
    private JPasswordField passwordField = new JPasswordField("");
    private STexturedButton playButton = new STexturedButton(Swinger.getResource("play_button.png"), Swinger.getResource("play_button.png"));
    private STexturedButton closeButton = new STexturedButton(Swinger.getResource("close_button.png"), Swinger.getResource("close_button.png"));
    private STexturedButton minimizeButton = new STexturedButton(Swinger.getResource("minimize_button.png"), Swinger.getResource("minimize_button.png"));
    private STexturedButton optionsButton = new STexturedButton(Swinger.getResource("options_button.png"), Swinger.getResource("options_button.png"));
    private STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord_button.png"), Swinger.getResource("discord_button.png"));
    private STexturedButton twitterButton = new STexturedButton(Swinger.getResource("twitter_button.png"), Swinger.getResource("twitter_button.png"));
    private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100), new Color(30, 206, 30, 150));
    private JLabel progressBarText = new JLabel("", SwingConstants.CENTER);
    BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    public LauncherPanel() {
        basicTextEncryptor.setPasswordCharArray(Config.ENCRYPTION_KEY.toCharArray());

        if (!launcherProp.exists()) {
            try {
                launcherProp.getParentFile().mkdirs();
                launcherProp.createNewFile();
                saver.set("username", "");
                saver.set("password", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Font retroFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream(Config.RESOURCES_PATH + "primitive.ttf")));
            customTextFont = retroFont.deriveFont(40f);
        } catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }

        this.setLayout(null);
        usernameField.setFont(customTextFont.deriveFont(30f));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(358, 298, 260, 45);
        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    startGame();
                }
            }
        });
        this.add(usernameField);

        if (Config.AUTO_LOAD_PASSWORD) {
            String pwd = saver.get("password", "");
            if (!pwd.equals("")) pwd = basicTextEncryptor.decrypt(pwd);
            passwordField.setText(pwd);
        }
        passwordField.setFont(getFont().deriveFont(30f));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(358, 418, 260, 45);
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    startGame();
                }
            }
        });
        this.add(passwordField);

        playButton.setBounds(358, 500);
        playButton.setSize(250, 85); // 300, 100
        playButton.addEventListener(this);
        this.add(playButton);

        closeButton.setBounds(Config.WINDOW_WIDTH - 120, 160);
        closeButton.setSize(40,40);
        closeButton.addEventListener(this);
        this.add(closeButton);

        minimizeButton.setBounds(Config.WINDOW_WIDTH - 165, 160);
        minimizeButton.setSize(40,40);
        minimizeButton.addEventListener(this);
        this.add(minimizeButton);

        optionsButton.setBounds(Config.WINDOW_WIDTH - 210, 160);
        optionsButton.setSize(40,40);
        optionsButton.addEventListener(this);
        this.add(optionsButton);

        discordButton.setBounds(80, 298);
        discordButton.setSize(64,64);
        discordButton.addEventListener(this);
        this.add(discordButton);

        twitterButton.setBounds(80, 418);
        twitterButton.setSize(64,64);
        twitterButton.addEventListener(this);
        this.add(twitterButton);

        progressBar.setBounds(75, Config.WINDOW_HEIGHT - 70, Config.WINDOW_WIDTH - 140, 20);
        this.add(progressBar);

        progressBarText.setFont(customTextFont.deriveFont(18f));
        progressBarText.setBounds(0, Config.WINDOW_HEIGHT - 70, Config.WINDOW_WIDTH, 20);
        progressBarText.setForeground(Color.WHITE);
        this.add(progressBarText);

        setComponentZOrder(progressBar, 1);
        setComponentZOrder(progressBarText, 0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (swingerEvent.getSource() == playButton) {
            startGame();
            return;
        }

        if (swingerEvent.getSource() == closeButton) {
            System.exit(0);
            return;
        }
        if (swingerEvent.getSource() == minimizeButton) {
            LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
            return;
        }

        if (swingerEvent.getSource() == optionsButton) {
            if (Utils.hasJava64Bits()) {
                LauncherFrame.getInstance().setVisible(false);
                new RamFrame();
            } else {
                JOptionPane.showMessageDialog(this, "You don't have java 64 bits " + System.getProperty("sun.arch.data.model"), "Error", JOptionPane.ERROR_MESSAGE);
                Utils.sendMessageInConsole("You don't have java 64 bits " + System.getProperty("sun.arch.data.model"), false);
            }
        }

        if (swingerEvent.getSource() == discordButton) {
            try {
                Utils.openURL("https://google.be/");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return;
        }

        if (swingerEvent.getSource() == twitterButton) {
            try {
                Utils.openURL("https://google.be/");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void setFieldsEnable(boolean enable) {
        usernameField.setEnabled(enable);
        passwordField.setEnabled(enable);
        playButton.setEnabled(enable);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }

    public void setTextProgressBar(String texte) {
        progressBarText.setText(texte);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    private void startGame() {
        setFieldsEnable(false);

        if (usernameField.getText().replace(" ", "").length() == 0 || passwordField.getText().replace(" ", "").length() == 0) {
            JOptionPane.showMessageDialog(this, "Please, enter a valid pseudo and password !", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnable(true);
            return;
        }

        Thread starting = new Thread() {
            @Override public void run() {

                try {
                    LauncherManager.auth(usernameField.getText(), passwordField.getText());
                } catch (AuthenticationException e) {
                    JOptionPane.showMessageDialog(LauncherPanel.this, "Unable to connect : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    LauncherPanel.this.setFieldsEnable(true);
                    return;
                }

                if (!launcherProp.exists()) {
                    launcherProp.getParentFile().mkdirs();
                    try {
                        launcherProp.createNewFile();
                        System.out.println("File properties created !");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                saver.set("username", usernameField.getText());
                if (Config.AUTO_LOAD_PASSWORD) {
                    saver.set("password", basicTextEncryptor.encrypt(passwordField.getText()));
                } else {
                    saver.set("password", "");
                }

                try
                {
                    LauncherManager.update();
                }
                catch (Exception e)
                {
                    LauncherManager.interruptThread();
                    LauncherManager.getCrashReporter().catchError(e, "Unable to update !");
                    //JOptionPane.showMessageDialog(LauncherPanel.this, "Impossible de faire la mise à jour : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    LauncherPanel.this.setFieldsEnable(true);
                    return;
                }

                try
                {
                    LauncherManager.launch();
                }
                catch (LaunchException e)
                {
                    LauncherManager.getCrashReporter().catchError(e, "Unable to start the game !");
                    //JOptionPane.showMessageDialog(LauncherPanel.this, "Impossible de lancer le jeu : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    LauncherPanel.this.setFieldsEnable(true);
                }
            }
        };
        starting.start();
    }
}
