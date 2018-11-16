import java.util.ArrayList;
import java.util.Scanner;

public class Game
{

	public final static int GRIDSIZE = 3;
	final static int PLAYERS = 3;
	boolean hitNext = false;
	static Scanner scan;

	//static int boatSpaces = 3;
	static int boatSpaces = (int) Math.ceil(GRIDSIZE * GRIDSIZE / 5 * 1.1);
	public ArrayList<Player> players = new ArrayList<Player>();

	public static void main(String[] args)
	{

		Game game = new Game();
		game.init();
		System.out.println(game.players.size());
		game.play();

		scan.close();
	}

	public int[] getShotLocation(Player player)
	{
		int column = 0;
		int row = 0;
		boolean validMove = false;
		do
		{
			System.out.println("Enter a co-ordinate:");
			String input = scan();

			try
			{
				row = Integer.parseInt(input.substring(1, input.length()));
				column = (int) (input.toUpperCase().charAt(0)) - 64;
				validMove = player.checkShotLocation(row, column);
			} catch (Exception e)
			{
				System.out.println("Error in input format. Please use e.g.\'A1\'");
				continue;
			}
		} while (!validMove);
		int[] coord = new int[2];
		coord[0] = row;
		coord[1] = column;

		return coord;
	}

	public int[] getBoatLocation(int player, Boat boat)
	{
		int column = 0;
		int row = 0;
		int dir = 0;
		boolean validMove = false;
		System.out.println("Placing " + boat.getName() + " (" + boat.getSize() + ")");
		while (!validMove)
		{
			System.out.print("Enter a co-ordinate and Direction: ");
			String input = scan();
			try
			{
				row = Integer.parseInt(input.substring(1, input.length() - 1));
				column = (int) (input.toUpperCase().charAt(0)) - 64;
				dir = (int) (input.toUpperCase().charAt(input.length() - 1));
				validMove = players.get(player).checkBoatLocation(boat, row, column, dir);
			} catch (Exception e)
			{
				System.out.println("Error in input format. Please use e.g.\'A1R\'");
				continue;
			}
		}
		int[] coord = new int[3];
		coord[0] = row;
		coord[1] = column;
		coord[2] = dir;

		return coord;
	}

	public void init()
	{
		System.out.println("Welcome to Battleships");
		System.out.println();

		for (int i = 0; i < PLAYERS; i++)
		{
			players.add(new Player(i + 1));
		}
		System.out.printf("Game is set to %d players, on a grid size of %d, with %d boats.\n", PLAYERS, GRIDSIZE,
				players.get(0).getBoats().size());
		System.out.println();
		for (int player = 0; player < PLAYERS; player++)
		{
			System.out.println();
			border(player + 1);
			System.out.println();
			setupBoats(player);
			System.out.println("\n\n\n\n\n\n\n\n\n\n");
		}
	}

	public void setupBoats(int i)
	{
		for (Boat boat : players.get(i).getBoats())
		{
			players.get(i).mapBoats.showGrid();
			System.out.println();
			players.get(i).placeBoats(boat, getBoatLocation(i, boat));
		}
	}

	public String scan()
	{
		scan = new Scanner(System.in);
		String input = scan.next();
		return input;
	}

	public void border(int player)
	{

		for (int i = 0; i < (GRIDSIZE * 2 - 8) / 2; i++)
		{
			System.out.print("-");
		}
		System.out.print(" Player " + player + " ");

		for (int i = 0; i < (GRIDSIZE * 2 - 8) / 2; i++)
		{
			System.out.print("-");
		}
		//System.out.println("\n");
	}

	public void play()
	{
		boolean winner = false;
		while (!winner)
		{
			for (int i = 0; i < players.size(); i++)
			{
				System.out.printf("Player %d take a shot!\n", players.get(i).getId());
				boolean hit = true;
				while (hit)
				{
					//border((i + 1) % players.size());
					//players.get((i + 1) % players.size()).mapShots.showGrid();
					showAllGrids();
					int shotPlayer = 0;
					if (hitNext || players.size() == 2)
					{
						shotPlayer = (i + 1) % players.size();
					}
					else
					{
						boolean validPlayer = false;
						while (!validPlayer)
						{
							System.out.print("Attack Player: ");
							String input = scan();
							try
							{
								shotPlayer = Integer.parseInt(input) - 1;
								if (shotPlayer < players.size() && shotPlayer >= 0 && shotPlayer != i)
									validPlayer = true;
								else if (shotPlayer == i)
									System.out.println("Error - Can not shoot yourself... ");
								else
									System.out.println("Error - out of range, enter a number less than " + players.size());
							} catch (Exception e)
							{
								System.out.println("Error in input format. Please enter a number, eg \'1\'");
								continue;
							}
						}
					}
					System.out.println("Attacking player " + players.get(shotPlayer).getId());
					hit = players.get(shotPlayer).placeShot(getShotLocation(players.get(shotPlayer)));
					if (players.get(shotPlayer).getHits() == boatSpaces)
					{
						System.out.printf("Player %d's fleet has been destroyed!\n", players.get(shotPlayer).getId());
						players.remove(shotPlayer);
						//i--;
						if (players.size() == 1)
							winner = true;
					}
					if (hit)
						System.out.println("You hit a ship! take another shot.\n");
					else
						System.out.println("You missed!\n");
				}
			}
		}
		System.out.printf("Congradulations player %d, you won!", players.get(0).getId());
	}

	public void showAllGrids()
	{
		for (Player player : players)
		{
			System.out.print(" ");
			border(player.getId());
			System.out.print("     ");
		}
		System.out.println();
		for (int row = 0; row < GRIDSIZE + 1; row++)
		{
			for (Player player : players)
			{
				for (int column = 0; column < GRIDSIZE + 1; column++)
				{
					System.out.print(player.mapShots.grid[row][column] + " ");
					
				}
				System.out.print("     ");
			}
			System.out.println();
		}
	}

}
