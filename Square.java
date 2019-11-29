import java.util.ArrayList;

/**
 * Class that implements a square in the grid.
 * @author hmbro
 *
 */
public class Square {

	int value;
	int x;
	int y;
	
	public Square(int x, int y, int value) {
		this.value = value;
		this.x = x;
		this.y = y;
	}

	public void printCoord() {
		System.out.print("(" + this.x + "," + this.y + ")");
	}
	
	public String toString() {
		return "" + this.value;
	}
	
}
