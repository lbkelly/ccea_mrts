// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 09/03/2018 14:17:27
// ******************************************************* 

/*
 * File is a modified version of the original MACHINE GENERATED CODE MoveToClosestAllies Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package bts.actions.execution;
import ai.abstraction.AbstractAction;
import ai.abstraction.BTController;
import rts.units.Unit;
import rts.units.UnitTypeTable;

import java.util.ArrayList;
import java.util.HashMap;

/** ExecutionAction class created from MMPM action MoveToClosestAllies. */
public class MoveToClosestAllies extends
		jbt.execution.task.leaf.action.ExecutionAction {
	UnitTypeTable utt = new UnitTypeTable();

	BTController Controller = new BTController(utt);
	
	// Are we moving?
	private Boolean isMoving = false;
	/**
	 * Value of the parameter "gameState" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Object gameState;
	/**
	 * Location, in the context, of the parameter "gameState" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String gameStateLoc;
	/**
	 * Value of the parameter "player" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer player;
	/**
	 * Location, in the context, of the parameter "player" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String playerLoc;
	/**
	 * Value of the parameter "unit" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Object unit;
	/**
	 * Location, in the context, of the parameter "unit" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String unitLoc;
	/**
	 * Value of the parameter "XCoord" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer XCoord;
	/**
	 * Location, in the context, of the parameter "XCoord" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String XCoordLoc;
	/**
	 * Value of the parameter "YCoord" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer YCoord;
	/**
	 * Location, in the context, of the parameter "YCoord" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String YCoordLoc;

	/**
	 * Constructor. Constructs an instance of MoveToClosestAllies that is able
	 * to run a bts.actions .MoveToClosestAllies.
	 * 
	 * @param gameState
	 *            value of the parameter "gameState", or null in case it should
	 *            be read from the context. If null,
	 *            <code>gameStateLoc<code> cannot be null.
	 * @param gameStateLoc
	 *            in case <code>gameState</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 * @param player
	 *            value of the parameter "player", or null in case it should be
	 *            read from the context. If null,
	 *            <code>playerLoc<code> cannot be null.
	 * @param playerLoc
	 *            in case <code>player</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param unit
	 *            value of the parameter "unit", or null in case it should be
	 *            read from the context. If null,
	 *            <code>unitLoc<code> cannot be null.
	 * @param unitLoc
	 *            in case <code>unit</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param XCoord
	 *            value of the parameter "XCoord", or null in case it should be
	 *            read from the context. If null,
	 *            <code>XCoordLoc<code> cannot be null.
	 * @param XCoordLoc
	 *            in case <code>XCoord</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param YCoord
	 *            value of the parameter "YCoord", or null in case it should be
	 *            read from the context. If null,
	 *            <code>YCoordLoc<code> cannot be null.
	 * @param YCoordLoc
	 *            in case <code>YCoord</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public MoveToClosestAllies(bts.actions.MoveToClosestAllies modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent,
			java.lang.Object gameState, java.lang.String gameStateLoc,
			java.lang.Integer player, java.lang.String playerLoc,
			java.lang.Object unit, java.lang.String unitLoc,
			java.lang.Integer XCoord, java.lang.String XCoordLoc,
			java.lang.Integer YCoord, java.lang.String YCoordLoc) {
		super(modelTask, executor, parent);

		this.gameState = gameState;
		this.gameStateLoc = gameStateLoc;
		this.player = player;
		this.playerLoc = playerLoc;
		this.unit = unit;
		this.unitLoc = unitLoc;
		this.XCoord = XCoord;
		this.XCoordLoc = XCoordLoc;
		this.YCoord = YCoord;
		this.YCoordLoc = YCoordLoc;
	}

	/**
	 * Returns the value of the parameter "gameState", or null in case it has
	 * not been specified or it cannot be found in the context.
	 */
	public java.lang.Object getGameState() {
		if (this.gameState != null) {
			return this.gameState;
		} else {
			return (java.lang.Object) this.getContext().getVariable(
					this.gameStateLoc);
		}
	}

	/**
	 * Returns the value of the parameter "player", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getPlayer() {
		if (this.player != null) {
			return this.player;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.playerLoc);
		}
	}

	/**
	 * Returns the value of the parameter "unit", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Object getUnit() {
		if (this.unit != null) {
			return this.unit;
		} else {
			return (java.lang.Object) this.getContext().getVariable(
					this.unitLoc);
		}
	}

	/**
	 * Returns the value of the parameter "XCoord", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getXCoord() {
		if (this.XCoord != null) {
			return this.XCoord;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.XCoordLoc);
		}
	}

	/**
	 * Returns the value of the parameter "YCoord", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getYCoord() {
		if (this.YCoord != null) {
			return this.YCoord;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.YCoordLoc);
		}
	}

	protected void internalSpawn() {
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		
		// The aim of this method is to move to the closest ally
		Unit f = (Unit) this.getContext().getVariable("unitVar");
//		System.out.println("$$$$$$$$$$$$$$$  IN INTERNAL SPAWN FOR " + f.getID() + "  $$$$$$$$$$$$$$$$$$$$");
		@SuppressWarnings("unchecked")
		ArrayList<Unit> allies = (ArrayList<Unit>) this.getContext().getVariable("allies");
		
//		for (Unit x : allies) {
//			System.out.println("The unit is: " + x.getID() + " its hash code is " + x.hashCode());
//		}
//		
//		System.out.println("----------------------------");
//		System.out.println("My ID: " + f.getID());
		// If the friends list is not empty AND its not of size one, size one being itself
		if (allies.isEmpty() == false && allies.size() > 1)
		{
			// Get the closest Ally
			Unit closestAlly = null; // stores closest enemy
			int closestDistance = 999; // stores distance to closest enemy

			for (Unit u: allies)
			{		
				
				int d = Math.abs(u.getX() - f.getX()) + Math.abs(u.getY() - f.getY());
				
				// If this units ID doesn't equal the current unit we are checking's ID AND
				// The closestAlly is null OR d is less than the closest distance
				if (f.getID() != u.getID() && (closestAlly == null || d < closestDistance))
				{					
//					System.out.println("DISTANCE : " + d);
					closestAlly = u; // set closest enemy
					closestDistance = d; // set closest distance
					isMoving = true;
				}
				
			}		
//		    int x = closestAlly.getX();
			int x;
		    int y;

			if (isMoving) {
				x = closestAlly.getX();
			    y = closestAlly.getY();
//				Controller.move(f, x, y); // Move method call
				Controller.attack(f, closestAlly);
			}

		}
		

		
		HashMap<Unit, AbstractAction> Actions = Controller.getActionsHash(); // Set Actions to local actions hash
		this.getContext().setVariable("actions", Actions); // Set actions context variable
		
		

		System.out.println(this.getClass().getCanonicalName() + " spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		if(isMoving)
		{
			System.out.println(this.getClass().getCanonicalName() + " running");
			return jbt.execution.core.ExecutionTask.Status.RUNNING;
		}
		System.out.println(this.getClass().getCanonicalName() + " failing");
		return jbt.execution.core.ExecutionTask.Status.FAILURE;
	}

	protected void internalTerminate() {
		/* TODO: this method's implementation must be completed. */
	}

	protected void restoreState(jbt.execution.core.ITaskState state) {
		/* TODO: this method's implementation must be completed. */
	}

	protected jbt.execution.core.ITaskState storeState() {
		/* TODO: this method's implementation must be completed. */
		return null;
	}

	protected jbt.execution.core.ITaskState storeTerminationState() {
		/* TODO: this method's implementation must be completed. */
		return null;
	}
}