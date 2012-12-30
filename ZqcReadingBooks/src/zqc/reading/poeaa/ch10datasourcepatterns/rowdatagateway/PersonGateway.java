package zqc.reading.poeaa.ch10datasourcepatterns.rowdatagateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import zqc.reading.poeaa.ApplicationException;
import zqc.reading.poeaa.ch10datasourcepatterns.DB;

public class PersonGateway extends Gateway {

    private String lastName;
    private String firstName;
    private int    numberOfDependents;

    public PersonGateway(Long id, String lastName, String firstName, int numberOfDependents) {

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

    private static final String updateStatementString = "UPDATE people SET lastname = ?, firstname = ?, number_of_dependents = ? WHERE id = ?";

    public void update() {

        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, lastName);
            updateStatement.setString(2, firstName);
            updateStatement.setInt(3, numberOfDependents);
            updateStatement.setInt(4, getID().intValue());
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(updateStatement);
        }
    }

    private static final String insertStatementString = "INSERT INTO people VALUES(?, ?, ?, ?)";

    public Long insert() {

        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatementString, "id");
            setID(findNextDataseId());
            insertStatement.setInt(1, getID().intValue());
            insertStatement.setString(2, lastName);
            insertStatement.setString(3, firstName);
            insertStatement.setInt(4, numberOfDependents);
            insertStatement.executeUpdate();
            // ResultSet rsKey = insertStatement.getGeneratedKeys();
            // rsKey.next();
            // setID(rsKey.getLong(1));
            Registry.addPerson(this);
            return getID();
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(insertStatement);
        }
    }

    private Long findNextDataseId() {

        // TODO Auto-generated method stub
        return Long.valueOf(0);
    }

    public static PersonGateway load(ResultSet rs) throws SQLException {

        Long id = Long.valueOf(rs.getLong(1));
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if (result != null)
            return result;
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        result = new PersonGateway(id, lastNameArg, firstNameArg, numDependentsArg);
        Registry.addPerson(result);
        return result;
    }

}
