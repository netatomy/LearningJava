package zqc.reading.poeaa.ch09domainlogic.appservices;

import zqc.reading.poeaa.ch09domainlogic.domainmodel.Contract;


public interface IntegrationGateway {
    void publishRevenueRecognitionCalculation(Contract contract);
}
