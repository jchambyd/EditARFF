/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertfile;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
 
import java.io.File;
import java.io.IOException;
import weka.core.Attribute;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;
 
public class ConvertFIle {
  /**
   * takes 2 arguments:
   * - CSV input file
   * - ARFF output file
   */
	public static void main(String[] args) throws Exception 
	{
		//txtToArff("microarray.txt", false);

		mxConvertNumericToNominal("microarray.arff", 0);		
		//arff_nominaltoBinary("UG_2C_3D.arff", true);		
		//arff_removeNominal("gaussian.arff", true);
	}
  
	public static void mxConvertNumericToNominal(String filename, int attribute)
	{
		try
		{
			ArffLoader loader = new ArffLoader();
			loader.setSource(new File(filename));
			Instances dataset = loader.getDataSet();
			NumericToNominal convert = new NumericToNominal();
			String[] options = new String[2];
			options[0] = "-R";
			options[1] = "" + attribute;		
			convert.setOptions(options);
			convert.setInputFormat(dataset);
			dataset = Filter.useFilter(dataset, convert);
			
			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(dataset);
			saver.setFile(new File(filename.substring(0, filename.indexOf(".arff"))  + "_norm.arff"));
			saver.writeBatch();
		} catch (Exception exception) {
			System.out.println("Failed to convert numeric attr. to nominal. Error message: " + exception.getMessage());				
		}
	}
	
	//Load txt file and convert to arff file. Can normalize the data with "lNorm" = true
	public static void txtToArff(String filename, boolean lNorm) throws IOException, Exception
	{
		// load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(filename));
		Instances data = loader.getDataSet();

		//data.deleteAttributeAt(1);
		
		if(lNorm)
		{
			Normalize filter = new Normalize();
			filter.setInputFormat(data);
			data = Filter.useFilter(data, filter);
		}
		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(filename.substring(0, filename.indexOf(".txt"))  + "_norm.arff"));
		saver.setDestination(new File(filename.substring(0, filename.indexOf(".txt"))  + "_norm.arff"));
		saver.writeBatch();
	}
	
	//Load arff file and convert nominal attributes to binary (except the class). Can normalize the data with "lNorm" = true
	public static void arff_nominaltoBinary(String filename, boolean lNorm) throws IOException, Exception
	{
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(filename));
		Instances dataset = loader.getDataSet();
		
		StringBuilder nominalAttrRange = new StringBuilder();
		String rangeDelimiter = ",";
		
		for (int i = 0; i < dataset.numAttributes() - 1; i++)
		{
			Attribute attribute = dataset.attribute(i);
			if (attribute.isNumeric() == false) {
				if (attribute.isNominal()) {
					nominalAttrRange.append((attribute.index() + 1) + rangeDelimiter);
				} else {
					// fail check if any other attribute type than nominal or numeric is used
					return;
				}
			}
		}

		NominalToBinary nominalToBinaryFilter;
		
		// convert any nominal attributes to binary
		if (nominalAttrRange.length() > 0) {
			nominalAttrRange.deleteCharAt(nominalAttrRange.lastIndexOf(rangeDelimiter));
			try {
				nominalToBinaryFilter = new NominalToBinary();
				nominalToBinaryFilter.setAttributeIndices(nominalAttrRange.toString());
				nominalToBinaryFilter.setInputFormat(dataset);
				dataset = Filter.useFilter(dataset, nominalToBinaryFilter);
			} catch (Exception exception) {
				nominalToBinaryFilter = null;
				System.out.println("Failed to apply NominalToBinary filter to the input instances data. " +
							"Error message: " + exception.getMessage());				
			}
		}
		
		if(lNorm)
		{		
			Normalize filter2 = new Normalize();
			filter2.setInputFormat(dataset);
			dataset = Filter.useFilter(dataset, filter2);
		}	
		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File(filename.substring(0, filename.indexOf(".arff"))  + "_norm.arff"));
		saver.writeBatch();
	}  
	
	//Load arff file and remove nominal attributes. Can normalize the data with "lNorm" = true
	public static void arff_removeNominal(String filename, boolean lNorm) throws IOException, Exception
	{
		ArffLoader loader = new ArffLoader();

		loader.setSource(new File(filename));
		Instances dataset = loader.getDataSet();
		
		dataset.deleteAttributeType(1);//Numeric 0, nominal 1, STRING = 2, DATE = 3, RELATIONAL = 4

		if(lNorm)
		{
			Normalize filter = new Normalize();
			filter.setInputFormat(dataset);
			dataset = Filter.useFilter(dataset, filter);
		}	
		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File(filename.substring(0, filename.indexOf(".arff"))  + "_norm.arff"));
		saver.writeBatch();
	}
}
