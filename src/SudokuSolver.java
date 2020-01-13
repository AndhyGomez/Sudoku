import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class SudokuSolver
{
	/**
	 * @author Andhy Gomez
	 * St. Thomas University
	 * 
	 * Description: Program that will read a sudoku puzzle/matrix from a file
	 * and then return the puzzle solved
	 * 
	 * ***NOTE***
	 * -> file can not be in src folder
	 * 
	 * @param args
	 */
	
	// File that will be used throughout the code
	static File file = new File("s01a.txt"); // Insert file name
	
	// Global variable to manipulate and get index of number to input
	static String trueIndex;

	public static void main(String []args) throws IOException , FileNotFoundException
	{
		// Declare constant size for puzzle
		final int ROWSCOLS = 9;
		
		// Initialize variables
		boolean isSolved;
		boolean[] checkedRows, checkedCols; //********* Remember to move to solvePuzzle method
		int count; //********* Remember to move to solvePuzzle method
		
		// Declare 2D array to hold the current puzzle
		String [][] puzzle = new String[ROWSCOLS][ROWSCOLS];
		
		// String array holding all possible values
		String[] possibleValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

		// Call setPuzzle method
		setPuzzle(puzzle);
		
		// Ensure puzzle was read correctly
		displayMatrix(puzzle);
		
		 /******************************************** NOTE TO SELF ********************************************
		 * 
		 * SOLVE PUZZLE
		 * ------------
		 * Going to convert into method
		 * 
		 * void solvePuzzle(String[][] array)
		 * 		- call isSolved after each completed for loop to stop when isSolved = true
		 * 		- call displayMatrix when isSolved = true to display solution.
		 * 
		 * Question: checkRow/Col returns bool, how do you get the value that needs to be replaced?
		 */
		
		// Assign false to initial puzzle
		isSolved = isSolved(puzzle);
		
		
		
		while(!isSolved)
		{
			for (int row = 0; row < puzzle.length; row++)
            {
                for (int col = 0; col < puzzle[0].length; col++)
                {
                	if(puzzle[row][col].equals("0"))
                	{
                		
                		boolean rowCheck = checkRow(puzzle, row);
                		boolean colCheck = checkCol(puzzle, col);
                		
                		
                	}
                }
            }
		
		}
	}
	
	    /*
	     * end solvePuzzle
	     */
	
	/**
	 * Description: Reads a 2D array from a desired file
	 * 
	 * @param array 2D square String array
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void setPuzzle(String[][] array) throws IOException, FileNotFoundException
	{
		//Create scanner buffered reader and file reader to read desired file
		Scanner thisFile = new Scanner(new BufferedReader(new FileReader(file)));
		
		while (thisFile.hasNext())
		{
            for (int row = 0; row < array.length; row++)
            {
                for (int col = 0; col < array[0].length; col++)
                {
                    array[row][col] = thisFile.next();
                }
            }   

        }
		
		thisFile.close();
	
	}
	
	/**
	 * Description: Prints a 2D array formatted to display as a matrix
	 * 
	 * @param array 2D square String array
	 */
	public static void displayMatrix(String[][] array)
	{
		for(int row = 0; row < array.length; row++)
		{
			for(int col = 0; col < array[0].length; col++)
			{
				if(row == 0 && col == 0)
				{
					System.out.print("- - - - - - - - - "
							+ "" +  "\n");
				}
				
				System.out.print(array[row][col] + " ");
				
				if(col == (array.length - 1))
				{
					System.out.print("\n");
					System.out.print("- - - - - - - - - " +  "\n");
				}
			}
		}
	}
	
	/**
	 * Description: Determines if the sudoku puzzle has been solved
	 * 
	 * @param array 2D String array
	 * @return boolean value for whether or not the sudoku puzzle has been solved
	 */
	public static boolean isSolved(String[][] array)
	{
		// Initialize variables
		String currentValue;
		
		boolean isSolved = true;

		for(int row = 0; row < array.length; row++)
		{
			for(int col = 0; col < array[0].length; col++)
			{
				// Set current value to index of position in the array
				currentValue = array[row][col];
					
				if(currentValue.equals("0"))
				{
					isSolved = false;
				}
				else
				{
					isSolved = true;
				}
			}
		}
		
		return isSolved;
		
	}
	
	public static int returnTrueIndex(int index)
	{
		return index;
	}
	
	public static boolean checkRow(String[][] array, int row)
	{
		// Initialize variables
		boolean isViable;
		String testValue;
		String currentValue;
		
		// String array holding all values to check
		String[] valuesToCheck = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		// Stores boolean values to determine when to place a number
		boolean[] shouldPlace = new boolean[array.length];
		
		for(int col = 0; col < array[0].length; col++)
		{
			currentValue = array[row][col];			// Because valuesToCheck contains the same number of elements as
			testValue = valuesToCheck[col]; 		// number of columns in the 2D String array, iterator 'col' can 
													// used as index for both
			
			if(testValue.equals(currentValue))
			{
				isViable = false;
				shouldPlace[col] = isViable;
			}
			else
			{
				isViable = true;
				trueIndex = testValue;
				shouldPlace[col] = isViable;
			}													
		}
		
		// Reset is viable
		isViable = true;
		
		for(int index = 0; index < shouldPlace.length; index++)
		{
			if(!shouldPlace[index])
			{
				// Box is not viable
				isViable = false;
				break;
			}	
		}
		
		return isViable;
		
	}

	public static boolean checkCol(String[][] array, int col)
	{
		// Initialize variables
		boolean isViable;
		String testValue;
		String currentValue;
		
		// String array holding all values to check
		String[] valuesToCheck = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		// Stores boolean values to determine when to place a number
		boolean[] shouldPlace = new boolean[array.length];
		
		for(int row = 0; row < array[0].length; row++)
		{
			currentValue = array[row][col];			// Because valuesToCheck contains the same number of elements as
			testValue = valuesToCheck[row]; 		// number of columns in the 2D String array, iterator 'col' can 
													// used as index for both
			
			if(testValue.equals(currentValue))
			{
				isViable = false;
				shouldPlace[row] = isViable;
			}
			else
			{
				isViable = true;
				shouldPlace[row] = isViable;
			}													
		}
		
		// Reset is viable
		isViable = true;
		
		for(int index = 0; index < shouldPlace.length; index++)
		{
			if(!shouldPlace[index])
			{
				// Box is not viable
				isViable = false;
				break;
			}	
		}
		
		return isViable;
	}
}