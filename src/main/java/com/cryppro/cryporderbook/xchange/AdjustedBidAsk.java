package com.cryppro.cryporderbook.xchange;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

import lombok.Data;

@Data
public class AdjustedBidAsk {

	private String exchange;
	private CurrencyPair pair;
	private double fee;
	private double bid;
	private double ask;
	private double feeAdjBid;
	private double feeAdjAsk;
	private double size;
	private List<LimitOrder> usedBids = new ArrayList<>();
	private List<LimitOrder> usedAsks = new ArrayList<>();
	
}
