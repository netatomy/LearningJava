package zqc.reading.poeaa.ch10datasourcepatterns.rowdatagateway;

import java.util.HashMap;
import java.util.Map;


public class Registry {

    private static final Map<Long, Gateway> gatewayList = new HashMap<>();

    public static Gateway getPerson(Long id) {

        return gatewayList.get(id);
    }

    public static void addPerson(PersonGateway person) {

        gatewayList.put(person.getID(), person);
    }

}
