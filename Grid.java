import java.util.ArrayList;

public class Grid
{
	public ArrayList<ArrayList<Boid>> grid;

	//public ArrayList<ArrayList<Boid>> spacialGrid;
	
	public int gridSize;
	public int numberOfSquares;

	public Grid()
	{
		this.gridSize = Config.gridSize;
		this.numberOfSquares = Config.numberOfSquares;

		this.grid = new ArrayList<ArrayList<Boid>>(numberOfSquares * numberOfSquares);
		
		for(int index = 0; index < numberOfSquares * numberOfSquares; index++)
		{
			this.grid.add(new ArrayList<Boid>(20));
		}
	}

	public void placeInGrid(Boid boid)
	{
		int x = (int)boid.position.x / Config.gridSize;
		int y = (int)boid.position.y / Config.gridSize;

		this.grid.get(getIndex(x, y)).add(boid);			
	}

	public int getIndex(int row, int col)
	{
		if(row < 0)
		{
			row = numberOfSquares - 1;
		}
		else if(row >= this.numberOfSquares)
		{
			row = 0;
		}

		if(col < 0)
		{
			col = numberOfSquares - 1;
		}
		else if(col >= this.numberOfSquares)
		{
			col = 0;
		}

		if(row * numberOfSquares + col > 255)
		{
			System.out.println("Index is off. Row: " + row + ", Col: " + col);
		}

		return row * numberOfSquares + col;
	}

	public void clearGrid()
	{
		for(int index = 0; index < this.numberOfSquares * this.numberOfSquares; index++)
		{
			grid.get(index).clear();
		}
	}
}
