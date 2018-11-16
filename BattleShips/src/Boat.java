public class Boat {
	
	private int size;
	private String name;
	private int row;
	private int column;
	private boolean right;

	Boat(int size, String name)
	{
		this.size = size;
		this.name = name;
	}

	public int getSize()
	{
		return size;
	}

	public int getRow()
	{
		return row;
	}
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getColumn()
	{
		return column;
	}
	public void setColumn(int column)
	{
		this.column = column;
	}

	public boolean getRight()
	{
		return right;
	}
	public void setRight(boolean right)
	{
		this.right = right;
	}
	
	public String getName()
	{
		return name;
	}

}
