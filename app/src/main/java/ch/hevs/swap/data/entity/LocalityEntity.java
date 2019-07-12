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

    @ColumnInfo(name="nameLocality")
    private String nameLocality;

    @ColumnInfo(name="npa")
    private Integer npa;

    @ColumnInfo(name="canton")
    private String canton;

    @ColumnInfo(name="nrLocality")
    private Integer nrLocality;

    public String getNameLocality() {
        return nameLocality;
    }
    
    public Long getIdLocality() {
        return idLocality;
    }

    public String getName() {
        return nameLocality;
    }

    public Integer getNpa() { return npa; }

    public String getCanton() { return canton; }

    public Integer getNrLocality() { return nrLocality;}


    public void setIdLocality(Long idLocality) {
        this.idLocality = idLocality;
    }

    public void setNameLocality(String nameLocality) {
        this.nameLocality = nameLocality;
    }

    public void setNpa(Integer npa) {
        this.npa = npa;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public void setNrLocality(Integer nrLocality) {
        this.nrLocality = nrLocality;
    }
}
