package be.kakumi.custommclauncher.ram;

import be.kakumi.custommclauncher.Config;
import be.kakumi.custommclauncher.utils.LauncherManager;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;

@SuppressWarnings("serial")
public class RamFrame extends JFrame {

	private RamPanel ramPanel;
	private RamFrame instance;
	
	public RamFrame() {
		setTitle(Config.LAUNCHER_NAME + " - Options");
		setSize(600, 450);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setIconImage(Swinger.getResource("logo.png"));
		setContentPane(ramPanel = new RamPanel(this));
		WindowMover mover = new WindowMover(this);
		addMouseListener(mover);
		addMouseMotionListener(mover);
		setBackground(Swinger.TRANSPARENT);
		setVisible(true);
		instance = this;
	}
}
