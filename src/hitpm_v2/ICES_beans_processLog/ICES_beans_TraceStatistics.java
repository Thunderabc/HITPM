package hitpm_v2.ICES_beans_processLog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;

import hitpm_v2.ICES_beans_xml.XML3;

import javax.servlet.jsp.JspWriter;

public class ICES_beans_TraceStatistics {
	
	private XLog log;
		
	public void setLog(XLog log) {
		this.log = log;
	}

	public XLog getLog() {
		return this.log;
	}



	public static void printTraceTable(JspWriter out, XLog log){

		try {
			XML3 myParse=new XML3();
//			List<Map> listSta=new ArrayList<Map>();
//			List<String> list=new ArrayList<String>();
			HashMap<String, Integer> traceValueMap= new HashMap<String, Integer>();
			
			for (XTrace trace : log) {
				String trace_list=getTrace_list(trace);
//				list.add(trace_list);
				if(!traceValueMap.containsKey(trace_list)) {
					traceValueMap.put(trace_list, 1);
				}else {
					int num = traceValueMap.get(trace_list);
					num=num+1;
					traceValueMap.replace(trace_list, num);
					
				}
				
			}
			int logSize=myParse.getLogSize(log);
//			System.out.println(traceValueMap);
//			System.out.print(logSize);
			tracesprint(out,traceValueMap,logSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void tracesprint(JspWriter out,HashMap<String, Integer> traceValueMap,int logSize)throws Exception{
//		out.println("<tr>");
////        out.println("<td align=\"center\">"+ "Trace" +"</td>");
////        out.println("<td align=\"center\">"+ "Num" + "</td>");
////        out.println("<td align=\"center\">"+ "Percentage" +"</td>");
//        
//        out.println("<td align=\"center\">"+ "流程实例" +"</td>");
//        out.println("<td align=\"center\">"+ "数量" + "</td>");
//        out.println("<td align=\"center\">"+ "占流程总数的比例" +"</td>");
//        out.println("</tr>");
        
        for (String trace : traceValueMap.keySet()) {
//            System.out.println("key: " + trace + " value: " + traceValueMap.get(trace));
	        out.println("<td>"+ trace +"</td>");
	        out.println("<td align=\"center\">"+ traceValueMap.get(trace) + "</td>");
	        // 鍒涘缓涓�涓暟鍊兼牸寮忓寲瀵硅薄
	    	NumberFormat numberFormat = NumberFormat.getInstance();
	    	// 璁剧疆绮剧‘鍒板皬鏁扮偣鍚�2浣�
	    	numberFormat.setMaximumFractionDigits(2);
	    	String rate = numberFormat.format((float) traceValueMap.get(trace) / (float) logSize * 100);
//	        float rate=(float) traceValueMap.get(trace) / (float) logSize;
	       
	        out.println("<td align=\"center\">"+ rate+"%</td>");
	        out.println("</tr>");	
			
		}
	}
	
	public static String getTrace_list(XTrace trace){//寰楀埌瀛楃涓插舰寮忕殑trace娲诲姩鍚嶅簭鍒楋紝trace涓虹┖鍒欒繑鍥炲瓧绗︿覆""
		String trace_list="";
		if (!trace.isEmpty()) {
			for (int i = 0; i < trace.size(); i++) {
				XAttributeMap xMap = trace.get(i).getAttributes();
				String activityName = xMap.get("concept:name").toString();
				if(!(i==trace.size()-1)){
					trace_list=trace_list+activityName+"-->";
				}
				else
					trace_list=trace_list+activityName;
			}
		}
		 

		return trace_list;
	}

}
