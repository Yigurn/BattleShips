import java.util.Arrays;

public class Grid
{

	private static int gridsize;
	public String[][] grid;

	Grid(int gridsize)
	{
		Grid.gridsize = gridsize;
	}

	public void createGrid()
	{
		grid = new String[gridsize + 1][gridsize + 1];
		for (String[] a : grid)
		{
			Arrays.fill(a, "~");
		}
		for (int i = 0; i <= gridsize; i++)
		{
			if (i < 10)
				grid[i][0] = " " + i;
			else
				grid[i][0] = i + "";
			grid[0][i] = (char) (i + 64) + "";
		}
		grid[0][0] = "  ";
	}

	public void showGrid()
	{
		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				System.out.print(grid[row][column] + " ");
			}
			System.out.println();
		}
	}

	public void placeBoat(Boat boat)
	{
		if (boat.getRight())
			for (int i = 0; i < boat.getSize(); i++)
			{
				grid[boat.getRow()][boat.getColumn() + i] = boat.getName().substring(0, 1);
			}
		else
			for (int i = 0; i < boat.getSize(); i++)
			{
				grid[boat.getRow() + i][boat.getColumn()] = boat.getName().substring(0, 1);
			}
	}
}
