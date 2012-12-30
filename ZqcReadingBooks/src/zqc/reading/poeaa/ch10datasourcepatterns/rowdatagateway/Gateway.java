package zqc.reading.poeaa.ch10datasourcepatterns.rowdatagateway;


public class Gateway {
    
    private Long id;
    
    public Gateway(){
    }
    
    public Gateway(Long id){
        this.id = id;
    }
    
    public Long getID(){
        return id;
    }
    
    public void setID(Long id){
        this.id = id;
    }

}
