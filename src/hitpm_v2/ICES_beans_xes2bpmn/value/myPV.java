package hitpm_v2.ICES_beans_xes2bpmn.value;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XAttributeList;
import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.PetrinetNode;
//import org.processmining.newpackage.plugins.ActorValue;
//import org.processmining.newpackage.plugins.ValueElement;
import javax.servlet.jsp.JspWriter;

public class myPV {
	
	public HashMap<String, String> convert(XLog log) throws IOException, ParseException
	{
		HashMap<String, String> aav=new HashMap<String, String>();
		HashMap<String, HashMap<String,ActorValue>> tempProcessValue=mineProcessValue(log);
		for (String activity : tempProcessValue.keySet()) {
			
			String activityID=activity.replaceAll("\\s*", "");
			
			aav.put(activityID, getString(tempProcessValue, activity));
		}
		//System.out.println(aav);
		return aav;
	}
	
	//��ö�Ӧ����ַ����������ߡ����������ֵ��
	public String getString(HashMap<String, HashMap<String,ActorValue>> tempProcessValue,
			String activity)
	{
//		String temp;
		StringBuffer buffer = new StringBuffer("");
		HashMap<String,ActorValue> map = tempProcessValue.get(activity);
		for (String key : map.keySet()) {
			String temp;
			ActorValue actorValue = map.get(key);
			ValueElement inValue = actorValue.inValue;
			ValueElement outValue = actorValue.outValue;
			temp=key+"\n"+"-vin("+inValue.getValue()+")"+"\n"
			+"-vout("+outValue.getValue()+")"+"\n";
			buffer.append(temp);
		}
		return buffer.toString();
	}
	
	public HashMap<String, HashMap<String,ActorValue>>  mineProcessValue(XLog log) throws IOException, ParseException {
		//Write w = new Write();
		// TODO Auto-generated method stub
		HashMap<String, HashMap<String,ActorValue>> tempProcessValue = new HashMap<String, HashMap<String,ActorValue>>();
		HashMap<String, ActorValue> actorValueMap;
		String activityName, actorName;
		ActorValue tempActorValueMap;
		
		//HashMap<String,Set<String>> tmpBenifitValue = new HashMap<String, Set<String>>();//��ʱ�洢���������Ϣ
		//Set<String> tmpBenifitSet = new HashSet<String>();//��ʱ�洢��������
		ArrayList<Double> evaluationValue = new ArrayList<Double>(5);//�洢���ۼ�ֵ����ƽ��ֵ���㣡
		evaluationValue.add(0.0);evaluationValue.add(0.0);evaluationValue.add(0.0);evaluationValue.add(0.0);evaluationValue.add(0.0);
		
		for (XTrace trace : log) {//��valueMap��ֵ
			if (!trace.isEmpty()) {
				for (int i = 0; i < trace.size(); i++) {
					XAttributeMap xMap = trace.get(i).getAttributes();
					
					if (xMap.size() > 3) {//20211201:4�ĳ�3
						for (Entry e : xMap.entrySet()) {//attribute
//							if (!unuse.contains(e.getKey().toString())) {
							if (e.getKey().toString().equals("concept:name")||e.getKey().toString().equals("lifecycle:transition")||e.getKey().toString().equals("time:timestamp")) {}
							else{
							actorValueMap = new HashMap();
							activityName = xMap.get("concept:name").toString();
							
							/*��ֵָ��ӳ���ʼ��*/
							if(!tempProcessValue.containsKey(activityName)) {
								tempProcessValue.put(activityName, actorValueMap);
							}else {//���߻�ȡ���Ӧ�Ĳ����߼�ֵ
								actorValueMap = tempProcessValue.get(activityName);
							}
							//��Ӳ����ߣ�����ʼ����Ӳ�����-��ֵӳ��
							if(e.getKey().toString().equals("org:resource")||e.getKey().toString().equals("org:role")) {
								//w.rwFile("e-value=="+e.getValue().toString());
								actorName = e.getValue().toString();
								if(!actorValueMap.keySet().contains(actorName))
									actorValueMap.put(actorName, new ActorValue());
							}
							//ָ��ǰ���Ӧ�Ĳ����� ����ļ�ֵ
							if(xMap.get("org:resource")!=null) {
								tempActorValueMap = tempProcessValue.get(activityName).get(xMap.get("org:resource").toString());
							}else if((xMap.get("org:resource")==null && (xMap.get("org:role")!=null))) {
								tempActorValueMap = tempProcessValue.get(activityName).get(xMap.get("org:role").toString());
							}else {
								tempActorValueMap=new ActorValue();
								tempActorValueMap.inValue = new ValueElement();
								tempActorValueMap.outValue = new ValueElement();
							}
							if(tempActorValueMap==null) {
								tempActorValueMap=new ActorValue();
								tempActorValueMap.inValue = new ValueElement();
								tempActorValueMap.outValue = new ValueElement();
							}
							
							/*��ȡ���ԣ�������ֳ�ǰ׺�ͺ�׺*/
							// ------------------------------lyq��--------------------------:
							String atrributeName_temp = e.getKey().toString();// ����������
							//BelongTo2 b = new BelongTo2();
							//String atrributeName = b.addPrefix(atrributeName_temp);
							String atrributeName = atrributeName_temp;
							// ------------------------------lyq��--------------------------
							//String prefix = atrributeName.substring(0, atrributeName.indexOf(":"));//�������Ե�ǰ׺�������ж������ĸ�ά��
							//String suffix = atrributeName.substring(prefix.length() + 1,
							//atrributeName.length());//�������Եĺ�׺
							
							//if(atrributeName.indexOf(":")>0) {
							if(true) {
								if (atrributeName.equals("payment")) {
									
									tempActorValueMap.outValue.addProfit("payment: "+e.getValue().toString());
								}
								
								/* ���exprά�ȼ�ֵ */
								
								if (atrributeName.equals("evaluation")) {
									//w.rwFile(e.getValue().toString());//����Ƕ������ʶ��
									
									XAttributeList attriList = (XAttributeList) e.getValue();
									int tempCount = 0;
									Double numbersCount = evaluationValue.get(4);//�ҵ������ж��ٸ����ۼ�ֵ�Ĵ洢ֵ
									for(XAttribute serVal:attriList.getCollection()) {
										//���ǵ�ַ������
										Double tmpStore = evaluationValue.get(tempCount);
										//w.rwFile(" content: "+serVal.toString()+" ; Class: "+serVal.toString().getClass());
										Double tmpValue = Double.parseDouble(serVal.toString());
										tmpStore += tmpValue;
										evaluationValue.set(tempCount, tmpStore);
										tempCount++;
									}
									numbersCount++;
									evaluationValue.set(4, numbersCount);
								}
								
							}
							if (atrributeName.equals("productState")) {
								tempActorValueMap.outValue.addExpr(e.getValue().toString());//��Ʒ״̬Ӧ��������ۣ�
							}else if (atrributeName.equals("knowledgeShare")) {
								tempActorValueMap.inValue.addExpr(e.getValue().toString());
							}else if (atrributeName.equals("price:type")) {
								tempActorValueMap.outValue.addProfit(e.getValue().toString());
							}
							else if (atrributeName.equals("product:warranty")) {
								tempActorValueMap.outValue.addProfit(e.getValue().toString());
							}else if (atrributeName.equals("product:errorCode")) {
//								tempActorValueMap.inValue.addExpr(e.getValue().toString());
								tempActorValueMap.outValue.addExpr(e.getValue().toString());
							}else if (atrributeName.equals("part:partModel")) {
								tempActorValueMap.inValue.addExpr(e.getValue().toString());
							}else if (atrributeName.equals("netPoint")) {
								tempActorValueMap.inValue.addTs("netPoint:"+e.getValue().toString());
							}
							else if (atrributeName.equals("performance:level")) {
								tempActorValueMap.outValue.addExpr("level"+e.getValue().toString());
							}
							else if (atrributeName.equals("product:status")) {
								tempActorValueMap.outValue.addExpr(e.getValue().toString());
							}
//							else if (atrributeName.equals("payment")) {
//								 tempActorValueMap.outValue.addExpr("payment: "+e.getValue().toString()); }
							else if (atrributeName.equals("numberRestartRepairs")) {
								tempActorValueMap.inValue.addExpr("numberRestartRepairs:"+e.getValue().toString());
							}else if (atrributeName.equals("numberRepairs")) {
								tempActorValueMap.inValue.addExpr("numberRepairs:"+e.getValue().toString());
							}
							else if (atrributeName.equals("product:productType")) {
								tempActorValueMap.inValue.addExpr(e.getValue().toString());
//								tempActorValueMap.outValue.addExpr(e.getValue().toString());
							}
							else if (atrributeName.equals("part:inStock")) {
								tempActorValueMap.outValue.addExpr(e.getValue().toString());
							}
							else if (atrributeName.equals("serviceType")) {
								tempActorValueMap.outValue.addExpr(e.getValue().toString());
							}
							else if (atrributeName.equals("stockLoc")) {
								tempActorValueMap.outValue.addTs(e.getValue().toString());
							}
							else if (atrributeName.equals("customerFeature")) {
								tempActorValueMap.inValue.addExpr(e.getValue().toString());
							}
							else if (atrributeName.equals("address")) {
								tempActorValueMap.outValue.addTs(e.getValue().toString());
							}
							else if (atrributeName.equals("payment(renew)")) {
//								tempActorValueMap = tempProcessValue.get(activityName).get(xMap.get("org:role").toString());
								tempActorValueMap.outValue.addProfit(e.getValue().toString());
							}
						}
						}
					}
				}
			}
		}
//		ActorValue tempActorValueMap1;
//		HashMap<String, HashMap<String, List<Long>>> timeMapWithActor=findtime(log,tempProcessValue);		
//		
//		
//		for (String activity:tempProcessValue.keySet()) {
//			String tt = activity.toString();
//			for (String name:tempProcessValue.get(activity).keySet()) {
//				String tName = name + "," + tt;
//				if (!timeMapWithActor.get(tt).get(name).isEmpty()) {
//					long temp1 = 0L, size = 0;
//					List<Long> timeList = timeMapWithActor.get(tt).get(name);
//					;
//					size = timeList.size();
//					for (Long l : timeList) {
//						temp1 += l;
//					}
//					double avg = temp1 / size;
//					String tValue = "AvgTime:" + millisToStringShort(avg);// ���ʱ��
//					tempActorValueMap1 = tempProcessValue.get(activity).get(name);
//					tempActorValueMap1.inValue.addTs("AvgTime: "+millisToStringShort(avg));
//
//				}
//			}
//		}
		
		
		//w.rwFile("evaluation List0716---:"+evaluationValue);
/*		for(int tmpi=0;tmpi<4;tmpi++) {
			//evaluationValue.set(tmpi, (Double.parseDouble(new DecimalFormat("#.##").format(evaluationValue.get(tmpi)/evaluationValue.get(3)))));
			tempProcessValue.get("��������").get("Customer").inValue.addExpr(new DecimalFormat("#.##").format(evaluationValue.get(tmpi)/evaluationValue.get(4)));//����
			//w.rwFile("0716test evaluation---processValue: "+evaluationValue.get(tmpi)+"--"+evaluationValue.get(3));
			//w.rwFile("0716test evaluationAvg---processValue: "+new DecimalFormat("#.##").format(evaluationValue.get(tmpi)/evaluationValue.get(3)));
			
		}*/
		//w.rwFile("0716test evalList---:"+tempProcessValue.get("Service Evaluation"));
		//tempProcessValue.get("Service Evaluation").get("Customer").inValue.addExpr(Double.parseDouble(new DecimalFormat("#.##").format(evaluationValue.get(tmpi)/evaluationValue.get(3))));
		
		
		return tempProcessValue;
	}

	
	/* �،��ĺ�actor�ĕr�g�yӋtimeMap */
	static HashMap<String, HashMap<String, List<Long>>> findtime(XLog log, Petrinet petrinet, List<String> actor)
			throws ParseException {
		HashMap<String, HashMap<String, List<Long>>> timeMapWithActor = new HashMap<String, HashMap<String, List<Long>>>();
		HashMap<String, HashMap<String, String>> tempState = new HashMap<String, HashMap<String, String>>();// ����������timemapʱ��ʱ��¼��ǰ�¼��ǿ�ʼ���ǽ���
		HashMap<String, HashMap<String, Long>> temp = new HashMap<String, HashMap<String, Long>>();// ����������timemapʱ��ʱ��¼��ǰ�¼���ʱ���
		String time = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (PetrinetNode petrinetNode : petrinet.getTransitions()) {// ��ʼ��timeMap
			HashMap<String, List<Long>> actorlisttemp = new HashMap<String, List<Long>>();
			for (String actorlist : actor) {
				actorlisttemp.put(actorlist, new ArrayList<>());
			}
			timeMapWithActor.put(petrinetNode.toString(), actorlisttemp);
		}
		for (XTrace trace : log) {// ��valueMap��ֵresource//trace  tkx:�������������¼��������߶����ȥ��
			if (!trace.isEmpty()) {
				for (int i = 0; i < trace.size(); i++) {//event
					XAttributeMap xMap = trace.get(i).getAttributes();//xMap��һ��event
					String key = xMap.get("concept:name").toString();// ����Ǩ����Ϊkey
					// zy�޸ĺ�timeMap��actor
					time = xMap.get("time:timestamp").toString().replace("T", " ");// zy����־��ʱ�����tȥ��
					HashMap<String, List<Long>> tempList = timeMapWithActor.get(key);// zy��������ָ��map�еĵ�ǰ��Ǩ�µ�actor��map
					if (xMap.containsKey("org:resource")) {//�������ߵ��¼�
						String actorKey = xMap.get("org:resource").toString();//�������
						List<Long> timeList = tempList.get(actorKey);
						if (xMap.get("lifecycle:transition") != null) {
							if (!temp.containsKey(key)) {//û������¼�
								HashMap<String, Long> tempMap = new HashMap<String, Long>();
								tempMap.put(actorKey, format.parse(time).getTime());
								temp.put(key, tempMap);//�¼���+��������+ʱ�䣩  ��һ���¼����
								HashMap<String, String> tempMap3 = new HashMap<String, String>();
								tempMap3.put(actorKey, xMap.get("lifecycle:transition").toString());
								tempState.put(key, tempMap3);//�¼���+start/complete
							} else if (!temp.get(key).containsKey(actorKey)) {//���¼����¼���û���������
								HashMap<String, Long> tempMap2 = new HashMap<String, Long>();
								tempMap2.put(actorKey, format.parse(time).getTime());
								temp.put(key, tempMap2);
								HashMap<String, String> tempMap3 = new HashMap<String, String>();
								tempMap3.put(actorKey, xMap.get("lifecycle:transition").toString());
								tempState.put(key, tempMap3);
							} else if (!tempState.get(key).get(actorKey).equals("complete")) {//start
								timeList.add(format.parse(time).getTime() - temp.get(key).get(actorKey));
								temp.remove(key);
							}
						}
					}
				}
			}
			temp.clear();
		}
		for (XTrace trace : log) {// ��valueMap��ֵrole
			if (!trace.isEmpty()) {
				for (int i = 0; i < trace.size(); i++) {
					XAttributeMap xMap = trace.get(i).getAttributes();
					String key = xMap.get("concept:name").toString();// ����Ǩ����Ϊkey
					// zy�޸ĺ�timeMap��actor
					time = xMap.get("time:timestamp").toString().replace("T", " ");// zy����־��ʱ�����tȥ��
					HashMap<String, List<Long>> tempList = timeMapWithActor.get(key);// zy��������ָ��map�еĵ�ǰ��Ǩ�µ�actor��map
					if (xMap.containsKey("org:role")) {
						String actorKey = xMap.get("org:role").toString();
						List<Long> timeList = tempList.get(actorKey);
						if (xMap.get("lifecycle:transition") != null) {
							if (!temp.containsKey(key)) {
								HashMap<String, Long> tempMap = new HashMap<String, Long>();
								tempMap.put(actorKey, format.parse(time).getTime());
								temp.put(key, tempMap);
								HashMap<String, String> tempMap3 = new HashMap<String, String>();
								tempMap3.put(actorKey, xMap.get("lifecycle:transition").toString());
								tempState.put(key, tempMap3);
							} else if (!temp.get(key).containsKey(actorKey)) {
								HashMap<String, Long> tempMap2 = new HashMap<String, Long>();
								tempMap2.put(actorKey, format.parse(time).getTime());
								temp.put(key, tempMap2);
								HashMap<String, String> tempMap3 = new HashMap<String, String>();
								tempMap3.put(actorKey, xMap.get("lifecycle:transition").toString());
								tempState.put(key, tempMap3);
							} else if (!tempState.get(key).get(actorKey).equals("complete")) {
								timeList.add(format.parse(time).getTime() - temp.get(key).get(actorKey));
								temp.remove(key);
							}
						}
					}
				}
			}
			temp.clear();
		}
		/*
		 * System.out.println(
		 * "___________________________timeMap���Էָ���_____________________________________________"
		 * );//zy_timeMap���Էָ��� for (PetrinetNode petrinetNode :
		 * petrinet.getTransitions()) { for (HashMap.Entry<String, List<Long>>
		 * tempActormap : timeMapWithActor.get(petrinetNode.toString()) .entrySet()) {
		 * 
		 * //System.out.println(petrinetNode.toString()); if
		 * (!tempActormap.getValue().isEmpty()) System.out.println("����timeMap����" +
		 * petrinetNode.toString() + "-" + tempActormap.getKey() +
		 * tempActormap.getValue()); } }
		 */
		return timeMapWithActor;
	}
	public StringBuffer millisToStringShort(double avg) {
		StringBuffer sb = new StringBuffer();
		long millis = 1;
		long seconds = 1000 * millis;
		long minutes = 60 * seconds;
		long hours = 60 * minutes;
		long days = 24 * hours;
		if (avg / days >= 1)
			sb.append((int) (avg / days) + " d ");
		if (avg % days / hours >= 1)
			sb.append((int) (avg % days / hours) + " h ");
		if (avg % days % hours / minutes >= 1)
			sb.append((int) (avg % days % hours / minutes) + " m ");
		if (avg % days % hours % minutes / seconds >= 1)
			sb.append((int) (avg % days % hours % minutes / seconds) + " s ");
		if (avg % days % hours % minutes % seconds / millis >= 1)
			sb.append((int) (avg % days % hours % minutes % seconds / millis) + " ms ");
		return sb;
	}
		
//	String�� set����Listʵ���ַ������
	public String aListConvert11(Set<String> set) {
		return "[" + String.join(",", set) + "]";
	}
}