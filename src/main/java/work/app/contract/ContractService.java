package work.app.contract;

public interface ContractService {
    void saveContract(ContractDto contract);

    Contract findByNumber(String contractNumber);
}
