package com.data;

public class Weight {
	
	private int id;             
	private double weight;
	
	public Weight(int id, double weight) {
		super();
		this.id = id;
		this.weight = weight;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
