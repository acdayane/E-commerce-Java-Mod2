package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.INotifierOrderUseCase;
import br.ada.customer.crud.usecases.IOrderShippingUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderShippingUseCaseImpl implements IOrderShippingUseCase {

    private OrderRepository orderRepository;
    private INotifierOrderUseCase notifierEmail;
    private INotifierOrderUseCase notifierSms;

    public OrderShippingUseCaseImpl(OrderRepository repository, INotifierOrderUseCase notifierEmail, INotifierOrderUseCase notifierSms) {
        this.orderRepository = repository;
        this.notifierEmail = notifierEmail;
        this.notifierSms = notifierSms;
    }

    @Override
    public void shipping(Order order) {
        checkOrderStatus(order);

        order.setStatus(OrderStatus.FINISH);

        orderRepository.update(order);
        
        notifierEmail.shipping(order);
        notifierSms.shipping(order);
    }

    private void checkOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.PAID) {
            throw new RuntimeException("Pedido ainda não pago.");
        }
    }

}
