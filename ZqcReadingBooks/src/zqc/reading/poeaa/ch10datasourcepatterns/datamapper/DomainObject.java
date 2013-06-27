package zqc.reading.poeaa.ch10datasourcepatterns.datamapper;

public class DomainObject {

    private Long id;

    public DomainObject(Long id) {

        this.id = id;
    }

    public Long getID() {
    
        return id;
    }

    public void setID(Long id) {
    
        this.id = id;
    }

}