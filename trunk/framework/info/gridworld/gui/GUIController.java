/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Julie Zelenski
 * @author Cay Horstmann
 */

package info.gridworld.gui;

import info.gridworld.actor.Apple;
import info.gridworld.actor.Cherry;
import info.gridworld.actor.Fruit;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Galaxian;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.Melon;
import info.gridworld.actor.Orange;
import info.gridworld.actor.MazeWall;
import info.gridworld.actor.MsPacMan;
import info.gridworld.actor.PacMan;
import info.gridworld.actor.PacManClosed;
import info.gridworld.actor.PacManMiddle;
import info.gridworld.actor.PacManOpen;
import info.gridworld.actor.Strawberry;
import info.gridworld.grid.*;
import info.gridworld.world.PacWorld;
import info.gridworld.world.World;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

import Levels.Level;

/**
 * The GUIController controls the behavior in a WorldFrame. <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */

public class GUIController<T>
{	
    public static final int INDEFINITE = 0, FIXED_STEPS = 1, PROMPT_STEPS = 2;

    private static final int MIN_DELAY_MSECS = 20, MAX_DELAY_MSECS = 130;
    public static final int INITIAL_DELAY = MIN_DELAY_MSECS
            + (MAX_DELAY_MSECS - MIN_DELAY_MSECS) / 2;

    private Timer timer;
    private JButton stepButton, runButton, stopButton;
    private JComponent controlPanel;
    private GridPanel display;
    private WorldFrame<T> parentFrame;
    private int numStepsToRun, numStepsSoFar;
    private ResourceBundle resources;
    private DisplayMap displayMap;
    private boolean running;
    private Set<Class> occupantClasses;

    /**
     * Creates a new controller tied to the specified display and gui
     * frame.
     * @param parent the frame for the world window
     * @param disp the panel that displays the grid
     * @param displayMap the map for occupant displays
     * @param res the resource bundle for message display
     */
    public GUIController(WorldFrame<T> parent, GridPanel disp,
            DisplayMap displayMap, ResourceBundle res)
    {
        resources = res;
        display = disp;
        parentFrame = parent;
        this.displayMap = displayMap;
        makeControls();

        occupantClasses = new TreeSet<Class>(new Comparator<Class>()
        {
            @Override
			public int compare(Class a, Class b)
            {
                return a.getName().compareTo(b.getName());
            }
        });

        World<T> world = parentFrame.getWorld();
        Grid<T> gr = world.getGrid();
        for (Location loc : gr.getOccupiedLocations())
            addOccupant(gr.get(loc));
        for (String name : world.getOccupantClasses())
            try
            {
                occupantClasses.add(Class.forName(name));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        timer = new Timer(INITIAL_DELAY, new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent evt)
            {
                step();
            }
        });

        //@author max: comment this out to get rid of click able clickable locations
        display.addMouseListener(new MouseAdapter()
        {
            @Override
			public void mousePressed(MouseEvent evt)
            {
                Grid<T> gr = parentFrame.getWorld().getGrid();
                Location loc = display.locationForPoint(evt.getPoint());
                if (loc != null && gr.isValid(loc) && !isRunning())
                {
                    display.setCurrentLocation(loc);
                    locationClicked();
                }
            }
        });
        
        
        stop();
    }

    /**
     * Advances the world one step.
     */
    public void step()
    {    
    	GridPanel.c++;
    	
    	//Place the fruit
    	//@author vivek
    	boolean secondFruit = PacMan.getCurrentPoints()>2000 && !PacWorld.getLevel().PlacedFruit2();
    	boolean firstFruit = PacMan.getCurrentPoints()>1000 && !PacWorld.getLevel().PlacedFruit1();		//Should be like 1000
    	Grid g = PacWorld.getLevel().getGrid();
    	Location fruitplace = new Location(g.getNumRows() / 2 + 2, g.getNumCols() / 2);
    	if(g.get(fruitplace) == null){							//only do stuff if unoccupied
    		int cols = PacWorld.getLevel().getGrid().getNumCols();
    		Fruit fruit1;
    		Fruit fruit2;
    		if(cols==16){ //level 1
    			fruit1 = new Cherry();
    			fruit2 = new Apple();
    		}
    		else if(cols == 19){ //level 2
    			fruit1 = new Strawberry();
    			fruit2 = new Orange();
    		}
    		else{ //level 3
    			fruit1 = new Melon();
    			fruit2 = new Galaxian();
    		}
    		if(secondFruit){
    			fruit2.putSelfInGrid(g, fruitplace);
    			PacWorld.getLevel().PlaceFruit(2);
    		}
    		else if(firstFruit){
    			fruit1.putSelfInGrid(g, fruitplace);
    			PacWorld.getLevel().PlaceFruit(1);
    		}
   		}
    
    	if (PacWorld.isGameOver()){
    		GridPanel.resetC();
    		stop();
    		PacWorld p = new PacWorld(PacWorld.level1());
    		
    		PacMan.setDead(false);
    		PacMan.setLives(3);
    		PacMan.setPoints(0);
    		PacWorld.setGameOver(false);
    		
    		p.refreshMessage();
            p.show();
            
    		parentFrame.dispose();
    	}
    	
    	//@author max: important stuff for leveling.
    	if (Level.won() && PacWorld.isGameWon() == false){
    		PacMan.setDead(false);
    		PacWorld.setGameOver(false);
    		
        	Level l = PacWorld.nextLevel();
        	
        	if (l == null){ //if the last level
        		PacWorld.setGameWon(true);
        		MsPacMan ms = Level.getMsPac();
        		ms.moveTo(new Location(1, 16));
        		ms.setDirection(180);
        		((Actor) Level.getGrid().get(new Location(2, 16))).removeSelfFromGrid();
        		PacWorld.setGameWon(true);
        		for (Ghost g: Level.getGhosts()){ //remove ghosts
        			if (g.getGrid() != null)
        				g.removeSelfFromGrid();
        		}
        	}
        	else{
        		GridPanel.resetC();
        		PacMan.setLives(PacMan.getLives() + 1);
        		PacWorld pc = new PacWorld(l);
        		JOptionPane.showMessageDialog(parentFrame, new JLabel("You've completed the level! Press the button below to continue."), "Level Complete: Difficulty Increased", 2,
            			new ImageIcon(PacWorld.getFrame().getClass().getResource("GridWorld.png")));
        		pc.show();
                parentFrame.dispose(); 
                stop();
        	}    
    	}
    	
    	if (PacMan.isDead()){
    		PacMan.GetEaten();
    		GridPanel.resetC();
    		if (PacMan.getLives() > 0){
    			PacWorld.restart();
    			PacMan.extrapac();
    			pause(3);
    		}
    		else{
    			stop();
    			PacWorld.gameOver();
    		}
    	}
    	
        parentFrame.getWorld().step();
        parentFrame.repaint();
        if (++numStepsSoFar == numStepsToRun)
            stop();
        Grid<T> gr = parentFrame.getWorld().getGrid();
        
        for (Location loc : gr.getOccupiedLocations())
            addOccupant(gr.get(loc));
    		
    }
    
    
    //pauses for three seconds
    public static void pause(int x) {
    	try {
    		Thread.currentThread().sleep(x * 1000);
    	}
    	catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    private void addOccupant(T occupant)
    {
        Class cl = occupant.getClass();
        do
        {
            if ((cl.getModifiers() & Modifier.ABSTRACT) == 0)
                occupantClasses.add(cl);
            cl = cl.getSuperclass();
        }
        while (cl != Object.class);
    }

    /**
     * Starts a timer to repeatedly carry out steps at the speed currently
     * indicated by the speed slider up Depending on the run option, it will
     * either carry out steps for some fixed number or indefinitely
     * until stopped.
     */
    public void run()
    {
        display.setToolTipsEnabled(false); // hide tool tips while running
        parentFrame.setRunMenuItemsEnabled(false);
        stopButton.setEnabled(true);
        stepButton.setEnabled(false);
        runButton.setEnabled(false);
        numStepsSoFar = 0;
        timer.start();
        running = true;
    }

    /**
     * Stops any existing timer currently carrying out steps.
     */
    public void stop()
    {
        display.setToolTipsEnabled(true); //@author max: disable to disable tooltips
        parentFrame.setRunMenuItemsEnabled(false);
        timer.stop();
        stopButton.setEnabled(false);
        runButton.setEnabled(true);
        stepButton.setEnabled(true);
        running = false;
    }

    public boolean isRunning()
    {
        return running;
    }

    /**
     * Builds the panel with the various controls (buttons and
     * slider).
     */
    private void makeControls()
    {
        controlPanel = new JPanel();
        stepButton = new JButton(resources.getString("button.gui.step"));
        runButton = new JButton(resources.getString("button.gui.run"));
        stopButton = new JButton(resources.getString("button.gui.stop"));
        
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBorder(BorderFactory.createEtchedBorder());
        
        Dimension spacer = new Dimension(5, stepButton.getPreferredSize().height + 10);

        controlPanel.add(Box.createRigidArea(spacer));

        controlPanel.add(stepButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(runButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(stopButton);
        runButton.setEnabled(false);
        stepButton.setEnabled(false);
        stopButton.setEnabled(false);

        /**controlPanel.add(Box.createRigidArea(spacer));
         * @author max: remove slider 
        controlPanel.add(new JLabel(resources.getString("slider.gui.slow")));
        JSlider speedSlider = new JSlider(MIN_DELAY_MSECS, MAX_DELAY_MSECS,
                INITIAL_DELAY);
        speedSlider.setInverted(true);
        speedSlider.setPreferredSize(new Dimension(100, speedSlider
                .getPreferredSize().height));
        speedSlider.setMaximumSize(speedSlider.getPreferredSize());
        */

        // remove control PAGE_UP, PAGE_DOWN from slider--they should be used
        // for zoom
        /*InputMap map = speedSlider.getInputMap();
        while (map != null)
        {
            map.remove(KeyStroke.getKeyStroke("control PAGE_UP"));
            map.remove(KeyStroke.getKeyStroke("control PAGE_DOWN"));
            map = map.getParent();
        }*/

        //controlPanel.add(speedSlider); @author max: remove slider
        //controlPanel.add(new JLabel(resources.getString("slider.gui.fast"))); @author max: remove slider
        //controlPanel.add(Box.createRigidArea(new Dimension(5, 0))); @author max: remove slider

        
        
        //The below stuff is for buttons, not keys. - Vivek
        
        stepButton.addActionListener(new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent e)
            {
                step();
            }
        });
        runButton.addActionListener(new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent e)
            {
                run();
            }
        });
        stopButton.addActionListener(new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent e)
            {
                stop();
            }
        });
        
        /**
         * @author max: remove slider
         * 
        speedSlider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent evt)
            {
                timer.setDelay(((JSlider) evt.getSource()).getValue());
            }
        });
        */ 
    }

    /**
     * Returns the panel containing the controls.
     * @return the control panel
     */
    public JComponent controlPanel()
    {
        return controlPanel;
    }

    /**
     * Callback on mousePressed when editing a grid.
     */
    
    /**
     * @author max: comment this out if you don't want clickable locations
     */
    private void locationClicked()
    {
        World<T> world = parentFrame.getWorld();
        Location loc = display.getCurrentLocation();
        if (loc != null && !world.locationClicked(loc))
            editLocation();
        parentFrame.repaint();
        
    }
    

    /**
     * Edits the contents of the current location, by displaying the constructor
     * or method menu.
     */
    
	// @author max: comment this out if you don't want clickable locations

    public void editLocation()
    {
    	  
        World<T> world = parentFrame.getWorld();

        Location loc = display.getCurrentLocation();
        if (loc != null)
        {
            T occupant = world.getGrid().get(loc);
            if (occupant == null)
            {
                MenuMaker<T> maker = new MenuMaker<T>(parentFrame, resources,
                        displayMap);
                JPopupMenu popup = maker.makeConstructorMenu(occupantClasses,
                        loc);
                Point p = display.pointForLocation(loc);
                popup.show(display, p.x, p.y);
            }
            else
            {
                MenuMaker<T> maker = new MenuMaker<T>(parentFrame, resources,
                        displayMap);
                JPopupMenu popup = maker.makeMethodMenu(occupant, loc);
                Point p = display.pointForLocation(loc);
                popup.show(display, p.x, p.y);
            }
        }
        parentFrame.repaint();
        
    }

    /**
     * Edits the contents of the current location, by displaying the constructor
     * or method menu.
     */
    public void deleteLocation()
    {
        World<T> world = parentFrame.getWorld();
        Location loc = display.getCurrentLocation();
        if (loc != null)
        {
            world.remove(loc);
            parentFrame.repaint();
        }
    }
}