package ai.group;

import ai.AbstractNpcAI;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.util.Rnd;

public class FrenzyOnAttack extends AbstractNpcAI
{
	private static final L2Skill ULTIMATE_BUFF = SkillTable.getInstance().getInfo(4318, 1);
	
	private static final String[] ORCS_WORDS =
	{
		"Dear ultimate power!!!",
		"The battle has just begun!",
		"I never thought I'd use this against a novice!",
		"You won't take me down easily."
	};
	
	public FrenzyOnAttack(String name, String descr)
	{
		super(name, descr);
		addAttackId(20270, 20495, 20588, 20778, 21116);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		// The only requirements are HPs < 25% and not already under the buff. It's not 100% aswell.
		if (npc.getCurrentHp() / npc.getMaxHp() < 0.25 && npc.getFirstEffect(ULTIMATE_BUFF) == null && Rnd.get(10) == 0)
		{
			npc.broadcastNpcSay(ORCS_WORDS[Rnd.get(ORCS_WORDS.length)]);
			npc.setTarget(npc);
			npc.doCast(ULTIMATE_BUFF);
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new FrenzyOnAttack(FrenzyOnAttack.class.getSimpleName(), "ai/group");
	}
}