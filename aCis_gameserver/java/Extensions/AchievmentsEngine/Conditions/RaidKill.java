package Extensions.AchievmentsEngine.Conditions;

import Extensions.AchievmentsEngine.Base.Condition;

import java.util.Map;

import net.sf.l2j.gameserver.instancemanager.RaidBossPointsManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class RaidKill extends Condition
{
	public RaidKill(Object value)
	{
		super(value);
		setName("Raid Kill");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		int val = Integer.parseInt(getValue().toString());
		Map<Integer, Integer> list = RaidBossPointsManager.getList(player);
		if (list != null)
		{
			for (int bid : list.keySet())
			{
				if (bid == val)
				{
					if (RaidBossPointsManager.getList(player).get(bid) > 0)
						return true;
				}
			}
		}
		return false;
	}
}