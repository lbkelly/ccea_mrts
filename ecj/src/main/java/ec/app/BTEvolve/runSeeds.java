package ec.app.BTEvolve;
import java.util.ArrayList;

import ec.Evolve;

public class runSeeds {
	
	// Runs all specified seeds over all specified crossover rates on a specific map (state) 
	public static void main(String[] args) throws Exception
	{
		// Seeds to test
		String[] seeds = new String[]{"426", "780", "981"};
		// Crossovers to test
		String[] crossovers = new String[]{"0.6", "0.7", "0.8", "0.9"};
		String[] arguments = null;
		String path = "microrts-master/states/Enemy_Heterogeneous/24x24_Far_Homo_Light.xml";
		
		// For each seed
		for (int i = 0; i <seeds.length; i++)
		{
			// For each crossover
			for(int j = 0; j<crossovers.length; j++)
			{
				String seed = seeds[i];
//				System.out.println(seed);
				String crossover = crossovers[j];
//				System.out.println(crossover);
				
				arguments = new String[] {"-file", "ecj/src/main/java/ec/app/BTEvolve/BT.params",
						"-p", "seed.0="+seed,
						"-p", "pop.subpop.0.species.pipe.source.0.prob="+crossover,
						"-p", "stat.file=$"+seed+"_"+crossover+"_out.stat",
						"-p", "path="+path,
						"-p", "stat.child.0.file=$"+seed+"_"+crossover+"_out_2.stat"};
				
				Evolve.main(arguments);
			}
		}
		
	}


	
	
}
