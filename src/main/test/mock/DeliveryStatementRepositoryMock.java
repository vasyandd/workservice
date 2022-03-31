package mock;


import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.repo.DeliveryStatementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryStatementRepositoryMock implements DeliveryStatementRepository{
    private final List<DeliveryStatement> database = new ArrayList<>();


    @Override
    public List<DeliveryStatement> findAllWithNotifications() {
        return null;
    }

    @Override
    public void delete(DeliveryStatement entity) {

    }

    @Override
    public Optional<DeliveryStatement> findByContract(Contract contract) {
        for (DeliveryStatement d : database) {
           if (d.getContract().getContractNumber().equals(contract.getContractNumber())
                   && d.getContract().getAdditionalAgreement().equals(contract.getAdditionalAgreement())) {
               return Optional.of(d);
           }
        }
        return Optional.empty();
    }

    @Override
    public <S extends DeliveryStatement> S save(S entity) {
        for (int i = 0; i < database.size(); i++) {
            if (entity.getId().equals(database.get(i).getId())) {
                database.add(i, entity);
                return entity;
            }
        }
        database.add(entity);
        return entity;
    }

    @Override
    public <S extends DeliveryStatement> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<DeliveryStatement> findById(Long aLong) {
        return Optional.of(database.get(aLong.intValue()));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<DeliveryStatement> findAll() {
        return null;
    }

    @Override
    public Iterable<DeliveryStatement> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends DeliveryStatement> entities) {
        database.clear();
    }

    @Override
    public void deleteAll() {

    }

}
