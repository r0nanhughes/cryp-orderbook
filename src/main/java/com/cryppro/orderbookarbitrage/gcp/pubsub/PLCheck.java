package com.cryppro.orderbookarbitrage.gcp.pubsub;

import java.time.Instant;

import org.knowm.xchange.currency.CurrencyPair;

import lombok.Data;

@Data
public class PLCheck {
	
	private Instant timestamp;
	private CurrencyPair pair;
	private double size;
	private String buyExchange;
	private String sellExchange;
	private double pl;

}
