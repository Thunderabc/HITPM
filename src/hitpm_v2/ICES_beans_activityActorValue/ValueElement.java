package hitpm_v2.ICES_beans_activityActorValue;

import java.util.HashSet;
import java.util.Set;

public class ValueElement {

	private Set<String> tsSet;
	private Set<String> exprSet;
	private Set<String> profitSet;
	
	public ValueElement(){
		this.tsSet = new HashSet<String>();
		this.exprSet = new HashSet<String>();
		this.profitSet = new HashSet<String>();
	}

	/*getter������ȡ��Ӧά�ȵļ�ֵ����*/
	public Set<String> getTs(){
		return tsSet;
	}
	
	public Set<String> getExpr(){
		return exprSet;
	}
	
	public Set<String> getProfit(){
		return profitSet;
	}
	
	/*setter�������Լ��ϵ���ʽΪÿ����ֵά��ȷ��ֵ*/
	public void setTs(Set<String> ts) {
			this.tsSet = ts;
	}
	public void setExpr(Set<String> expr) {
		this.exprSet = expr;
	}
	public void setProfit(Set<String> profit) {
		this.profitSet = profit;
	}
	
	/*adder��������ĳһ��ά����Ӽ�ֵ*/
	/*�Լ��ϵ���ʽ���*/
	public void addTs(Set<String> ts) {
		if(this.tsSet==null) {
			this.tsSet = ts;
		}else {
			this.tsSet.addAll(ts);
		}
	}
	public void addExpr(Set<String> expr) {
		if(this.exprSet==null) {
			this.exprSet = expr;
		}else {
			this.exprSet.addAll(expr);
		}
	}
	public void addProfit(Set<String> profit) {
		if(this.profitSet==null) {
			this.profitSet = profit;
		}else {
			this.profitSet.addAll(profit);
		}
	}
	/*��Ԫ�ص���ʽ���*/
	public void addProfit(String profit) {
		if(this.profitSet==null) {
			this.profitSet = new HashSet<String>();
		}
		this.profitSet.add(profit);
	}
	public void addExpr(String expr) {
		if(this.exprSet==null) {
			this.exprSet = new HashSet<String>();
		}
		this.exprSet.add(expr);
	}
	public void addTs(String ts) {
		if(this.tsSet==null) {
			this.tsSet = new HashSet<String>();
		}
		this.tsSet.add(ts);
	}
}

