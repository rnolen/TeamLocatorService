package com.geocent.teamdb.eao;

import com.geocent.teamdb.entity.AbstractEntity;
import com.geocent.teamlocator.exception.EntityNotFoundException;

public interface Eao
{
    /**
     * Get the Entity represented by the input class using the input Id as the key
     * @param clazz
     * @param id
     * @return the Entity found
     * @throws EntityNotFoundException if the entity is not found
     */
    public <T extends AbstractEntity> T get( Class<T> clazz, Integer id ) throws EntityNotFoundException;
    
    /**
     * Save the passed entity to the database. If the entity exists, then it will be updated; otherwise it will be created
     * @param entity
     * @return the updated entity
     */
    public <T extends AbstractEntity> T persist( T entity );

    /**
     * Delete the passed entity from the database
     * @param entity
     * @throws EntityNotFoundException
     */
    public <T extends AbstractEntity> void delete( T entity ) throws EntityNotFoundException;
    
    /**
     * Allows the holder of an Entity to ask the EntityManager to do a flush
     */
    public void flush();
}
