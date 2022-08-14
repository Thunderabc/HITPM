package hitpm_v2.ICES_beans_processLog;

import java.util.Date;
import java.text.SimpleDateFormat; 

public class ICES_beans_logInfo {
	private int log;
	private int traceNum;
	private int eventNum;
	private int eventKind;
	private int actor;
	private Date startTime;
	private Date endTime;

	
	
	
	public int getLog() {
		return log;
	}
	public void setLog(int log) {
		this.log = log;
	}
	public int getTraceNum() {
		return traceNum;
	}
	public void setTraceNum(int traceNum) {
		this.traceNum = traceNum;
	}
	public int getEventNum() {
		return eventNum;
	}
	public void setEventNum(int eventNum) {
		this.eventNum = eventNum;
	}
	public int getEventKind() {
		return eventKind;
	}
	public void setEventKind(int eventKind) {
		this.eventKind = eventKind;
	}
	
	public int getActor() {
		return actor;
	}
	public void setActor(int actor) {
		this.actor = actor;
	}
	public Date getStartTime() {
		return startTime;
	}
	public String getStartTimeStr() {
		String time;
	    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    time=formatter.format(startTime); 
		return time;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getEndTimeStr() {
		String time;
	    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    time=formatter.format(endTime); 
		return time;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	
	
}
