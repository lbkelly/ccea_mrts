// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 09/03/2018 14:17:27
// ******************************************************* 

/*
 * File created by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package bts.actions;

/** ModelAction class created from MMPM action AttackClosestBase. */
public class AttackClosestBase extends jbt.model.task.leaf.action.ModelAction {
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
	 * Constructor. Constructs an instance of AttackClosestBase.
	 * 
	 * @param gameState
	 *            value of the parameter "gameState", or null in case it should
	 *            be read from the context. If null, <code>gameStateLoc</code>
	 *            cannot be null.
	 * @param gameStateLoc
	 *            in case <code>gameState</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 * @param player
	 *            value of the parameter "player", or null in case it should be
	 *            read from the context. If null, <code>playerLoc</code> cannot
	 *            be null.
	 * @param playerLoc
	 *            in case <code>player</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param unit
	 *            value of the parameter "unit", or null in case it should be
	 *            read from the context. If null, <code>unitLoc</code> cannot be
	 *            null.
	 * @param unitLoc
	 *            in case <code>unit</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public AttackClosestBase(jbt.model.core.ModelTask guard,
			java.lang.Object gameState, java.lang.String gameStateLoc,
			java.lang.Integer player, java.lang.String playerLoc,
			java.lang.Object unit, java.lang.String unitLoc) {
		super(guard);
		this.gameState = gameState;
		this.gameStateLoc = gameStateLoc;
		this.player = player;
		this.playerLoc = playerLoc;
		this.unit = unit;
		this.unitLoc = unitLoc;
	}

	/**
	 * Returns a bts.actions.execution .AttackClosestBase task that is able to
	 * run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new bts.actions.execution.AttackClosestBase(this, executor,
				parent, this.gameState, this.gameStateLoc, this.player,
				this.playerLoc, this.unit, this.unitLoc);
	}
}