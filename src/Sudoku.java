import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

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
public class Sudoku 
{
	// Constant and static values
	final int ROWSCOLS = 9;
	final int BOXLENGTH = 3;
	
	// Class Variables
	int[][] grid = new int[ROWSCOLS][ROWSCOLS];
	int[] possibleValues = {1,2,3,4,5,6,7,8,9};
	int gridsGenerated;
	
	/**
	 * Description: Create a constructor that initializes the puzzle
	 * 
	 * @param filePath name of the file to be used as a String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Sudoku(String filePath) throws FileNotFoundException, IOException
	{				
		readPuzzle(filePath);
	}
	
	/**
	 * Description: Reads puzzle from filePath
	 * 
	 * @param filePath name of file to be read as a String data type
	 * @return 2D array read from file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	int[][] readPuzzle(String filePath) throws IOException, FileNotFoundException
	{	
		// Create scanner buffered reader and file reader to read desired file
		Scanner thisFile = new Scanner(new File(filePath));
		
		for(int row = 0; row < ROWSCOLS; row++)
		{
			for(int col = 0; col < ROWSCOLS; col++)
			{
				if(thisFile.hasNextInt())
				{
					grid[row][col] = thisFile.nextInt();				
				}
			}	
		}
		
		thisFile.close(); // Close the file
		
		return grid;
	}
	
	/**
	 * Description: Narrows down possible candidates checking row, column, and subGrid 
	 * where currentValue lies, if value at grid[row][col] equal to a value in candidatesList, 
	 * then it removes that value from the list of candidates.
	 * 
	 * @param row Index for row to check
	 * @param col Index for column to check
	 * @return array list of pottential candidates
	 */
	public ArrayList<Integer> getCandidates(int row, int col)
	{
		// Initialize variable
		int currentValue;
		
		// Create an array list of integers
		ArrayList<Integer> candidatesList = new ArrayList<Integer>();
		
		// Add values 1-9 to candidatesList
		for(int times = 0; times < ROWSCOLS; times++)
		{	
			candidatesList.add((times + 1)); // Times + 1 because values must be between 1 and 9.
		}
		
		// Loop through row to narrow candidatesList to remove impossible candidates
		for(int indexInRow = 0; indexInRow < candidatesList.size(); indexInRow++)
		{			
			currentValue = grid[row][indexInRow];
			
			if(candidatesList.contains(currentValue))
			{
				candidatesList.remove(new Integer(currentValue));
			}
			
			//System.out.println(candidatesList.toString());
		}
		
		// Loop through column to narrow candidatesList to remove impossible candidates
		for(int indexInCol = 0; indexInCol < candidatesList.size(); indexInCol++)
		{
			currentValue = grid[indexInCol][col];
				
			if(candidatesList.contains(currentValue))
			{
				candidatesList.remove(new Integer(currentValue));
			}
		}
		
		int rowIndex = row - row % BOXLENGTH;
		int colIndex = col - col % BOXLENGTH;
		
		// Loop through column to narrow candidatesList to remove impossible candidates
		for(int i = rowIndex; i < (rowIndex + BOXLENGTH); i++)
		{
			for(int j = colIndex; j < colIndex + BOXLENGTH; j++)
			{
				currentValue = grid[i][j];
				
				if(candidatesList.contains(currentValue))
				{
					candidatesList.remove(new Integer(currentValue));
				}
			}
		}
		
		return candidatesList;
	}
	
	/**
	 * Description: Checks given row to verify it has only numbers 1-9 with no repeated numbers.
	 * 
	 * @param row Index of desired row to check
	 * @return Boolean value for whether or not the row is valid
	 */
	boolean isRowValid(int row)
	{
		// Initialize variables
		boolean isValid = false;
		
		// Initialize array list
		ArrayList<Integer> numsInRow = new ArrayList<Integer>();
		
		// Loop through a specific row using index of columns as accumulator
		for(int col = 0; col < ROWSCOLS; col++)
		{
			// Add value from row index to arrayList
			numsInRow.add(grid[row][col]);
		}
		
		for(int index = 0; index < possibleValues.length; index++)
		{
			if(numsInRow.contains(possibleValues[index]))
			{
				numsInRow.remove(new Integer(possibleValues[index]));
			}
		}
		
		if(numsInRow.isEmpty())
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	/**
	 * Description: Checks given column to verify it has only numbers 1-9 with no repeated numbers.
	 * 
	 * @param col Index of desired column to check
	 * @return Boolean value for whether or not the column is valid
	 */
	boolean isColValid(int col)
	{
		// Initialize variables
		boolean isValid = false;
		
		// Initialize array list
		ArrayList<Integer> numsInCol = new ArrayList<Integer>();
		
		// Loop through a specific row using index of columns as accumulator
		for(int row = 0; row < ROWSCOLS; row++)
		{
			// Add value from row index to arrayList
			numsInCol.add(grid[row][col]);
		}
		
		for(int index = 0; index < possibleValues.length; index++)
		{
			if(numsInCol.contains(possibleValues[index]))
			{
				numsInCol.remove(new Integer(possibleValues[index]));
			}
		}
		
		if(numsInCol.isEmpty())
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	/**
	 * Description: Checks desired subGrid to verify it has only numbers 1-9 with no repeated numbers.
	 * 
	 * @param row Index of desired row to check
	 * @param col Index of desired column to check
	 * @return Boolean value for whether or not the subGrid is valid
	 */
	boolean isSubGridValid(int row, int col)
	{
		// Initialize variables
		boolean isValid = false;
				
		// Initialize array list
		ArrayList<Integer> numsInSubGrid = new ArrayList<Integer>();
		
		int rowIndex = row - row % BOXLENGTH;
		int colIndex = col - col % BOXLENGTH;
		
		for(int i = rowIndex; i < (rowIndex + BOXLENGTH); i++)
		{
			for(int j = colIndex; j < colIndex + BOXLENGTH; j++)
			{
				numsInSubGrid.add(grid[row][col]);
			}
		}
		
		for(int index = 0; index < possibleValues.length; index++)
		{
			for(int i = rowIndex; i < (rowIndex + BOXLENGTH); i++)
			{
				for(int j = colIndex; j < colIndex + BOXLENGTH; j++)
				{
					if(numsInSubGrid.contains(possibleValues[index]))
					{
						numsInSubGrid.remove(new Integer(possibleValues[index]));
					}
				}
			}
		}	
	
		if(numsInSubGrid.isEmpty())
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	public int[][] solve()
	{
		// Initialize Variables
		int currentValue;
		int currentRand;
		
		boolean isValidRow = false;
		boolean isValidCol = false;
		boolean isValidSubGrid = false;
		boolean isSolved = false;
		
		// Create a new random object
		Random rand = new Random();
		
		// Initialize array list
		ArrayList<Integer> viableCandidates = new ArrayList<Integer>();
		
		// Loop through 2D array grid
		
		do
		{
			for(int row = 0; row < ROWSCOLS; row++)
			{
				for(int col = 0; col < ROWSCOLS; col++)
				{
					currentValue = grid[row][col];
					
					if(currentValue == 0)
					{
						viableCandidates = getCandidates(row, col);
						
						if(viableCandidates.isEmpty())
						{
							break;
						}
						
						currentRand = rand.nextInt(viableCandidates.size());
						
						grid[row][col] = currentRand;
					}	
				}
			}
			
			gridsGenerated++;
			
			// Loop through grid again to determine if any row, column, or subgrid is invalid
gridLoop:	for(int row = 0; row < ROWSCOLS; row++)
			{
				for(int col = 0; col < ROWSCOLS; col++)
				{
					isValidRow = isRowValid(row);
					isValidCol = isColValid(col);
					isValidSubGrid = isSubGridValid(row, col);
					
					if(!isValidRow || !isValidCol || !isValidSubGrid)
					{
						break gridLoop; // breaks out of outermost loop
					}
				}
			}
			
			if(isValidRow && isValidCol && isValidSubGrid)
			{
				isSolved = true;
			}
			
		System.out.println("Number of grids attempted: " + gridsGenerated);
			
		}while(!isSolved);

		return grid;
	}
	
	/**
	 * Description: Displays matrix as a string
	 * 
	 * @return 2D array as a string
	 */
	public String toString()
	{
		String arrayString = "";
		
		for(int row = 0; row < grid.length; row++)
		{
			for(int col = 0; col < grid[0].length; col++)
			{
				arrayString += (grid[row][col] + " ");
				
				if(col == (grid.length - 1))
				{
					arrayString += "\n";
				}
			}
						
		}
		
		return arrayString;	
	}
	
	/**
	 * Description: Getter for grids generated
	 * 
	 * @return number of grids generated
	 */
	int getGridsGenerated()
	{
		return gridsGenerated;
	}
	
}
