import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int[numRows][numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
    grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	Color drawingColor = null;
	Color gray = Color.GRAY;
	Color tan = new Color(240,199,129);
	Color black = Color.BLACK;
	Color blue = new Color(53, 145, 217);
    for (int rows = 0; rows < grid.length; rows++)
    {
    		for (int cols = 0; cols < grid[0].length; cols++)
    		{
    			if (grid[rows][cols] == METAL)
    			{
    				drawingColor = gray;
    			}
    			else if (grid[rows][cols] == SAND)
    			{
    				drawingColor = tan;
    			}
    			else if (grid[rows][cols] == WATER)
    			{
    				drawingColor = blue;
    			}
    			else
    			{
    				drawingColor = black;
    			}
    			
    			display.setColor(rows, cols, drawingColor);
    		}
    }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    	int rowRandom = (int) (Math.random() * grid.length - 1);
    	int colRandom = (int) (Math.random() * grid[0].length);    	
    	
    	if (grid[rowRandom][colRandom] == SAND && grid[rowRandom + 1][colRandom] == EMPTY)
    	{
    		grid[rowRandom][colRandom] = EMPTY;
    		grid[rowRandom + 1][colRandom] = SAND;
    	}
    	else if (grid[rowRandom][colRandom] == SAND && grid[rowRandom + 1][colRandom] == WATER)
    	{
    		grid[rowRandom][colRandom] = WATER;
    		grid[rowRandom + 1][colRandom] = SAND;
    	}
    	
    	if (grid[rowRandom][colRandom] == WATER)
    	{
    		int waterRandom = (int) (Math.random() * 3);
    		switch (waterRandom)
    		{
    			case 0:	if (colRandom <= (grid[0].length - 2) && grid[rowRandom][colRandom + 1] == EMPTY)
    					{
    						grid[rowRandom][colRandom] = EMPTY;
    						grid[rowRandom][colRandom + 1] = WATER;
    					}
    					break;
    			case 1:	if (grid[rowRandom + 1][colRandom] == EMPTY)
					{
						grid[rowRandom][colRandom] = EMPTY;
						grid[rowRandom + 1][colRandom] = WATER;
					}
    					break;
    			case 2:	if (colRandom > 0 && grid[rowRandom][colRandom - 1] == EMPTY)
					{
						grid[rowRandom][colRandom] = EMPTY;
						grid[rowRandom][colRandom - 1] = WATER;
					}
    					break;
    		}
    	}
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
