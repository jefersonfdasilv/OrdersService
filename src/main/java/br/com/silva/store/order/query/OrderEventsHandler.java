package br.com.silva.store.order.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.silva.store.order.core.data.OrderEntity;
import br.com.silva.store.order.core.data.OrdersRepository;
import br.com.silva.store.order.core.events.OrderApprovedEvent;
import br.com.silva.store.order.core.events.OrderCreatedEvent;
import br.com.silva.store.order.core.events.OrderRejectedEvent;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@ProcessingGroup("order-group")
public class OrderEventsHandler {
    
    private final OrdersRepository ordersRepository;
   

    @EventHandler
    public void on(OrderCreatedEvent event) throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
 
        ordersRepository.save(orderEntity);
    }
    
    
    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
    	OrderEntity orderEntity = ordersRepository.findByOrderId(orderApprovedEvent.getOrderId());
   
    	if(orderEntity == null) {
    		// TODO: Do something about it
    		return;
    	}
    	
    	orderEntity.setOrderStatus(orderApprovedEvent.getOrderStatus());
    	
    	ordersRepository.save(orderEntity);
    
    }
    
    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
    	OrderEntity orderEntity = ordersRepository.findByOrderId(orderRejectedEvent.getOrderId());
    	orderEntity.setOrderStatus(orderRejectedEvent.getOrderStatus());
    	ordersRepository.save(orderEntity);
    }
    
}

