package work.app.contract;

public class ContractServiceImpl implements ContractService {
    ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public void saveContract(Contract contract) {
        contractRepository.saveContract(contract);
    }
}
