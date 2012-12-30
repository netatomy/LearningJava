package zqc.reading.poeaa.ch09domainlogic.appservices;

import zqc.reading.poeaa.ch09domainlogic.domainmodel.Contract;


public class RecoginitionService extends ApplicationService {

    public void calculateRevenueRecognitions(long contractNumber){
        Contract contract = Contract.readForUpdate(contractNumber);
        contract.calculateRecognitions();
        getEmailGateway().sendEmailMessage(contract.getAdministratorEmailAddress(), "RE: Contract #" + contractNumber, contract + " has had revenue recognitions calculated.");
        getIntegrationGateway().publishRevenueRecognitionCalculation(contract);
    }
}
