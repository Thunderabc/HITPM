package hitpm_v2.ICES_beans_activityActor;

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

public class ActivityActorMining {
	public static HashMap<String, ArrayList<String>> spvmMiner(JspWriter out, XLog log)
			throws IOException, ParseException{
		HashMap<String, ArrayList<String>>  processValue = mineProcessValue(log);
		//������̼�ֵ��ϣ��
		return processValue;
		
		}

	private static StringBuffer convertValueSetToString(Set<String> valDimension) {
		StringBuffer valString = new StringBuffer();
		/*if(valDimension.size()==1) {
			for(String s:valDimension)
				valString.append(s);
		}else */if(valDimension.size()>0&&!valDimension.isEmpty())
			valString.append(valDimension.toString());
		else
			valString.append("");
		return valString;
	}
	
	private static HashMap<String, ArrayList<String>>  mineProcessValue(XLog log) throws IOException, ParseException {
		//Write w = new Write();
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<String>> tempProcessValue = new HashMap<String, ArrayList<String>>();
//		HashMap<String, ActorValue> actorValueMap;
		String activityName;
		ArrayList<String> actorName = new ArrayList<String>();
//		ActorValue tempActorValueMap;
		
		//HashMap<String,Set<String>> tmpBenifitValue = new HashMap<String, Set<String>>();//��ʱ�洢���������Ϣ
		//Set<String> tmpBenifitSet = new HashSet<String>();//��ʱ�洢��������
//		ArrayList<Double> evaluationValue = new ArrayList<Double>(5);//�洢���ۼ�ֵ����ƽ��ֵ���㣡
//		evaluationValue.add(0.0);evaluationValue.add(0.0);evaluationValue.add(0.0);evaluationValue.add(0.0);evaluationValue.add(0.0);
		
		for (XTrace trace : log) {//��valueMap��ֵ
			if (!trace.isEmpty()) {
				for (int i = 0; i < trace.size(); i++) {
					XAttributeMap xMap = trace.get(i).getAttributes();
					
					if (xMap.size() > 3) {//20211201:4�ĳ�3
						for (Entry e : xMap.entrySet()) {//attribute
//							if (!unuse.contains(e.getKey().toString())) {
							if (e.getKey().toString().equals("concept:name")||e.getKey().toString().equals("lifecycle:transition")||e.getKey().toString().equals("time:timestamp")) {}
							else{
//							actorValueMap = new HashMap();
							activityName = xMap.get("concept:name").toString();
							
							/*��ֵָ��ӳ���ʼ��*/
							if(!tempProcessValue.containsKey(activityName)) {
							tempProcessValue.put(activityName, new ArrayList<String>());
							actorName = tempProcessValue.get(activityName);
							}else {//���߻�ȡ���Ӧ�Ĳ����߼�ֵ
								actorName = tempProcessValue.get(activityName);
							}
							//��Ӳ����ߣ�����ʼ����Ӳ�����-��ֵӳ��
							if(e.getKey().toString().equals("org:resource")||e.getKey().toString().equals("org:role")) {
								//w.rwFile("e-value=="+e.getValue().toString());
								String temp = e.getValue().toString();
								if(!actorName.contains(temp))
								{
									actorName.add(temp);
								}
								
							}

						}
						}
					}
				}
			}
		}
		return tempProcessValue;
	}

		
//	String�� set����Listʵ���ַ������
	public static String aListConvert11(Set<String> set) {
		return "[" + String.join(",", set) + "]";
	}
}