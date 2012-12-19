package com.geocent.teamdb.eao;

import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.util.jpa.Entity;

public interface Eao
{
    /**
     * Get the Entity represented by the input class using the input Id as the key
     * @param clazz
     * @param id
     * @return the Entity found
     * @throws EntityNotFoundException if the entity is not found
     */
    public <T extends Entity> T get( Class<T> clazz, Integer id ) throws EntityNotFoundException;
    
    /**
     * Save the passed entity to the database. If the entity exists, then it will be updated; otherwise it will be created
     * @param entity
     * @return the updated entity
     */
    public <T extends Entity> T persist( T entity );

    /**
     * Allows the holder of an Entity to ask the EntityManager to do a flush
     */
    public void flush();
}
