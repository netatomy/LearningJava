package zqc.reading.poeaa.ch10datasourcepatterns.rowdatagateway;

import java.util.List;


// Trasaction Script
public class PersonService {
    
    public String findResponsbiles(){
        PersonFinder finder = new PersonFinder();
        List<PersonGateway> responsiblePersons = finder.findResponsibles();
        StringBuilder result = new StringBuilder();
        for (PersonGateway each : responsiblePersons){
            result.append(each.getLastName());
            result.append(" ");
            result.append(each.getFirstName());
            result.append(" ");
            result.append(each.getNumberOfDependents());
            result.append("\n");
        }
        
        return result.toString();
    }

}
