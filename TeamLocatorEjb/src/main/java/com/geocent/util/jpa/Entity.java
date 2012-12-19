/**
 * 
 */
package com.geocent.util.jpa;

/**
 * @author rnolen
 *
 */
public abstract class Entity {
	
	public static boolean isId( Integer id ) {
		return( id != null && id > 0);
	}

	public boolean hasId() {
		return isId( getId() );
	}
	
	public abstract Integer getId();
}
