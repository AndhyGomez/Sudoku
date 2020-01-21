import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game 
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
	 * @param args name of a file to be read from as a String data type
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{	
		// Constant value for 2D array size
		final int SIZE = 9;
		
		// Variable to hold name of file
		String fileName = args[0];
		
		// Create an instance of the Sudoku object
		Sudoku sudoku = new Sudoku(fileName);
		
		// print initial puzzle
		System.out.println(sudoku.toString());		
		
		sudoku.solve();	
		
		// Print out solved puzzle
		System.out.println(sudoku.toString());
		
		System.out.println("Solved with " + sudoku.getGridsGenerated() + " grids generated.");
		
	}
	
}
