package zqc.reading.poeaa.ch10datasourcepatterns.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zqc.reading.poeaa.ApplicationException;
import zqc.reading.poeaa.ch10datasourcepatterns.DB;

public abstract class AbstractMapper<T extends DomainObject> {

    protected Map<Long, T> loadedMap = new HashMap<>();

    protected abstract String findStatement();

    protected T abstractFind(Long id) {

        T result = loadedMap.get(id);
        if (result != null)
            return result;
        PreparedStatement findStatement = null;
        try {
            findStatement = DB.prepare(findStatement());
            findStatement.setLong(1, id.longValue());
            ResultSet rs = findStatement.executeQuery();
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

    protected T load(ResultSet rs) throws SQLException {

        Long id = new Long(rs.getLong(1));
        if (loadedMap.containsKey(id))
            return loadedMap.get(id);
        T result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    protected abstract T doLoad(Long id, ResultSet rs) throws SQLException;

    protected List<T> loadAll(ResultSet rs) throws SQLException {

        List<T> result = new ArrayList<>();
        while (rs.next())
            result.add(load(rs));
        return result;
    }

    public List<T> findMany(StatementSource source) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(source.sql());
            for (int i = 0; i < source.parameters().length; i++)
                stmt.setObject(i + 1, source.parameters()[i]);
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
    
    public Long insert(T subject){
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatement());
            subject.setID(findNextDatabaseId());
            insertStatement.setInt(1, subject.getID().intValue());
            doInsert(subject, insertStatement);
            insertStatement.executeUpdate();
            loadedMap.put(subject.getID(), subject);
            return subject.getID();
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
        finally {
            DB.cleanUp(insertStatement);
        }
    }
    
    private Long findNextDatabaseId() {

        return new Long(0);
    }

    protected abstract String insertStatement();
    protected abstract void doInsert(T subject, PreparedStatement insertStatement) throws SQLException;
}