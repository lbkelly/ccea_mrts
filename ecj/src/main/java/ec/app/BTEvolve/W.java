package ec.app.BTEvolve;
import ec.util.*;
import ec.*;
import ec.gp.*;

public class W extends GPNode
{
	public String toString() {return "Wander";}

	public int expectedChildren() {return 0;}

    public void eval(final EvolutionState state,
    final int thread,
    final GPData input,
    final ADFStack stack,
    final GPIndividual individual,
    final Problem problem)
    {
    }
}