// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 09/03/2018 14:17:28
// ******************************************************* 

/*
 * File created by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package bts.conditions;

/** ModelCondition class created from MMPM condition EnemyBaseIsClose. */
public class EnemyBaseIsClose extends
		jbt.model.task.leaf.condition.ModelCondition {

	/** Constructor. Constructs an instance of EnemyBaseIsClose. */
	public EnemyBaseIsClose(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a bts.conditions.execution .EnemyBaseIsClose task that is able to
	 * run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new bts.conditions.execution.EnemyBaseIsClose(this, executor,
				parent);
	}
}