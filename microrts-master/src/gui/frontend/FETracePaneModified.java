/*
 * File is a modified version of the FETracePane Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package gui.frontend;

import gui.PhysicalGameStatePanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdom.input.SAXBuilder;
import rts.GameState;
import rts.Trace;
import rts.TraceEntry;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollBar;

/**
 *
 * @author santi
 */
public class FETracePaneModified extends JPanel {
    
    Trace currentTrace = null;
    int currentGameCycle = 0;
    
    PhysicalGameStatePanel statePanel;
    
    File[] dirFiles;
    File trace;
    
    public void loadTrace() {
    	for (File file : dirFiles)
        {
        	if (file.getName().equals("Evolution_trace.xml"))
        	{
        		trace = file;
        	}
        }
        
        try {
        	currentTrace = new Trace(new SAXBuilder().build(trace.getAbsolutePath()).getRootElement());
            currentGameCycle = 0;
            statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
            statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
            statePanel.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public FETracePaneModified(File dir) {
    	dirFiles = dir.listFiles();
        
        setLayout(new BorderLayout());
        
        JPanel p2 = new JPanel();
        statePanel = new PhysicalGameStatePanel((GameState)null);
        statePanel.setPreferredSize(new Dimension(512, 512));
        loadTrace();
        add(p2, BorderLayout.SOUTH);
                {
                }
        
                JPanel p1east = new JPanel();
                p1east.setLayout(new BoxLayout(p1east, BoxLayout.Y_AXIS));
                {
                    JButton b = new JButton("-1 Frame");
                    b.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b.setAlignmentY(Component.TOP_ALIGNMENT);
                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            if (currentGameCycle>0) {
                                currentGameCycle--;
                                statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
                                statePanel.repaint();
                            }
                        }
                    });
                    p1east.add(b);
                }
                {
                    JButton b = new JButton("+1 Action");
                    b.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b.setAlignmentY(Component.TOP_ALIGNMENT);
                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            for(TraceEntry te:currentTrace.getEntries()) {
                                if (te.getTime()>currentGameCycle) {
                                    currentGameCycle = te.getTime();
                                    statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
                                    statePanel.repaint();
                                    break;
                                }
                            }
                        }
                    });
                    {
                        JButton b_1 = new JButton("-1 Action");
                        b_1.setAlignmentX(Component.CENTER_ALIGNMENT);
                        b_1.setAlignmentY(Component.TOP_ALIGNMENT);
                        b_1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e)
                            {
                            	
                                TraceEntry target = null;
                                for(TraceEntry te:currentTrace.getEntries()) {
                                    if (te.getTime()<currentGameCycle) {
                                        if (target==null || te.getTime()>target.getTime()) {
                                            target = te;
                                        }
                                    }
                                }
                                if (target!=null) {
                                    currentGameCycle = target.getTime();
                                    statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
                                    statePanel.repaint();
                                }
                            }
                        });
                        p1east.add(b_1);
                    }
                    JButton b_2 = new JButton("-5 Action");
                    b_2.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b_2.setAlignmentY(Component.TOP_ALIGNMENT);
                    b_2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                        	for (int i=0; i<5;i++)
                        	{
                        		
                            TraceEntry target = null;
                            for(TraceEntry te:currentTrace.getEntries()) {
                                if (te.getTime()<currentGameCycle) {
                                    if (target==null || te.getTime()>target.getTime()) {
                                        target = te;
                                    }
                                }
                            }
                            if (target!=null) {
                                currentGameCycle = target.getTime();
                                statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
                                statePanel.repaint();
                            }
                        }
                        	}
                    });
                    p1east.add(b_2);
                    {
                        JButton b_1 = new JButton("+1 Frame");
                        b_1.setAlignmentX(Component.CENTER_ALIGNMENT);
                        b_1.setAlignmentY(Component.TOP_ALIGNMENT);
                        b_1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e)
                            {
                                if (!statePanel.getState().gameover()) {
                                    currentGameCycle++;
                                    GameState tmp_gs = currentTrace.getGameStateAtCycle(currentGameCycle);
                                    statePanel.setStateDirect(tmp_gs);
                                    statePanel.repaint();
                                }
                            }
                        });
                        p1east.add(b_1);
                    }
                    p1east.add(b);
                }
                
                // +5 actions
                {
                	JButton b_1 = new JButton("+5 Action");
                    b_1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    b_1.setAlignmentY(Component.TOP_ALIGNMENT);
                    b_1.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e)
                        {
                        	for (int i=0; i<5;i++)
                        	{
                        		
                        	
                            for(TraceEntry te:currentTrace.getEntries()) {
                                if (te.getTime()>currentGameCycle) {
                                    currentGameCycle = te.getTime();
                                    statePanel.setStateDirect(currentTrace.getGameStateAtCycle(currentGameCycle));
                                    statePanel.repaint();
                                    break;
                                }
                            }
                        	}
                        }
                    });
                    p1east.add(b_1);
                	
                }
        
        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setOrientation(JScrollBar.HORIZONTAL);
        GroupLayout gl_p2 = new GroupLayout(p2);
        gl_p2.setHorizontalGroup(
        	gl_p2.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_p2.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(p1east, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(36)
        			.addGroup(gl_p2.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_p2.createSequentialGroup()
        					.addGap(10)
        					.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addContainerGap())
        				.addComponent(statePanel, GroupLayout.PREFERRED_SIZE, 853, GroupLayout.PREFERRED_SIZE)))
        );
        gl_p2.setVerticalGroup(
        	gl_p2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_p2.createSequentialGroup()
        			.addContainerGap(255, Short.MAX_VALUE)
        			.addGroup(gl_p2.createParallelGroup(Alignment.TRAILING)
        				.addComponent(p1east, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_p2.createSequentialGroup()
        					.addComponent(statePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        p2.setLayout(gl_p2);
    }
}
