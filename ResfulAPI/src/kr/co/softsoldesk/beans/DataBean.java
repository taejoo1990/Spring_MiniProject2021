package kr.co.softsoldesk.beans;

public class DataBean {
	
	private String data1;
	private int data2;
	private double data3;
	private boolean data4;
	

	public DataBean(String data1, int data2, double data3, boolean data4) {
		this.data1=data1;
		this.data2=data2;
		this.data3=data3;
		this.data4=data4;
	}


	public String getData1() {
		return data1;
	}


	public int getData2() {
		return data2;
	}


	public double getData3() {
		return data3;
	}


	public boolean isData4() {
		return data4;
	}
	
	

}
