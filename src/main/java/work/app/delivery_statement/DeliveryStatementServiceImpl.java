package work.app.delivery_statement;

import work.app.contract.Contract;
import work.app.contract.ContractService;
import work.app.product.Product;
import work.app.product.ProductService;

public class DeliveryStatementServiceImpl implements DeliveryStatementService{
    private DeliveryStatementRepository deliveryStatementRepository;
    private ContractService contractService;
    private ProductService productService;

    public DeliveryStatementServiceImpl(DeliveryStatementRepository deliveryStatementRepository,
                                        ContractService contractService, ProductService productService) {
        this.deliveryStatementRepository = deliveryStatementRepository;
        this.contractService = contractService;
        this.productService = productService;
    }

    @Override
    public void saveDeliveryStatement(DeliveryStatementDto deliveryStatementDto) {
        Contract contract = contractService.findByNumber(deliveryStatementDto.getContractNumber());
        Product product = productService.findByName(deliveryStatementDto.getProductNumber());
        DeliveryStatement deliveryStatement = new DeliveryStatement();
        deliveryStatement.setContract(contract);
        deliveryStatement.setProduct(product);
        deliveryStatement.setScheduledProductQuantity(deliveryStatement.getScheduledProductQuantity());
        deliveryStatement.setActualProductQuantity(0);
        deliveryStatement.setClosed(false);
        deliveryStatement.setPeriod(deliveryStatementDto.getPeriod());
        deliveryStatement.setNote(deliveryStatementDto.getNote());
        deliveryStatement.setPriceForOneProduct(deliveryStatement.getPriceForOneProduct());
        deliveryStatement.setScheduledShipment(deliveryStatementDto.getScheduledShipment());
        deliveryStatementRepository.saveDeliveryStatement(deliveryStatement);
    }
}
