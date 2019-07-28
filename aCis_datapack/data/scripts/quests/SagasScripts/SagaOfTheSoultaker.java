package quests.SagasScripts;

/**
 * @author Emperorc
 */
public class SagaOfTheSoultaker extends SagasSuperClass
{
	public static String qn1 = "Q094_SagaOfTheSoultaker";
	public static int qnu = 94;
	public static String qna = "Saga of the Soultaker";
	
	public SagaOfTheSoultaker()
	{
		super(qnu, qn1, qna);
		
		NPC = new int[]
		{
			30832,
			31623,
			31279,
			31279,
			31645,
			31646,
			31648,
			31650,
			31654,
			31655,
			31657,
			31279
		};
		
		Items = new int[]
		{
			7080,
			7533,
			7081,
			7509,
			7292,
			7323,
			7354,
			7385,
			7416,
			7447,
			7085,
			0
		};
		
		Mob = new int[]
		{
			27257,
			27243,
			27265
		};
		
		qn = qn1;
		classid = 95;
		prevclass = 0x0d;
		
		X = new int[]
		{
			191046,
			46066,
			46087
		};
		
		Y = new int[]
		{
			-40640,
			-36396,
			-36372
		};
		
		Z = new int[]
		{
			-3042,
			-1685,
			-1685
		};
		
		registerNPCs();
	}
}