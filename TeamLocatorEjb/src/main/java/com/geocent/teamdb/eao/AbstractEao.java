/**
 * 
 */
package com.geocent.teamdb.eao;

import com.geocent.teamdb.entity.AbstractEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.geocent.teamlocator.exception.EntityNotFoundException;
import javax.ejb.EJB;

/**
 * @author rnolen
 *
 */
public abstract class AbstractEao implements Eao
{
    @PersistenceContext
    protected EntityManager em;

    @EJB
    protected Converter converter;
    
    /* (non-Javadoc)
     * @see com.geocent.teamdb.eao.Eao#get(java.lang.Class, java.lang.Integer)
     */
    public <T extends AbstractEntity> T get( Class<T> clazz, Integer id ) throws EntityNotFoundException {
        T entity = em.find( clazz, id );
        if( entity == null ) {
            throw new EntityNotFoundException( clazz, id );
        }
        return entity;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamdb.eao.Eao#persist(com.geocent.util.jpa.Entity)
     */
    public <T extends AbstractEntity> T persist( T entity ) {
        if( entity.hasId() ) {
            em.merge( entity );
        } else {
            em.persist( entity );
            if( entity.getId() == null ) {
                em.flush();
            }
        }
        return entity;
    }
    
    /* (non-Javadoc)
     * @see com.geocent.teamdb.eao.Eao#delete(com.geocent.util.jpa.Entity)
     */
    public <T extends AbstractEntity> void delete( T entity ) throws EntityNotFoundException {
        // Need to re-retrieve the entity we're going to delete so that it's 'managed'
        if( entity.hasId() ) {
            AbstractEntity work = get( entity.getClass(), entity.getId() );
            if( work != null ) {
                em.remove( work );
            }
        } else { 
            throw new EntityNotFoundException( entity.getClass(), entity.getId() );
        }
    }
    

    
    /* (non-Javadoc)
     * @see com.geocent.teamdb.eao.Eao#flush()
     */
    public void flush() {
        em.flush();
    }

}
