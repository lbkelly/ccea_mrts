/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

/*
 * File is a modified version of the original ECJ Add Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package ec.app.BTEvolve;
import ec.*;
import ec.gp.*;

public class SequenceChildOne extends GPNode
{
	public String toString() {return "Sequence";}

	public int expectedChildren() {return 1;}

    public void eval(final EvolutionState state,
    final int thread,
    final GPData input,
    final ADFStack stack,
    final GPIndividual individual,
    final Problem problem)
    {
        children[0].eval(state, thread, input, stack, individual, problem);
    }
}