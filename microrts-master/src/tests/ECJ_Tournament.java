package tests;

// imports
import ai.core.AI;
import ai.evaluation.EvaluationFunction;
import ai.evaluation.SimpleSqrtEvaluationFunction3;
import ai.montecarlo.MonteCarlo;
import ai.*;
import ai.abstraction.BTController;
import ai.abstraction.Custom_LightRush;
import ai.abstraction.EMRDeterministico;
import ai.abstraction.EconomyMilitaryRush;
import ai.abstraction.EconomyRush;
import ai.abstraction.EconomyRushBurster;
import ai.abstraction.HeavyDefense;
import ai.abstraction.WorkerRush;
import ai.abstraction.WorkerRushPlusPlus;
import ai.abstraction.LightRush;
import ai.abstraction.RangedDefense;
import ai.abstraction.RangedRush;
import ai.abstraction.SimpleEconomyRush;
import ai.abstraction.WorkerDefense;
import ai.abstraction.HeavyRush;
import ai.abstraction.LightDefense;
import ai.PassiveAI;
import ai.abstraction.pathfinding.AStarPathFinding;
import ai.competition.tiamat.Tiamat;
import gui.PhysicalGameStatePanel;
import jbt.model.core.ModelTask;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JFrame;


import rts.GameState;
import rts.PartiallyObservableGameState;
import rts.PhysicalGameState;
import rts.PlayerAction;
import rts.Trace;
import rts.TraceEntry;
import rts.units.Unit;
import rts.units.UnitTypeTable;
import util.XMLWriter;


/**
 *
 * @author santi
 * @return 
 */

/*
 * File is a modified version of the GameVisualSimulation Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

public class ECJ_Tournament {
    static double RedHP = 0.0;
    static double BlueHP = 0.0;
    static double MaxHP = 0.0;
    static ModelTask tree;
    static int generation = 0;
    static int result = 0;

    
    
	// Constructor used for evolution
    public ECJ_Tournament(ModelTask tree) 
    {
    	this.tree = tree;
    }
    
	// Constructor used for retrieving traces of best individuals
    public ECJ_Tournament(ModelTask tree, String scenePath, String opponent, File evolutionFolder, int turns, int timeBudget, int iterationBudget, int lookahead, int maxActions, String playoutAI, String visibilityType) throws Exception
    {
    	this.tree = tree;
    	
    	tournament_Trace(scenePath, opponent, evolutionFolder, turns, timeBudget, iterationBudget, lookahead, maxActions, playoutAI, visibilityType);
    	
    }
    
	// Definition of the "tournament" GameState
	public static GameState tournament(String scenePath, String opponent, int turns, int timeBudget, int iterationBudget, int lookahead, int maxActions, String playoutAI) throws Exception
	{
        UnitTypeTable utt = new UnitTypeTable();
        

        PhysicalGameState pgs = PhysicalGameState.load(scenePath, utt);

        GameState gs = new GameState(pgs, utt);
        
        int MAXCYCLES = turns;
        int PERIOD = 10;
        boolean gameover = false;
        
        reset();
        calcHP(pgs);

        AI ai1 = new BTController(utt, new AStarPathFinding(), tree, gs);        
        AI ai2 = getOpponent(opponent, utt, timeBudget, iterationBudget, lookahead, maxActions, playoutAI);
        
        JFrame w = PhysicalGameStatePanel.newVisualizer(gs,700,700,false,PhysicalGameStatePanel.COLORSCHEME_WHITE);
        
        long nextTimeToUpdate = System.currentTimeMillis() + PERIOD;
        do{
        	
            if (System.currentTimeMillis()>=nextTimeToUpdate) {
                PlayerAction pa1 = ai1.getAction(0, gs);
                PlayerAction pa2 = ai2.getAction(1, gs);
                
                gs.issueSafe(pa1);
                gs.issueSafe(pa2);

                // simulate:
                gameover = gs.cycle();
                w.repaint();
                nextTimeToUpdate+=PERIOD;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }while(!gameover && gs.getTime()<MAXCYCLES);

        ai1.gameOver(gs.winner());
        ai2.gameOver(gs.winner());
        
        result = 0;
        result = getResult(gs);
//        System.out.println(result);

        // close the jframe
        w.dispose();

        return gs;
	}
	
	// Definition of the "tournamentFast" GameState
	public static GameState tournamentFast(String scenePath, String opponent, int turns, int timeBudget, int iterationBudget, int lookahead, int maxActions, String playoutAI, String visibilityType) throws Exception
	{
        UnitTypeTable utt = new UnitTypeTable();
        
        PhysicalGameState pgs = PhysicalGameState.load(scenePath, utt);

        GameState gs = new GameState(pgs, utt);
        int MAXCYCLES = turns;
        int PERIOD = 0;
        boolean gameover = false;

        // Calculate initial red, blue and max HP
        reset();
        calcHP(pgs);

        AI ai1 = new BTController(utt, new AStarPathFinding(), tree, gs);        
        AI ai2 = getOpponent(opponent, utt, timeBudget, iterationBudget, lookahead, maxActions, playoutAI);

        
        long nextTimeToUpdate = System.currentTimeMillis() + PERIOD;
        do{
            if (System.currentTimeMillis()>=nextTimeToUpdate) {
            	PlayerAction pa1 = null;
            	PlayerAction pa2 = null;
            	
            	if(visibilityType.equals("Full")) {
            		pa1 = ai1.getAction(0, gs);
                    pa2 = ai2.getAction(1, gs);
            	}
            	else if(visibilityType.equals("Both Partial")) {
            		pa1 = ai1.getAction(0, new PartiallyObservableGameState(gs, 0));
            		pa2 = ai2.getAction(1, new PartiallyObservableGameState(gs, 1));
            	}
            	else if(visibilityType.equals("Self Partial")) {
            		pa1 = ai1.getAction(0, new PartiallyObservableGameState(gs, 0));
            		pa2 = ai2.getAction(1, gs);
            	}
            	else if(visibilityType.equals("Enemy Partial")) {
            		pa1 = ai1.getAction(0, gs);
            		pa2 = ai2.getAction(1, new PartiallyObservableGameState(gs, 1));
            	}
                
                gs.issueSafe(pa1);
                gs.issueSafe(pa2);

                // simulate:
                gameover = gs.cycle();
                nextTimeToUpdate+=PERIOD;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }while(!gameover && gs.getTime()<MAXCYCLES);

        ai1.gameOver(gs.winner());
        ai2.gameOver(gs.winner());     
        
        result = getResult(gs);

        return gs;
	}
	
	// Definition of the "tournamentComputationBudget" GameState
	public static GameState tournamentComputationBudget(String scenePath, String opponent, int turns, int timeBudget, int iterationBudget, int lookahead, int maxActions, String playoutAI) throws Exception
	{

        UnitTypeTable utt = new UnitTypeTable();

        PhysicalGameState pgs = PhysicalGameState.load(scenePath, utt);

        GameState gs = new GameState(pgs, utt);
        int max_cycles = turns;
        int max_inactive_cycles = 30;
        int PERIOD = 0;
        boolean gameover = false;
        long lastTimeActionIssued = 0;

        // Calculate initial red, blue and max HP
        reset();
        calcHP(pgs);

        AI ai1 = new BTController(utt, new AStarPathFinding(), tree, gs);        
        AI ai2 = getOpponent(opponent, utt, timeBudget, iterationBudget, lookahead, maxActions, playoutAI);

        long nextTimeToUpdate = System.currentTimeMillis() + PERIOD;
        do{
            if (System.currentTimeMillis()>=nextTimeToUpdate) {
                PlayerAction pa1 = ai1.getAction(0, gs);
                PlayerAction pa2 = ai2.getAction(1, gs);
                
                if (gs.issueSafe(pa1)) lastTimeActionIssued = gs.getTime();
                if (gs.issueSafe(pa2)) lastTimeActionIssued = gs.getTime();

                // simulate:
                gameover = gs.cycle();
                nextTimeToUpdate+=PERIOD;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }while (!gameover && 
                (gs.getTime() < max_cycles) && 
                (gs.getTime() - lastTimeActionIssued < max_inactive_cycles));

        ai1.gameOver(gs.winner());
        ai2.gameOver(gs.winner());

        result = getResult(gs);

        return gs;
	}
	
	// Definition of the "tournament_Trace" function used to retrieve traces of best individuals
	public static void tournament_Trace(String scenePath, String opponent, File evolutionFolder, int turns, int timeBudget, int iterationBudget, int lookahead, int maxActions, String playoutAI, String visibilityType) throws Exception
	{
		
        UnitTypeTable utt = new UnitTypeTable();
        PhysicalGameState pgs = PhysicalGameState.load(scenePath, utt);
        
        GameState gs = new GameState(pgs, utt);
        int MAXCYCLES = turns;
        int PERIOD = 0;
        boolean gameover = false;
        
        reset();
        calcHP(pgs);

        AI ai1 = new BTController(utt, new AStarPathFinding(), tree, gs);        
        AI ai2 = getOpponent(opponent, utt, timeBudget, iterationBudget, lookahead, maxActions, playoutAI);

        // Create a trace
        Trace trace = new Trace(utt);
        TraceEntry te = new TraceEntry(gs.getPhysicalGameState().clone(),gs.getTime());
        trace.addEntry(te);
        
        long nextTimeToUpdate = System.currentTimeMillis() + PERIOD;
        do{
            if (System.currentTimeMillis()>=nextTimeToUpdate) {
            	PlayerAction pa1 = null;
            	PlayerAction pa2 = null;
            	
            	if(visibilityType.equals("Full")) {
            		pa1 = ai1.getAction(0, gs);
                    pa2 = ai2.getAction(1, gs);
            	}
            	else if(visibilityType.equals("Both Partial")) {
            		pa1 = ai1.getAction(0, new PartiallyObservableGameState(gs, 0));
            		pa2 = ai2.getAction(1, new PartiallyObservableGameState(gs, 1));
            	}
            	else if(visibilityType.equals("Self Partial")) {
            		pa1 = ai1.getAction(0, new PartiallyObservableGameState(gs, 0));
            		pa2 = ai2.getAction(1, gs);
            	}
            	else if(visibilityType.equals("Enemy Partial")) {
            		pa1 = ai1.getAction(0, gs);
            		pa2 = ai2.getAction(1, new PartiallyObservableGameState(gs, 1));
            	}
                
                
                // If there are players
                if (!pa1.isEmpty() || !pa2.isEmpty()) {
                	// Create a new trace entry
                    te = new TraceEntry(gs.getPhysicalGameState().clone(),gs.getTime());
                    te.addPlayerAction(pa1.clone());
                    te.addPlayerAction(pa2.clone());
                    trace.addEntry(te);
                }
                
                gs.issueSafe(pa1);
                gs.issueSafe(pa2);

                // simulate:
                gameover = gs.cycle();
                nextTimeToUpdate+=PERIOD;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }while(!gameover && gs.getTime()<MAXCYCLES);

        ai1.gameOver(gs.winner());
        ai2.gameOver(gs.winner());
        
        te = new TraceEntry(gs.getPhysicalGameState().clone(), gs.getTime());
        trace.addEntry(te);
        result = 0;
        result = getResult(gs);
//        System.out.println(result);
//        String intResult = Integer.toString(result);

        XMLWriter xml = new XMLWriter(new FileWriter(evolutionFolder+"/Evolution_trace.xml"));
        trace.toxml(xml);
        xml.flush(); 
        xml.close();

	}
	
	// Gets the correct opponent AI based on entered string and returns it as "ai2"
	public static AI getOpponent(String opponent, UnitTypeTable utt, int time, int iteration, int lookahead, int actions, String playout) {
		AI ai2 = null;
		
        if (opponent.equals("WorkerRush")) {
        	ai2 = new WorkerRush(utt);
        }else if (opponent.equals("HeavyRush")){
        	ai2 = new HeavyRush(utt);
        }else if (opponent.equals("LightRush")){
        	ai2 = new LightRush(utt);
        }else if (opponent.equals("Passive")){
        	ai2 = new PassiveAI(utt);
        }else if (opponent.equals("Custom_LightRush")){
        	ai2 = new Custom_LightRush(utt);
        }else if (opponent.equals("Tiamat")){
        	ai2 = new Tiamat(utt);
        }else if (opponent.equals("EconomyMilitaryRush")){
        	ai2 = new EconomyMilitaryRush(utt);
        }else if (opponent.equals("EconomyRush")){
        	ai2 = new EconomyRush(utt);
        }else if (opponent.equals("EconomyRushBurster")){
        	ai2 = new EconomyRushBurster(utt);
        }else if (opponent.equals("EMRDeterministico")){
        	ai2 = new EMRDeterministico(utt);
        }else if (opponent.equals("HeavyDefense")){
        	ai2 = new HeavyDefense(utt);
        }else if (opponent.equals("LightDefense")){
        	ai2 = new LightDefense(utt);
        }else if (opponent.equals("RangedDefense")){
        	ai2 = new RangedDefense(utt);
        }else if (opponent.equals("RangedRush")){
        	ai2 = new RangedRush(utt);
        }else if (opponent.equals("SimpleEconomyRush")){
        	ai2 = new SimpleEconomyRush(utt);
        }else if (opponent.equals("WorkerDefense")){
        	ai2 = new WorkerDefense(utt);
        }else if (opponent.equals("WorkerRushPlusPlus")){
        	ai2 = new WorkerRushPlusPlus(utt);
        }else if (opponent.equals("MonteCarlo")){
        	
        	AI playOutAI = null;
        	
        	if(playout.equals("RandomAI")) {
        		playOutAI = new RandomAI(utt);
        	}else if(playout.equals("RandomBiasedAI")) {
        		playOutAI = new RandomBiasedAI(utt);
        	}else if(playout.equals("WorkerRush")){
        		playOutAI = new WorkerRush(utt);
        	}else if(playout.equals("LightRush")){
        		playOutAI = new LightRush(utt);
        	}else if(playout.equals("HeavyRush")){
        		playOutAI = new HeavyRush(utt);
        	}else if(playout.equals("RangedRush")){
        		playOutAI = new RangedRush(utt);
        	}
        	
        	EvaluationFunction eF = new SimpleSqrtEvaluationFunction3();
        	ai2 = new MonteCarlo(time, iteration, lookahead, actions, playOutAI, eF);
        }else {
    		ai2 = new PassiveAI(utt);
		}
        
		return ai2;
	}
	
	// Definition of the reset() function used to reset all HP variables and the result of an individual back to 0
	public static void reset()
	{
		RedHP = 0.0;
		BlueHP = 0.0;
		MaxHP = 0.0;
		result = 0;
	}
	
	// Definition of the calcHP() function used to calculate the starting health of both controllers and set the MaxHP variable to whichever is highest
    public static void calcHP(PhysicalGameState pgs)
    {
        // set the hp variables
        for(Unit u: pgs.getUnits())
        {
            if(u.getPlayer() == 1)
            {
                RedHP = RedHP + u.getHitPoints();
            }
            else if(u.getPlayer() == 0)
        	{
        		BlueHP = BlueHP + u.getHitPoints();
        	}
        }
        if (RedHP >= BlueHP)
        {
        	MaxHP = RedHP;
        } else MaxHP = BlueHP;
    }
    
	// Gets the MaxHP variable
    public static double getMaxHP()
    {
    	return MaxHP;
    }
    
	// Gets the starting HP of whichever player our controller is
    public static double getStartingHP(int player)
    {
        if(player == 1)
        {
            return RedHP;
        }
        else return BlueHP;
    }
    
	// Gets the HP of both controllers at the end of the simulation
    public static int getResult(GameState gs)
    {
    	double postBlueHP = 0.0;
    	double postRedHP = 0.0;
    	double resultDouble = 0.0;
    	
    	PhysicalGameState pgs = gs.getPhysicalGameState();
    	
    	for(Unit u: pgs.getUnits())
    	{
    		if(u.getPlayer() == 1)
    		{
    			postRedHP = postRedHP + u.getHitPoints();
    		}
    		else if (u.getPlayer() == 0)
    		{
    			postBlueHP = postBlueHP + u.getHitPoints();
    		}
    	}
    	resultDouble = (postBlueHP-postRedHP)+MaxHP;
    	return (int) resultDouble;
    }
    

    public static void main(String args[]) throws Exception {

    }    
}
