/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

/*
 * File is a modified version of the original ECJ MultiValuedRegression Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package ec.app.BTEvolve;
import ec.util.*;
import jbt.model.core.ModelTask;
import rts.GameState;
import rts.PhysicalGameState;
import rts.units.Unit;

import java.util.ArrayList;

import ec.*;
import ec.gp.*;
import ec.simple.*;
import ec.app.BTEvolve.Converter;
import tests.ECJ_Tournament;





public class MyProblem extends GPProblem implements SimpleProblemForm
    {
    private static final long serialVersionUID = 1;
    public GameState gs = null;
    public Unit u = null;
    
    // Fitness stats
    protected double currentResult = 0.0;
    
    // Unit stats
    protected double RedHP = 0.0;
    protected  double BlueHP = 0.0;
    protected double TotalRedHP = 0.0;
    protected double TotalBlueHP = 0.0;
    protected double MaxHP = 0.0;
    
    protected double finalResult = 0.0;
    
    protected int numEvaluations = 0;
    protected int numTurns = 0;
    
    protected int timeBudget = 0;
    protected int iterationBudget = 0;
    protected int playoutLookahead = 0;
    protected int maxActions = 0;
    
    
    
    protected ArrayList<Double> resultArray = null;
    
    public void setup(final EvolutionState state,
        final Parameter base)
        {
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof StringData))
            state.output.fatal("GPData class must subclass from " + StringData.class,
                base.push(P_DATA), null);
        }
        
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum)
        {
        if (((GPIndividual)ind).trees[0].child.expectedChildren() !=0)  // If an individual is not evaluated and the first node has expected child nodes
            {
            resultArray = new ArrayList<Double>();
        	

            
            // Get the current individuals tree
            String indTree = ((GPIndividual)ind).trees[0].child.makeLispTree();
            
//            System.out.println(indTree);
            ModelTask tree = Converter.makeModelTask(indTree); // Make a ModelTask

            ECJ_Tournament ET = null;
            ET = new ECJ_Tournament(tree);

            
            try {
            	
            	// Retrieve user input parameters from BT_App GUI
            	Parameter pathParam = new Parameter("path");
            	Parameter opponentParam = new Parameter("opponent");
            	Parameter tournamentParam = new Parameter("tournament");
            	Parameter evaluationParam = new Parameter("evaluationNumber");
            	Parameter turnParam = new Parameter("turnNumber");
            	Parameter visibilityParam = new Parameter("visibility");
            	
            	// Monte Carlo Settings
            	Parameter timeBudgetParam = new Parameter("timeBudget");
            	Parameter iterationBudgetParam = new Parameter("iterationBudget");
            	Parameter lookaheadParam = new Parameter("lookahead");
            	Parameter maxActionsParam = new Parameter("maxActions");
            	Parameter playoutAIParam = new Parameter("playoutAI");
            	
            	String time = state.parameters.getString(timeBudgetParam, null);
            	String iteration = state.parameters.getString(iterationBudgetParam, null);
            	String look = state.parameters.getString(lookaheadParam, null);
            	String actions = state.parameters.getString(maxActionsParam, null);
            	String playout = state.parameters.getString(playoutAIParam, null);

            	String visibility = state.parameters.getString(visibilityParam, null);
            	String turns = state.parameters.getString(turnParam, null);
            	String evaluation = state.parameters.getString(evaluationParam, null);
            	String scenePath = state.parameters.getString(pathParam, null);
            	String sceneOpponent = state.parameters.getString(opponentParam, null);
            	String tournamentType = state.parameters.getString(tournamentParam, null);
            	
            	// Initiate a loop for the number of evaluations entered in the BT_App GUI
            	numEvaluations = Integer.parseInt(evaluation);
            	numTurns = Integer.parseInt(turns);
            	
            	if (sceneOpponent.equals("MonteCarlo")) {
                	timeBudget = Integer.parseInt(time);
                	iterationBudget = Integer.parseInt(iteration);
                	playoutLookahead = Integer.parseInt(look);
                	maxActions = Integer.parseInt(actions);
            	}

            	
            	
            	for (int i = 0; i < numEvaluations; i++) {
            		
            		// Reset the variables
            		reset();
            		
            		// Run the tournament
            		gs = getTournamentType(tournamentType, ET, scenePath, sceneOpponent, numTurns, timeBudget, iterationBudget, playoutLookahead, maxActions, playout, visibility);
                    PhysicalGameState pgs = gs.getPhysicalGameState();
                    
                    // Variables for fitness calculation:
                    TotalRedHP = ET.getStartingHP(1); // Red team HP
                    TotalBlueHP = ET.getStartingHP(0); // Blue team HP
                    MaxHP = ET.getMaxHP(); // Highest starting HP
                    
                    // Get/Set the remaining HP of the units
                    for(Unit u : pgs.getUnits())
                    {
                        if (u.getPlayer() == 1) // If red
                        {
                            RedHP = RedHP + u.getHitPoints();
                            
                        }
                        else // If blue
                            {
                            BlueHP = BlueHP + u.getHitPoints();
                            }
                    }
                    
                    // Calculate the fitness of the individuals solution
                    currentResult = (BlueHP-RedHP)+MaxHP;   
                    
                    // Tiamat creates situations where there can be negative scores due to enemy workers being created
                    if (currentResult <= 0) 
                    {
                    	currentResult = 0;
                	};
                	
                	// Add the result to an array of results
                	resultArray.add(currentResult);
                    
            	}
            	
            	
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Get the total of all evaluation results
            for (double doubles : resultArray) {
            	finalResult = finalResult + doubles;

            }
            
            // Get the average result of all evaluations
            finalResult = finalResult/numEvaluations;
            
            // Calculate the fitness
            SimpleFitness f = ((SimpleFitness)ind.fitness);
            f.setFitness(state, finalResult, false);
//            f.hits = hits;
            ind.evaluated = true;
            
            finalResult = 0.0;
            }
        else {
        	SimpleFitness f = ((SimpleFitness)ind.fitness);
			f.setFitness(state, 0.0, false);

			ind.evaluated = true;
        	
        }
    }
    
    public GameState getTournamentType(String tournament, ECJ_Tournament ET, String scenePath, String sceneOpponent, int numTurns, int time, int iteration, int look, int actions, String playoutAI, String visibility) throws Exception {
    	GameState gs = null;
    	
		// Returns the desired tournament type based on user input as a GameState object
    	if(tournament.equals("tournament"))
    	{
    		gs = ET.tournamentFast(scenePath, sceneOpponent, numTurns, time, iteration, look, actions, playoutAI, visibility);
    	}
    	else if(tournament.equals("budget tournament"))
    	{
    		// Depreciated.
//    		gs = ET.tournamentComputationBudget(scenePath, sceneOpponent, numTurns, time, iteration, look, actions, playoutAI, visibility);
    	}
    	else {gs = ET.tournamentFast(scenePath, sceneOpponent, numTurns, time, iteration, look, actions, playoutAI, visibility);}

    	return gs;
    }
    
    public void reset() {
        // Fitness stats
    	currentResult = 0.0;
        
        // Unit stats
        RedHP = 0.0;
        BlueHP = 0.0;
        TotalRedHP = 0.0;
        TotalBlueHP = 0.0;
        MaxHP = 0.0;
    }
    
    }

