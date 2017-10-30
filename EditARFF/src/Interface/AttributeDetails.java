/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.ArrayList;
import java.util.Enumeration;
import weka.core.Attribute;
import weka.core.Instances;

/**
 *
 * @author liac01
 */
public class AttributeDetails {

	private Instances dataset;	
	private String name;
	private int type;
	private int numInst;
	private double minValue;
	private double maxValue;
	private double mean;
	private double variance;
	private double stdDeviation;
	private ArrayList<String> nominalValues;
	
	public AttributeDetails(Instances dataset, int attribute)
	{
		this.dataset = dataset;	
		this.numInst = this.dataset.numInstances();
		this.nominalValues = new ArrayList<>();
		this.mxCalculateDetails(attribute);
	}
	
	private void mxCalculateDetails(int attribute)
	{
		Attribute attr = this.dataset.attribute(attribute);
		this.mean = this.variance = this.stdDeviation = 0;
		this.name = attr.name();
		this.type = attr.type();
		
		switch(this.type)
		{
			case Attribute.NUMERIC:
				
				this.minValue = this.maxValue = dataset.get(0).value(attribute);
				// Compute the sample mean
				for (int i = 1; i < this.numInst; i++) {
					double value = dataset.get(i).value(attribute);
					if(value < minValue)
						this.minValue = value;
					else if(value > maxValue)
						this.maxValue = value;
					this.mean += value;
				}				
				this.mean = this.mean / this.numInst;
				// Computes the sum of the squares of the differences from the mean
				for (int i = 1; i < this.numInst; i++) {
					this.variance += Math.pow(dataset.get(i).value(attribute) - this.mean, 2);
				}
				this.variance = this.variance / (this.numInst - 1);
				this.stdDeviation = Math.sqrt(this.variance);
				break;
			case Attribute.NOMINAL:				
				Enumeration e = attr.enumerateValues();
				while (e.hasMoreElements()){
					this.nominalValues.add((String)e.nextElement());
				}					
				break;
			default:
				break;
		}
	}
	
	public int getType()
	{
		return this.type;
	}
	public String getName()
	{
		return this.name;
	}
	/**
	 * @return the minValue
	 */
	public double getMinValue()
	{
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(double minValue)
	{
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public double getMaxValue()
	{
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(double maxValue)
	{
		this.maxValue = maxValue;
	}

	/**
	 * @return the mean
	 */
	public double getMean()
	{
		return mean;
	}

	/**
	 * @param mean the mean to set
	 */
	public void setMean(double mean)
	{
		this.mean = mean;
	}

	/**
	 * @return the variance
	 */
	public double getVariance()
	{
		return variance;
	}

	/**
	 * @param variance the variance to set
	 */
	public void setVariance(double variance)
	{
		this.variance = variance;
	}

	/**
	 * @return the stdDeviation
	 */
	public double getStdDeviation()
	{
		return stdDeviation;
	}

	/**
	 * @param stdDeviation the stdDeviation to set
	 */
	public void setStdDeviation(double stdDeviation)
	{
		this.stdDeviation = stdDeviation;
	}
	
	public ArrayList<String> getNominalValues()
	{
		return this.nominalValues;
	}
}
