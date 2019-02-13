package com.cryppro.orderbookarbitrage.xchange;

import org.knowm.xchange.dto.marketdata.OrderBook;

import lombok.Data;

@Data
public class XChangeOrderBook {

	private String exchangeName;
	private OrderBook orderbook;
	
}
