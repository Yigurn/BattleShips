import java.util.ArrayList;

public class Player {
	
	public Grid mapBoats;
	public Grid mapShots;
	final private int id;
	private int hits = 0;
	
	private ArrayList<Boat> boats = new ArrayList<Boat>();
	
	Player(int id) {
		mapBoats = new Grid(Game.GRIDSIZE);
		mapShots = new Grid(Game.GRIDSIZE);
		mapBoats.createGrid();
		mapShots.createGrid();
		
		this.id = id;
		createBoats(Game.boatSpaces);
		//createBoats(3);
	}
	
	public void createBoats(int size)
	{
		Boat patrol = new Boat(2, "Patrol Boat");
		Boat battleship = new Boat(3, "Battleship");
		Boat submarine = new Boat(3, "Submarine");
		Boat destroyer = new Boat(4, "Destroyer");
		Boat carrier = new Boat(5, "Carrier");
		
		while (size > 1)
		{
			if (size == 3)
			{
				boats.add(battleship);
				size -= 3;
			}
			if (size >= 2)
			{
				boats.add(patrol);
				size -= 2;
			}
			if (size >= 3)
			{
				boats.add(battleship);
				size -= 3;
			}
			if (size >= 3)
			{
				boats.add(submarine);
				size -= 3;
			}
			if (size >= 4)
			{
				boats.add(destroyer);
				size -= 4;
			}
			if (size >= 5)
			{
				boats.add(carrier);
				size -= 5;
			}
		}
		
		ArrayList<Boat> sortedBoats = new ArrayList<Boat>();
		for (int i = 2; i <= 5; i++)
		{
			if (i == 3)
			{
				for (Boat boat : boats)
				{
					if ( boat.getName().equals("Battleship"))
						sortedBoats.add(boat);
				}
				for (Boat boat : boats)
				{
					if ( boat.getName().equals("Submarine"))
						sortedBoats.add(boat);
				}
			}
			else
			for (Boat boat : boats)
			{
				if ( boat.getSize() == i)
				{
					sortedBoats.add(boat);
				}
			}
		}
		boats = sortedBoats;		
	}
	
	public void placeBoats(Boat boat, int[] location)
	{		
			boat.setRow(location[0]);
			boat.setColumn(location[1]);
			if (location[2] == 82)
				boat.setRight(true);
			else
				boat.setRight(false);
			mapBoats.placeBoat(boat);
	}
	
	public boolean placeShot(int[] location)
	{
		if (!mapBoats.grid[location[0]][location[1]].equals("~"))
		{
			mapShots.grid[location[0]][location[1]] = "O";
			hits++;
			return true;
		}
		else
		{
		mapShots.grid[location[0]][location[1]] = "X";
		return false;
		}
	}
	
	public boolean checkShotLocation(int row, int column)
	{
		// out of bounds
		if (row < 1 || row > Game.GRIDSIZE || column < 1 || row > Game.GRIDSIZE)
		{
			System.out.println("Error - Shot is off screen. Shoot on screen...");
			return false;
		}
		// on 'X'
		if (!mapShots.grid[row][column].equals("~"))
		{
			System.out.println("This location has been shot at, shoot somewhere else");
			return false;
		}
		return true;		
	}
	
	public boolean checkBoatLocation(Boat boat, int row, int column, int dir)
	{
		if (row > Game.GRIDSIZE || column > Game.GRIDSIZE) // Out of Bounds
		{
			System.out.println("Error - Boat goes off screen");
			return false;
		}
		if (!(dir == 82 || dir  == 68)) // Not Right or Down
		{
			System.out.println("Error - Direction needs to be either Right, \'R\' or Down, \'D\'");
			return false;
		}
		if (dir == 82)
		{
			if (column + boat.getSize() > Game.GRIDSIZE + 1)
			{
				System.out.println("Error - Boat goes off screen");
				return false;
			}
		} else
		{
			if (row + boat.getSize() > Game.GRIDSIZE + 1)
				System.out.println("Error - Boat goes off screen");
		}
		for (int i = 0; i < boat.getSize(); i++)
		{
			if (dir == 82)
				if (!mapBoats.grid[row][column + i].equals("~")) // On other Boat
				{
					System.out.println("Error - On another Boat");
					return false;					
				}
				else
					return true;
			if (dir == 68)
				if (!mapBoats.grid[row + i][column].equals("~")) // On other Boat
				{
					System.out.println("Error - On another Boat");
					return false;	
				}
		}
		return true;
	}

	public ArrayList<Boat> getBoats()
	{
		return boats;
	}
	
	public int getHits()
	{
		return hits;
	}
	
	public int getId()
	{
		return id;
	}
}
