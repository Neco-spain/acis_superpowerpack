package net.sf.l2j.gameserver.model.actor.stat;

import Extensions.Balancer.BalanceLoad;

import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.skills.Calculator;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Stats;

public class CharStat
{
	private final L2Character _activeChar;
	
	private long _exp = 0;
	private int _sp = 0;
	private byte _level = 1;
	
	public CharStat(L2Character activeChar)
	{
		_activeChar = activeChar;
	}
	
	/**
	 * Calculate the new value of the state with modifiers that will be applied on the targeted L2Character.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * A L2Character owns a table of Calculators called <B>_calculators</B>. Each Calculator (a calculator per state) own a table of Func object. A Func object is a mathematic function that permit to calculate the modifier of a state (ex : REGENERATE_HP_RATE...) : <BR>
	 * <BR>
	 * FuncAtkAccuracy -> Math.sqrt(_player.getDEX())*6+_player.getLevel()<BR>
	 * <BR>
	 * When the calc method of a calculator is launched, each mathematic function is called according to its priority <B>_order</B>. Indeed, Func with lowest priority order is executed firsta and Funcs with the same order are executed in unspecified order. The result of the calculation is stored in
	 * the value property of an Env class instance.<BR>
	 * <BR>
	 * @param stat The stat to calculate the new value with modifiers
	 * @param init The initial value of the stat before applying modifiers
	 * @param target The L2Charcater whose properties will be used in the calculation (ex : CON, INT...)
	 * @param skill The L2Skill whose properties will be used in the calculation (ex : Level...)
	 * @return
	 */
	public final double calcStat(Stats stat, double init, L2Character target, L2Skill skill)
	{
		if (_activeChar == null || stat == null)
			return init;
		
		final int id = stat.ordinal();
		
		final Calculator c = _activeChar.getCalculators()[id];
		if (c == null || c.size() == 0)
			return init;
		
		// Create and init an Env object to pass parameters to the Calculator
		final Env env = new Env();
		env.setCharacter(_activeChar);
		env.setTarget(target);
		env.setSkill(skill);
		env.setValue(init);
		
		// Launch the calculation
		c.calc(env);
		
		// avoid some troubles with negative stats (some stats should never be negative)
		if (env.getValue() <= 0)
		{
			switch (stat)
			{
				case MAX_HP:
				case MAX_MP:
				case MAX_CP:
				case MAGIC_DEFENCE:
				case POWER_DEFENCE:
				case POWER_ATTACK:
				case MAGIC_ATTACK:
				case POWER_ATTACK_SPEED:
				case MAGIC_ATTACK_SPEED:
				case SHIELD_DEFENCE:
				case STAT_CON:
				case STAT_DEX:
				case STAT_INT:
				case STAT_MEN:
				case STAT_STR:
				case STAT_WIT:
					env.setValue(1);
			}
		}
		return env.getValue();
	}
	
	/**
	 * @return the STR of the L2Character (base+modifier).
	 */
	public final int getSTR()
	{
		int str = (int) calcStat(Stats.STAT_STR, _activeChar.getTemplate().getBaseSTR(), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			str += BalanceLoad.STR[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return str;
	}
	
	/**
	 * @return the DEX of the L2Character (base+modifier).
	 */
	public final int getDEX()
	{
		int DEX = (int) calcStat(Stats.STAT_DEX, _activeChar.getTemplate().getBaseDEX(), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			DEX += BalanceLoad.DEX[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return DEX;
	}
	
	/**
	 * @return the CON of the L2Character (base+modifier).
	 */
	public final int getCON()
	{
		int CON = (int) calcStat(Stats.STAT_CON, _activeChar.getTemplate().getBaseCON(), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			CON += BalanceLoad.CON[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return CON;
	}
	
	/**
	 * @return the INT of the L2Character (base+modifier).
	 */
	public int getINT()
	{
		int INT = (int) calcStat(Stats.STAT_INT, _activeChar.getTemplate().getBaseINT(), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			INT += BalanceLoad.INT[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return INT;
	}
	
	/**
	 * @return the MEN of the L2Character (base+modifier).
	 */
	public final int getMEN()
	{
		int MEN = (int) calcStat(Stats.STAT_MEN, _activeChar.getTemplate().getBaseMEN(), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			MEN += BalanceLoad.MEN[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return MEN;
	}
	
	/**
	 * @return the WIT of the L2Character (base+modifier).
	 */
	public final int getWIT()
	{
		int WIT = (int) calcStat(Stats.STAT_WIT, _activeChar.getTemplate().getBaseWIT(), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			WIT += BalanceLoad.WIT[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return WIT;
	}
	
	/**
	 * @param target
	 * @param skill
	 * @return the Critical Hit rate (base+modifier) of the L2Character.
	 */
	public int getCriticalHit(L2Character target, L2Skill skill)
	{
		int criticalHit = Math.min((int) calcStat(Stats.CRITICAL_RATE, _activeChar.getTemplate().getBaseCritRate(), target, skill), 500);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			criticalHit += BalanceLoad.Critical[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return criticalHit;
	}
	
	/**
	 * @param target
	 * @param skill
	 * @return the Magic Critical Hit rate (base+modifier) of the L2Character.
	 */
	public final int getMCriticalHit(L2Character target, L2Skill skill)
	{
		int mrate = (int) calcStat(Stats.MCRITICAL_RATE, 8, target, skill);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			mrate += BalanceLoad.MagicCritical[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return mrate;
	}
	
	/**
	 * @param target
	 * @return the Attack Evasion rate (base+modifier) of the L2Character.
	 */
	public int getEvasionRate(L2Character target)
	{
		int val = (int) calcStat(Stats.EVASION_RATE, 0, target, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.Evasion[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	/**
	 * @return the Accuracy (base+modifier) of the L2Character in function of the Weapon Expertise Penalty.
	 */
	public int getAccuracy()
	{
		int val = (int) calcStat(Stats.ACCURACY_COMBAT, 0, null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.Accuracy[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	public int getMaxHp()
	{
		double val = calcStat(Stats.MAX_HP, _activeChar.getTemplate().getBaseHpMax(_activeChar.getLevel()), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.HP[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		if (val > 0)
			return (int) val;
		return 0;
	}
	
	public int getMaxCp()
	{
		double val = calcStat(Stats.MAX_CP, _activeChar.getTemplate().getBaseCpMax(_activeChar.getLevel()), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.CP[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		if (val >= 0)
			return (int) val;
		return 0;
	}
	
	public int getMaxMp()
	{
		double val = calcStat(Stats.MAX_MP, _activeChar.getTemplate().getBaseMpMax(_activeChar.getLevel()), null, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.MP[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		if (val >= 0)
			return (int) val;
		return 0;
	}
	
	/**
	 * @param target The L2Character targeted by the skill
	 * @param skill The L2Skill used against the target
	 * @return the MAtk (base+modifier) of the L2Character for a skill used in function of abnormal effects in progress.
	 */
	public int getMAtk(L2Character target, L2Skill skill)
	{
		double attack = _activeChar.getTemplate().getBaseMAtk() * ((_activeChar.isChampion()) ? 2 : 1);
		
		// Add the power of the skill to the attack effect
		if (skill != null)
			attack += skill.getPower();
		
		// Calculate modifiers Magic Attack
		int val = (int) calcStat(Stats.MAGIC_ATTACK, attack, target, skill);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.MAtk[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	/**
	 * @return the MAtk Speed (base+modifier) of the L2Character in function of the Armour Expertise Penalty.
	 */
	public int getMAtkSpd()
	{
		int val = (int) calcStat(Stats.MAGIC_ATTACK_SPEED, 333.0 * (_activeChar.isChampion() ? 2 : 1), null, null);
		
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.MAtkSpd[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	/**
	 * @param target The L2Character targeted by the skill
	 * @param skill The L2Skill used against the target
	 * @return the MDef (base+modifier) of the L2Character against a skill in function of abnormal effects in progress.
	 */
	public int getMDef(L2Character target, L2Skill skill)
	{
		// Calculate modifiers Magic Attack
		int val = (int) calcStat(Stats.MAGIC_DEFENCE, _activeChar.getTemplate().getBaseMDef() * ((_activeChar.isRaid()) ? 2 : 1), target, skill);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.MDef[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	/**
	 * @param target
	 * @return the PAtk (base+modifier) of the L2Character.
	 */
	public int getPAtk(L2Character target)
	{
		int val = (int) calcStat(Stats.POWER_ATTACK, _activeChar.getTemplate().getBasePAtk(), target, null);
		
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
		//{
		//	val = val * 3;
			val += BalanceLoad.PAtk[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		//}
		return val;
	}
	
	/**
	 * @return the PAtk Speed (base+modifier) of the L2Character in function of the Armour Expertise Penalty.
	 */
	public int getPAtkSpd()
	{
		int val = (int) calcStat(Stats.POWER_ATTACK_SPEED, _activeChar.getTemplate().getBasePAtkSpd() * (_activeChar.isChampion() ? 2 : 1), null, null);
		
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.PAtkSpd[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	/**
	 * @param target
	 * @return the PDef (base+modifier) of the L2Character.
	 */
	public int getPDef(L2Character target)
	{
		int val = (int) calcStat(Stats.POWER_DEFENCE, _activeChar.getTemplate().getBasePDef() * (_activeChar.isRaid() ? 2 : 1), target, null);
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			val += BalanceLoad.PDef[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return val;
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against animals.
	 */
	public final double getPAtkAnimals(L2Character target)
	{
		return calcStat(Stats.PATK_ANIMALS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against dragons.
	 */
	public final double getPAtkDragons(L2Character target)
	{
		return calcStat(Stats.PATK_DRAGONS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against insects.
	 */
	public final double getPAtkInsects(L2Character target)
	{
		return calcStat(Stats.PATK_INSECTS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against monsters.
	 */
	public final double getPAtkMonsters(L2Character target)
	{
		return calcStat(Stats.PATK_MONSTERS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against plants.
	 */
	public final double getPAtkPlants(L2Character target)
	{
		return calcStat(Stats.PATK_PLANTS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against giants.
	 */
	public final double getPAtkGiants(L2Character target)
	{
		return calcStat(Stats.PATK_GIANTS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PAtk Modifier against magic creatures
	 */
	public final double getPAtkMagicCreatures(L2Character target)
	{
		return calcStat(Stats.PATK_MCREATURES, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against animals.
	 */
	public final double getPDefAnimals(L2Character target)
	{
		return calcStat(Stats.PDEF_ANIMALS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against dragons.
	 */
	public final double getPDefDragons(L2Character target)
	{
		return calcStat(Stats.PDEF_DRAGONS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against insects.
	 */
	public final double getPDefInsects(L2Character target)
	{
		return calcStat(Stats.PDEF_INSECTS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against monsters.
	 */
	public final double getPDefMonsters(L2Character target)
	{
		return calcStat(Stats.PDEF_MONSTERS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against plants.
	 */
	public final double getPDefPlants(L2Character target)
	{
		return calcStat(Stats.PDEF_PLANTS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against giants.
	 */
	public final double getPDefGiants(L2Character target)
	{
		return calcStat(Stats.PDEF_GIANTS, 1, target, null);
	}
	
	/**
	 * @param target
	 * @return the PDef Modifier against giants.
	 */
	public final double getPDefMagicCreatures(L2Character target)
	{
		return calcStat(Stats.PDEF_MCREATURES, 1, target, null);
	}
	
	/**
	 * @return the Physical Attack range (base+modifier) of the L2Character.
	 */
	public int getPhysicalAttackRange()
	{
		return _activeChar.getTemplate().getBaseAtkRange();
	}
	
	/**
	 * @param target
	 * @return the weapon reuse modifier
	 */
	public final double getWeaponReuseModifier(L2Character target)
	{
		return calcStat(Stats.ATK_REUSE, 1, target, null);
	}
	
	/**
	 * @return the WalkSpeed (base+modifier) of the L2Character.
	 */
	public int getWalkSpeed()
	{
		int baseWalkSpd = _activeChar.getTemplate().getBaseWalkSpd();
		if (baseWalkSpd == 0)
			return 0;
		if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
			baseWalkSpd += BalanceLoad.Speed[((L2PcInstance) _activeChar).getClassId().getId() - 88];
		return (int) calcStat(Stats.WALK_SPEED, baseWalkSpd, null, null);
	}
	
	/**
	 * @return the RunSpeed (base+modifier) of the L2Character in function of the Armour Expertise Penalty.
	 */
	
	public int getRunSpeed()
	{
		double val = (calcStat(Stats.RUN_SPEED, _activeChar.getTemplate().getBaseRunSpd(), null, null));
		if (_activeChar instanceof L2PcInstance)
		{
			if (_activeChar instanceof L2PcInstance && ((L2PcInstance) _activeChar).getClassId().getId() >= 88)
				val += BalanceLoad.Speed[((L2PcInstance) _activeChar).getClassId().getId() - 88];
			if (val > 0)
				return (int) val;
			return 0;
		}
		return (int) val;
	}
	
	/**
	 * @return the ShieldDef rate (base+modifier) of the L2Character.
	 */
	public final int getShldDef()
	{
		return (int) calcStat(Stats.SHIELD_DEFENCE, 0, null, null);
	}
	
	/**
	 * @param skill
	 * @return the mpConsume.
	 */
	public final int getMpConsume(L2Skill skill)
	{
		if (skill == null)
			return 1;
		
		double mpConsume = skill.getMpConsume();
		if (skill.isDance())
			if (_activeChar != null && _activeChar.getDanceCount() > 0)
				mpConsume += _activeChar.getDanceCount() * skill.getNextDanceMpCost();
		
		mpConsume = calcStat(Stats.MP_CONSUME, mpConsume, null, skill);
		
		if (skill.isDance())
			return (int) calcStat(Stats.DANCE_MP_CONSUME_RATE, mpConsume, null, null);
		else if (skill.isMagic())
			return (int) calcStat(Stats.MAGICAL_MP_CONSUME_RATE, mpConsume, null, null);
		else
			return (int) calcStat(Stats.PHYSICAL_MP_CONSUME_RATE, mpConsume, null, null);
	}
	
	/**
	 * @param skill
	 * @return the mpInitialConsume.
	 */
	public final int getMpInitialConsume(L2Skill skill)
	{
		if (skill == null)
			return 1;
		
		double mpConsume = calcStat(Stats.MP_CONSUME, skill.getMpInitialConsume(), null, skill);
		
		if (skill.isDance())
			return (int) calcStat(Stats.DANCE_MP_CONSUME_RATE, mpConsume, null, null);
		else if (skill.isMagic())
			return (int) calcStat(Stats.MAGICAL_MP_CONSUME_RATE, mpConsume, null, null);
		else
			return (int) calcStat(Stats.PHYSICAL_MP_CONSUME_RATE, mpConsume, null, null);
	}
	
	public int getAttackElementValue(byte attackAttribute)
	{
		switch (attackAttribute)
		{
			case 1: // wind
				return (int) calcStat(Stats.WIND_POWER, 0, null, null);
			case 2: // fire
				return (int) calcStat(Stats.FIRE_POWER, 0, null, null);
			case 3: // water
				return (int) calcStat(Stats.WATER_POWER, 0, null, null);
			case 4: // earth
				return (int) calcStat(Stats.EARTH_POWER, 0, null, null);
			case 5: // holy
				return (int) calcStat(Stats.HOLY_POWER, 0, null, null);
			case 6: // dark
				return (int) calcStat(Stats.DARK_POWER, 0, null, null);
			default:
				return 0;
		}
	}
	
	public int getDefenseElementValue(byte defenseAttribute)
	{
		switch (defenseAttribute)
		{
			case 1: // wind
				return (int) calcStat(Stats.WIND_RES, 0, null, null);
			case 2: // fire
				return (int) calcStat(Stats.FIRE_RES, 0, null, null);
			case 3: // water
				return (int) calcStat(Stats.WATER_RES, 0, null, null);
			case 4: // earth
				return (int) calcStat(Stats.EARTH_RES, 0, null, null);
			case 5: // holy
				return (int) calcStat(Stats.HOLY_RES, 0, null, null);
			case 6: // dark
				return (int) calcStat(Stats.DARK_RES, 0, null, null);
			default:
				return 0;
		}
	}
	
	public float getMovementSpeedMultiplier()
	{
		return getRunSpeed() / (float) _activeChar.getTemplate().getBaseRunSpd();
	}
	
	/**
	 * @return the Attack Speed multiplier (base+modifier) of the L2Character to get proper animations.
	 */
	public final float getAttackSpeedMultiplier()
	{
		return (float) ((1.1) * getPAtkSpd() / _activeChar.getTemplate().getBasePAtkSpd());
	}
	
	/**
	 * @return the RunSpeed (base+modifier) or WalkSpeed (base+modifier) of the L2Character in function of the movement type.
	 */
	public int getMoveSpeed()
	{
		return (_activeChar.isRunning()) ? getRunSpeed() : getWalkSpeed();
	}
	
	public long getExp()
	{
		return _exp;
	}
	
	public void setExp(long value)
	{
		_exp = value;
	}
	
	public int getSp()
	{
		return _sp;
	}
	
	public void setSp(int value)
	{
		_sp = value;
	}
	
	public byte getLevel()
	{
		return _level;
	}
	
	public void setLevel(byte value)
	{
		_level = value;
	}
	
	public L2Character getActiveChar()
	{
		return _activeChar;
	}
}