/*
 * File created by: Matthew Burr as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */
// Reference : https://stackoverflow.com/questions/702415/how-to-know-if-other-threads-have-finished

package ec.app.BTEvolve;


import ec.Evolve;
import ec.util.Output;
import jbt.model.core.ModelTask;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tests.ECJ_Tournament;

import java.io.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public class MyThread extends Thread 
{

	
	// Array to store evolution arguments
	String[] arguments;
	// File to store evolution folder
	static File evolutionFolder;
	// String to store scenario path
	String scenarioPath = "";
	// String to store opponent name
	String opponentName = "";
	// String to store behaviour tree string
	String tree = "";
	int turns = 0;
	int timeBudget = 0;
	int iterationBudget = 0;
	int lookahead = 0;
	int maxActions = 0;
	String playoutAI = "";
	String visibilityType = "";

	// Output to store a custom output object
	Output output = new Output(true);
	// Evolve to store custom evolve object
	Evolve evolve;
	
	
	// MyThread constructor
	MyThread(String[] arguments, File evolutionFolder, String scenarioPath, String opponentName, String turnNumber, String timeBudget, String iterationBudget, String lookahead, String maxActions, String playoutAI, String visibilityType){
		this.arguments = arguments; // Set arguments to input
		this.evolutionFolder = evolutionFolder; // Set evolutionFolder to input
		this.scenarioPath = scenarioPath;
		this.opponentName = opponentName;
		this.turns = Integer.parseInt(turnNumber);
		this.visibilityType = visibilityType;
		
		if (opponentName.equals("MonteCarlo")) {
			this.timeBudget = Integer.parseInt(timeBudget);
			this.iterationBudget = Integer.parseInt(iterationBudget);
			this.lookahead = Integer.parseInt(lookahead);
			this.maxActions = Integer.parseInt(maxActions);
			this.playoutAI = playoutAI;
		}


	}
	
	MyThread(String[] arguments, File evolutionFolder, String scenarioPath, String opponentName, String turnNumber, String visibilityType){
		this.arguments = arguments;
		this.evolutionFolder = evolutionFolder;
		this.scenarioPath = scenarioPath;
		this.opponentName = opponentName;
		this.turns = Integer.parseInt(turnNumber);
		this.visibilityType = visibilityType;
	}
	
	public void run() {
		try {
			// Run the main evolve loop
			runEvolve();
			// Generate the trace file
			genTrace();
			// Generate the excel file
			genExcel();
		} catch (java.lang.Error e) {
			// Cleanup
			System.out.println(e);
			System.out.println("\nAborted Evolution\n");
			output.flush(); // Flush output streams
			output.close(); // Close output streams
			cleanUp(evolutionFolder); // Remove partial files
			


		} catch (InterruptedException e) {
			System.out.println("\nAborted Evolution\n");
			
		} finally {
			// Notify on thread completion
			notifyListeners();
		}
	}

	// Run the Evolve main loop
	public void runEvolve() throws InterruptedException {
		evolve = new Evolve(buildOutput());
		evolve.main(arguments);
	}
	
	// Generate best tree trace
	public void genTrace(){
		// Get the stats file
		File[] dirFiles = evolutionFolder.listFiles();
		File statsFile = null;
		for (File file : dirFiles) {
			if (file.getName().equals("Evolution_out.txt"))
			{
				statsFile = file;
			}
		}

		
		// Read the stats file
		readFile(statsFile);
		// Convert the string tree
		convertTree();
		
	}
	
	public void readFile(File statsFile){
		String goal = "Best Individual of Run:";
		String tree = " ";
		try {
			BufferedReader br = new BufferedReader(new FileReader(statsFile));
			for (String line; (line = br.readLine()) != null;)
			{
				if (line.equals(goal))
				{
					for (int i = 0; i<4; i++)
					{
						line = br.readLine();
						
					}
					
					for (String treeLine; (treeLine = br.readLine()) != null;)
					{
						treeLine = treeLine.trim();
						tree = tree+" "+treeLine;
					}

				}
			}
			br.close();

			tree = " (Sequence AttackClosestBase)";

			this.tree = tree;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void convertTree() {
		String tree = this.tree;
		ModelTask modelTree = Converter.makeModelTask(tree);
		try {
			ECJ_Tournament ET = new ECJ_Tournament(modelTree, scenarioPath, opponentName, evolutionFolder, turns, timeBudget, iterationBudget, lookahead, maxActions, playoutAI, visibilityType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Create Output object log streams
	public Output buildOutput() {
        output.addLog(ec.util.Log.D_STDOUT,false);
        output.addLog(ec.util.Log.D_STDERR,true);
		return output;
	}
	
	// CleanUp partial files
	public void cleanUp(File evolutionFolder) {
		// Get all files in the evolutionFolder directory
		File[] dirFiles = evolutionFolder.listFiles();
		
		// Delete all the files
		for (File file : dirFiles) {
			file.delete();
		}
		
		// Delete the directory
		evolutionFolder.delete();
	}
	
	// Generate the excel file
	public static void genExcel() {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Evolution results");
		
		// Create a row
		Row labelRow = sheet.createRow(0);
		for (int i = 0; i < 4; i++) {
			Cell labelCell = labelRow.createCell(i);
			switch (i){
				case 0:
					labelCell.setCellValue("Gen");
					break;
				case 1:
					labelCell.setCellValue("Mean");
					break;
				case 2:
					labelCell.setCellValue("Best of Gen");
					break;
				case 3:
					labelCell.setCellValue("Best overall");
					break;
			}
		}
		
		// Find the stat file
		File[] dirFiles = evolutionFolder.listFiles();
		File statsFile = null;
		for (File file : dirFiles) {
			if (file.getName().equals("Evolution_out_2.txt"))
			{
				statsFile = file;
			}
		}
		
		// Read the stat file line by line
		try {
			BufferedReader br = new BufferedReader(new FileReader(statsFile));
			int lineCount = 1;
			for (String line; (line = br.readLine()) != null;)
			{
				Row row = sheet.createRow(lineCount);
				
				String[] splitLine = line.split(" ");
				
				int cellCount = 0;
				for (String s : splitLine) {
					Cell cell = row.createCell(cellCount);
					cell.setCellValue(new BigDecimal(s).doubleValue());
					cellCount++;
				}
				lineCount++;
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try (OutputStream fileOut = new FileOutputStream(evolutionFolder+"/Evolution_out_2.xlsx")){
			wb.write(fileOut);
			wb.close();
		} catch (IOException ioe) {
			System.out.print("genExcel() IOException");
		}
	}
	
	// Methods defining the Listeners
	private final Set<ThreadCompleteListener> listeners
					= new CopyOnWriteArraySet<ThreadCompleteListener>();
	
	public final void addListener(final ThreadCompleteListener listener) {
		listeners.add(listener);
	}
	
	public final void removeListener(final ThreadCompleteListener listener) {
		listeners.remove(listener);
	}
	
	private final void notifyListeners() {
		for (ThreadCompleteListener listener : listeners) {
			listener.notifyThreadCompletion(this);
		}
	}
}




