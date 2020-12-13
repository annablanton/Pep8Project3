package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class for GUI for pep8 virtual machine project.
 * This class mainly includes main frame, up-panel, line-start panel, 
 * line-end panel, menu bar and their components. 
 ** OPEN & Load button open the file chooser to read codes info from .txt file
 ** start & run & run code button read object code and print output
 ** new button close and reopen the application
 ** save button save the input and output result into src/result.txt file
 ** Cut object code button is copying the object code
 ** paste object code button is pasting copy codes into the object code.
 *
 * @author GROUP 6 Taehong Kim
 * @version 12/13/2020
 */
public class GUI extends JFrame implements ActionListener{ 
	/**SerialVersion for GUI class.**/
	private static final long serialVersionUID = 1L;
	/**Setting Object code text area.**/
	private JTextArea ObjCode;
	/**Setting Main frame.**/
	private JFrame frame;
	/**Setting up-Panel.**/
	private JPanel upPanel = new JPanel();
	/**Setting Line Start Panel.**/
	private JPanel lineStartPanel = new JPanel();
	/**Setting Center main Panel.**/
	private JPanel CenterPanel;
	/**Setting font type for titles.**/
	final Font myFont = new Font("Plain", Font.BOLD, 17);
	/**Setting font types for inside of text area.**/
	final Font myFont2 = new Font("Plain", Font.PLAIN,9);
	/**Setting CPU panel.**/
	private JPanel CpuPanel;
	/**Setting top of CPU panel.**/
	private JPanel CpupanelTop;
	/**Setting Accumulator text area.**/
	private JTextField Accumulatorout1;
	/**Setting Accumulator second text area.**/
	private JTextField Accumulatorout2;
	/**Setting program counter text area.**/
	private JTextField ProgramCounterout1;
	/**Setting program counter text second area.**/
	private JTextField ProgramCounterout2;
	/**Setting Instruction text area.**/
	private JTextField Instructionout1;
	/**Setting Batch IO text area.**/
	private JTextArea BatchIO;
	/**Setting Output text area.**/
	private JTextArea Outputtext;
	/**Setting Line-End main Panel.**/
	private JPanel LineEndPanel;
	/**Setting Memory Panel.**/
	private JTextArea Memory;
	/**Setting Scroll for memory panel.**/
	private JScrollPane scroll;
	/**Setting main Menu bar.**/
	private JMenuBar menuBar;
	/**Setting source code tab with text area.**/
	private JTextArea sourceTab;
	/**Setting Text area for Aslisting.**/
	private JTextArea AsListing;
	/**Setting down of cpu panel.**/
	private JPanel Cpupaneldown;
	/**Setting Operand text field1.**/
	private JTextField Operand1;
	/**Setting Operand text field2.**/
	private JTextField Operand2;
	/**Setting screensize to calculate size of application**/
	final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/**Tells which character of BatchIO to send**/
	private int batchIndex = -1;
	/**Setting N flag box**/
	private JCheckBox Nbox;
	/**Setting Z flag box**/
	private JCheckBox Zbox;
	/**Setting V flag box**/
	private JCheckBox Vbox;
	/**Setting C flag box**/
	private JCheckBox Cbox;

	/**
	 * Main method to run GUI methods
	 * @param args args
	 */
	public static void main(String []args) {
		new GUI();
	}
	
    /**
     * Constructor Virtual simulator GUI with frame. 
     */
	public GUI() {
		frame = new JFrame("Virtual Simulator");	
		frame.setSize(screenSize.width*3/4,screenSize.height*3/4); // frame size
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} 
		});	
		MenuBar();
		frame.setJMenuBar(menuBar);
		UpPanel();
		frame.getContentPane().add(upPanel, BorderLayout.PAGE_START);
		LineStartPanel();
		frame.getContentPane().add(lineStartPanel, BorderLayout.LINE_START);
		CentralPanel();
		frame.getContentPane().add(CenterPanel, BorderLayout.CENTER);	
		LineEndPanel();
		frame.getContentPane().add(LineEndPanel, BorderLayout.LINE_END);
		frame.pack();
	
	}
	
	/**
	 * Constructor Up-Panel with buttons.
	 */
	private void UpPanel() {
		/*setting main UpPanel*/
		upPanel = new JPanel();
		upPanel.setLayout(new GridLayout(1,9));
		upPanel.setPreferredSize(new Dimension (screenSize.width*3/4,screenSize.height*3/50));
		/*setting buttons on main upPanel*/
		final JButton newButton = new JButton("New");
		newButton.setFont(myFont);
		newButton.addActionListener(this);
		newButton.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setFont(myFont);
		saveButton.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));

		JButton runObject = new JButton("Run Object");
		runObject.setFont(myFont);
		runObject.addActionListener(this);
		runObject.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));

		JButton debugObjectButton = new JButton("Debug Object");
		debugObjectButton.addActionListener(this);
		debugObjectButton.setFont(myFont);
		debugObjectButton.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));

		JButton assebleButton = new JButton("Assemble");
		assebleButton.setFont(myFont);
		assebleButton.setEnabled(true);
		assebleButton.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));
		assebleButton.addActionListener(this);

		JButton runSourceButton = new JButton("Run Source");
		runSourceButton.setFont(myFont);
		runSourceButton.setEnabled(true);
		runSourceButton.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));
		runSourceButton.addActionListener(this);

		JButton debugSourceButton = new JButton("Debug Source");
		debugSourceButton.setFont(myFont);
		debugSourceButton.setEnabled(true);
		debugSourceButton.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));
		debugSourceButton.addActionListener(this);

		/*Setting empty panels to fill empty areas of upPanel*/
		JPanel emptyPanel = new JPanel();
		emptyPanel.setLayout(new GridLayout(1,1));
		emptyPanel.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));
		JPanel emptyPanel2 = new JPanel();
		emptyPanel2.setLayout(new GridLayout(1,1));
		emptyPanel2.setPreferredSize(new Dimension (screenSize.width/12,screenSize.height*3/50));

		/*Adding whole buttons in UpPanel*/
		upPanel.add(newButton);upPanel.add(saveButton);upPanel.add(runObject);
		upPanel.add(debugObjectButton);upPanel.add(assebleButton);upPanel.add(runSourceButton);
		upPanel.add(debugSourceButton);upPanel.add(emptyPanel);
	}
	
	/**
	 * Constructor for line start Panel with components small panels.
	 */
	private void LineStartPanel () {
		/*setting main Line Start Panel*/
		lineStartPanel = new JPanel();
		lineStartPanel.setLayout(new GridLayout(1,1));
		lineStartPanel.setBorder(new TitledBorder(null, "CODE", TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		lineStartPanel.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*9/14));	
		/*Create first sub-panel with Tabbed pane*/
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setSize(screenSize.width/4,screenSize.height*9/14);
		tabbedPane.setFont(myFont);
		/*Setting first tab panel*/
		JPanel firstTab = new JPanel();
		firstTab.setLayout(new GridLayout(3,1));
		/*Setting trace tab with text area*/
		JTextArea traceTab = new JTextArea(null,screenSize.width/4,screenSize.height*3/14);
		traceTab.setEditable(false);
		traceTab.setLineWrap(true);
		/*Adding tabs to the tabbed pane*/
		tabbedPane.addTab("Code", firstTab); tabbedPane.addTab("Trace", traceTab);
		/*Add tabbedpane to lineStartPanel*/
		lineStartPanel.add(tabbedPane);
		/*Setting source code text areas and add to first tab panel*/
		sourceTab = new JTextArea("Please type your Souce Code here",screenSize.width/4,screenSize.height*3/14);
		sourceTab.setFont(myFont);
		sourceTab.setLineWrap(false);
		firstTab.add(sourceTab);
		scroll = new JScrollPane(sourceTab);
		scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		firstTab.add(scroll);
		/*Setting object code text areas and add to first tab panel*/
		ObjCode = new JTextArea(null,screenSize.width/4,screenSize.height*3/14);
		ObjCode.setFont(myFont);
		ObjCode.setBorder(new TitledBorder(null, "Object Code", TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		ObjCode.setLineWrap(true);
		firstTab.add(ObjCode);
		scroll = new JScrollPane(ObjCode);
		scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		firstTab.add(scroll);
		/*Setting Aslisting text areas and add to first tab panel*/
		AsListing = new JTextArea(null,screenSize.width/4,screenSize.height*3/14);
		AsListing.setFont(myFont);
		AsListing.setEditable(false);
		AsListing.setLineWrap(true);
		AsListing.setBorder(new TitledBorder(null, "Error Listing", TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		firstTab.add(AsListing);
		scroll = new JScrollPane(AsListing);
		scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		firstTab.add(scroll);
	}
	
	/**
	 * Constructor for main Central Panel with component small panels.
	 */
	private void CentralPanel() {
		/*setting main Center Panel*/
		CenterPanel = new JPanel();
		CenterPanel.setLayout(new GridLayout(3,1));
		CenterPanel.setBorder(new TitledBorder(null, "CPU", TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		CenterPanel.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*9/14));
		/*setting cpu Center sub Panel*/
		CpuPanel = new JPanel();
		CpuPanel.setLayout(new GridLayout(2,1));
		CpuPanel.setBorder(new TitledBorder(null, null, TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		CpuPanel.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*3/14));
		/*setting  top Panel*/
		CpupanelTop = new JPanel();
		CpupanelTop.setLayout(new GridLayout(1,8));
		CpupanelTop.setSize(screenSize.width/4,screenSize.height/112);
		/*Calling components for buttons, labels, and text areas.*/
		CpuChecker();
		downCpuPanel();
		AccumulatorPanel();
		IndexRegister();
		StackPointer();
		ProgramCounterPanel();
		InstructionPanel();
		OperandSpecifier();
		Operand();
		step();
		BatchIO();
	}

	/**
	 * Constructor N,Z,V,C checker with labels for cpu panel. 
	 */
	private void CpuChecker() {
		/*adding check box with labels N.*/
		JLabel Nmark = new JLabel("N");
		Nmark.setFont(myFont);	
		Nmark.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Nmark);
		Nbox = new JCheckBox();
		Nbox.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Nbox);
			
		/*adding check box with labels Z.*/
		JLabel Zmark = new JLabel("Z");
		Zmark.setFont(myFont);	
		Zmark.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Zmark);	
		Zbox = new JCheckBox();
		Zbox.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Zbox);
			
		/*adding check box with labels V.*/
		JLabel Vmark = new JLabel("V");
		Vmark.setFont(myFont);	
		Vmark.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Vmark);	
		Vbox = new JCheckBox();
		Vbox.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Vbox);
			
		/*adding check box with labels C.*/
		JLabel Cmark = new JLabel("C");
		Cmark.setFont(myFont);	
		Cmark.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Cmark);	
		Cbox = new JCheckBox();
		Cbox.setSize(new Dimension(screenSize.width/112,screenSize.height/112));
		CpupanelTop.add(Cbox);
		CpuPanel.add(CpupanelTop,  BorderLayout.NORTH);
	}
	
	/**
	 * Constructor for down of cpu penl in main cpu penl.
	 */
	private void downCpuPanel() {
		/*Setting down of CPU panel*/
		Cpupaneldown = new JPanel();
		Cpupaneldown.setLayout(new GridLayout(8,3));
		Cpupaneldown.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*3/16));	
		CpuPanel.add(Cpupaneldown, BorderLayout.CENTER);
	}
	
	/**
	 * Constructor for Accumulator labels, text areas.
	 */
	private void AccumulatorPanel() {
		JLabel Accumulator = new JLabel("Accumulator");
		Accumulator.setFont(myFont2);
		Accumulator.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(Accumulator);
		Accumulatorout1 = new JTextField();
		Accumulatorout1.setEditable(false);
		Accumulatorout1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Accumulatorout1.setFont(myFont2);
		Cpupaneldown.add(Accumulatorout1);
		Accumulatorout2 = new JTextField();
		Accumulatorout2.setEditable(false);
		Accumulatorout2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Accumulatorout2.setFont(myFont2);
		Cpupaneldown.add(Accumulatorout2);
	}

	/**
	 * Constructor for ProgramCounter labels, text areas.
	 */
	private void ProgramCounterPanel() {
		JLabel ProgramCounter = new JLabel("ProgramCounter");
		ProgramCounter.setFont(myFont2);
		ProgramCounter.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(ProgramCounter);
		ProgramCounterout1 = new JTextField();
		ProgramCounterout1.setEditable(false);
		ProgramCounterout1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		ProgramCounterout1.setFont(myFont2);
		Cpupaneldown.add(ProgramCounterout1);
		ProgramCounterout2 = new JTextField();
		ProgramCounterout2.setEditable(false);
		ProgramCounterout2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		ProgramCounterout2.setFont(myFont2);
		Cpupaneldown.add(ProgramCounterout2);		
	}
	
	/**
	 * Constructor for StackPointer labels, text areas.
	 */
	private void StackPointer() {
		JLabel StackPointer = new JLabel("StackPointer");
		StackPointer.setFont(myFont2);
		StackPointer.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(StackPointer);
		JTextField StackPointer1 = new JTextField();
		StackPointer1.setEditable(false);
		StackPointer1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		StackPointer1.setFont(myFont2);
		Cpupaneldown.add(StackPointer1);
		JTextField StackPointer2 = new JTextField();
		StackPointer2.setEditable(false);
		StackPointer2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		StackPointer2.setFont(myFont2);
		Cpupaneldown.add(StackPointer2);		
	}
	
	/**
	 * Constructor for Index register labels, text areas.
	 */
	private void IndexRegister() {
		JLabel IndexRegister = new JLabel("IndexRegister");
		IndexRegister.setFont(myFont2);
		IndexRegister.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(IndexRegister);
		JTextField IndexRegister1 = new JTextField();
		IndexRegister1.setEditable(false);
		IndexRegister1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		IndexRegister1.setFont(myFont2);
		Cpupaneldown.add(IndexRegister1);
		JTextField IndexRegister2 = new JTextField();
		IndexRegister2.setEditable(false);
		IndexRegister2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		IndexRegister2.setFont(myFont2);
		Cpupaneldown.add(IndexRegister2);		
	}
	
	/**
	 * Constructor for Operand labels, text areas.
	 */
	private void Operand() {
		JLabel Operand = new JLabel("(Operand)");
		Operand.setFont(myFont2);
		Operand.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(Operand);
		Operand1 = new JTextField();
		Operand1.setEditable(false);
		Operand1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Operand1.setFont(myFont2);
		Cpupaneldown.add(Operand1);
		Operand2 = new JTextField();
		Operand2.setEditable(false);
		Operand2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Operand2.setFont(myFont2);
		Cpupaneldown.add(Operand2);		
	}
	
	/**
	 * Constructor for Operand specifier labels, text areas.
	 */
	private void OperandSpecifier() {
		JLabel OperandSpecifier = new JLabel("Operand Specifier");
		OperandSpecifier.setFont(myFont2);
		OperandSpecifier.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(OperandSpecifier);
		JTextField OperandSpecifier1 = new JTextField();
		OperandSpecifier1.setEditable(false);
		OperandSpecifier1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		OperandSpecifier1.setFont(myFont2);
		Cpupaneldown.add(OperandSpecifier1);
		JTextField OperandSpecifier2 = new JTextField();
		OperandSpecifier2.setEditable(false);
		OperandSpecifier2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		OperandSpecifier2.setFont(myFont2);
		Cpupaneldown.add(OperandSpecifier2);		
	}

	/**
	 * Constructor for InstructionPanel labels, text areas.
	 */
	private void InstructionPanel() {
		JLabel Instruction = new JLabel("Instruction Specifier");
		Instruction.setFont(myFont2);
		Instruction.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(Instruction);
		Instructionout1 = new JTextField();
		Instructionout1.setEditable(false);
		Instructionout1.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Instructionout1.setFont(myFont2);
		Cpupaneldown.add(Instructionout1);
		JTextField Instructionout2 = new JTextField();
		Instructionout2.setEditable(false);
		Instructionout2.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(Instructionout2);
		CpuPanel.add(Cpupaneldown);
		CenterPanel.add(CpuPanel, BorderLayout.SOUTH);		
	}

	/**
	 * step and resume buttons in Central down panel
	 */
	private void step(){
		JPanel steppanel = new JPanel();
		steppanel.setFont(myFont2);
		steppanel.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		Cpupaneldown.add(steppanel);
		JButton stepbutton = new JButton("Single Step");
		stepbutton.addActionListener(this);
		stepbutton.setEnabled(false);
		stepbutton.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		stepbutton.setFont(myFont2);
		Cpupaneldown.add(stepbutton);
		JButton resumebutton = new JButton("Resume");
		resumebutton.addActionListener(this);
		resumebutton.setPreferredSize(new Dimension(screenSize.width/12,screenSize.height*3/112));
		resumebutton.setFont(myFont2);
		resumebutton.setEnabled(false);
		Cpupaneldown.add(resumebutton);
	}

	/**
	 * Constructor for BatchIO and terminal text area with tabs.
	 */
	private void BatchIO() {
		JTabbedPane tabbedPane2 = new JTabbedPane();
		tabbedPane2.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*3/14));
		tabbedPane2.setFont(myFont);
		BatchIO = new JTextArea(null,screenSize.width/4,screenSize.height*3/14);
		BatchIO.setFont(myFont);
		JTextArea Terminal = new JTextArea(null,screenSize.width/4,screenSize.height*3/14);
		tabbedPane2.addTab("BatchI/O", BatchIO); tabbedPane2.addTab("Terminal I/O", Terminal);
		CenterPanel.add(tabbedPane2);
		Outputtext = new JTextArea(null,screenSize.width/4,screenSize.height*3/14);
		Outputtext.setFont(myFont);
		Outputtext.setEditable(true);
		Outputtext.setBorder(new TitledBorder(null, "OUTPUT", TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		Outputtext.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*3/14));
		CenterPanel.add(Outputtext, BorderLayout.CENTER);
	}
	
	/**
	 * Constructor for Line end Panel with components panels and scroll.
	 */
	private void LineEndPanel() {	
		LineEndPanel = new JPanel();
		LineEndPanel.setLayout(new GridLayout(1,1));
		LineEndPanel.setBorder(new TitledBorder(null, "Memory", TitledBorder.CENTER, TitledBorder.TOP, myFont, null));	
		LineEndPanel.setPreferredSize(new Dimension(screenSize.width/4,screenSize.height*9/14));
		Memory = new JTextArea(null,screenSize.width/4,screenSize.height*9/14);
		Memory.setFont(myFont);
		Memory.setEditable(false);
		scroll = new JScrollPane(Memory);
		scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		Memory.setBorder(new TitledBorder(null, null, TitledBorder.CENTER, TitledBorder.TOP, myFont, null));
		LineEndPanel.add(scroll);
	}
	
	/**
	 * Constructor for Menu Bar with sub-menu.
	 */
	private void MenuBar() {
		/*Create file menu with sub-menu*/
		menuBar = new JMenuBar();
		JMenu File = new JMenu("File");
		File.setFont(myFont);
		JMenuItem newMenu = new JMenuItem("New");
		newMenu.addActionListener(this);
		JMenuItem openMenu = new JMenuItem("Open");
		openMenu.addActionListener(this);
		newMenu.setFont(myFont);
		openMenu.setFont(myFont);
		File.add(newMenu);
		File.add(openMenu);
		
		/*Create build menu with sub-menu*/
		JMenu Build = new JMenu("Build");
		Build.setFont(myFont);
		JMenuItem RunSourceMenu = new JMenuItem("Run Source");
		RunSourceMenu.addActionListener(this);
		JMenuItem StartDebuggingMenu = new JMenuItem("Debug Source");
		StartDebuggingMenu.addActionListener(this);
		JMenuItem RunObjectMenu = new JMenuItem("Run Object");
		RunObjectMenu.addActionListener(this);
		JMenuItem DebugObjectMenu= new JMenuItem("Debug Object");
		DebugObjectMenu.addActionListener(this);
		RunSourceMenu.setFont(myFont);
		StartDebuggingMenu.setFont(myFont);
		RunObjectMenu.setFont(myFont);
		DebugObjectMenu.setFont(myFont);
		Build.add(RunSourceMenu);
		Build.add(StartDebuggingMenu);
		Build.add(RunObjectMenu);
		Build.add(DebugObjectMenu);

		/*Create edit menu with sub-menu*/
		JMenu Edit = new JMenu("Edit");
		Edit.setFont(myFont);
		JMenuItem obtCutMenu = new JMenuItem("Cut Object Code");
		obtCutMenu.addActionListener(this);
		JMenuItem objpasteMenu = new JMenuItem("Paste into Object Code");
		objpasteMenu.addActionListener(this);
		JMenuItem srcCutMenu = new JMenuItem("Cut Assembly Code");
		srcCutMenu.addActionListener(this);
		JMenuItem srcpasteMenu = new JMenuItem("Paste into Assembly Code");
		srcpasteMenu.addActionListener(this);
		srcCutMenu.setFont(myFont);
		srcpasteMenu.setFont(myFont);
		obtCutMenu.setFont(myFont);
		objpasteMenu.setFont(myFont);
		Edit.add(obtCutMenu);
		Edit.add(objpasteMenu);
		Edit.add(srcCutMenu);
		Edit.add(srcpasteMenu);
		/*Adding all menu buttons to menubar*/
		menuBar.add(File);menuBar.add(Build);menuBar.add(Edit);
		frame.setJMenuBar(menuBar);
	}

	/**
	 * Performs actions based on the action of the user. Open, Load, Save, Start, etc.
	 * @param e The event triggered.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String userinput = e.getActionCommand();
		if(userinput.equals("Open")||userinput.equals("Load")) {
			JFileChooser chooser = new JFileChooser();
			StringBuilder sb = new StringBuilder();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Text files", "txt");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				//Check that the file can actually be read from.
				if (!file.canRead()) {
					throw new IllegalArgumentException(file.getName() + " cannot be read.");
				} else {
					try (Scanner sc = new Scanner(file)) {
						while (sc.hasNext()) {
							sb.append(sc.nextLine() + '\n');
						}
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, e1.getMessage());
					}
				}
			}
			sourceTab.setText(sb.toString());
        } else if(userinput.equals("Save")) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Text files", "txt");
			chooser.setFileFilter(filter);
			//Loop until the user has successfully chosen a file location to save to or has cancelled out.
			boolean loop = true;
			while (loop) {
				int returnVal = chooser.showSaveDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File outputFile = chooser.getSelectedFile();
					//Add the .txt file extension if the user didn't do it already
					if (!outputFile.getAbsolutePath().endsWith(".txt")) {
						outputFile = new File(outputFile.getAbsolutePath() + ".txt");
					}
					try {
						//Try creating a file at the chosen location
						if (outputFile.createNewFile()) {
							FileWriter fw = new FileWriter(outputFile);
							fw.append(sourceTab.getText());
							fw.close();
							loop = false;
						} else {
							//Ask the user if they want to overwrite an existing file.
							int overwrite = JOptionPane.showConfirmDialog(frame,
									"This file already exists would you like to replace it?");
							if (overwrite == JOptionPane.YES_OPTION) {
								if (!outputFile.delete()) {
									//Inform the user that the file cannot be replaced.
									JOptionPane.showMessageDialog(frame,
											outputFile.getName() +" could not be replaced.");
								} else {
									if (outputFile.createNewFile()) {
										FileWriter fw = new FileWriter(outputFile);
										fw.append(sourceTab.getText());
										fw.close();
										loop = false;
									}
								}
							} else {
								if (overwrite != JOptionPane.NO_OPTION) {
									loop = false;
								}
							}
						}
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame, e1.getMessage());
					}
				} else {
					loop = false;
				}
			}
        } else if(userinput.equals("Run Object")) {
        	firePropertyChange("Start", null, null);
        } else if(userinput.equals("Debug Object")) {
			firePropertyChange("Debug Object", null, null);
		} else if(userinput.equals("Assemble")) {
			firePropertyChange("Assemble", null, null);
		} else if(userinput.equals("Run Source")) {
			firePropertyChange("Run Source", null, null);
		} else if(userinput.equals("Debug Source")) {
			firePropertyChange("Debug Source", null, null);
		} else if(userinput.equals("New")) {
			sourceTab.setText("");
			ObjCode.setText("");
			AsListing.setText("");
			Accumulatorout1.setText("");
			Accumulatorout2.setText("");
			Instructionout1.setText("");
			Operand1.setText("");
			Operand2.setText("");
			ProgramCounterout1.setText("");
			ProgramCounterout2.setText("");
			Memory.setText("");
			BatchIO.setText("");
			Outputtext.setText("");
			batchIndex = -1;
			firePropertyChange("New", null, null);
		} else if (userinput.equals("Cut Object Code")) {
			ObjCode.cut();
		} else if (userinput.equals("Paste into Object Code")) {
			ObjCode.paste();
		} else if (userinput.equals("Cut Assembly Code")) {
			sourceTab.cut();
		} else if (userinput.equals("Paste into Assembly Code")) {
			sourceTab.paste();
		}
	}

	/**
	 * Sets the memory text area based on the copy of memory given by the Machine class.
	 * @param memCopy byte array copy of the memory.
	 */
	public void setMemText(byte[] memCopy) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < memCopy.length; i++) {
			if (i % 8 == 0) {
				sb.append("\n");
				String addrHex = String.format("%04x", i);
				sb.append(addrHex.toUpperCase());
				sb.append(": ");
			}
			String hex = String.format("%02x", memCopy[i]);
			sb.append(hex.toUpperCase());
			sb.append(" ");
		}
		sb.replace(0, 1, "");
		Memory.setText(sb.toString());
		Memory.setCaretPosition(0);
	}

	/**
	 * getter for current memory information
	 * @return String information about memory
	 */
	public String getMemText(){
		return Memory.getText();
	}

	/**
	 * Sets the register text values according to the values passed in. Assumes the order is as listed below
	 * @param registerValues short[] {InstrRegSpec, InstrRegOper, ProgCountReg, AccumReg}
	 */
	public void setRegistersText(short[] registerValues) {
		String instrSpecHex = String.format("%04x", registerValues[0]);
		Instructionout1.setText(instrSpecHex.toUpperCase());
		String operandHex = String.format("%04x", registerValues[1]);
		Operand1.setText(operandHex.toUpperCase());
		Operand2.setText("" + registerValues[1]);
		String progCountHex = String.format("%04x", registerValues[2]);
		ProgramCounterout1.setText(progCountHex.toUpperCase());
		ProgramCounterout2.setText("" + registerValues[2]);
		String accumHex = String.format("%04x", registerValues[3]);
		Accumulatorout1.setText(accumHex.toUpperCase());
		Accumulatorout2.setText("" + registerValues[3]);
	}

	/**
	 * This returns the characters stored in the BatchIO text area one character at a time.
	 * @return The next character from batch input based upon how many times this function has been called.
	 */
	public char getBatchInput() {
		batchIndex++;
		return (BatchIO.getText()).charAt(batchIndex);
	}

	/**
	 * Adds the specified character to the output text area.
	 * @param c
	 */
	public void output(char c) {
		Outputtext.setText(Outputtext.getText() + c);
	}

	/**
	 * Setter for source tab
	 * @param str string input values
	 */
	public void setSourceTab(String str){
		sourceTab.setText(str);
	}

	/**
	 * getter for source code tab
	 * @return current source tab data
	 */
	public String getSourceCode(){
		return sourceTab.getText();
	}

	/**
	 * Setter for object Code area
	 * @param str input values for object code
	 */
	public void setObjectCode(String str){
		ObjCode.setText(str);
	}

	/**
	 * getter for object code area
	 * @return current object code area string data
	 */
	public String getObjectCode(){
		return ObjCode.getText();
	}

	/**
	 * setter for Aslisting area
	 * @param str string input for aslisting area
	 */
	public void setAsListing(String str){
		AsListing.setText(str);
	}

	/**
	 * getter for Aslisting area
	 * @return current Aslisting area string data
	 */
	public String getAsListing(){
		return AsListing.getText();
	}

	/**
	 * Setter for Nbox status
	 * @param b boolean value for Nbox
	 */
	public void setNbox(boolean b){
		Nbox.setSelected(b);
	}

	/**
	 * getter for N box status
	 * @return return boolean value for current Nbox
	 */
	public boolean getNbox(){
		return Nbox.isSelected();
	}
	/**
	 * Setter for Zbox status
	 * @param b boolean value for Zbox
	 */
	public void setZbox(boolean b){
		Zbox.setSelected(b);
	}

	/**
	 * getter for Z box status
	 * @return return boolean value for current Zbox
	 */
	public boolean getZbox(){
		return Zbox.isSelected();
	}

	/**
	 * Setter for Vbox status
	 * @param b boolean value for Nbox
	 */
	public void setVbox(boolean b){
		Vbox.setSelected(b);
	}

	/**
	 * getter for V box status
	 * @return return boolean value for current Vbox
	 */
	public boolean getVbox(){
		return Vbox.isSelected();
	}

	/**
	 * Setter for Vbox status
	 * @param b boolean value for Vbox
	 */
	public void setCbox(boolean b){
		Cbox.setSelected(b);
	}

	/**
	 * getter for C box status
	 * @return return boolean value for current Cbox
	 */
	public boolean getCbox(){
		return Cbox.isSelected();
	}

}
