package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener{

	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener stringListener;
	
	public Toolbar(){
		//setBorder(BorderFactory.createEtchedBorder());
		
		//setFloatable(false);
		
		saveButton = new JButton();
		saveButton.setIcon(createIcon("/images/save.gif"));
		saveButton.setToolTipText("Save");
		
		refreshButton = new JButton();
		refreshButton.setIcon(createIcon("/images/refresh.gif"));
		refreshButton.setToolTipText("Refresh");
		
		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);
		
		add(saveButton);
		//addSeparator();
		add(refreshButton);
	}
	
	private ImageIcon createIcon(String path){
		URL url = getClass().getResource(path);
		if(url == null){
			System.err.println("Unable to load image");
		}
		ImageIcon icon = new ImageIcon(url);
		return icon;
	}

	public void setToolbarListner(ToolbarListener listener) {
		this.stringListener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if(clicked == saveButton){
			if(stringListener != null){
				stringListener.saveEventOccured();
			}
		} else if(clicked == refreshButton){
			if(stringListener != null){
				stringListener.refreshEventOccured();
			}
		}
	}
}
