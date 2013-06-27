package zqc.reading.poeaa.ch09domainlogic.domainmodel;

import java.util.ArrayList;
import java.util.List;

import zqc.reading.poeaa.ch09domainlogic.MfDate;
import zqc.reading.poeaa.ch09domainlogic.Money;


public class Contract {
    private List<RevenueRecognition> revenueRecognitions = new ArrayList<>();
    private Product product;
    private Money revenue;
    private MfDate whenSigned;
    private Long id;
    
    
    public Contract(Product product, Money revenue, MfDate whenSigned) {
        this.product = product;
        this.revenue = revenue;
        this.whenSigned = whenSigned;
    }
    
    
    public Money recognizedRevenue(MfDate asOf){
        Money result = Money.dollars(0);
        for (RevenueRecognition r : revenueRecognitions){
            if (r.isRecognizableBy(asOf))
                result = result.add(r.getAmount());
        }
        return result;
    }
    
    public void calculateRecognitions() {

        product.calculateRevenueRecognitions(this);
    }

    public Money getRevenue() {

        return revenue;
    }
    

    public MfDate getWhenSigned(){
        return whenSigned;
    }


    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {

        revenueRecognitions.add(revenueRecognition);
    }


    public static Contract readForUpdate(long contractNumber) {

        return null;
    }

    public String getAdministratorEmailAddress() {

        return null;
    }
}
