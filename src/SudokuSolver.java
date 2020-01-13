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
		
		// Declare 2D array to hold the current puzzle
		String [][] puzzle = new String[ROWSCOLS][ROWSCOLS];
		
		/* Declare 2D array to contain viability per coordinate in puzzle
		 * and fill all indexes to be false.
		 */
		boolean[][] viability = new boolean [ROWSCOLS][ROWSCOLS];
		fillBoolArray(viability, false);
		
		// String array holding all possible values
		String[] possibleValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		// Initialize variables 
		boolean rowCheck;
		boolean colCheck;
		boolean onlyOption;

		// Call setPuzzle method
		setPuzzle(puzzle);
		
		// Ensure puzzle was read correctly
		//displayMatrix(puzzle);
		
		boolean isSolved = isSolved(puzzle);
		
		
		//while(!isSolved) 
		//{
			for(int possibleValueIndex = 0; possibleValueIndex < possibleValues.length; possibleValueIndex++)
			{
				for(int row = 0; row < puzzle.length; row++)
				{
					for(int col = 0; col < puzzle[0].length; col++)
					{
						if(puzzle[row][col].equals("0"))
						{
							rowCheck = checkRow(puzzle, row, possibleValues[possibleValueIndex]);
							colCheck = checkCol(puzzle, col, possibleValues[possibleValueIndex]);
							
							if(rowCheck == true && colCheck == true)
							{	
								viability[row][col] = true;
							}
							
							onlyOption = isOnePossibility(viability);
							
							if(onlyOption == true)
							{
								puzzle[row][col] = possibleValues[possibleValueIndex];
							}
							
							// Reset viability for next iteration
							fillBoolArray(viability, false);
						}
						
						
					}
				}
			}
		
			isSolved = isSolved(puzzle);
		//}
		
		// View solution
		displayMatrix(puzzle);
	}
	
	/**
	 * Description: Reads a 2D array from a desired file
	 * 
	 * @param array 2D square String array
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void setPuzzle(String[][] array) throws IOException, FileNotFoundException
	{
		// Create scanner buffered reader and file reader to read desired file
		Scanner thisFile = new Scanner(new BufferedReader(new FileReader(file)));
		
		// Loop until the end of the file
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
	 * Description: Fills all elements in a 2D boolean array to be 
	 * either true or false. 
	 * 
	 * @param array 2D boolean array
	 * @param trueORfalse Fill indexes to be either true or false
	 */
	public static void fillBoolArray(boolean[][] array, boolean trueORfalse)
	{
		for(int row = 0; row < array.length; row++)
		{
			for(int col = 0; col < array[0].length; col++)
			{
				array[row][col] = trueORfalse;
			}
		}
	}
	
	public static boolean isOnePossibility(boolean[][] array)
	{
		boolean isOnePossibility = false;
		int possibilityCounter = 0;
		
		for(int row = 0; row < array.length; row++)
		{
			for(int col = 0; col < array[0].length; col++)
			{
				if(array[row][col] == true)
				{
					possibilityCounter++;
				}
			}
		}
		
		if(possibilityCounter == 1)
		{
			isOnePossibility = true;
		}
		
		return isOnePossibility;
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
	 * 
	 * @param array
	 * @param row
	 * @param valueToCheck
	 * @return
	 */
	public static boolean checkRow(String[][] array, int row, String valueToCheck)
	{
		// Initialize Variables
		String currentValue;
		boolean isViable = false;
		
		for(int col = 0; col < array[0].length; col++)
		{
			currentValue = array[row][col];
			
			if(valueToCheck.equals(currentValue))
			{
				isViable = false;
				break;
			}
			else
			{
				isViable = true;
			}
		}
		
		return isViable;
	}
	
	/**
	 * 
	 * @param array
	 * @param col
	 * @param valueToCheck
	 * @return
	 */
	public static boolean checkCol(String[][] array, int col, String valueToCheck)
	{
		// Initialize Variables
		String currentValue;
		boolean isViable = false;
		
		for(int row = 0; row < array.length; row++)
		{
			currentValue = array[row][col];
			
			if(valueToCheck.equals(currentValue))
			{
				isViable = false;
				break;
			}
			else
			{
				isViable = true;
			}
		}
		
		return isViable;
	}
	
	public static boolean check3x3(String[][] array, int minRow, int maxRow, int minCol, int maxCol, String valueToCheck)
	{
		// Initialize Variables
		String currentValue;
		boolean isViable = false;
		
		for(int row = minRow; row < maxRow; row++)
		{
			for(int col = minCol; col < maxCol; col++)
			{
				currentValue = array[row][col];
				
				if(valueToCheck.equals(currentValue))
				{
					isViable = false;
					break;
				}
				else
				{
					isViable = true;
				}
			}
		
		}
		
			return isViable;
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
		int index;
		boolean currentBool;
		boolean isFilled = false;
		boolean isSolved = true;
		
		// Boolean array to store wether or not a value is filled
		boolean[] allChecks = new boolean[array.length * array.length];

		index = 0; // accumulator for allChecks index
		
		for(int row = 0; row < array.length; row++)
		{
			for(int col = 0; col < array[0].length; col++)
			{
				// Set current value to index of position in the array
				currentValue = array[row][col];
					
				if(currentValue.equals("0"))
				{
					isFilled = false;
					allChecks[index] = isFilled;
					index++;
				}
				else
				{
					isFilled = true;
					allChecks[index] = isFilled;
					index++;
				}
				
			}
		}
		
		for(int i = 0; i < allChecks.length; i++)
		{
			currentBool = allChecks[i];
			if(!currentBool)
			{
				isSolved = false;
				break;
			}
		}
		
		return isSolved;
		
	}
}
	
