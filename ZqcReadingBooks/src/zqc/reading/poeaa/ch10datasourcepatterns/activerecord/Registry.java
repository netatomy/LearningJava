package zqc.reading.poeaa.ch10datasourcepatterns.activerecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Registry {
    private static final Map<Long, ActiveRecord> map = new HashMap<>();
    
    public static ActiveRecord getPerson(Long id){
        return map.get(id);
    }
    
    public static void addPerson(Person person){
        map.put(person.getID(), person);
    }
    
    public static List<ActiveRecord> GetPersons(){
        return new ArrayList<ActiveRecord>(map.values());
    }
}
