package com.javaschool.ivanov.Domain;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


/**
 * Base class for entity
 */
@MappedSuperclass
public class AbstractEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    protected AbstractEntity()
    {
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public int hashCode()
    {
        return id != null ? id : super.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AbstractEntity)) return false;

        if (!o.getClass().isAssignableFrom(getClass()) &&
                !getClass().isAssignableFrom(o.getClass()))
            return false;

        AbstractEntity entity = (AbstractEntity) o;

        return id!= null && id.equals(entity.id);

    }
}