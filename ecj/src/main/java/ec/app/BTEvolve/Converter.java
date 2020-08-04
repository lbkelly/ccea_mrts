/*
 * File created by: Matthew Burr as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */
package ec.app.BTEvolve;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader; 
import jbt.model.core.ModelTask;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
// This is the Converter class which aims to take a tree in ECJ format and create a new ModelTask
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    

public class Converter {
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
// Methods to create the new ModelTask
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
    public static ModelTask makeModelTask(String tree)
    {
    	tree = formatString(tree);
    	// Variables to store things:
    	int max = getMax(tree);
    	//System.out.println(max);
        ModelTask task = makeTask(tree, max);
//        System.out.println(task);

    	return task;
    }
    
    public static ModelTask makeTask(String tree, int max)
    {
    	//System.out.println(tree);
    	ArrayList<String> stringArray = new ArrayList();
    	ArrayList<ModelTask> modelArray = new ArrayList();    	
    	// Loop through tree and get sub-strings [Y]
    	while (max >= 0)
    	{
    		int index = 0;
    		int endIndex = 0;
    		for (int i = 0; i < tree.length(); i++)
    		{
    			if (tree.charAt(i) == '{')
    			{
    				// Found the start
    				String sub = tree.substring(i, tree.length());
    				String findNum = sub.substring(0, sub.indexOf('}')+1);
    				String treeS = tree.substring(i, i+sub.indexOf('}')+1);
    				int number = Integer.parseInt(findNum.replaceAll("\\D", ""));
    				
    				
    				if (number == max)
    				{
    					index = i;
    					for(int j = 0; j<tree.length(); j++)
    					{
    						if (tree.charAt(j) == '{' && j > i)
    						{
    							// Found the start
    		    				String sub2 = tree.substring(j, tree.length());
    		    				String findNum2 = sub2.substring(0, sub2.indexOf('}')+1);
    		    				int number2 = Integer.parseInt(findNum2.replaceAll("\\D", ""));
    						
    						
    		    				if(number2 == number)
        						{
    		    					// Get the index of the next max value
    		    					endIndex = j+sub2.indexOf('}')+1;

            						String subString = tree.substring(index, endIndex);
            						subString = subString.replaceAll("\\{", "'");
            						subString = subString.replaceAll("\\}", "'");            						
            						stringArray.add(subString);
                		    		i = endIndex;
                		    		evalSub(subString, stringArray, modelArray);
            						
                		    		index = 0;
                		    		endIndex = 0;
                		    		break;
                    		    		
        						}
    						}
    						
    					}
    				}
    			}
    		}
		max--;
    	}

    	ModelTask finalTask = modelArray.get(modelArray.size()-1);
    	return finalTask;
    }
    public static void evalSub(String sub, ArrayList<String> stringArray, ArrayList<ModelTask> modelArray) {
  
    	boolean requirements = false;
    	
		for(int k=0; k<stringArray.size();k++)
		{
			// If the string contains another string, but they're not the same one
			// Or the string is not the exact same as another string
			if(sub.contains(stringArray.get(k)) && stringArray.indexOf(sub) != k
				&&	!sub.equals(stringArray.get(k)))
			{
				requirements = true;
				break;
			}
		}
		
		// If the current string contains a previous string
		if (requirements == false)
		{
			// Make the task
			String task = sub; // Set the task
			String parent = "";
			int numChild = 0; // Set child counter to 0
    		task = task.replaceAll("[0-9]", "");
    		task = task.replaceAll("'", "");
    		task = task.trim();
        	String[] taskSplit = task.split("\\s+"); // Split the task up by spaces
        	
        	for(String taskString :taskSplit)
        	{
        		numChild++; // increment # child
        	}
        	
    		parent = taskSplit[0]; // Set a parent
    		if(parent.equals("Selector"))
            {	
    			ArrayList<ModelTask> child = new ArrayList<ModelTask>();
            	for (int i=1; i< numChild;i++)
            	{
            		child.add(getTaskType(taskSplit[i]));

            	}
            	modelArray.add(createSel(child));// Create the model task

            }
            else if(parent.equals("Sequence"))
            {
            	ArrayList<ModelTask> child = new ArrayList<ModelTask>();
            	for (int i=1; i< numChild;i++)
            	{
            		child.add(getTaskType(taskSplit[i]));
            		
            	}
            	
            	modelArray.add(createSeq(child));// Create the model task
            }
			
		}
		else { 
			
			String task = sub; // Get the task
			task = task.trim();
			int numChild = 0; // Set child counter to 0
			String parent = "";
			for (int intNum = stringArray.size(); --intNum >= 0;)
			{
				// If the task contains a string, which is not itself
				if(task.contains(stringArray.get(intNum)) && !task.equals(stringArray.get(intNum)))
				{
                    task = task.replaceAll(stringArray.get(intNum), "[c"+intNum+"]");
					
				}
			}
			task = task.replaceAll("\\'\\d+\\'", "");
			
        	String[] taskSplit = task.split("\\s+"); // Split the task up by spaces
        	
        	for(String substrings :taskSplit)
        	{
        		numChild++; // increment # child
        	}
        	
    		parent = taskSplit[0]; // Set a parent
    		
    		if(parent.equals("Selector"))
            {	
    			ArrayList<ModelTask> child = new ArrayList<ModelTask>();
            	for (int i=1; i< numChild;i++)
            	{
            		
            		if(taskSplit[i].contains("[c"))
            		{
            			int intCount = Integer.parseInt(taskSplit[i].replaceAll("\\D",""));
            			child.add(modelArray.get(intCount));

            		}
            		else
            		{
            			child.add(getTaskType(taskSplit[i]));
            		}
            		
            	}

            	modelArray.add(createSel(child));// Create the model task
            }
            else if(parent.equals("Sequence"))
            {
            	ArrayList<ModelTask> child = new ArrayList<ModelTask>();
            	for (int i=1; i< numChild;i++)
            	{
            		if(taskSplit[i].contains("[c"))
            		{
            			int intCount = Integer.parseInt(taskSplit[i].replaceAll("\\D",""));
            			child.add(modelArray.get(intCount));
            		}
            		else
            		{
            			child.add(getTaskType(taskSplit[i]));


            		}
            	}

            	modelArray.add(createSeq(child));// Create the model task
            }
			
		}
	}
    
	// Create a ModelTask Sequence based on number of child nodes
    public static ModelTask createSeq(ArrayList<ModelTask> children)
    {
    	int numChildren = 0;
    	ModelTask seq = null;
    	
    	for (ModelTask m : children)
    	{
    		numChildren++;
    	}
    	
    	if (numChildren == 1)
    	{
    		seq = new jbt.model.task.composite.ModelSequence(null, children.get(0));
    	}
    	else if (numChildren == 2)
    	{
    		seq = new jbt.model.task.composite.ModelSequence(null, children.get(0), children.get(1));
    	}
    	else if (numChildren == 3)
    	{
    		seq = new jbt.model.task.composite.ModelSequence(null, children.get(0), children.get(1), children.get(2));
    	}
    	else if (numChildren == 4)
    	{
    		seq = new jbt.model.task.composite.ModelSequence(null, children.get(0), children.get(1), children.get(2), children.get(3));
    	}
		return seq;
    }
    
	// Create a ModelTask Selector based on number of child nodes
    public static ModelTask createSel(ArrayList<ModelTask> children)
    {
    	int numChildren = 0;
    	ModelTask sel = null;
    	
    	for (ModelTask m : children)
    	{
    		numChildren++;
    	}
    	
    	if (numChildren == 1)
    	{
    		sel = new jbt.model.task.composite.ModelSelector(null, children.get(0));
    	}
    	else if (numChildren == 2)
    	{
        	sel = new jbt.model.task.composite.ModelSelector(null, children.get(0), children.get(1));
    	}
    	else if (numChildren == 3)
    	{
    		sel = new jbt.model.task.composite.ModelSelector(null, children.get(0), children.get(1), children.get(2));
    	}
    	else if (numChildren == 4)
    	{
    		sel = new jbt.model.task.composite.ModelSelector(null, children.get(0), children.get(1), children.get(2), children.get(3));
    	}
		return sel;
    }
	// Get the correct task based on string name and return as ModelTask
    public static ModelTask getTaskType(String task)
    {
    	ModelTask result = null;
    	
    	switch(task)
        {
        case "AttackClosestEnemy":
        	result = new bts.actions.AttackClosestEnemy(null, null, "gsVar",
    				null, "playerVar", null, "unitVar");
            break;
        case "AttackClosestBase":
        	result = new bts.actions.AttackClosestBase(null, null, "gsVar",
    				null, "playerVar", null, "unitVar");
            break;
        case "MoveToClosestAllies":
        	result = new bts.actions.MoveToClosestAllies(null, null, "gsVar",
    				null, "playerVar", null, "unitVar", null, null, null, null);
            break;
        case "MoveLeft":
        	result = new bts.actions.MoveLeft(null, null, "gsVar",
    				null, "playerVar", null, "unitVar", null, null, null, null);
            break;
        case "MoveRight":
        	result = new bts.actions.MoveRight(null, null, "gsVar",
    				null, "playerVar", null, "unitVar", null, null, null, null);
            break;
        case "MoveUp":
        	result = new bts.actions.MoveUp(null, null, "gsVar",
    				null, "playerVar", null, "unitVar", null, null, null, null);
            break;
        case "MoveDown":
        	result = new bts.actions.MoveDown(null, null, "gsVar",
    				null, "playerVar", null, "unitVar", null, null, null, null);
            break;
        case "Idle":
        	result = new bts.actions.Idle(null, null, "gsVar",
    				null, "playerVar", null, "unitVar");
        	break;
        case "CheckForEnemies":
        	result = new bts.conditions.EnemyIsClose(null);
            break;
        case "HealthLessThanTwo":
        	result = new bts.conditions.HealthLessThanTwo(null);
            break;
        case "CheckForBases":
        	result = new bts.conditions.EnemyBaseIsClose(null);
            break;
        case "CheckForAllies":
        	result = new bts.conditions.CheckForAllies(null);
            break;
        case "CheckIfLight":
        	result = new bts.conditions.CheckIfLight(null);
            break;
        case "CheckIfHeavy":
        	result = new bts.conditions.CheckIfHeavy(null);
            break;
        case "CheckIfRanged":
        	result = new bts.conditions.CheckIfRanged(null);
            break;
        default:
        	result = null;
            break;
        }
    	
		return result;
    }

    // Method to get the deepest child in a tree
    public static int getMax(String tree)
    {
        int max = 0;
        // For the string
        for(int i = 0;i<tree.length();i++)
        {   
            // Try get the max integer bracket and solve it first.
            try
            {
            	if (tree.charAt(i) == '{')
            	{
            		String sub = tree.substring(i, tree.length()-1);
            		String maxNumber = sub.substring(0, sub.indexOf('}'));

            		
            		int newInt = Integer.parseInt(maxNumber.replaceAll("\\D",""));
            		
            		if (newInt > max)
            		{
            			max = newInt;
            		}
            	}
            }catch (Exception e)
            {
            	continue;
            }

        }
        return max;
    }
    
    // Method to format the tree (Replacing parentheses with integer values to denote how depth)
    public static String formatString(String tree)
    {
    	// Count = 0;
    	int count = -1;
    	String finalString = "";

    	for (int i=0; i < tree.length(); i++)
        {
            if (tree.charAt(i) == '(')
            {    
            	count++;
            	
            	String intString = Integer.toString(count);
            	tree = tree.substring(0, i) + "{" + intString + "}" + tree.substring(i+1);

            	
                
            }
            else if(tree.charAt(i) == ')')
            {
            	String intString = Integer.toString(count);
            	tree = tree.substring(0, i) + "{" + intString + "}" + tree.substring(i+1);
                count--;
            }
            
        }
		return tree;
    }
    

    
    public static void main(String[] args)
    {
        
    }
}
