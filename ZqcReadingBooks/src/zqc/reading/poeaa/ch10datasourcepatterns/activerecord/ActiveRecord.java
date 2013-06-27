package zqc.reading.poeaa.ch10datasourcepatterns.activerecord;


public class ActiveRecord {
    
    private Long id;
    
    public ActiveRecord(Long id) {
        this.id = id;
    }

    public Long getID(){
        return id;
    }
    
    public void setID(Long id){
        this.id = id;
    }

}
