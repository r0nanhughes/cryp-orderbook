package com.cryppro.orderbookarbitrage.xchange;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cryppro.orderbookarbitrage.xchange.exchanges.Binance;
import com.cryppro.orderbookarbitrage.xchange.exchanges.BitFinex;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Bitstamp;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Bittrex;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Cexio;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Gemini;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Huobi;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Kraken;
import com.cryppro.orderbookarbitrage.xchange.exchanges.Poloniex;

import lombok.Data;

@Component
@Data
public class ExchangeInitializer {

	@Autowired
	private Binance binance;
	
	@Autowired
	private BitFinex bitfinex;
	
	@Autowired
	private Bitstamp bitstamp;

	@Autowired
	private Bittrex bittrex;

	@Autowired
	private Cexio cexio;

	@Autowired
	private Gemini gemini;

	@Autowired
	private Huobi huobi;

	@Autowired
	private Kraken kraken;

	@Autowired
	private Poloniex poloniex;
	
	private List<XChange> exchanges;
	
	@PostConstruct
	private void init() {
		
		exchanges = new ArrayList<>();
		
		exchanges.add(binance);
		exchanges.add(bitfinex);
		exchanges.add(bitstamp);
		exchanges.add(bittrex);
		exchanges.add(cexio);
		exchanges.add(gemini);
		exchanges.add(huobi);
		exchanges.add(kraken);
		exchanges.add(poloniex);
	
	}
}
