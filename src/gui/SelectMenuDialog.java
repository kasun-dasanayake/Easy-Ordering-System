package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SelectMenuDialog extends JDialog {

	private JButton okButton;
	private JButton cancelButton;
	private JTextField nameField;
	private JTextField priceField;
	
	private RecipeFormListener recipeListener;
	
	public SelectMenuDialog(JFrame parent) {
		super(parent, "New Recipe");
		
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		nameField = new JTextField(10);
		priceField = new JTextField(10);
		
		
		layoutControls();
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String price = priceField.getText(); 
				
				RecipeFormEvent ev = new RecipeFormEvent(this,name,price);
				if(recipeListener != null){
					recipeListener.recipeformEventOccurred(ev);;
				}
				//System.out.println(ev);
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				setVisible(false);
			}
		});
		
		setSize(350,200);
		setLocationRelativeTo(parent);
	}
	

	public void setFromListener(RecipeFormListener recipeListener) {
		this.recipeListener = recipeListener;
	}

	private void layoutControls(){
		
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		int space = 10;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("add Recipe");
		
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));
		
		controlsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();

		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);
		
		//////// first row////////
		
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Recipe Name: "),gc);
		
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(nameField,gc);
		
		//////////// next row //////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		
		gc.gridx = 0;

		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Recipe Price: "),gc);
		
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(priceField,gc);
		
		
		/////////////////// Buttons /////////
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);
		
		// add sub panels to dialog
		setLayout(new BorderLayout());
		add(controlsPanel,BorderLayout.CENTER);
		add(buttonsPanel,BorderLayout.SOUTH);
	}
}
