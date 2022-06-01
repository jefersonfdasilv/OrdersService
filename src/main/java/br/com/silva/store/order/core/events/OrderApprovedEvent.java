package br.com.silva.store.order.core.events;

import br.com.silva.store.order.core.model.OrderStatus;
import lombok.Value;

@Value
public class OrderApprovedEvent {

	private final String orderId;
	private final OrderStatus orderStatus = OrderStatus.APPROVED;
	
}
