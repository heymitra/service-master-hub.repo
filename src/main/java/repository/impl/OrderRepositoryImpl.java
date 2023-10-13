package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Order;
import jakarta.persistence.EntityManager;
import repository.OrderRepository;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order, Long>
        implements OrderRepository {
    public OrderRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }
}
