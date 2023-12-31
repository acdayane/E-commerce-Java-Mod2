package br.ada.customer.crud.factory;

import br.ada.customer.crud.integration.database.MemoryDatabase;
import br.ada.customer.crud.integration.email.OrderEmailNotifierImpl;
import br.ada.customer.crud.integration.email.SendEmail;
import br.ada.customer.crud.integration.memoryrepository.OrderEntityMerge;
import br.ada.customer.crud.integration.memoryrepository.OrderMemoryRepositoryImpl;
import br.ada.customer.crud.integration.sms.OrderSmsNotifierImpl;
import br.ada.customer.crud.integration.sms.SendSms;
import br.ada.customer.crud.usecases.*;
import br.ada.customer.crud.usecases.impl.CreateOrderUseCaseImpl;
import br.ada.customer.crud.usecases.impl.OrderItemUseCaseImpl;
import br.ada.customer.crud.usecases.impl.OrderPayUseCaseImpl;
import br.ada.customer.crud.usecases.impl.OrderPlaceUseCaseImpl;
import br.ada.customer.crud.usecases.impl.OrderShippingUseCaseImpl;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderFactory {

    public static ICreateOrderUseCase createUseCase() {
        return new CreateOrderUseCaseImpl(
                createRepository(),
                CustomerFactory.createRepository()
        );
    }

    public static IOrderItemUseCase orderItemUseCase() {
        return new OrderItemUseCaseImpl(createRepository());
    }

    public static IOrderPlaceUseCase placeOrderUseCase() {
        return new OrderPlaceUseCaseImpl(
                createRepository(),
                createEmailNotifier(),
                createSmsNotifier()
        );
    }

    public static IOrderPayUseCase payOrderUseCase() {
        return new OrderPayUseCaseImpl(
                createRepository(),
                createEmailNotifier(),
                createSmsNotifier()
        );
    }

    public static IOrderShippingUseCase shippingUseCase() {
        return new OrderShippingUseCaseImpl (
                createRepository(),
                createEmailNotifier(),
                createSmsNotifier()
        );
    }

    public static OrderRepository createRepository() {
        return new OrderMemoryRepositoryImpl(
                MemoryDatabase.getInstance(),
                new OrderEntityMerge(MemoryDatabase.getInstance())
        );
    }

    private static INotifierOrderUseCase createEmailNotifier() {
        return new OrderEmailNotifierImpl(
            new SendEmail()
        );
    }

    private static INotifierOrderUseCase createSmsNotifier() {
        return new OrderSmsNotifierImpl(
            new SendSms()
        );
    }
}
