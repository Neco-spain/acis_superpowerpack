package net.sf.l2j.gameserver.network.clientpackets;

import java.util.List;

import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.PledgeReceiveWarList;

/**
 * Format: (ch) dd
 * @author -Wooden-
 */
public final class RequestPledgeWarList extends L2GameClientPacket
{
	private int _page;
	private int _tab;
	
	@Override
	protected void readImpl()
	{
		_page = readD();
		_tab = readD();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		final L2Clan clan = activeChar.getClan();
		if (clan == null)
			return;
		
		final List<Integer> list;
		if (_tab == 0)
			list = clan.getWarList();
		else
		{
			list = clan.getAttackerList();
			
			// The page, reaching the biggest section, should send back to 0.
			_page = Math.max(0, (_page > list.size() / 13) ? 0 : _page);
		}
		
		activeChar.sendPacket(new PledgeReceiveWarList(list, _tab, _page));
	}
}