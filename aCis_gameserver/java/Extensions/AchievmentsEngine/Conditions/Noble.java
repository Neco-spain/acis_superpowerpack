package Extensions.AchievmentsEngine.Conditions;

import Extensions.AchievmentsEngine.Base.Condition;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Noble extends Condition
{
	public Noble(Object value)
	{
		super(value);
		setName("Noble");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		if (player.isNoble())
			return true;
		
		return false;
	}
}