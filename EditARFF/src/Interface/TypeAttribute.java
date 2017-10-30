/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 *
 * @author liac01
 */
public enum TypeAttribute {
	NUMERIC(0, "NUMERIC"), 
	NOMINAL(1, "NOMINAL"), 
	STRING(2, "STRING"), 
	DATA(3,"DATA"), 
	RELATIONAL(4,"RELATIONAL"); 
	
	private final int code;
	private final String description;
	
	private TypeAttribute(int code, String description)
	{
		this.code = code;
		this.description = description;
	}
	
	/**
	 * @return the code
	 */
	public int getCode()
	{
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
}
