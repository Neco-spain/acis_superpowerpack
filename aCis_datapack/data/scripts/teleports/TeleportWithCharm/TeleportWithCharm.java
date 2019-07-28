package teleports.TeleportWithCharm;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;

public class TeleportWithCharm extends Quest
{
	private final static int WHIRPY = 30540;
	private final static int TAMIL = 30576;
	
	private final static int ORC_GATEKEEPER_CHARM = 1658;
	private final static int DWARF_GATEKEEPER_TOKEN = 1659;
	
	public TeleportWithCharm(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(WHIRPY, TAMIL);
		addTalkId(WHIRPY, TAMIL);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = "";
		
		int npcId = npc.getNpcId();
		if (npcId == WHIRPY)
		{
			if (st.getQuestItemsCount(DWARF_GATEKEEPER_TOKEN) >= 1)
			{
				st.takeItems(DWARF_GATEKEEPER_TOKEN, 1);
				player.teleToLocation(-80826, 149775, -3043, 0);
			}
			else
				htmltext = "30540-01.htm";
		}
		else if (npcId == TAMIL)
		{
			if (st.getQuestItemsCount(ORC_GATEKEEPER_CHARM) >= 1)
			{
				st.takeItems(ORC_GATEKEEPER_CHARM, 1);
				player.teleToLocation(-80826, 149775, -3043, 0);
			}
			else
				htmltext = "30576-01.htm";
		}
		
		st.exitQuest(true);
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new TeleportWithCharm(-1, "TeleportWithCharm", "teleports");
	}
}