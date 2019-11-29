import java.util.ArrayList;
import java.util.Random;

/**
 * Class that implements Conway's Game of Life
 * @author hmbro
 *
 */
public class Game{
	
	Square [][] grid;
	
	/**
	 * Constructor to initialise game with specified grid size with random values
	 */
	public Game(int num) {
		Random rand = new Random();
		grid = new Square[num][num];
		for(int i = 0; i < num; i++) {
			for(int j = 0; j < num; j++) {
				grid[i][j] = new Square(i, j, rand.nextInt(2));
			}
		}
	}
	
	/**
	 * Constructor to initialise a game with given values parameter
	 * @param values
	 */
	public Game(int[][] values) {
		grid = new Square[values.length][values.length];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length; j++) {
				grid[i][j] = new Square(i,j,values[i][j]);
			}
		}
	}
	
	/**
	 * Constructor to initialise a game with given matrix of square objects
	 * @param values
	 */
	public Game(Square[][] values) {
		this.grid = new Square[values.length][values.length];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length; j++) {
				grid[i][j] = values[i][j];
			}
		}
	}
	
	/**
	 * Method to get a given squares list of neighbours
	 * @param x - matrix row
	 * @param y - matrix column
	 * @return list of neighbouring square objects
	 */
	public ArrayList<Square> getNeighbours(int x, int y) {
		ArrayList<Square> output = new ArrayList<Square>();
		
		if(x - 1 >= 0) {
			output.add(this.grid[x-1][y]);
			if(y -1 >= 0) {
				output.add(this.grid[x-1][y-1]);
			}
			if(y + 1 < grid.length) {
				output.add(this.grid[x-1][y+1]);
			}
		}
		
		if(y - 1 >= 0) {
			output.add(this.grid[x][y-1]);
			
		}
		
		if(x + 1 < grid.length) {
			output.add(this.grid[x+1][y]);
			if(y + 1 < grid.length) {
				output.add(this.grid[x+1][y+1]);
			}
		}
		
		if(y+1 < grid.length) {
			output.add(this.grid[x][y+1]);
		}
		
		if(x + 1 < grid.length && y - 1 >= 0) {
			output.add(this.grid[x+1][y-1]);
		}
		return output;
	}
	
	/**
	 * Method to return the number of alive cells from a squares neighbours
	 * @param s - current square
	 * @return number of alive cells
	 */
	public int numActiveNeighbours(Square s) {
		int count = 0;
		for(Square nei: this.getNeighbours(s.x, s.y)) {
			if(nei.value == 1) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Method to print out the current game grid
	 */
	public void print() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				System.out.print(grid[i][j]);
				System.out.print("\t");
			}
			System.out.println("");
		}
	}
	
	/**
	 * Method to run the current game object for n iterations with certain rules
	 * @param iter - number of iterations
	 */
	public void run(int iter) {
		for(int it = 0; it < iter; it++) {
			
			Game oldg = new Game(this.grid);
			
			
			int[][] values = new int [grid.length][grid.length];
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid.length; j++) {
					values[i][j] = 0;
				}
			}
			Game newg = new Game(values);
			
			//code for scenario 0: No iteractions
			boolean flag = true;
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid.length; j++) {
					if(grid[i][j].value != 0) {
						flag &= false;
					}
				}
			}
			if(flag) {
				System.out.println("================================");
				this.print();
				continue;
			}
			
			//checking cells and updating new grid
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid.length; j++) {
					
					//debug
					/*System.out.println("\nCell:");
					oldg.grid[i][j].printCoord();
					System.out.println("\nValue:" + oldg.grid[i][j].value);
					for(Square s: this.getNeighbours(i,j)) {
						s.printCoord();
					}
					System.out.println("\nNeighbours:" + oldg.numActiveNeighbours(grid[i][j]));
					*/
					
					//code for all scenarios
					if(oldg.grid[i][j].value == 1 && oldg.numActiveNeighbours(grid[i][j]) < 2) {
						newg.grid[i][j].value = 0;
						//System.out.println("Changed less than 2 neigh");
					}else if(oldg.grid[i][j].value == 1 && oldg.numActiveNeighbours(grid[i][j]) > 3) {
						newg.grid[i][j].value = 0;
						//System.out.println("Changed more than 3 neigh");
					}else if(oldg.grid[i][j].value == 1 && ((oldg.numActiveNeighbours(grid[i][j]) == 3) || oldg.numActiveNeighbours(grid[i][j]) == 2)) {
						newg.grid[i][j].value = 1;
						//System.out.println("Changed 2 or 3 neigh");
					}else if(oldg.grid[i][j].value == 0 && oldg.numActiveNeighbours(grid[i][j]) == 3) {
						newg.grid[i][j].value = 1;
						//System.out.println("Changed exactly 3");
					}else {
						newg.grid[i][j].value = oldg.grid[i][j].value;
					}
					
				}
			}
			
			this.grid = newg.grid;
			System.out.println("================================");
			this.print();
		}
	}
	
	/**
	 * Method to return the Square object at current position in matrix
	 * @param x - matrix row
	 * @param y - matrix column
	 * @return square object at [x][y]
	 */
	public Square getSquare(int x, int y) {
		return this.grid[x][y];
	}
	
	public static void main(String[] args) {
		//number of iterations
		int iter = 5;
		
		System.out.println("WELCOME TO CONWAY'S GAME OF LIFE!");
		System.out.println("1's represent ALIVE CELLS");
		System.out.println("0's represent DEAD CELLS");
		System.out.println("========================");
		
		//run a specified grid of values
		System.out.println("The Game being played for Scenario: ");
		int[][] values = {{0,1,0},{0,1,0},{0,1,0}};
		Game g = new Game(values);
		System.out.println("Input State:");
		g.print();
		g.run(iter);
		System.out.println("Game ran " + iter + " times!");
		
		//run a random grid of values
		System.out.println("The Game being played for Random Scenario: ");
		Game game = new Game(5);
		System.out.println("Input State:");
		game.print();
		game.run(iter);
		System.out.println("Game ran " + iter + " times!");
		
	}

}
