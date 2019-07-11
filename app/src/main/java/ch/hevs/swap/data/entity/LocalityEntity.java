package ch.hevs.swap.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ch.hevs.swap.data.model.Locality;

@Entity(tableName = "localities")
public class LocalityEntity implements Locality {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="idLocality")
    private Long idLocality;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="zipcode")
    private String zipcode;


    public Long getIdLocality() {
        return idLocality;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return zipcode;
    }

    public void setIdLocality(Long idLocality) {
        this.idLocality = idLocality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
