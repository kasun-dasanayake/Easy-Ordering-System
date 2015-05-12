package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class FormPanel extends JPanel {
	
	private JLabel buyerLable;
	private JLabel countLable;
	private JTextField buyerField;
	private JTextField countField;
	private JList<StateCategory> stateList;
	private JComboBox<String> type;
	private JCheckBox couponCheck;
	private JLabel couponLable;
	private JTextField couponField;
	private JButton addMenu;
	private JButton okButton;
	private SelectMenuDialog selectMenuDialog;
	private FormListener formListener;
	
	private JRadioButton paidRadio;
	private JRadioButton notPaidRadio;
	private ButtonGroup payGroup;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMaximumSize(dim);
		
		buyerLable = new JLabel("Buyer: ");
		countLable = new JLabel("Count: ");
		buyerField = new JTextField(10);
		countField = new JTextField(10);
		stateList = new JList<StateCategory>();
		type = new JComboBox<String>();
		couponCheck = new JCheckBox();
		addMenu = new JButton("add Menu");
		couponLable = new JLabel("Coupon: ");
		couponField = new JTextField(10);
		
		paidRadio = new JRadioButton("Paid");
		notPaidRadio = new JRadioButton("Not Paid");
		paidRadio.setActionCommand("paid");
		notPaidRadio.setActionCommand("Not Paid");
		payGroup = new ButtonGroup();
		notPaidRadio.setSelected(true);

		okButton = new JButton("OK");
		
		// set up Mnemonic
		okButton.setMnemonic(KeyEvent.VK_O);
		buyerLable.setDisplayedMnemonic(KeyEvent.VK_B);
		buyerLable.setLabelFor(buyerField);
		
		///// Set up Paid radios
		
		payGroup.add(notPaidRadio);
		payGroup.add(paidRadio); 
		
		////// set up Coupon 
		
		couponLable.setEnabled(false);
		couponField.setEnabled(false);
		
		couponCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = couponCheck.isSelected();
				couponLable.setEnabled(isTicked);
				couponField.setEnabled(isTicked);
			}
		});
		
		////// set up List box
		
		DefaultListModel<StateCategory> stateModel = new DefaultListModel<StateCategory>();
		stateModel.addElement(new StateCategory(0, "newOrder"));
		stateModel.addElement(new StateCategory(1, "activeOrder"));
		stateModel.addElement(new StateCategory(2, "finishedOrder"));
		stateList.setModel(stateModel);
		stateList.setPreferredSize(new Dimension(100,70));
		stateList.setBorder(BorderFactory.createEtchedBorder());
		stateList.setSelectedIndex(0);
		
		////// set up Combo box
		
		DefaultComboBoxModel<String> typeModel = new DefaultComboBoxModel<String>();
		typeModel.addElement("TakeAway");
		typeModel.addElement("Other");
		type.setModel(typeModel);
		type.setSelectedIndex(0);
		type.setEditable(true);
		
		addMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				recipeDialog.setVisible(true);
				
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String buyer = buyerField.getText();
				String count = countField.getText();
				StateCategory stateCat = stateList.getSelectedValue();
				String typeCat = (String) type.getSelectedItem();
				String couponValue = couponField.getText();
				boolean couponChecker = couponCheck.isSelected();
				String  payCommand = payGroup.getSelection().getActionCommand();
				
				FormEvent ev = new FormEvent(this, buyer, count,stateCat.getId(),
						typeCat,couponValue,couponChecker,payCommand);
				if(formListener != null){
					formListener.formEventOccurred(ev);
				}
			}
		});
		
		Border innerBorder = BorderFactory.createTitledBorder("Add Order");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
	
		layoutComponents();
	}
	
	private void layoutComponents(){
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//////// First row/ ///////

		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(buyerLable,gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(buyerField,gc);
		
		//////// Second row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(countLable,gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(countField,gc);

		////////Third row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("State: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(stateList,gc);
		
		////////Fourth row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Type: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(type,gc);

		////////fifth row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Coupon ?? : "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(couponCheck,gc);
		
		////////sixth row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(couponLable,gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(couponField,gc);

		////////seventh row/ ///////
		

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(addMenu,gc);

		////////seven1th row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.05;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Bill: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(notPaidRadio,gc);

		////////eghitth row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(paidRadio,gc);
		
		//////// Third row/ ///////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2.0;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(okButton,gc);
	}
	
	public void setFormListener(FormListener listener){
		this.formListener = listener;
	}

class StateCategory {
	private int id;
	private String text;
	
	public StateCategory(int id, String text){
		this.id = id;
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public int getId() {
		return id;
	}
	
}
	
}
