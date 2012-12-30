package zqc.reading.poeaa.ch10datasourcepatterns.activerecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import zqc.reading.poeaa.ApplicationException;
import zqc.reading.poeaa.ch10datasourcepatterns.DB;

public class Person extends ActiveRecord {

    private String lastName;
    private String firstName;
    private int    numberOfDependents;

    public Person(Long id, String lastName, String firstName, int numberOfDependents) {

        super(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.numberOfDependents = numberOfDependents;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public int getNumberOfDependents() {

        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {

        this.numberOfDependents = numberOfDependents;
    }
    
    @Override
    public String toString() {
    
        return String.format("ID: %d, Name: %s %s, NumberOfDependents: %d",
                        getID().intValue(),
                        firstName,
                        lastName,
                        numberOfDependents);
    }
    

    private final static String findStatementString = "SELECT id, lastname, firstname, number_of_dependents FROM people WHERE id = ?";

    public static Person find(Long id) {

        Person result = (Person) Registry.getPerson(id);
        if (result != null)
            return result;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = DB.prepare(findStatementString);
            rs = findStatement.executeQuery();
            rs.next();
            result = load(rs);
            return result;
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(findStatement);
        }
    }

    public static Person find(long id) {

        return find(Long.valueOf(id));
    }

    private static Person load(ResultSet rs) throws SQLException {

        Long id = Long.valueOf(rs.getLong(1));
        Person result = (Person) Registry.getPerson(id);
        if (result != null)
            return result;
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        result = new Person(id, lastNameArg, firstNameArg, numDependentsArg);
        return result;
    }

    private final static String updateStatementString = "UPDATE people SET lastname = ?, firstname = ?, number_of_dependents = ? WHERE id = ?";

    public void update() {

        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, lastName);
            updateStatement.setString(2, firstName);
            updateStatement.setInt(3, numberOfDependents);
            updateStatement.setInt(4, getID().intValue());
            updateStatement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(updateStatement);
        }
    }

    private final static String insertStatementString = "INSERT INTO people VALUES (?, ?, ?, ?)";

    public Long insert() {

        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatementString, "id");
            //setID(findNextDatabaseId());
            insertStatement.setInt(1, 0); //getID().intValue()
            insertStatement.setString(2, lastName);
            insertStatement.setString(3, firstName);
            insertStatement.setInt(4, numberOfDependents);
            insertStatement.executeUpdate();
            
            ResultSet rsKeys = insertStatement.getGeneratedKeys();
            rsKeys.next();
            setID(rsKeys.getLong(1));
            Registry.addPerson(this);
            return getID();
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally{
            DB.cleanUp(insertStatement);
        }
    }

    private static Long findNextDatabaseId() {

        // TODO Auto-generated method stub
        return null;
    }

}
