package teleports.ToIVortex;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;

public class ToIVortex extends Quest
{
	private static final int GREEN_STONE = 4401;
	private static final int BLUE_STONE = 4402;
	private static final int RED_STONE = 4403;
	
	public ToIVortex(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30952, 30953, 30954);
		addTalkId(30952, 30953, 30954);
		addFirstTalkId(30952, 30953, 30954);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (event.equalsIgnoreCase("blue"))
		{
			if (st.hasQuestItems(BLUE_STONE))
			{
				st.takeItems(BLUE_STONE, 1);
				player.teleToLocation(114097, 19935, 935, 0);
			}
			else
				htmltext = "no-items.htm";
		}
		else if (event.equalsIgnoreCase("green"))
		{
			if (st.hasQuestItems(GREEN_STONE))
			{
				st.takeItems(GREEN_STONE, 1);
				player.teleToLocation(110930, 15963, -4378, 0);
			}
			else
				htmltext = "no-items.htm";
		}
		else if (event.equalsIgnoreCase("red"))
		{
			if (st.hasQuestItems(RED_STONE))
			{
				st.takeItems(RED_STONE, 1);
				player.teleToLocation(118558, 16659, 5987, 0);
			}
			else
				htmltext = "no-items.htm";
		}
		st.exitQuest(true);
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);
		
		return npc.getNpcId() + ".htm";
	}
	
	public static void main(String[] args)
	{
		new ToIVortex(-1, "ToIVortex", "teleports");
	}
}