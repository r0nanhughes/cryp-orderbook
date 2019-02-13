package com.cryppro.orderbookarbitrage.xchange;

import org.knowm.xchange.currency.CurrencyPair;

public interface XChange {

	boolean hasSymbol(CurrencyPair pair);
	String getExchangeName();
	XChangeOrderBook refreshOrderBook(CurrencyPair pair, int depth);
	
}
