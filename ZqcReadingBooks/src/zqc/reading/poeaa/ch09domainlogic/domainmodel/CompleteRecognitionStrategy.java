package zqc.reading.poeaa.ch09domainlogic.domainmodel;


public class CompleteRecognitionStrategy extends RecognitionStrategy {

    @Override
    void calculateRevenueRecognitions(Contract contract) {
        contract.addRevenueRecognition(new RevenueRecognition(contract.getRevenue(), contract.getWhenSigned()));
    }

}
