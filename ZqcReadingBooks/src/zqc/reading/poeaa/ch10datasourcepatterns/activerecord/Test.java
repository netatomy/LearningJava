package zqc.reading.poeaa.ch10datasourcepatterns.activerecord;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Person person = new Person(new Long(99), "test", "Bill", 10);
        person.insert();

        Person person2 = new Person(Long.valueOf(22), "Gosling", "James", 99);
        person2.insert();

        person.setLastName("Gates");
        person.update();

        for (ActiveRecord each : Registry.GetPersons()) {
            System.out.println(each);
        }
    }

}
