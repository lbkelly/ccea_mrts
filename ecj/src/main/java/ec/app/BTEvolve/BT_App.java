/*
 * File created by: Matthew Burr as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package ec.app.BTEvolve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;


public class BT_App implements ActionListener, ThreadCompleteListener{

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Variables:
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	String[] arguments = null; // parameter arguments
	
	MyThread thread; // thread
	
	boolean check = false;
	Random r = new Random(); // random for seed
	
	PrintWriter w;
	
	// File handlers
	File scenariofolder; // file to store scenario folder
	File[] scenarioFiles; // file array to store scenarios
	String evolutionFolderName = ""; // name of the evolution folder
	File evolutionFolder; // file to store evolution folder
	
	// Parameter Variables
	protected String scenarioPath;	// path to scenario
	protected String scenarioName;	// name of scenario
	protected String opponentName;	// type of opponent
	protected String tournamentName;	// type of tournament
	protected String selectionName;	// type of selection scheme
	protected String visibilityType; // type of visibility
	
	// MonteCarlo Variables
	protected String timeBudget; // time budget MC
	protected String iterationBudget; // iteration budget MC
	protected String lookahead; // enemy AI lookahead MC
	protected String maxActions; // simulation max actions MC
	protected String playoutAI; // enemy AI behaviour MC

	// Send the console outputs to the text area
	protected PrintStream standardOut;
	protected PrintStream printStream;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Components:
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private JFrame frmMicrortsAiGui;
	
	// TextField Components:
	private JTextField textFieldSeed;
	private JTextField textFieldPopulation;
	private JTextField textFieldGeneration;
	private JTextField textFieldMutation;
	private JTextField textFieldCrossover;
	private JTextField textFieldEvaluations;
	private JTextField textFieldTurns;
	private JTextField textFieldTimeBudget;
	private JTextField textFieldIterationBudget;
	private JTextField textFieldLookAhead;
	private JTextField textFieldMaxActions;
	private JTextField textFieldCheckpoint;
	
	// Label Components:
	private JLabel lblSeed;
	private JLabel lblPopulation;
	private JLabel lblGenerations;
	private JLabel lblMutationRate;
	private JLabel lblCrossoverRate;
	private JLabel lblScenario;
	private JLabel lblOpponent;
	private JLabel lblSelectionScheme;
	private JLabel lblEvaluations;
	private JLabel lblNumberOfturns;
	private JLabel lblCheckpoint;
	private JLabel lblVisibility;
	private JLabel lblTimeBudget;
	private JLabel lblIterationBudget;
	private JLabel lblPlayoutLookahead;
	private JLabel lblMaxActions;
	private JLabel lblPlayoutAI;
	
	// Label Icon Components:
	private JLabel lblIconSeed;
	private JLabel lblIconPopulation;
	private JLabel lblIconGeneration;
	private JLabel lblIconMutation;
	private JLabel lblIconCrossover;
	private JLabel lblIconSelectionScheme;
	private JLabel lblIconEvaluations;
	private JLabel lblIconTurns;
	private JLabel lblIconTimeBudget;
	private JLabel lblIconIterationBudget;
	private JLabel lblIconLookahead;
	private JLabel lblIconMaxActions;
	private JLabel lblIconPlayoutAI;
	
	// TextArea Components:
	private JTextArea textAreaOutput;
	
	// Button Components:
	private JButton btnRun;
	private JButton btnClear;
	private JButton btnRunResults;
	private JButton btnCreateScenario;
	private JButton btnDeleteScenario;
	private JButton btnContinue;
	
	// ComboBox Components:
	private JComboBox comboBoxOpponent;
	private JComboBox comboBoxSelectionScheme;
	private JComboBox comboBoxScenarios;
	private JComboBox comboBoxPlayoutAI;
	private JComboBox comboBoxVisibility;
	
	// CheckBox Components:
	private JCheckBox chckbxRandomSeed;

	private JPanel parametersPanel;
	private JPanel simulationPanel;
	private stateDialogWindow stateDialog;
	private traceDialogWindow traceDialog;
	private graphDialogWindow graphDialog;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BT_App window = new BT_App();
					window.frmMicrortsAiGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BT_App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMicrortsAiGui = new JFrame();
		frmMicrortsAiGui.setTitle("MicroRTS AI GUI: Evolution");
		frmMicrortsAiGui.setBounds(100, 100, 771, 577);
		frmMicrortsAiGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMicrortsAiGui.getContentPane().setLayout(null);
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Parameters panel:
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		parametersPanel = new JPanel();
		parametersPanel.setBounds(10, 2, 354, 315);
		frmMicrortsAiGui.getContentPane().add(parametersPanel);
		
		// Parameter labels
		lblSeed = new JLabel("Seed:");
		lblSeed.setBounds(10, 5, 96, 13);
		lblSeed.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblPopulation = new JLabel("Population size:");
		lblPopulation.setBounds(10, 28, 96, 13);
		lblPopulation.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblGenerations = new JLabel("Max generations:");
		lblGenerations.setBounds(10, 51, 110, 13);
		lblGenerations.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblMutationRate = new JLabel("Mutation rate:");
		lblMutationRate.setBounds(10, 74, 96, 13);
		lblMutationRate.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblCrossoverRate = new JLabel("Crossover rate:");
		lblCrossoverRate.setBounds(10, 97, 96, 13);
		lblCrossoverRate.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblSelectionScheme = new JLabel("Selection scheme:");
		lblSelectionScheme.setBounds(10, 157, 110, 13);
		lblSelectionScheme.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblEvaluations = new JLabel("<html>\r\nNumber of \r\n<br>\r\nevaluations:\r\n</html>");
		lblEvaluations.setBounds(10, 172, 76, 43);
		lblEvaluations.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblNumberOfturns = new JLabel("<html>\r\nNumber of \r\n<br>turns:\r\n</html>");
		lblNumberOfturns.setBounds(10, 214, 76, 43);
		lblNumberOfturns.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblCheckpoint = new JLabel("Checkpoint:");
		lblCheckpoint.setBounds(10, 258, 76, 14);
		lblCheckpoint.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblVisibility = new JLabel("Visibility:");
		lblVisibility.setBounds(10, 283, 76, 14);
		lblVisibility.setHorizontalAlignment(SwingConstants.LEFT);
		
		// Parameter Panel textFields
		textFieldSeed = new JTextField();
		textFieldSeed.setBounds(130, 0, 96, 19);
		textFieldSeed.setEnabled(true);
		textFieldSeed.setColumns(10);
		textFieldSeed.setText("101");
		
		textFieldPopulation = new JTextField("");
		textFieldPopulation.setBounds(130, 23, 96, 19);
		textFieldPopulation.setColumns(10);
		textFieldPopulation.setText("1");
		
		textFieldGeneration = new JTextField("");
		textFieldGeneration.setBounds(130, 46, 96, 19);
		textFieldGeneration.setColumns(10);
		textFieldGeneration.setText("1");
		
		textFieldMutation = new JTextField("");
		textFieldMutation.setBounds(130, 69, 96, 19);
		textFieldMutation.setColumns(10);
		textFieldMutation.setText("0.2");
		
		textFieldCrossover = new JTextField("");
		textFieldCrossover.setBounds(130, 92, 96, 19);
		textFieldCrossover.setColumns(10);
		textFieldCrossover.setText("0.8");
		
		textFieldEvaluations = new JTextField();
		textFieldEvaluations.setBounds(130, 181, 53, 20);
		textFieldEvaluations.setColumns(10);
		textFieldEvaluations.setText("1");
		
		textFieldTurns = new JTextField();
		textFieldTurns.setText("120");
		textFieldTurns.setBounds(130, 218, 53, 20);
		textFieldTurns.setColumns(10);
		
		textFieldCheckpoint = new JTextField();
		textFieldCheckpoint.setText("1000");
		textFieldCheckpoint.setBounds(130, 255, 53, 20);
		textFieldCheckpoint.setColumns(10);
		
		// Parameter Panel comboBox
		String[] selectionScheme = {"SUS", "Tourn3"};
		comboBoxSelectionScheme = new JComboBox(selectionScheme);
		comboBoxSelectionScheme.setBounds(130, 151, 96, 19);
		
		String[] visibility = {"Full", "Both Partial", "Self Partial", "Enemy Partial"};
		comboBoxVisibility = new JComboBox(visibility);
		comboBoxVisibility.setBounds(130, 280, 118, 20);
		
		// Parameter Panel checkBox
		chckbxRandomSeed = new JCheckBox("Random Seed\r\n", true);
		chckbxRandomSeed.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chckbxRandomSeed.setVerticalAlignment(SwingConstants.TOP);
		chckbxRandomSeed.setBounds(254, 0, 93, 34);
		chckbxRandomSeed.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textFieldSeed.setEnabled(false);
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					textFieldSeed.setEnabled(true);
				}
			}
			
		});
		
		// Parameter Panel buttons
		btnClear = new JButton("Clear Parameters");
		btnClear.setBounds(86, 121, 140, 23);
		btnClear.addActionListener(this);
		
		// Parameter Panel icons
		lblIconCrossover = new JLabel("");
		lblIconCrossover.setBounds(232, 94, 16, 16);
		lblIconCrossover.setToolTipText("<html>\r\nThe crossover rate.\r\n<br>\r\nAllows you to specify the probability that single-point crossover will occur.\r\n</html>");
		lblIconCrossover.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		
		lblIconMutation = new JLabel("");
		lblIconMutation.setBounds(232, 71, 16, 16);
		lblIconMutation.setToolTipText("<html>\r\nThe mutation rate.\r\n<br>\r\nAllows you to specify the probability that sub-tree mutation will occur.\r\n</html>");
		lblIconMutation.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		
		lblIconGeneration = new JLabel("");
		lblIconGeneration.setBounds(232, 48, 16, 16);
		lblIconGeneration.setToolTipText("<html>\r\nThe number of generations.\r\n<br>\r\nAllows you to specify how many generations of individuals should be evolved.\r\n</html>");
		lblIconGeneration.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		
		lblIconPopulation = new JLabel("");
		lblIconPopulation.setBounds(232, 26, 16, 16);
		lblIconPopulation.setToolTipText("<html>\r\nThe size of the population.\r\n<br>\r\nAllows you to specify how large the population size of individuals is.\r\n</html>");
		lblIconPopulation.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		
		lblIconSeed = new JLabel("");
		lblIconSeed.setBounds(232, 2, 16, 16);
		lblIconSeed.setToolTipText("<html>\r\nThe seed for the random number generator.\r\n<br>\r\nAllows for the generation of the same initial starting population each time the same seed is used.\r\n<br>\r\nTick the 'Random' checkbox for a random seed within the range of -9999 to 9999.\r\n</html>\r\n\r\n");
		lblIconSeed.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		
		lblIconSelectionScheme = new JLabel("");
		lblIconSelectionScheme.setBounds(232, 154, 16, 16);
		lblIconSelectionScheme.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconSelectionScheme.setToolTipText("<html>\r\nThe selection scheme.\r\n<br>\r\nAllows you to specify the type of selection scheme for use in evolution.\r\n<br>\r\n</html>");
		
		lblIconEvaluations = new JLabel("");
		lblIconEvaluations.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconEvaluations.setToolTipText("<html>\r\nThe number of evaluations.\r\n<br>\r\nAllows you to specify the number of times an individual of a generation is evaluated.\r\n<br>\r\n</html>");
		lblIconEvaluations.setBounds(232, 181, 16, 16);
		
		lblIconTurns = new JLabel("");
		lblIconTurns.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconTurns.setToolTipText("<html>\r\nThe number of MicroRTS game turns.\r\n<br>\r\nAllows you to specify the maximum number of MicroRTS game turns before a gameover state is reached.\r\n<br>\r\n</html>");
		lblIconTurns.setBounds(232, 221, 16, 16);
		
		
		// Parameter Panel components
		parametersPanel.setLayout(null);
		
		parametersPanel.add(lblSeed);
		parametersPanel.add(lblPopulation);
		parametersPanel.add(lblGenerations);
		parametersPanel.add(lblMutationRate);
		parametersPanel.add(lblCrossoverRate);
		parametersPanel.add(lblSelectionScheme);
		parametersPanel.add(lblEvaluations);
		parametersPanel.add(lblNumberOfturns);
		parametersPanel.add(lblCheckpoint);
		parametersPanel.add(lblVisibility);

		parametersPanel.add(lblIconSeed);
		parametersPanel.add(lblIconPopulation);
		parametersPanel.add(lblIconGeneration);
		parametersPanel.add(lblIconMutation);
		parametersPanel.add(lblIconCrossover);
		parametersPanel.add(lblIconSelectionScheme);
		parametersPanel.add(lblIconEvaluations);
		parametersPanel.add(lblIconTurns);

		parametersPanel.add(textFieldSeed);
		parametersPanel.add(textFieldPopulation);
		parametersPanel.add(textFieldGeneration);
		parametersPanel.add(textFieldMutation);
		parametersPanel.add(textFieldCrossover);
		parametersPanel.add(textFieldEvaluations);
		parametersPanel.add(textFieldTurns);
		parametersPanel.add(textFieldCheckpoint);
		
		parametersPanel.add(comboBoxSelectionScheme);
		parametersPanel.add(comboBoxVisibility);

		parametersPanel.add(chckbxRandomSeed);

		parametersPanel.add(btnClear);
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Simulation panel:
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		simulationPanel = new JPanel();
		simulationPanel.setBounds(364, 2, 381, 315);
		frmMicrortsAiGui.getContentPane().add(simulationPanel);

		// Simulation Panel labels
		lblOpponent = new JLabel("Opponent");
		lblOpponent.setBounds(10, 3, 76, 13);
		
		lblScenario = new JLabel("Scenario:");
		lblScenario.setBounds(10, 137, 76, 13);
		
		lblTimeBudget = new JLabel("Time budget:");
		lblTimeBudget.setBounds(10, 34, 76, 14);
		lblTimeBudget.setVisible(false);
		
		lblIterationBudget = new JLabel("<html>Iteration \r\n<br> \r\nbudget:\r\n</html>");
		lblIterationBudget.setBounds(10, 56, 76, 28);
		lblIterationBudget.setVisible(false);
		
		lblPlayoutLookahead = new JLabel("Lookahead:");
		lblPlayoutLookahead.setBounds(10, 91, 76, 14);
		lblPlayoutLookahead.setVisible(false);
		
		lblMaxActions = new JLabel("Max actions:");
		lblMaxActions.setBounds(193, 34, 76, 14);
		lblMaxActions.setVisible(false);
		
		lblPlayoutAI = new JLabel("Playout AI:");
		lblPlayoutAI.setBounds(193, 56, 61, 14);
		lblPlayoutAI.setVisible(false);
		
		// Simulation Panel Icon Labels
		lblIconTimeBudget = new JLabel("");
		lblIconTimeBudget.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconTimeBudget.setToolTipText("<html>\r\nThe number of milliseconds the function 'getAction' is set for a MonteCarlo simulation.\r\n<br>\r\nAllows you to specify the maximum number of milliseconds the function 'getAction' may use. If this is set to -1 then there is no limit.\r\n<br>\r\n</html>");
		lblIconTimeBudget.setBounds(156, 34, 16, 16);
		lblIconTimeBudget.setVisible(false);
		
		lblIconIterationBudget = new JLabel("");
		lblIconIterationBudget.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconIterationBudget.setToolTipText("<html>\r\nThe number internal iterations the AI within a MonteCarlo simulation may use. Defines the number of leaves or playouts an AI can explore to find an optimal move. \r\n<br>\r\nAllows you to specify the number of internal iterations the AI may use. If this is set to -1 then there is no limit.\r\n<br>\r\n</html>");
		lblIconIterationBudget.setBounds(156, 56, 16, 16);
		lblIconIterationBudget.setVisible(false);
		
		lblIconLookahead = new JLabel("");
		lblIconLookahead.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconLookahead.setToolTipText("<html>\r\nThe maximum simulation time for a playout.\r\n<br>\r\nAllows you to specify the maximum number time for a MonteCarlo simulation playout.\r\n<br>\r\n</html>");
		lblIconLookahead.setBounds(156, 91, 16, 16);
		lblIconLookahead.setVisible(false);
		
		lblIconMaxActions = new JLabel("");
		lblIconMaxActions.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconMaxActions.setToolTipText("<html>\r\nThe number of actions an AI using MonteCarlo behaviour may perform before computation of the best action calculated so far is halted.\r\n<br>\r\nAllows you to specify the maximum number of actions an AI using MonteCarlo behaviour may use to compute possible actions.\r\n<br>\r\n</html>");
		lblIconMaxActions.setBounds(355, 34, 16, 16);
		lblIconMaxActions.setVisible(false);
		
		lblIconPlayoutAI = new JLabel("");
		lblIconPlayoutAI.setIcon(new ImageIcon(BT_App.class.getResource("/ec/app/BTEvolve/Icons/exclamation(1).png")));
		lblIconPlayoutAI.setToolTipText("<html>\r\nThe AI's used in a MonteCarlo simulation playout.\r\n<br>\r\nAllows you to specify the type of AI to be used in a simulation playout.\r\n<br>\r\n</html>");
		lblIconPlayoutAI.setBounds(355, 56, 16, 16);
		lblIconPlayoutAI.setVisible(false);
		
		// Simulation Panel textFields
		textFieldTimeBudget = new JTextField();
		textFieldTimeBudget.setText("100");
		textFieldTimeBudget.setBounds(99, 30, 56, 20);
		
		textFieldTimeBudget.setColumns(10);
		textFieldTimeBudget.setVisible(false);
		
		textFieldIterationBudget = new JTextField();
		textFieldIterationBudget.setText("-1");
		textFieldIterationBudget.setColumns(10);
		textFieldIterationBudget.setBounds(99, 53, 56, 20);
		textFieldIterationBudget.setVisible(false);
		
		textFieldLookAhead = new JTextField();
		textFieldLookAhead.setText("100");
		textFieldLookAhead.setColumns(10);
		textFieldLookAhead.setBounds(99, 88, 56, 20);
		textFieldLookAhead.setVisible(false);
		
		textFieldMaxActions = new JTextField();
		textFieldMaxActions.setText("100");
		textFieldMaxActions.setColumns(10);
		textFieldMaxActions.setBounds(298, 30, 56, 20);
		textFieldMaxActions.setVisible(false);
		
		// Simulation Panel buttons
		btnRunResults = new JButton("Results Folder");
		btnRunResults.setBounds(205, 281, 166, 23);
		btnRunResults.setVisible(false);
		
		btnRun = new JButton("Run");
		btnRun.setBounds(205, 237, 166, 43);
		
		btnCreateScenario = new JButton("Modify/Create Scenario");
		btnCreateScenario.setBounds(205, 137, 166, 19);
		
		btnDeleteScenario = new JButton("Delete scenario");
		btnDeleteScenario.setBounds(205, 194, 166, 19);
		
		btnContinue = new JButton("Resume checkpoint");
		btnContinue.setBounds(29, 237, 166, 43);
		
		// Simulation Panel comboBox
		comboBoxScenarios = new JComboBox(getScenarios());
		comboBoxScenarios.setBounds(156,164, 215, 19);
		
		String[] opponentAi = {
				"PassiveAI",
				"Custom_LightRush",
				"LightRush", 
				"HeavyRush", 
				"WorkerRush",  
//				"MonteCarlo",
//				"Tiamat",
				"EconomyMilitaryRush",
				"EconomyRush",
				"EconomyRushBurster",
				"EMRDeterministico",
				"HeavyDefense",
				"LightDefense",
				"RangedDefense",
				"RangedRush",
				"SimpleEconomyRush",
				"WorkerDefense",
				"WorkerRushPlusPlus"};
		comboBoxOpponent = new JComboBox(opponentAi);
		comboBoxOpponent.setBounds(96,0, 191, 19);
		
		String[] playoutAI = {"RandomAI", "RandomBiasedAI", "WorkerRush", "LightRush", "HeavyRush", "RangedRush"};
		comboBoxPlayoutAI = new JComboBox(playoutAI);
		comboBoxPlayoutAI.setBounds(264, 53, 90, 20);
		comboBoxPlayoutAI.setVisible(false);
		
		// Add action listeners
		comboBoxOpponent.addActionListener(this);
		btnRunResults.addActionListener(this);
		comboBoxScenarios.addActionListener(this);
		btnRun.addActionListener(this);
		btnCreateScenario.addActionListener(this);
		btnDeleteScenario.addActionListener(this);
		btnContinue.addActionListener(this);
		
		// Simulation Panel components
		simulationPanel.setLayout(null);
		
		simulationPanel.add(lblScenario);
		simulationPanel.add(lblOpponent);
		simulationPanel.add(lblTimeBudget);
		simulationPanel.add(lblIterationBudget);
		simulationPanel.add(lblPlayoutLookahead);
		simulationPanel.add(lblMaxActions);
		simulationPanel.add(lblPlayoutAI);
		
		simulationPanel.add(lblIconPlayoutAI);
		simulationPanel.add(lblIconMaxActions);
		simulationPanel.add(lblIconLookahead);
		simulationPanel.add(lblIconIterationBudget);
		simulationPanel.add(lblIconTimeBudget);
		
		simulationPanel.add(textFieldTimeBudget);
		simulationPanel.add(textFieldIterationBudget);
		simulationPanel.add(textFieldLookAhead);
		simulationPanel.add(textFieldMaxActions);
		
		simulationPanel.add(comboBoxScenarios);
		simulationPanel.add(comboBoxOpponent);
		simulationPanel.add(comboBoxPlayoutAI);
		
		simulationPanel.add(btnRunResults);
		simulationPanel.add(btnRun);
		simulationPanel.add(btnCreateScenario);
		simulationPanel.add(btnDeleteScenario);
		simulationPanel.add(btnContinue);
		
		
		
		// JDialogWindow listeners
		try {
			stateDialog = new stateDialogWindow(frmMicrortsAiGui);

		} catch (Exception e) {
			e.printStackTrace();
		}
		stateDialog.addWindowListener(new WindowAdapter() {
			public void windowClosed(final WindowEvent event) {
				DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(getScenarios());
				comboBoxScenarios.setModel(comboBoxModel);
				comboBoxScenarios.repaint();
			}
		});
		
		// Text area output
		textAreaOutput = new JTextArea();
		textAreaOutput.setLineWrap(true);
		textAreaOutput.setBounds(201, 195, 225, 59);
		
		// Scroll panel for text area output
		JScrollPane scrollPane = new JScrollPane(textAreaOutput);
		scrollPane.setBounds(10, 328, 735, 197);
		frmMicrortsAiGui.getContentPane().add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
//		// Print console info to text area
		printStream = new PrintStream(new CustomOutputStream(textAreaOutput));

		// keeps reference of standard output stream
        standardOut = System.out;
        
        System.out.println(printStream);
         
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);


	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Methods:
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Checks if the passed string is a number
	public boolean checkIfNum(String number) {
		try {
			Integer.parseInt(number);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	// Checks if the passed string is of the type double
	public boolean checkIfDouble(String number) {
		try {
			Double.parseDouble(number);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	// Checks for correct input of fields within the GUI
	public String errorCheck(JTextField[] fields)
	{
		String errorMessage = "OK";
			
		for (int i = 0; i < fields.length; i++) {
			// If the field is enabled and contains no input
			if(fields[i].isEnabled() == true && fields[i].getText().equals("") || fields[i].getText().equals(" ")) {
				// Get the name of the textfield and return false
				String name = errorName(i);
				errorMessage = "Error: No "+name+" parameter input.\n";
				return errorMessage;
			}
		}
		
		for (int j = 0; j < fields.length; j++) {
			// seed || population size || max generations || number of evaluations || number of turns || checkpoint
			if (j == 0 || j == 1 || j == 2 || j == 5 || j == 6 || j == 7)
			{
				// field is enabled and is not a number
				if(fields[j].isEnabled() == true && checkIfNum(fields[j].getText()) == false) {
					// Get the name of the textfield and return false
					String name = errorName(j);
					errorMessage = "Error: "+name+" parameter must be an integer value.\n";
					return errorMessage;
				}
			}
		
			if (j == 1 || j == 2 || j == 5 || j == 6 || j == 7) {
				// Check if number less than 1
				if (Integer.parseInt(fields[j].getText()) < 1) {
					 String name = errorName(j);
					 errorMessage = "Error: "+name+" parameter must be at least '1'\n";
					 return errorMessage;
			 }
			 }

		}
		for (int k = 0; k < fields.length; k++) {
			if (k == 3 || k == 4) {
				// If the field is not a double
				if(checkIfDouble(fields[k].getText()) == false) {
					String name = errorName(k);
					errorMessage = "Error: "+name+" parameter must be a double value.\n";
					return errorMessage;
				}
				double doubleVal = Double.parseDouble(fields[k].getText());
				if(doubleVal < 0 || doubleVal > 1) {
					String name = errorName(k);
					errorMessage = "Error:"+name+" parameter must be between 0 and 1.0\n";
					return errorMessage;
				}
			}

		}

		return errorMessage;
	}
	
	public String errorName(int index) {
		String name = "";
		
		switch(index) {
		case 0:
			name = "Seed";
			break;
			
		case 1:
			name = "Population size";
			break;
			
		case 2:
			name = "Max generations";
			break;
			
		case 3:
			name = "Mutation rate";
			break;
			
		case 4:
			name = "Crossover rate";
			break;
		
		case 5:
			name = "Number of evaluations";
			break;
		
		case 6:
			name = "Number of turns";
			break;
			
		case 7:
			name = "Checkpoint";
			break;
		}
		return name;
		
	}
	
	public String[] getScenarios() {
		this.scenariofolder = new File("./scenarios");
		this.scenarioFiles = scenariofolder.listFiles();
		String[] fileNames = new String[scenarioFiles.length];
		
		if (scenarioFiles != null) {
			for (int i = 0; i < scenarioFiles.length; i++) {
				fileNames[i] = scenarioFiles[i].getName();
			}
		}
		
		return fileNames;
		
	}
	
	public void deleteScenario(String delete) {
		File[] dirFiles = scenariofolder.listFiles();
		
		// Delete all the files
		for (File file : dirFiles) {
			if (file.getName().equals(delete)) {
				file.delete();
				System.out.println("Scenario Deleted: "+delete);
				DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(getScenarios());
				comboBoxScenarios.setModel(comboBoxModel);
				comboBoxScenarios.repaint();
			}

		}
	}
	
	// Gets the values of the comboBoxes
	public void getValues(JTextField[] fields, JComboBox opponent, JComboBox selectionScheme, JComboBox scenarios, JComboBox visibility) {
		
		// get the random seed
		if (fields[0].isEnabled() == false) {
			int randSeed = r.nextInt(9999 - (-9999) + 1) + (-9999);
			fields[0].setText(Integer.toString(randSeed));
		}
		
		this.opponentName = (String)opponent.getSelectedItem(); // get opponent
		this.selectionName = (String)selectionScheme.getSelectedItem(); // get selection scheme
		this.scenarioName = (String)scenarios.getSelectedItem(); // get scenario name
		this.scenarioPath = scenarioFiles[scenarios.getSelectedIndex()].getAbsolutePath(); // get scenario path
		this.visibilityType = (String)visibility.getSelectedItem(); // set visbility type
		
	}
	
	public void createConfig(JTextField[] fields, String opponentName, String scenarioName, String selectionName, String evolutionFolder, String scenarioPath, String visibilityType) {
		try {
			
			File configFile = new File("./"+evolutionFolder+"/Evolution_configuration.txt");
			configFile.getParentFile().mkdirs();
			
			w = new PrintWriter(configFile);
			for (int i = 0; i<fields.length;i++) {
				if (i == 0) {
					w.print("Seed: ");
				}else if (i == 1) {
					w.print("Population: ");

				}else if (i == 2) {
					w.print("Generations: ");

				}else if (i == 3) {
					w.print("Mutation rate: ");

				}else if(i == 4) {
					w.print("Crossover rate: ");

				}else if(i == 5) {
					w.print("Number of evaluations: ");
					
				}else if(i == 6) {
					w.print("Number of turns: ");
					
				}else if(i == 7) {
					w.print("Checkpoint: ");
				}
				

				w.println(fields[i].getText());
			}
			w.println("Opponent: "+opponentName);
			w.println("Scenario: "+scenarioName);
			w.println("Scenario path: "+scenarioPath);
			w.println("Selection scheme: "+selectionName);
			w.println("Visibility: "+visibilityType);
			w.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Method to read the config file
	public void readFile(File file) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			for (String line; (line = br.readLine()) != null;)
			{
        		
				if (line.contains("Seed:"))
				{
					String[] split = line.split(":");
					String intSubstring = split[1].trim();
	        		textFieldSeed.setText(intSubstring);

				}
				else if (line.contains("Population:"))
				{
					String[] split = line.split(":");
					String intSubstring = split[1].trim();
					textFieldPopulation.setText(intSubstring);

				}
				else if (line.contains("Generations:"))
				{
					String[] split = line.split(":");
					String intSubstring = split[1].trim();
					textFieldGeneration.setText(intSubstring);

				}
				else if (line.contains("Mutation rate:"))
				{
					String[] split = line.split(":");
					String dblSubstring = split[1].trim();
					textFieldMutation.setText(dblSubstring);
				}
				else if (line.contains("Crossover rate:"))
				{
					String[] split = line.split(":");
					String dblSubstring = split[1].trim();
					textFieldCrossover.setText(dblSubstring);
				}
				else if (line.contains("Number of evaluations:"))
				{
					String[] split = line.split(":");
					String intSubstring = split[1].trim();
					textFieldEvaluations.setText(intSubstring);
				}
				else if (line.contains("Number of turns:"))
				{
					String[] split = line.split(":");
					String intSubstring = split[1].trim();
					textFieldTurns.setText(intSubstring);
				}
				else if (line.contains("Checkpoint:"))
				{
					String[] split = line.split(":");
					String intSubstring = split[1].trim();
					textFieldCheckpoint.setText(intSubstring);
				}
				else if (line.contains("Opponent:"))
				{
					String[] split = line.split(":");
					String strSubstring = split[1].trim();
					for (int i=0; i<comboBoxOpponent.getItemCount()-1; i++) {
						comboBoxOpponent.setSelectedIndex(i);
						String currentItem = (String)comboBoxOpponent.getSelectedItem();
						if(currentItem.equals(strSubstring)) {
							break;
						}
					}
				}
				else if (line.contains("Scenario:"))
				{
					String[] split = line.split(":");
					String strSubstring = split[1].trim();
					for (int i=0; i<comboBoxScenarios.getItemCount()-1; i++) {
						comboBoxScenarios.setSelectedIndex(i);
						String currentItem = (String)comboBoxScenarios.getSelectedItem();
						if(currentItem.equals(strSubstring)) {
							break;
						}

					}
				}
				else if (line.contains("Selection scheme:"))
				{
					String[] split = line.split(":");
					String strSubstring = split[1].trim();
					for (int i=0; i<comboBoxSelectionScheme.getItemCount()-1; i++) {
						comboBoxSelectionScheme.setSelectedIndex(i);
						String currentItem = (String)comboBoxSelectionScheme.getSelectedItem();
						if(currentItem.equals(strSubstring)) {
							break;
						}
					}
				}
				else if (line.contains("Visibility:"))
				{
					String[] split = line.split(":");
					String strSubstring = split[1].trim();
					for (int i=0; i<comboBoxVisibility.getItemCount()-1; i++) {
						comboBoxVisibility.setSelectedIndex(i);
						String currentItem = (String)comboBoxVisibility.getSelectedItem();
						if(currentItem.equals(strSubstring)) {
							break;
						}
					}
				}

			}
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void notifyThreadCompletion(final Thread t) {
		btnRun.setText("Run");
		
		// Run has completed successfully
		if (check == true)
		{	
			// Display results button to open evolution folder.
			btnRunResults.setVisible(true);
			check = false;
			
			// Display trace window
			try {
				traceDialog = new traceDialogWindow(evolutionFolder, frmMicrortsAiGui);
				traceDialog.run();
				
        		JTextField[] fields = {textFieldSeed, textFieldPopulation, textFieldGeneration, textFieldMutation, textFieldCrossover, textFieldEvaluations, textFieldCheckpoint};
				graphDialog = new graphDialogWindow(frmMicrortsAiGui, evolutionFolder, fields, opponentName, scenarioName, selectionName);
				graphDialog.run();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {      
        if (e.getSource() == btnClear) { // clear button
			textFieldSeed.setText("");
        	textFieldPopulation.setText("");
        	textFieldGeneration.setText(""); 
        	textFieldMutation.setText(""); 
        	textFieldCrossover.setText("");
        	textFieldEvaluations.setText("");
        }else if (e.getSource() == comboBoxOpponent) { // check opponent for MonteCarlo
        	String check = "MonteCarlo";
        	String selected = (String)comboBoxOpponent.getSelectedItem();
        	
        	if (selected.equals(check)) { // if MonteCarlo display fields
        		
        		lblTimeBudget.setVisible(true);
        		lblIterationBudget.setVisible(true);
        		lblPlayoutLookahead.setVisible(true);
        		lblMaxActions.setVisible(true);
        		lblPlayoutAI.setVisible(true);
        		lblMaxActions.setVisible(true);
        		lblPlayoutAI.setVisible(true);
        		
        		lblIconTimeBudget.setVisible(true);
        		lblIconIterationBudget.setVisible(true);
        		lblIconLookahead.setVisible(true);
        		
        		textFieldTimeBudget.setVisible(true);
        		textFieldIterationBudget.setVisible(true);
        		textFieldLookAhead.setVisible(true);
        		textFieldMaxActions.setVisible(true);
        		
        		comboBoxPlayoutAI.setVisible(true);
        	}
        	else { // hide fields
        		
        		lblTimeBudget.setVisible(false);
        		lblIterationBudget.setVisible(false);
        		lblPlayoutLookahead.setVisible(false);
        		lblMaxActions.setVisible(false);
        		lblPlayoutAI.setVisible(false);
        		lblMaxActions.setVisible(false);
        		lblPlayoutAI.setVisible(false);
        		
        		lblIconTimeBudget.setVisible(false);
        		lblIconIterationBudget.setVisible(false);
        		lblIconLookahead.setVisible(false);
        		
        		textFieldTimeBudget.setVisible(false);
        		textFieldIterationBudget.setVisible(false);
        		textFieldLookAhead.setVisible(false);
        		textFieldMaxActions.setVisible(false);
        		
        		comboBoxPlayoutAI.setVisible(false);
        	}
        }
        else if (e.getSource() == btnCreateScenario) { // create a scenario
			try { // display the state window
				stateDialog.run();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
    	}else if (e.getSource() == btnDeleteScenario) { // delete a scenario
    		String delete = (String)comboBoxScenarios.getSelectedItem();
    		deleteScenario(delete);
    	}else if (e.getSource() == btnRunResults) {
        	try {
				Desktop.getDesktop().open(evolutionFolder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Error: Evolution folder not found.");
			}
        }
    	else if(e.getSource() == btnContinue) { // resume from checkpoint
    		
    		JFileChooser fc = new JFileChooser();
    		int returnVal = fc.showOpenDialog(null);
    		boolean checkpoint = false;
    		
    		File cpFile = null;
    		File cpEvolutionFolder = null;
    		String errorMessage = "";
    		
    		// Select the checkpoint file
    		if(returnVal == fc.APPROVE_OPTION) {
    			cpFile = fc.getSelectedFile();
    			
    			// If the file is indeed a checkpoint file
    			if (cpFile.getName().toLowerCase().endsWith(".gz")) {
    				cpEvolutionFolder = fc.getCurrentDirectory();
    				evolutionFolder = cpEvolutionFolder;
    				checkpoint = true;
    			}
    			// If it is not then set cpFile to null
    			else {
    				cpFile = null;
    				errorMessage = "Selected file is not a valid checkpoint file.";
    				JOptionPane.showMessageDialog(new JFrame(), errorMessage);
    			}
    			
    		}
    		else {
    			errorMessage = "Open command cancelled by user.";
    			JOptionPane.showMessageDialog(new JFrame(), errorMessage);
    		}
    		
    		// If a valid checkpoint file has been chosen
    		if (checkpoint == true) {
    			// Get the values of the config file
    			File[] dirFiles = cpEvolutionFolder.listFiles();
    			File configFile = null;
    			for (File file : dirFiles) {
    				if (file.getName().equals("Evolution_configuration.txt"))
    				{
    					configFile = file;
    				}
    			}
    			
    			try {
    				// Get configuration from the config file
					readFile(configFile);
					
					// Resume evolution at the checkpoint
					if (thread != null && thread.isAlive())
		        	{
		        		// abort evolution and perform cleanup
						errorMessage = "WARNING!: An evolution is currently running.\nPlease ABORT the current evolution and try again.";
						JOptionPane.showMessageDialog(new JFrame(), errorMessage);
		            	check = false;
		            	
		        	}
		        	else {
			        	// run button
		        		JTextField[] fields = {textFieldSeed, textFieldPopulation, textFieldGeneration, textFieldMutation, textFieldCrossover, textFieldEvaluations, textFieldTurns, textFieldCheckpoint};
		        		errorMessage = errorCheck(fields);
		        		if(errorMessage == "OK")
		        		{
		        			
		        			btnRunResults.setVisible(false);
		        			check = true;
		        			
		        			getValues(fields, comboBoxOpponent, comboBoxSelectionScheme, comboBoxScenarios, comboBoxVisibility);
		      
		            		String turnNumber = textFieldTurns.getText();
		            		
		            		// Monte Carlo Textfields.
		            		if (opponentName.equals("MonteCarlo")) {
		            			timeBudget = textFieldTimeBudget.getText();
		            			iterationBudget = textFieldIterationBudget.getText();
		            			lookahead = textFieldLookAhead.getText();
		            			maxActions = textFieldMaxActions.getText();
		            			playoutAI = (String)comboBoxPlayoutAI.getSelectedItem();
		            		} else {
		            			timeBudget = null;
		            			iterationBudget = null;
		            			lookahead = null;
		            			maxActions = null;
		            			playoutAI = null;
		            		}
		            		
		    	        	String cpFileName = cpFile.getAbsolutePath();
		    	        	
		    	        	// Run evolve
		    	        	arguments = new String[] {"-checkpoint", cpFileName};
		    	        	
		    				thread = new MyThread(arguments, cpEvolutionFolder, scenarioPath, opponentName, turnNumber, visibilityType);
		    				thread.addListener(this);
		    				thread.start();
		    				btnRun.setText("Abort");
		        		} else {
		        			JOptionPane.showMessageDialog(new JFrame(), errorMessage);
		        		}

						
						
						
		    		}		
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    			
    			
    			
    		}
    	}
    	else if (e.getSource() == btnRun) {
        	
        	if (thread != null && thread.isAlive())
        	{
        		// abort evolution and perform cleanup
            	thread.interrupt();
            	btnRun.setText("Run");
            	check = false;
            	
        	}
        	else {
	        	// run button
        		JTextField[] fields = {textFieldSeed, textFieldPopulation, textFieldGeneration, textFieldMutation, textFieldCrossover, textFieldEvaluations, textFieldTurns, textFieldCheckpoint};
        		String errorMessage = errorCheck(fields);
        		if(errorMessage == "OK")
        		{
        			
        			btnRunResults.setVisible(false);
        			check = true;
        			
//        			Date date = new Date();
//        			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
//        			String dateNow = (String)formatter.format(date);
        			getValues(fields, comboBoxOpponent, comboBoxSelectionScheme, comboBoxScenarios, comboBoxVisibility);
        			// sceneario name - mut - cross - date - time
//        			evolutionFolderName = "Evolution - "+String.valueOf(System.currentTimeMillis());
        			evolutionFolderName = "Scenario- " + scenarioName + " c- " + textFieldCrossover.getText() + " m- " + textFieldMutation.getText() + " " + System.currentTimeMillis();
        			evolutionFolder = new File("./"+evolutionFolderName);
        			
            		
            		createConfig(fields, opponentName, scenarioName, selectionName, evolutionFolderName, scenarioPath, visibilityType);
            		
            		
            		String seed = textFieldSeed.getText();
            		String population = textFieldPopulation.getText();
            		String generation = textFieldGeneration.getText();
            		String mutation = textFieldMutation.getText();
            		String crossover = textFieldCrossover.getText();
            		String tournamentName = "tournament";
            		String evaluationNumber = textFieldEvaluations.getText();
            		String turnNumber = textFieldTurns.getText();
            		String checkpoint = textFieldCheckpoint.getText();
            		String selection = null;
            		
//            		
            		
            		if (String.valueOf(comboBoxSelectionScheme.getSelectedItem()) == "SUS") {
            			selection = "ec.select.SUSSelection";
            		} else if (String.valueOf(comboBoxSelectionScheme.getSelectedItem()) == "Tourn3") {
            			selection = "ec.select.TournamentSelection";
            		}
          
            		
            		// Monte Carlo Textfields.
            		if (opponentName.equals("MonteCarlo")) {
            			timeBudget = textFieldTimeBudget.getText();
            			iterationBudget = textFieldIterationBudget.getText();
            			lookahead = textFieldLookAhead.getText();
            			maxActions = textFieldMaxActions.getText();
            			playoutAI = (String)comboBoxPlayoutAI.getSelectedItem();
            		} else {
            			timeBudget = null;
            			iterationBudget = null;
            			lookahead = null;
            			maxActions = null;
            			playoutAI = null;
            		}
            		
    	        	
    	        	// Run evolve
    	        	arguments = new String[] {"-from", "app/BTEvolve/BT.params",
    						"-p", "seed.0="+seed,
    						"-p", "pop.subpop.0.size="+population,
    						"-p", "generations="+generation,
    						"-p", "pop.subpop.0.species.pipe.source.0.prob="+crossover,
    						"-p", "pop.subpop.0.species.pipe.source.1.prob="+mutation,
    						"-p", "gp.koza.mutate.source.0="+selection,
    						"-p", "breed.reproduce.source.0="+selection,
    						"-p", "stat.file=$"+evolutionFolderName+"/Evolution_out.txt",
    						"-p", "path="+scenarioPath,
    						"-p", "opponent="+opponentName,
    						"-p", "tournament="+tournamentName,
    						"-p", "evaluationNumber="+evaluationNumber,
    						"-p", "turnNumber="+turnNumber,
    						"-p", "evolutionfolder="+evolutionFolderName,
    						"-p", "stat.child.0.file=$"+evolutionFolderName+"/Evolution_out_2.txt",
    						"-p", "timeBudget="+timeBudget,
    						"-p", "iterationBudget="+iterationBudget,
    						"-p", "lookahead="+lookahead,
    						"-p", "maxActions="+maxActions,
    						"-p", "playoutAI="+playoutAI,
    						"-p", "checkpoint=true",
    						"-p", "checkpoint-modulo="+checkpoint,
    						"-p", "checkpoint-prefix=checkpoint",
    						"-p", "checkpoint-directory=$"+evolutionFolderName+"/",
    						"-p", "visibility="+visibilityType};
    	        	
    				thread = new MyThread(arguments, evolutionFolder, scenarioPath, opponentName, turnNumber, timeBudget, iterationBudget, lookahead, maxActions, playoutAI, visibilityType);
    				thread.addListener(this);
    				thread.start();
    				btnRun.setText("Abort");
        		} else {
        			JOptionPane.showMessageDialog(new JFrame(), errorMessage);
        		}

				
				
				
    		}		
    	}
	}
}

	
