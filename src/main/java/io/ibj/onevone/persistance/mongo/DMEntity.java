package io.ibj.onevone.persistance.mongo;

import io.ibj.onevone.persistance.DEntity;
import org.bson.types.ObjectId;

import java.util.UUID;

/**
 * Represents a Mongo database entry. Note, that this uses both the ObjectID and UUID. While a conversion can be made, both will be stored, for stability of the overlying system.
 */
public class DMEntity implements DEntity {

    UUID id;
    ObjectId objId;

    @Override
    public UUID getId() {
        return id;
    }

    public ObjectId getDbId(){
        return objId;
    }

    protected void setDbId(ObjectId newObjId){
        objId = newObjId;
    }

    protected void setId(UUID id){
        this.id = id;
    }
}
