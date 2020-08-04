// *******************************************************
//                   MACHINE GENERATED CODE
//                MUST BE CAREFULLY COMPLETED
//
//           ABSTRACT METHODS MUST BE IMPLEMENTED
//
// Generated on 09/03/2018 14:17:27
// *******************************************************

/*
 * File is a modified version of the original MACHINE GENERATED CODE AttackClosestBase Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package bts.actions.execution;
import rts.units.*;
import rts.PhysicalGameState;
import rts.GameState;
import rts.UnitActionAssignment;
import ai.abstraction.pathfinding.BFSPathFinding;
import jbt.model.core.ModelTask;
import ai.abstraction.AbstractAction;
import ai.abstraction.BTController;

import java.util.ArrayList;
import java.util.HashMap;

/** ExecutionAction class created from MMPM action AttackClosestBase. */
public class AttackClosestBase extends
		jbt.execution.task.leaf.action.ExecutionAction {

			UnitTypeTable utt = new UnitTypeTable();
			ModelTask m = null;
			BTController Controller = new BTController(utt);

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
	 * Constructor. Constructs an instance of AttackClosestBase that is able to
	 * run a bts.actions .AttackClosestBase.
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
	 */
	public AttackClosestBase(bts.actions.AttackClosestBase modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent,
			java.lang.Object gameState, java.lang.String gameStateLoc,
			java.lang.Integer player, java.lang.String playerLoc,
			java.lang.Object unit, java.lang.String unitLoc) {
		super(modelTask, executor, parent);

		this.gameState = gameState;
		this.gameStateLoc = gameStateLoc;
		this.player = player;
		this.playerLoc = playerLoc;
		this.unit = unit;
		this.unitLoc = unitLoc;
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

	protected void internalSpawn() {
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);

		// This method gives a unit the action 'Attack'
		@SuppressWarnings("unchecked")
		ArrayList<Unit> enemyBases = (ArrayList<Unit>) this.getContext().getVariable("enemyBases");
		Unit f = (Unit) this.getContext().getVariable("unitVar");

		// If there are enemies
		if(enemyBases.isEmpty() == false)
		{
			// Variables:
			Unit closestEnemyBase = null; // stores closest enemy
			int closestDistance = 0; // stores distance to closest enemy

			//Find the closest enemy
			for(Unit e : enemyBases)
			{
				int d = Math.abs(e.getX() - f.getX()) + Math.abs(e.getY() - f.getY());
				if (closestEnemyBase == null || d < closestDistance)
				{
					closestEnemyBase = e; // set closest enemy
					closestDistance = d; // set closest distance
				}
			}		
			// Attack the closest base
			Controller.attack(f, closestEnemyBase);
		}
		
		HashMap<Unit, AbstractAction> Actions = Controller.getActionsHash(); // Set Actions to local actions hash
		this.getContext().setVariable("actions", Actions); // Set actions context variable
		
		System.out.println(this.getClass().toString());
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
//		System.out.println("Inside Attack closest base");
		GameState GS = (GameState) this.getContext().getVariable("gsVar");
		@SuppressWarnings("unchecked")
		ArrayList<Unit> friendly = (ArrayList<Unit>) this.getContext().getVariable("friendly");
		ArrayList<Unit> enemyBases = (ArrayList<Unit>) this.getContext().getVariable("enemyBases");
		
		if (enemyBases.isEmpty() == false)
		{
			// Maybe if one of the units could perform this action, return success.
//			return jbt.execution.core.ExecutionTask.Status.SUCCESS;
			return jbt.execution.core.ExecutionTask.Status.RUNNING;
		}
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
