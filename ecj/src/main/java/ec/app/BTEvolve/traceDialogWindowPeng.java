package ec.app.BTEvolve;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.Timer;

import org.eclipse.jdt.internal.core.JavaModel;
import org.jdom.input.SAXBuilder;

import gui.PhysicalGameStatePanel;
import rts.GameState;
import rts.Trace;
import rts.TraceEntry;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class traceDialogWindowPeng extends JFrame implements AdjustmentListener, ActionListener{

	// Misc variables
    int currentGameCycle = 0; // Stores the current game cycle
    int finalPosition = 0; // Stores the ending game cycle
    int PERIOD = 0; // Stores the period of delay for playback

    // MicroRTS Components
    PhysicalGameStatePanel statePanel; // Simulation display
	Trace currentTrace = null; // Trace initialisation
	
	// File variables
    File[] dirFiles; // Trace directory
    File trace; // Trace file
    
    // J Swing Components
    private JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL); // ScrollBar
    private JButton b_1; // Play
    private JButton b_2; // Pause
    private JButton b; // Stop
    private JPanel panel_1;
    private JLabel lblDelay; // Delay label
    private JTextField delayText; // Delay text
    private JComboBox traces; // Trace comboBox
    private javax.swing.Timer timer = null; // Timer for playback loop
    

    // Method to get the current traces
    public String[] getTraces() {
    	String[] traceNames = new String[dirFiles.length];
    	
    	// If there's a directory
    	if (dirFiles != null) {
    		for (int i = 0; i < dirFiles.length; i++) {
    			// Add the file name to the traceNames array
    			traceNames[i] = dirFiles[i].getName();
    		}
    	}
    	
    	// Return the traceNames array
    	return traceNames;
    }
    
    // Method to load a selected trace
    public void loadTrace(String traceName) {
    	finalPosition = 0;
    	// Reset the scrollBar if needed.
    	scrollBar.setValue(0);
    	scrollBar.repaint();
    	
    	// Get the selected trace
    	for (File file : dirFiles)
        {
        	if (file.getName().equals(traceName))
        	{
        		trace = file;
        	}
        }
        
    	// If a timer is currently running, stop it.
        try {
        	if (timer != null) {
        		timer.stop();
        	}
        	
        	// create a new trace based on the trace file stored.
        	currentTrace = new Trace(new SAXBuilder().build(trace.getAbsolutePath()).getRootElement());
            // Set the current game cycle to 0
        	currentGameCycle = 0;
            
        	// Calculate the final position of the trace entry
            for(TraceEntry te:currentTrace.getEntries()) {
                if (te.getTime()>finalPosition) {
                	// Set final position
                    finalPosition = te.getTime();
                    // Set the maximum value of the scrollBar
                    scrollBar.setMaximum(finalPosition+10);
                }
            }
            // Set the current statePanel view to be that of the current trace at current game cycle
            statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
            statePanel.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Method to load an initial trace on JAR startup
    public void initLoadTrace() {
    	// Get the first entry in the file directory
    	trace = dirFiles[0];
        
        try {
        	// create a new trace based on the trace file stored.
        	currentTrace = new Trace(new SAXBuilder().build(trace.getAbsolutePath()).getRootElement());
            // Set the current game cycle to 0
        	currentGameCycle = 0;
            
        	// Calculate the final position of the trace entry
            for(TraceEntry te:currentTrace.getEntries()) {
                if (te.getTime()>finalPosition) {
                	// Set final position
                    finalPosition = te.getTime();
                    // Set the maximum value of the scrollBar
                    scrollBar.setMaximum(finalPosition+10);

                }
            }
            // Set the current statePanel view to be that of the current trace at current game cycle
            statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
            statePanel.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public traceDialogWindowPeng() {
    	super("Trace Playback Application:");
    	this.setSize(1231, 692);
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	dirFiles = new File("./traces").listFiles();
    	
        
        getContentPane().setLayout(new BorderLayout());
        
        JPanel p2 = new JPanel();
        statePanel = new PhysicalGameStatePanel((GameState)null);
        statePanel.setPreferredSize(new Dimension(600, 600));
        initLoadTrace();
        
        
        
        getContentPane().add(p2, BorderLayout.SOUTH);
                {
                }
        
                JPanel p1east = new JPanel();
                {
                    b_1 = new JButton("Play");
                    b_1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b_1.setAlignmentY(Component.TOP_ALIGNMENT);
                    b_1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                        	PERIOD = Integer.parseInt(delayText.getText());
                        	
                        	if (timer == null) {
                        		timer = new javax.swing.Timer(PERIOD, new ActionListener() {

    								@Override
    								public void actionPerformed(ActionEvent e) {
    									if (currentGameCycle != finalPosition) {
    										currentGameCycle++;
    	                                	statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
    	                                	statePanel.repaint();
    	                                	scrollBar.setValue(currentGameCycle);
    	                                	scrollBar.repaint();
    									}
    									else if (currentGameCycle == finalPosition)
    									{
    										timer.stop();
    									}
                            			
    								}
                                	
                                });
                        		timer.start();
                        	} else {
                        		timer.setDelay(PERIOD);
                        		timer.start();
                        	}
                            
                        }
                    });
                }
                {
                    b_2 = new JButton("Pause");
                    b_2.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b_2.setAlignmentY(Component.TOP_ALIGNMENT);
                    b_2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            timer.stop();
                        }
                    });
                }
                {
                    b = new JButton("Restart");
                    b.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b.setAlignmentY(Component.TOP_ALIGNMENT);
                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            timer.stop();
                            timer = null;
                            currentGameCycle = 0;
                        	statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
                        	statePanel.repaint();
                        	scrollBar.setValue(currentGameCycle);
                        	scrollBar.repaint();
                        }
                    });
                }
        
     
        scrollBar.addAdjustmentListener(this);
        scrollBar.setUnitIncrement(1);
        scrollBar.setMinimum(0);
        
        JPanel panel = new JPanel();
        
        JLabel lblLoadTraces = new JLabel("Load Traces:");
        
        traces = new JComboBox(getTraces());
        traces.addActionListener(this);
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addComponent(traces, Alignment.LEADING, 0, 157, Short.MAX_VALUE)
        				.addComponent(lblLoadTraces, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblLoadTraces)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(traces, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(187, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        
        panel_1 = new JPanel();
        
        
        
        GroupLayout gl_p2 = new GroupLayout(p2);
        gl_p2.setHorizontalGroup(
        	gl_p2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_p2.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_p2.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
        				.addComponent(p1east, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_p2.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_p2.createSequentialGroup()
        					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap())
        				.addGroup(gl_p2.createParallelGroup(Alignment.LEADING)
        					.addGroup(gl_p2.createSequentialGroup()
        						.addGap(10)
        						.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        						.addContainerGap())
        					.addGroup(gl_p2.createSequentialGroup()
        						.addComponent(statePanel, GroupLayout.DEFAULT_SIZE, 1009, Short.MAX_VALUE)
        						.addGap(13)))))
        );
        gl_p2.setVerticalGroup(
        	gl_p2.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_p2.createSequentialGroup()
        			.addGap(78)
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(167)
        			.addGroup(gl_p2.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_p2.createSequentialGroup()
        					.addComponent(statePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_p2.createSequentialGroup()
        					.addGap(19)
        					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
        					.addGap(61)
        					.addComponent(p1east, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        
        JLabel lblNewLabel = new JLabel("Controls:");
        
        lblDelay = new JLabel("Delay:");
        
        delayText = new JTextField();
        delayText.setText("1");
        delayText.setColumns(10);
        GroupLayout gl_p1east = new GroupLayout(p1east);
        gl_p1east.setHorizontalGroup(
        	gl_p1east.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_p1east.createSequentialGroup()
        			.addGap(21)
        			.addGroup(gl_p1east.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_p1east.createSequentialGroup()
        					.addComponent(lblDelay, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap())
        				.addGroup(gl_p1east.createParallelGroup(Alignment.LEADING)
        					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        					.addGroup(gl_p1east.createSequentialGroup()
        						.addGroup(gl_p1east.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(b, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(b_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(b_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(delayText, 0, 0, Short.MAX_VALUE))
        						.addGap(23)))))
        );
        gl_p1east.setVerticalGroup(
        	gl_p1east.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_p1east.createSequentialGroup()
        			.addGap(6)
        			.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(b_1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(b_2)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(b)
        			.addGap(18)
        			.addComponent(lblDelay)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(delayText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(77))
        );
        p1east.setLayout(gl_p1east);
        p2.setLayout(gl_p2);
    }
    
    public void adjustmentValueChanged(AdjustmentEvent e) {

    	
    	// Get the value of the scrollBar
    	int i = e.getValue();
    	
    	// Set the current game cycle to the current value of scrollBar
    	currentGameCycle = i;
    	
    	// Update the state panel to reflect the current game cycle
    	statePanel.setStateDirect(currentTrace.getGameStateAtCycle(i));
        statePanel.repaint();

    	
    }
    


	@Override
	public void actionPerformed(ActionEvent e) {
		// If the traces combobox is interacted with
		if (e.getSource() == traces) {
			
	    	// If the timer is still going, stop the timer.
	    	if (timer != null) {
	    		timer.stop();
	    	}
	    	
			// Get the name of the selected item
			String traceName = (String)traces.getSelectedItem();
			// Load the trace
	        loadTrace(traceName);
		}
		
		
	}
	
    public static void main(String[] args) {
    	// Create a new instance of the traceDialogWindowPeng
    	new traceDialogWindowPeng().setVisible(true);
    }
}
