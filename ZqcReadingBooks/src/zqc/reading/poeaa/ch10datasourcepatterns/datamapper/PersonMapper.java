package zqc.reading.poeaa.ch10datasourcepatterns.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zqc.reading.poeaa.ApplicationException;
import zqc.reading.poeaa.ch10datasourcepatterns.DB;

public class PersonMapper extends AbstractMapper<Person> {

    private static final String COLUMNS = "ID, LastName, FirstName, NumberOfDependents ";

    @Override
    protected String findStatement() {

        return "SELECT " + COLUMNS + "FROM People WHERE ID = ?";
    }

    public Person find(Long id) {

        return abstractFind(id);
    }

    public Person find(long id) {

        return find(new Long(id));
    }

    @Override
    protected Person doLoad(Long id, ResultSet rs) throws SQLException {

        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);

        return new Person(id, lastNameArg, firstNameArg, numDependentsArg);
    }

    private static String findLastNameStatement = "SELECT " + COLUMNS + " FROM People "
                                                                + " WHERE UPPER(LastName) LIKE UPPER(?)"
                                                                + " ORDER BY LastName";

    public List<Person> findByLastName(String name) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(findLastNameStatement);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            return loadAll(rs);
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(stmt, rs);
        }
    }

    public List<Person> findByLastName2(String pattern) {

        return findMany(new FindByLastName(pattern));
    }

    static class FindByLastName implements StatementSource {

        private String lastName;

        public FindByLastName(String lastName) {

            this.lastName = lastName;
        }

        @Override
        public String sql() {

            return "SELECT " + COLUMNS + " FROM People " + " WHERE UPPER(LastName) = UPPER(?)" + " ORDER BY LastName";
        }

        @Override
        public Object[] parameters() {

            return new Object[] { lastName };
        }

    }
    
    private static final String updateStatementString = "UPDATE People " +
                    " SET LastName = ?, FirstName = ?, NumberOfDpendents = ? " +
                    " WHERE ID = ?";
    public void update(Person subject){
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, subject.getLastName());
            updateStatement.setString(2, subject.getFirstName());
            updateStatement.setInt(3, subject.getNumberOfDependents());
            updateStatement.setInt(4, subject.getID().intValue());
            updateStatement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(updateStatement);
        }
    }

    private static final String insertStatementString = "INSERT INTO People(LastName, FirstName, NumberOfDependents) VALUES(?, ?, ?)";
    @Override
    protected String insertStatement() {

        return insertStatementString;
    }

    @Override
    protected void doInsert(Person subject, PreparedStatement stmt) throws SQLException {

        stmt.setString(2, subject.getLastName());
        stmt.setString(3, subject.getFirstName());
        stmt.setInt(4, subject.getNumberOfDependents());
    }
}
