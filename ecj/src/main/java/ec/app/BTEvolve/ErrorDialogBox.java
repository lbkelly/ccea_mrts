package ec.app.BTEvolve;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane.*;

public class ErrorDialogBox {
	
	private static String error = "Invalid delay value.\n"
			+ "Must be an integer value\n, "
			+ "and be greater than or equal to 0.";
	
	public static void main (String args[]) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Error:", JOptionPane.ERROR_MESSAGE);
	}

}
