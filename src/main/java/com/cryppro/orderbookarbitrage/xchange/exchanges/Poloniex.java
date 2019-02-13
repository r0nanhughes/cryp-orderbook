package com.cryppro.orderbookarbitrage.xchange.exchanges;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.springframework.stereotype.Component;

import com.cryppro.orderbookarbitrage.xchange.XChange;
import com.cryppro.orderbookarbitrage.xchange.XChangeOrderBook;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
public class Poloniex implements XChange {

	private final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());

	@Override
	public boolean hasSymbol(CurrencyPair pair) {
		return exchange.getExchangeSymbols().contains(pair);
	}

	@Override
	public XChangeOrderBook refreshOrderBook(CurrencyPair pair, int depth) {
		
		XChangeOrderBook orderbook = new XChangeOrderBook();
		orderbook.setExchangeName(getExchangeName());
		
		try {
			orderbook.setOrderbook(exchange.getMarketDataService().getOrderBook(pair, depth, depth));
		} catch (Exception ex) {
			log.warn(orderbook.getExchangeName() + " failed to retreive orderbook.");
			log.error(ex.getMessage());
		}
		
		return orderbook;
	}

	@Override
	public String getExchangeName() {
		return exchange.getDefaultExchangeSpecification().getExchangeName();
	}
	 
}