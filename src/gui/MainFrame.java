package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.sound.midi.Receiver;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import Controller.Controller;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 3636641408840767506L;
	private TablePanel tablePanel;
	private FormPanel formPanel;
	private Toolbar toolbar;
	private JFileChooser fileChooser;
	private Controller controller;
	private PrefsDialog prefsDialog;
	private RecipeDialog recipeDialog;
	private Preferences prefs;
	private JSplitPane splitPane;
	private JTabbedPane tabPane;
	private MessagePanel messagePanel;
	private RecipesTable recipesTable;
	private TablesTable tablesTable;
	private UsersTable usersTable;
	
	public MainFrame(){
		super("Restaurant");
		
		setLayout(new BorderLayout());
		
		toolbar = new Toolbar();
		tablePanel = new TablePanel();
		formPanel = new FormPanel();
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new OrderFileFilter());
		prefsDialog = new PrefsDialog(this);
		recipeDialog = new RecipeDialog(this);
		tabPane = new JTabbedPane();
		messagePanel = new MessagePanel();
		recipesTable = new RecipesTable();
		tablesTable = new TablesTable();
		usersTable = new UsersTable();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,formPanel,tabPane);
		splitPane.setOneTouchExpandable(true);
		
		tabPane.addTab("Orders", tablePanel);
		tabPane.addTab("Recipes", recipesTable);
		tabPane.addTab("Tables", tablesTable);
		tabPane.addTab("Users", usersTable);
		tabPane.addTab("Messages", messagePanel);
		
		
		prefs = Preferences.userRoot().node("db");
		
		controller = new Controller();
		
		tablePanel.setData(controller.getOrders());
		tablePanel.addOrderTableListener(new OrderTableListener(){
			public void rowDeleted(int row){
				controller.removeOrder(row);
			}
		});
		
		recipesTable.setData(controller.getRecipes());
		tablesTable.setData(controller.getTables());
		usersTable.setData(controller.getUsers());
		
		prefsDialog.setPrefsListener(new PrefsListener() {
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
			}
		});
		
		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		Integer port = prefs.getInt("port", 3306);
		prefsDialog.setDefaults(user, password, port);
		
		setJMenuBar(createMenuBar());
		
		toolbar.setToolbarListner(new ToolbarListener() {
			public void saveEventOccured() {
				connect();
				try {
					controller.orderSave();
					controller.recipeSave();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this,"Unble to save to Database",
							"Database connection error", JOptionPane.ERROR_MESSAGE);
				}
			}

			public void refreshEventOccured() {
				connect();
				try {
					controller.orderLoad();
					controller.recipeLoad();
					controller.tableLoad();
					controller.userLoad();
				} catch (SQLException e) {
					System.out.println(e);
					JOptionPane.showMessageDialog(MainFrame.this,"Unble to load from Database",
							"Database connection error", JOptionPane.ERROR_MESSAGE);
				}
				tablePanel.refresh();
				recipesTable.refresh();
				tablesTable.refresh();
				usersTable.refresh();
			}
		});;
		
		
		formPanel.setFormListener(new FormListener(){
			public void formEventOccurred(FormEvent e){
				controller.addOrder(e);
				tablePanel.refresh();
			}
		});
		
		recipeDialog.setFromListener(new RecipeFormListener() {
			@Override
			public void recipeformEventOccurred(RecipeFormEvent e) {
				controller.addRecipeOrder(e);
				recipesTable.refresh();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.out.println("window closing");
				dispose();
				System.gc();
			}
			
		});

		add(toolbar,BorderLayout.PAGE_START);
		add(splitPane,BorderLayout.CENTER);
		
		setMinimumSize(new Dimension(600,450));
		setSize(800,600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void connect(){
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this,"Cannot connect to Database",
					"Database connection error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu newMenu = new JMenu("New");
		JMenuItem orderItem = new JMenuItem("Order");
		JMenuItem recipeItem = new JMenuItem("Recipe");
		JMenuItem tableItem = new JMenuItem("Table");
		JMenuItem userItem = new JMenuItem("user");
		JMenuItem exportItem = new JMenuItem("Export Data...");
		JMenuItem importItem = new JMenuItem("import Data...");
		JMenuItem close = new JMenuItem("Exit");
		newMenu.add(orderItem);
		newMenu.add(recipeItem);
		newMenu.add(tableItem);
		newMenu.add(userItem);
		fileMenu.add(newMenu);
		fileMenu.add(exportItem);
		fileMenu.add(importItem);
		fileMenu.addSeparator();
		fileMenu.add(close);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		
		JCheckBoxMenuItem showMenuItem = new JCheckBoxMenuItem("Order Form");
		showMenuItem.setSelected(true);
		showMenu.add(showMenuItem);
		windowMenu.add(prefsItem);
		windowMenu.add(showMenu);
		
		prefsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});
		
		recipeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recipeDialog.setVisible(true);
			}
		});
		
		showMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JCheckBoxMenuItem menuItem =(JCheckBoxMenuItem) ev.getSource();
				
				if(menuItem.isSelected()){
					splitPane.setDividerLocation((int)formPanel.getMinimumSize().getWidth());
				}
				formPanel.setVisible(menuItem.isSelected());
			}
		});
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		close.setMnemonic(KeyEvent.VK_X);

		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
		
		importItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
		exportItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file.", "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the application",
						"Confirm Exit",JOptionPane.OK_CANCEL_OPTION);
				if(action == JOptionPane.OK_OPTION){
					WindowListener[] listeners = getWindowListeners();
					for(WindowListener listener : listeners){
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});
		
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		
		return menuBar;
	}
}
