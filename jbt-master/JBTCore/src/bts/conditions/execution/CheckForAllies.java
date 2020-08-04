// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 09/03/2018 14:17:28
// ******************************************************* 

/*
 * File is a modified version of the original MACHINE GENERATED CODE CheckForAllies Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package bts.conditions.execution;

import java.util.ArrayList;

import rts.units.Unit;

/** ExecutionCondition class created from MMPM condition CheckForAllies. */
public class CheckForAllies extends
		jbt.execution.task.leaf.condition.ExecutionCondition {
	
	private Boolean check = false;
	
	/**
	 * Constructor. Constructs an instance of CheckForAllies that is able to run
	 * a bts.conditions .CheckForAllies.
	 */
	public CheckForAllies(bts.conditions.CheckForAllies modelTask,
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
		// This method will attempt to check for allies around a unit
		Unit f = (Unit) this.getContext().getVariable("unitVar");
		@SuppressWarnings("unchecked")
		ArrayList<Unit> allies = (ArrayList<Unit>) this.getContext().getVariable("allies");
		
		// If the friends list is not empty AND its not of size one, size one being itself
		if (allies.isEmpty() == false && allies.size() > 1)
		{
//			System.out.println("Size of friendly list: " + friendly.size());
			// default to impossible value
			int closestDistance = 999; // stores distance to closest enemy
			
			for (Unit u: allies)
			{
				int d = Math.abs(u.getX() - f.getX()) + Math.abs(u.getY() - f.getY());
				// If this units ID doesn't equal the current unit we are checking's ID AND
				// d is less than the closest distance
				if (f.getID() != u.getID() && d < closestDistance)
				{
					closestDistance = d; // set closest distance
				}
			}
			
			if (closestDistance <= 2)
			{
				check = true;
			}
		}	
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		if (check == true){
			return jbt.execution.core.ExecutionTask.Status.SUCCESS;
		}
		else {
			return jbt.execution.core.ExecutionTask.Status.FAILURE;
		} 
			
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