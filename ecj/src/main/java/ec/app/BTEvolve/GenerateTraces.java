package ec.app.BTEvolve;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import jbt.model.core.ModelTask;
import tests.ECJ_Tournament;

public class GenerateTraces {
	File[] statFiles;
	File[] scenarioFiles;
	File output = new File("./altBestTraces");
	ArrayList<String> trees = new ArrayList<String>();
	ArrayList<ModelTask> mTrees = new ArrayList<ModelTask>();
	
	
	GenerateTraces() {
		getTree();
		convertTree();
		//generateTrace();
		
		
		String scenarioPath = "C:\\Users\\lbkelly0\\ECU_work\\DSTG\\2019 RTS\\Experiments\\Working\\Choke point\\Choke point\\scenarios\\chokePoint.xml";
		String scenarioName = "chokePoint";
		String opponentName = "Custom_LightRush";
		ModelTask currentTree = mTrees.get(4);
		try {
			ECJ_Tournament ET = new ECJ_Tournament(currentTree, scenarioPath, opponentName, output, 3000, 1, 1, 1, 1, "null", "Full");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void getTree() {
		statFiles = new File("./stats").listFiles();
		
		for (File file : statFiles) {
			System.out.println(file.getName());
			
			String goal = "Best Individual of Run:";
			//String tree = " ";
			
			String tree = "Selector AttackClosestEnemy";
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
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
				trees.add(tree);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void convertTree() {
		for (String tree : trees) {
			ModelTask mTree = Converter.makeModelTask(tree);
			mTrees.add(mTree);
		}
	}
	
//	public void generateTrace() {
//		scenarioFiles = new File("./scenarios").listFiles();
//		
//		for (int i = 0; i< scenarioFiles.length; i++) {
//			//String scenarioPath = scenarioFiles[i].getAbsolutePath();
//			String scenarioPath = "C:\\Users\\lbkelly0\\ECU_work\\DSTG\\2019 RTS\\Experiments\\Working\\Choke point\\Choke point\\scenarios\\chokePoint.xml";
//			String scenarioName = "chokePoint";
//			//String scenarioName = scenarioFiles[i].getName();
//			String opponentName = "Custom_LightRush";
//			ModelTask currentTree = mTrees.get(4);
//			
//			try {
//				ECJ_Tournament ET = new ECJ_Tournament(currentTree, scenarioPath, opponentName, output, 3000, 1, 1, 1, 1, "null", "Full");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//
//	}
	
	public static void main(String[] args) {
		new GenerateTraces();
	}
}
