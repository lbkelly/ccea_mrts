// *******************************************************
//                   MACHINE GENERATED CODE
//                MUST BE CAREFULLY COMPLETED
//
//           ABSTRACT METHODS MUST BE IMPLEMENTED
//
// Generated on 09/03/2018 14:17:28
// *******************************************************

/*
 * File is a modified version of the original MACHINE GENERATED CODE EnemyBaseIsClose Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package bts.conditions.execution;

import java.util.ArrayList;

import rts.units.Unit;

/** ExecutionCondition class created from MMPM condition EnemyBaseIsClose. */
public class EnemyBaseIsClose extends
		jbt.execution.task.leaf.condition.ExecutionCondition {
	
	private Boolean check = false;

	/**
	 * Constructor. Constructs an instance of EnemyBaseIsClose that is able to
	 * run a bts.conditions .EnemyBaseIsClose.
	 */
	public EnemyBaseIsClose(bts.conditions.EnemyBaseIsClose modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		super(modelTask, executor, parent);

	}

	protected void internalSpawn() {
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
				
		// Get enemy base array list
		@SuppressWarnings("unchecked")
		ArrayList<Unit> enemyBases = (ArrayList<Unit>) this.getContext().getVariable("enemyBases");
		
		// if the enemy list is not empty
		if (enemyBases.isEmpty() == false)
		{
			// the check has succeeded
			check = true;
		}
		
		//System.out.println(this.getClass().getCanonicalName() + " spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		if (check == true)
		{
			return jbt.execution.core.ExecutionTask.Status.SUCCESS;
		}
		else return jbt.execution.core.ExecutionTask.Status.FAILURE; 
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
