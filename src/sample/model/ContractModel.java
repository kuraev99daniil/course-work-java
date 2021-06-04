package sample.model;

import sample.util.Const;
import sample.util.Converter;

public class ContractModel {
    private String contractNumber = "";
    private String dateContract = "";

    private int idClient = -1;
    private int idPolicy = -1;

    public ContractModel() {
    }

    public ContractModel(String contractNumber, String dateContract, int idClient, int idPolicy) {
        this.contractNumber = contractNumber;
        this.dateContract = dateContract;
        this.idClient = idClient;
        this.idPolicy = idPolicy;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getDateContract() {
        return dateContract;
    }

    public void setDateContract(String dateContract) {
        this.dateContract = dateContract;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(int idPolicy) {
        this.idPolicy = idPolicy;
    }

    public static boolean isEmpty(ContractModel contractModel) {
        return contractModel.getContractNumber().isBlank() ||
                contractModel.getDateContract().isBlank();
    }

    public static String isValid(ContractModel contractModel) {
        String dateContract = contractModel.dateContract;
        if (!Converter.isDate(dateContract)) {
            return String.format(Const.incorrectFormatDate, Const.dateContractField);
        }

        return Const.success;
    }
}
