package com.classifier;


public class WordDetails {

	private int negCount;
	private double negProb;
	private int posCount;
	private double posProb;

	public WordDetails() {
		// TODO Auto-generated constructor stub
	}

	public int getNegCount() {
		return negCount;
	}

	public void setNegCount(int negCount) {
		this.negCount = negCount;
	}

	public double getNegProb() {
		return negProb;
	}

	public void setNegProb(double negProb) {
		this.negProb = negProb;
	}

	public int getPosCount() {
		return posCount;
	}

	public void setPosCount(int posCount) {
		this.posCount = posCount;
	}

	public double getPosProb() {
		return posProb;
	}

	public void setPosProb(double posProb) {
		this.posProb = posProb;
	}
}
