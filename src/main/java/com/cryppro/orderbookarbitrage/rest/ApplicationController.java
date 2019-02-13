package com.cryppro.orderbookarbitrage.rest;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryppro.orderbookarbitrage.config.GoogleConfiguration;
import com.cryppro.orderbookarbitrage.xchange.AdjustedBidAsk;
import com.cryppro.orderbookarbitrage.xchange.ExchangeInitializer;
import com.cryppro.orderbookarbitrage.xchange.utils.CrypproUtils;

@RestController
public class ApplicationController {

	@Autowired
	private ExchangeInitializer exchange;

	@Autowired
	private PubSubTemplate pubSubTemplate;

	@Autowired
	private GoogleConfiguration googleConfig;

	@RequestMapping("/go")
	public void go(@RequestParam(value = "pair", defaultValue = "ETH_BTC") CurrencyPair pair,
			@RequestParam(value = "depth", defaultValue = "20") int depth,
			@RequestParam(value = "size", defaultValue = "1") double size,
			@RequestParam(value = "fee", defaultValue = "0.0001") double fee) {

		List<AdjustedBidAsk> orders = exchange.getExchanges().stream().parallel()
				.filter(exchange -> exchange.hasSymbol(pair)).map(a -> a.refreshOrderBook(pair, depth))
				.filter(orderbook -> Objects.nonNull(orderbook.getOrderbook()))
				.map(orderbook -> CrypproUtils.adjustedBidAsk(orderbook, pair, size, fee)).collect(Collectors.toList());

		CrypproUtils.calcPL(orders, pair, size, Instant.now())
				.forEach(result -> this.pubSubTemplate.publish(googleConfig.getPubSubTopic(), result));

	}

}
