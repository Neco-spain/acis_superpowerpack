package Extensions.Events.Phoenix.Engines;

public class EventTeam implements Comparable<EventTeam>
{
	private int score;
	private String name;
	private int[] nameColor;
	private int[] startPos;
	private int id;
	
	public EventTeam(int id, String name, int[] color, int[] startPos)
	{
		this.id = id;
		this.name = name;
		this.startPos = startPos;
		score = 0;
		nameColor = color;
	}
	
	@Override
	public int compareTo(EventTeam second)
	{
		if (getScore() > second.getScore())
			return 1;
		else if (getScore() < second.getScore())
			return -1;
		
		return 0;
	}
	
	public String getHexaColor()
	{
		return (nameColor[0] > 15 ? Integer.toHexString(nameColor[0]) : "0" + Integer.toHexString(nameColor[0])) + (nameColor[1] > 15 ? Integer.toHexString(nameColor[1]) : "0" + Integer.toHexString(nameColor[1])) + (nameColor[2] > 15 ? Integer.toHexString(nameColor[2]) : "0" + Integer.toHexString(nameColor[2]));
	}
	
	protected int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int[] getTeamColor()
	{
		return nameColor;
	}
	
	protected int[] getTeamPos()
	{
		return startPos;
	}
	
	public void increaseScore()
	{
		score++;
	}
	
	public void increaseScore(int ammount)
	{
		score += ammount;
	}
	
	protected void setScore(int ammount)
	{
		score = ammount;
	}
}