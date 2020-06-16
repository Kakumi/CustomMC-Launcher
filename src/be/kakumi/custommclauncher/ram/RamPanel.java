package be.kakumi.custommclauncher.ram;

import be.kakumi.custommclauncher.LauncherFrame;
import be.kakumi.custommclauncher.panel.LauncherPanel;
import be.kakumi.custommclauncher.utils.LauncherManager;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

@SuppressWarnings("serial")
public class RamPanel extends JPanel {

	public Image background = Swinger.getResource("background_option.png");
	private STexturedButton valider;
	public JSlider ramSlider = new JSlider();
	public JLabel ramTitle = new JLabel("Ram :");
	public JLabel ramLabel = new JLabel(LauncherManager.getLauncherProp().exists() ? RamManager.getRam() + "Go" : "1Go");
	private RamFrame ramFrame;

	public RamPanel(RamFrame ramFrame) {
		this.ramFrame = ramFrame;
		setLayout(null);

		ramTitle.setFont(LauncherPanel.customTextFont);
		ramTitle.setForeground(Color.black);
		ramTitle.setBounds(125, 215, 200, 48);
		add(ramTitle);

		ramLabel.setFont(LauncherPanel.customTextFont);
		ramLabel.setForeground(Color.black);
		ramLabel.setBounds(325, 215, 110, 48);
		add(ramLabel);

		ramSlider.setMinimum(1);
		ramSlider.setMaximum(8);
		ramSlider.setBackground(Swinger.TRANSPARENT);
		ramSlider.setOpaque(false);
		ramSlider.setBounds(125, 250, 310, 50);
		ramSlider.setFocusable(false);
		ramSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				RamManager.setRam(ramSlider.getValue() + "Go");
				ramLabel.setText(ramSlider.getValue() + "Go");
			}
		});
		ramSlider.setValue(LauncherManager.getLauncherProp().exists() ? Integer.parseInt(RamManager.getRam()) : 1);
		valider = new STexturedButton(Swinger.getResource("valid_ram.png"), Swinger.getResource("valid_ram.png"));
		valider.setBounds(165, 300);
		valider.addEventListener(new SwingerEventListener() {
			
			@Override
			public void onEvent(SwingerEvent arg0) {
				LauncherFrame.getInstance().setVisible(true);
				ramFrame.setVisible(false);
				
			}
		});
		valider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(ramLabel);
		add(ramSlider);
        add(valider);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Swinger.drawFullsizedImage(g, this, this.background);
	}
	
	public RamFrame getRamFrame() {
		return ramFrame;
	}

}
