/**
 * 
 */
package com.geocent.teamdb.entity;

/**
 * @author rnolen
 *
 */
public abstract class AbstractEntity {
	
	public static boolean isId( Integer id ) {
		return( id != null && id > 0);
	}

	public boolean hasId() {
		return isId( getId() );
	}
	
	public abstract Integer getId();
}
