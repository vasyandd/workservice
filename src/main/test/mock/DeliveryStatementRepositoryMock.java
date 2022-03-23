package mock;



import work.app.delivery_statement.entity.DeliveryStatementEntity;
import work.app.delivery_statement.repo.DeliveryStatementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryStatementRepositoryMock implements DeliveryStatementRepository{
    private final List<DeliveryStatementEntity> database = new ArrayList<>();


    @Override
    public Optional<DeliveryStatementEntity> findByContractNumberAndAdditionalAgreement(String contractNumber, String agreement) {
        for (DeliveryStatementEntity d : database) {
           if (d.getContractNumber().equals(contractNumber) && d.getAdditionalAgreement().equals(agreement)) {
               return Optional.of(d);
           }
        }
        return Optional.empty();
    }

    @Override
    public <S extends DeliveryStatementEntity> S save(S entity) {
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
    public <S extends DeliveryStatementEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<DeliveryStatementEntity> findById(Long aLong) {
        return Optional.of(database.get(aLong.intValue()));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<DeliveryStatementEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<DeliveryStatementEntity> findAllById(Iterable<Long> longs) {
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
    public void delete(DeliveryStatementEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends DeliveryStatementEntity> entities) {
        database.clear();
    }

    @Override
    public void deleteAll() {

    }

}
