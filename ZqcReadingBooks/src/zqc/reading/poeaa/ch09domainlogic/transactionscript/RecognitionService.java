package zqc.reading.poeaa.ch09domainlogic.transactionscript;

import java.sql.ResultSet;
import java.sql.SQLException;

import zqc.reading.poeaa.ApplicationException;
import zqc.reading.poeaa.ch09domainlogic.MfDate;
import zqc.reading.poeaa.ch09domainlogic.Money;

public class RecognitionService {

    private TableGateway db;

    // 计算指定日期前的某合同的确认收入合计
    public Money recognizedRevenue(long contractNumber, MfDate asOf) {

        Money result = Money.dollars(0);
        try {
            ResultSet rs = db.findRecognitionsFor(contractNumber, asOf);
            while (rs.next()) {
                result = result.add(Money.dollars(rs.getBigDecimal("amount")));
            }
            return result;
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    // 为指定合同计算收入确认
    public void calculateRevenueRecognitions(long contractNumber) {

        try {
            ResultSet contracts = db.findContract(contractNumber);
            contracts.next();
            Money totalRevenue = Money.dollars(contracts.getBigDecimal("revenue"));
            MfDate recognitionDate = new MfDate(contracts.getDate("dateSigned"));
            String type = contracts.getString("type");
            // 根据产品类型计算并插入收入确认记录
            switch (type) {
            case "S": // 电子表格软件
                Money[] allocation = totalRevenue.allocate(3);
                db.insertRecognition(contractNumber, allocation[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation[1], recognitionDate.addDays(60));
                db.insertRecognition(contractNumber, allocation[2], recognitionDate.addDays(90));
                break;
            case "W": // 文字处理软件
                db.insertRecognition(contractNumber, totalRevenue, recognitionDate);
                break;
            case "D": // 数据库
                Money[] allocation2 = totalRevenue.allocate(3);
                db.insertRecognition(contractNumber, allocation2[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation2[1], recognitionDate.addDays(30));
                db.insertRecognition(contractNumber, allocation2[2], recognitionDate.addDays(60));
                break;
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}