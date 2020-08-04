package ec.app.BTEvolve;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;


public class treeDialogWindow {
	StringBuilder htmlBuilder = new StringBuilder();
	InputStream input;
	
	public static void main(String[] args) {
//		new treeDialogWindow();
		
	}
	
	public treeDialogWindow() {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				// create jeditorpane
				JEditorPane jEditorPane = new JEditorPane();
				
				// make it read-only
		        jEditorPane.setEditable(false);
		        
		        // create a scrollpane; modify its attributes as desired
		        JScrollPane scrollPane = new JScrollPane(jEditorPane);
		        
		        // add an html editor kit
		        HTMLEditorKit kit = new HTMLEditorKit();
		        jEditorPane.setEditorKit(kit);
		        
		        // add some styles to the html
		        String ecltree = this.getClass().getResource("/ec/app/BTEvolve/latexEcl/ecltree.sty").toExternalForm();
		        String epic = this.getClass().getResource("/ec/app/BTEvolve/latexEpic/epic.sty").toExternalForm();
		        
		       StyleSheet sheet = kit.getStyleSheet();
		        String latex = "\\documentclass{article}"
		        		+ "\\usepackage{ecltree, epic}"
		        		+ "\\begin{bundle}{\\gpbox{Sequence}}"
		        		+ "\\chunk{\\gpbox{CheckForAllies}}"
		        		+ "\\chunk{\\gpbox{AttackClosestEnemy}}"
		        		+ "\\chunk{\\gpbox{AttackClosestBase}}"
		        		+ "\\chunk{\\gpbox{CheckForBases}}"
		        		+ "\\end{bundle}\r\n";
		        

		        // create some simple html as a string
		        String htmlString = "<html>\n"
		                          + "<body>\n"
		                          + "<h1>Welcome!</h1>\n"
		                          + "<h2>This is an H2 header</h2>\n"
		                          + "<p>This is some sample text</p>\n"
		                          + "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n"
		                          + "</body>\n";
		        
		        // create a document, set it on the jeditorpane, then add the html
		        Document doc = kit.createDefaultDocument();
		        jEditorPane.setDocument(doc);
		        jEditorPane.setText(latex);
		        
		        // now add it all to a frame
		        JFrame j = new JFrame("HtmlEditorKit Test");
		        j.getContentPane().add(scrollPane, BorderLayout.CENTER);

		        // make it easy to close the application
		        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        // display the frame
		        j.setSize(new Dimension(300,200));
		        
		        // pack it, if you prefer
		        //j.pack();
		        
		        // center the jframe, then make it visible
		        j.setLocationRelativeTo(null);
		        j.setVisible(true);
			}
			
		});
	}
}
