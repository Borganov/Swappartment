package ch.hevs.swap.data.model;

public interface Locality  {
    Long getIdLocality();
    String getName();
    Integer getNpa();
    String getCanton();
    Integer getNrLocality();

    void setIdLocality(Long idLocality);
    void setNameLocality(String nameLocality);
    void setNpa(Integer npa);
    void setCanton(String canton);
    void setNrLocality(Integer nrLocality);
}
