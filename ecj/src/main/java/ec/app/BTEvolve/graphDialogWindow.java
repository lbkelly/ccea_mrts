// Reference: https://stackoverflow.com/questions/36754524/how-to-plot-a-line-graph-in-java-using-dataset-from-a-text-file

package ec.app.BTEvolve;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class graphDialogWindow extends JDialog {
	
	File evolutionFolder;
	private XYSeriesCollection data = null;
	
	public graphDialogWindow(Frame parent, File file, JTextField[] fields, String opponentName, String scenarioName, String selectionName) {
    	this.evolutionFolder = file;
    	
    	final JTextArea textArea = new JTextArea(8,16);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        
        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Evolution Run Details"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        ChartPanel graphicsArea = new ChartPanel(null);
        graphicsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Graphical Representation"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textScrollPane, graphicsArea);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.LINE_END);
        
        // Add the evolution run details
        for(int i = 0; i < fields.length; i++) {
        	if (i == 0) {
				textArea.append("Seed: "+fields[i].getText()+"\n");
			}else if (i == 1) {
				textArea.append("Population: "+fields[i].getText()+"\n");

			}else if (i == 2) {
				textArea.append("Generations: "+fields[i].getText()+"\n");

			}else if (i == 3) {
				textArea.append("Mutation rate: "+fields[i].getText()+"\n");

			}else if(i == 4) {
				textArea.append("Crossover rate: "+fields[i].getText()+"\n");

			}else if(i == 5) {
				textArea.append("Number of evaluations: "+fields[i].getText()+"\n");
			}


		}
        
        textArea.append("Opponent: "+opponentName+"\n");
        textArea.append("Scenario: "+scenarioName+"\n");
        textArea.append("Selection: "+selectionName+"\n");
        
        // get the file
		File[] dirFiles = evolutionFolder.listFiles();
		File statsFile = null;
		for (File files : dirFiles) {
			if (files.getName().equals("Evolution_out_2.txt"))
			{
				statsFile = files;
			}
		}
		
        // create a jchart
        graphicsArea.setChart(createLineChart(statsFile));
        pack();
    	
    	Point parentLocation = parent.getLocation();
    	int parentWidth = parent.getWidth()+20;
    	setLocation(parentLocation.x+parentWidth, parentLocation.y);
    	
    	
	}
	public void run() {
		this.setVisible(true);
    	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	this.setSize(950, 400);
    	this.setModal(false);
    	this.setTitle("Graph Results");

	}
	
	private JFreeChart createLineChart(File file) {
		data = new XYSeriesCollection();
		String title = "Best/Mean Fitness over Generational Evolution";
		String xAxisLabel = "Generations";
		String yAxisLabel = "Fitness";
		BufferedReader br = null;
		int start = 1;
		XYSeries meanSet = new XYSeries("Mean");
		XYSeries bestSetOverall = new XYSeries("Best Overall");
		XYSeries bestSetGen = new XYSeries("Best of Gen");
		try {
			br = new BufferedReader(new FileReader(file));
			
			for (String line; (line = br.readLine()) != null;)
			{
				String[] split = line.trim().split(" ");

				for (int i = 0; i < split.length; i++) {
					if (i == 1) {
						meanSet.add(start, Double.valueOf(split[1]));
					}
					
					if (i == 2) {
						bestSetGen.add(start, Double.valueOf(split[2]));
					}
					
					if (i == 3) {
						bestSetOverall.add(start, Double.valueOf(split[3]));
					}
					

				}
				start++;
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		data.addSeries(meanSet);

		data.addSeries(bestSetGen);
		
		data.addSeries(bestSetOverall);
		JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, data, PlotOrientation.VERTICAL, true, false, false);
		return chart;
		
	}
}
