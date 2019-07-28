package net.sf.l2j.gameserver.network.serverpackets;

import java.util.List;

import net.sf.l2j.util.StringUtil;

public class ShowBoard extends L2GameServerPacket
{
	public static final ShowBoard STATIC_SHOWBOARD_102 = new ShowBoard(null, "102");
	public static final ShowBoard STATIC_SHOWBOARD_103 = new ShowBoard(null, "103");
	
	private final static String TOP = "bypass _bbshome";
	private final static String FAV = "bypass _bbsgetfav";
	private final static String REGION = "bypass _bbsloc";
	private final static String CLAN = "bypass _bbsclan";
	private final static String MEMO = "bypass _bbsmemo";
	private final static String MAIL = "bypass _maillist_0_1_0_";
	private final static String FRIENDS = "bypass _friendlist_0_";
	private final static String ADDFAV = "bypass bbs_add_fav";
	
	private final StringBuilder _htmlCode;
	
	public ShowBoard(String htmlCode, String id)
	{
		_htmlCode = StringUtil.startAppend(500, id, "\u0008", htmlCode);
	}
	
	public ShowBoard(List<String> arg)
	{
		_htmlCode = StringUtil.startAppend(500, "1002\u0008");
		for (String str : arg)
			StringUtil.append(_htmlCode, str, " \u0008");
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x6e);
		writeC(0x01); // 1 to show, 0 to hide
		writeS(TOP);
		writeS(FAV);
		writeS(REGION);
		writeS(CLAN);
		writeS(MEMO);
		writeS(MAIL);
		writeS(FRIENDS);
		writeS(ADDFAV);
		writeS(_htmlCode.toString());
	}
}