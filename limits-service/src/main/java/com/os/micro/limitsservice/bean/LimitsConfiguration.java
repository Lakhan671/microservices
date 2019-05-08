package com.os.micro.limitsservice.bean;

public class LimitsConfiguration {
private int maxlimum;
private int minimum;
public LimitsConfiguration(int maxlimum, int minimum) {
	super();
	this.maxlimum = maxlimum;
	this.minimum = minimum;
}
public LimitsConfiguration() {

}
public int getMaxlimum() {
	return maxlimum;
}
public void setMaxlimum(int maxlimum) {
	this.maxlimum = maxlimum;
}
public int getMinimum() {
	return minimum;
}
public void setMinimum(int minimum) {
	this.minimum = minimum;
}

}
