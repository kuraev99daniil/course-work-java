package sample.model.converter;

import sample.model.CarDriverModel;
import sample.model.ClientModel;
import sample.model.ContractModel;
import sample.model.InsuranceEventModel;
import sample.model.table.CarDriverTable;
import sample.model.table.ClientTable;
import sample.model.table.ContractTable;
import sample.model.table.InsuranceEventTable;

public class ConverterModel {
    public static ClientTable convertClientModelToClientTable(ClientModel clientModel) {
        return new ClientTable(
                clientModel.getId(),
                clientModel.getSurname(),
                clientModel.getName(),
                clientModel.getMiddleName(),
                clientModel.getPasswordSeries(),
                clientModel.getPasswordNumber(),
                clientModel.getDateOfBirth(),
                clientModel.getAddress(),
                clientModel.getPhotoPath()
        );
    }

    public static ContractTable convertContractModelToContractTable(ContractModel contractModel) {
        return new ContractTable(
                contractModel.getContractNumber(),
                contractModel.getDateContract(),
                contractModel.getIdClient(),
                contractModel.getIdPolicy()
        );
    }

    public static ContractModel convertContractTableToContractModel(ContractTable contractTable) {
        return new ContractModel(
                contractTable.getContractNumber(),
                contractTable.getDateContract(),
                contractTable.getIdClient(),
                contractTable.getIdPolicy()
        );
    }

    public static CarDriverModel convertCarDriverTableToCarDriverModel(CarDriverTable carDriverTable) {
        return new CarDriverModel(
                carDriverTable.getId(),
                carDriverTable.getSurname(),
                carDriverTable.getName(),
                carDriverTable.getMiddleName(),
                carDriverTable.getSeries(),
                carDriverTable.getNumber()
        );
    }

    public static CarDriverTable convertCarDriverModelToCarDriverTable(CarDriverModel carDriverModel) {
        return new CarDriverTable(
                carDriverModel.getId(),
                carDriverModel.getSurname(),
                carDriverModel.getName(),
                carDriverModel.getMiddleName(),
                carDriverModel.getSeries(),
                carDriverModel.getNumber()
        );
    }

    public static InsuranceEventModel convertInsuranceTableToInsuranceModel(InsuranceEventTable insuranceEventTable) {
        return new InsuranceEventModel(
                insuranceEventTable.getId(),
                insuranceEventTable.getDate(),
                insuranceEventTable.getDescription(),
                insuranceEventTable.getAmountPayment(),
                insuranceEventTable.getIdService(),
                insuranceEventTable.getIdPolicy(),
                insuranceEventTable.getService()
        );
    }

    public static InsuranceEventTable convertInsuranceEventModelToInsuranceEventTable(InsuranceEventModel insuranceEventModel) {
        return new InsuranceEventTable(
                insuranceEventModel.getId(),
                insuranceEventModel.getDate(),
                insuranceEventModel.getDescription(),
                insuranceEventModel.getAmountPayment(),
                insuranceEventModel.getIdService(),
                insuranceEventModel.getIdPolicy(),
                insuranceEventModel.getService()
        );
    }
}
