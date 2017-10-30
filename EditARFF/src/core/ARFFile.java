/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 *
 * @author liac01
 */
public class ARFFile {

	private Instances dataset;
	
	public ARFFile()
	{
	}
	
	public ARFFile(String filename)
	{
		this.mxLoadARFF(filename);		
	}
	
	public ARFFile(Instances dataset)
	{
		this.dataset = dataset;
	}
	
	public void mxLoadARFF(String filename)
	{
		try
		{
			ArffLoader loader = new ArffLoader();
			loader.setSource(new File(filename));
			this.setDataset(loader.getDataSet());
		}catch(IOException exception){
			System.out.println("Failed to load ARFF. Error message: " + exception.getMessage());	
		}
	}
	
	public void mxLoadCSV(String filename)
	{
		try
		{		
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File(filename));
			this.setDataset(loader.getDataSet());
		}catch(IOException exception){
			System.out.println("Failed to load CSV. Error message: " + exception.getMessage());	
		}
	}	
	
	public void generateFile(String namefile)
	{
		try
		{
			ArffSaver saver = new ArffSaver();
			saver.setInstances(this.getDataset());
			saver.setFile(new File(namefile));
			saver.setDestination(new File(namefile));
			saver.writeBatch();
		}catch(IOException exception){
			System.out.println("Failed to generate output file. Error message: " + exception.getMessage());	
		}
	}
	
	public void mxNormalizeAttribute(int attribute)
	{
		try 
		{
			Normalize filter = new Normalize();
			filter.setIgnoreClass(true);
			filter.setInputFormat(this.getDataset());
			this.setDataset(Filter.useFilter(getDataset(), filter));
		} catch (Exception exception) {
			System.out.println("Failed to apply normatization. Error message: " + exception.getMessage());				
		}
	}
	
	public void mxNormalizeAttribute(int attribute, double min, double max)
	{
		for (int i = 0; i < this.dataset.numInstances(); i++) {
			this.dataset.get(i).setValue(attribute, (this.dataset.get(i).value(attribute) - min) / (max - min));
		}
	}
	
	// First Index = 0
	public void mxRemoveAttributes(ArrayList<Integer> lstAttributes)
	{
		for (int i = lstAttributes.size() - 1; i >= 0; i--) {
			this.getDataset().deleteAttributeAt(lstAttributes.get(i));
		}
	}
	
	// First Index = 1
	public void mxConvertNumericToNominal(int attribute)
	{
		try
		{
			NumericToNominal convert = new NumericToNominal();
			String[] options = new String[2];
			options[0] = "-R";
			options[1] = "" + attribute;		
			convert.setOptions(options);
			convert.setInputFormat(this.getDataset());
			this.dataset = Filter.useFilter(this.getDataset(), convert);
		} catch (Exception exception) {
			System.out.println("Failed to convert numeric attr. to nominal. Error message: " + exception.getMessage());				
		}
	}
	
	/**
	 * @return the dataset
	 */
	public Instances getDataset()
	{
		return dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Instances dataset)
	{
		this.dataset = dataset;
	}
}
