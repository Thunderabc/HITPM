package hitpm_v2.ICES_beans_activityActorValue;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deckfour.xes.model.XLog;

import hitpm_v2.ICES_beans_activityActorValue.ActorValue;
import hitpm_v2.ICES_beans_activityActorValue.myPV;

public class AavTable {
	List<Map> list=new ArrayList<Map>();
//	public List<Map> getData(XLog log) throws IOException, ParseException {
//		spvmMiner(log);
//		return list;
//	}
	public List<Map> getData() {
		
		return list;
	}
	public void setList(List<Map> list)
	{
		this.list=list;
	}
	public List<Map> pvMiner(XLog log)
			throws IOException, ParseException{
		HashMap<String, HashMap<String,ActorValue>>  processValue = myPV.mineProcessValue(log);
		//������̼�ֵ��ϣ��
		
		for(String activity: processValue.keySet()) {

//			/* System. */out.print(activity+"= { ");	//0714
			
			int i = 0;//0714
			Map<String, String> dataMap=new HashMap<String, String>();	        
			dataMap.put("activity", activity);
			list.add(dataMap);
			
			for(String actor: processValue.get(activity).keySet()) {


//				out.print(actor+"= { ");
				StringBuffer inValueBuffer1=myPV.convertValueSetToString(processValue.get(activity).get(actor).inValue.getTs());
				StringBuffer inValueBuffer2	=myPV.convertValueSetToString(processValue.get(activity).get(actor).inValue.getExpr());
				StringBuffer inValueBuffer3		=myPV.convertValueSetToString(processValue.get(activity).get(actor).inValue.getProfit());
				String inValue1 = new String(inValueBuffer1);
				String inValue2= new String(inValueBuffer2);
				String inValue3 = new String(inValueBuffer3);
				
				StringBuffer outValueBuffer1=myPV.convertValueSetToString(processValue.get(activity).get(actor).outValue.getTs());
				StringBuffer outValueBuffer2		=myPV.convertValueSetToString(processValue.get(activity).get(actor).outValue.getExpr());
				StringBuffer outValueBuffer3		=myPV.convertValueSetToString(processValue.get(activity).get(actor).outValue.getProfit());
				String outValue1 = new String(outValueBuffer1);
				String outValue2= new String(outValueBuffer2);
				String outValue3 = new String(outValueBuffer3);

				i++;//0714
				
				
				
				Map<String, String> dataMap1=new HashMap<String, String>();	        
				
		        dataMap1.put("actor", actor);
		        dataMap1.put("invalue", inValue1+","+inValue2+","+inValue3);
		        dataMap1.put("outvalue", outValue1+","+outValue2+","+outValue3);
		        list.add(dataMap1);
			
			}
			//System.out.println("test0713--1");
//			out.println("}");//0714
		}
		//���-������-��ֵ ���ݲ�ͬά�������
		return list;
		
		
		
		}
	
	public int getActorNum(int j){
		Map dataMap = list.get(j);
		if (!dataMap.containsKey("activity")){
			System.out.print("���Ϊj��map�����������0�����飡");
			return 0;
		}
		String activity;
    	activity = dataMap.get("activity").toString();
    	int actorNum=0;
    	for(int i = j+1; i < list.size(); i++){
    		dataMap= list.get(i);
    		if (!dataMap.containsKey("activity")){
    			actorNum++;
//    			System.out.print(actorNum);
    		}
    		else
    			break;
    	}
        
		return actorNum;
		
	}
	
	
}