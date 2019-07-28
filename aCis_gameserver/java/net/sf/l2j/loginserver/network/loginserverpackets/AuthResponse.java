package net.sf.l2j.loginserver.network.loginserverpackets;

import net.sf.l2j.loginserver.GameServerTable;
import net.sf.l2j.loginserver.network.serverpackets.ServerBasePacket;

/**
 * @author -Wooden-
 */
public class AuthResponse extends ServerBasePacket
{
	public AuthResponse(int serverId)
	{
		writeC(0x02);
		writeC(serverId);
		writeS(GameServerTable.getInstance().getServerNameById(serverId));
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}