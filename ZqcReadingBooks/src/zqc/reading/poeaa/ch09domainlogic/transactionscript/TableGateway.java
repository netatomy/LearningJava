package zqc.reading.poeaa.ch09domainlogic.transactionscript;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import zqc.reading.poeaa.ch09domainlogic.MfDate;
import zqc.reading.poeaa.ch09domainlogic.Money;

class TableGateway {

    public ResultSet findRecognitionsFor(long contractID, MfDate asof) throws SQLException {

        PreparedStatement stmt = db.prepareStatement(findRecognitionsStatement);
        stmt.setLong(1, contractID);
        stmt.setDate(2, asof.toSqlDate());
        ResultSet result = stmt.executeQuery();
        return result;
    }

    public ResultSet findContract(long contractID) throws SQLException {

        PreparedStatement stmt = db.prepareStatement(findContractStatement);
        stmt.setLong(1, contractID);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    public void insertRecognition(long contractID, Money amount, MfDate asOf) throws SQLException {

        PreparedStatement stmt = db.prepareStatement(insertRecognitionStatement);
        stmt.setLong(1, contractID);
        stmt.setBigDecimal(2, amount.amount());
        stmt.setDate(3, asOf.toSqlDate());
        stmt.executeUpdate();
    }

    private static final String findRecognitionsStatement  = "SELECT amount FROM RevenueRecognitions WHERE contract = ? AND recognizedOn <= ?";
    private static final String findContractStatement      = "SELECT * FROM Contracts AS c INNER JOIN Products AS p ON c.product = p.ID WHERE ID = ?";
    private static final String insertRecognitionStatement = "INSERT INTO RevenueRecognitions VALUES(?,?,?)";
    private Connection          db;
}