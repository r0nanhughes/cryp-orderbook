package com.cryppro.orderbookarbitrage.xchange.utils;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

import com.cryppro.orderbookarbitrage.xchange.AdjustedBidAsk;
import com.cryppro.orderbookarbitrage.xchange.PLCheck;
import com.cryppro.orderbookarbitrage.xchange.PLResults;
import com.cryppro.orderbookarbitrage.xchange.XChangeOrderBook;

public class CrypproUtils {

	public static AdjustedBidAsk adjustedBidAsk(XChangeOrderBook orderbook, CurrencyPair pair, double size, double fee) {

		double cumulativeBid = 0.;
		double cumulativeBidVolume = 0.;
		double cumulativeAsk = 0.;
		double cumulativeAskVolume = 0.;
		List<LimitOrder> usedAsks = new ArrayList<>();
		List<LimitOrder> usedBids = new ArrayList<>();

		for( LimitOrder order : orderbook.getOrderbook().getBids()) {

			double orderAmount = order.getOriginalAmount().doubleValue();
			double orderPrice = order.getLimitPrice().doubleValue();
			usedBids.add(order);

			cumulativeBidVolume += orderAmount;

			if( cumulativeBidVolume < size ) {
				cumulativeBid += (orderPrice * orderAmount);
			} else {
				cumulativeBid += orderPrice * (size - (cumulativeBidVolume - orderAmount));
				cumulativeBid /= size;
				break;
			}

		}

		for( LimitOrder order : orderbook.getOrderbook().getAsks()) {

			double orderAmount = order.getOriginalAmount().doubleValue();
			double orderPrice = order.getLimitPrice().doubleValue();
			usedAsks.add(order);

			cumulativeAskVolume += orderAmount;

			if( cumulativeAskVolume < size ) {
				cumulativeAsk += (orderPrice * orderAmount);
			} else {
				cumulativeAsk += orderPrice * (size - (cumulativeAskVolume - orderAmount));
				cumulativeAsk /= size;
				break;
			}

		}
		AdjustedBidAsk adjustedBidAsk = new AdjustedBidAsk();
		adjustedBidAsk.setAsk(cumulativeAsk);
		adjustedBidAsk.setBid(cumulativeBid);
		adjustedBidAsk.setFee(fee);
		adjustedBidAsk.setFeeAdjBid(cumulativeBid - (cumulativeBid * fee));
		adjustedBidAsk.setFeeAdjAsk(cumulativeBid + (cumulativeAsk * fee));
		adjustedBidAsk.setSize(size);
		adjustedBidAsk.setExchange(orderbook.getExchangeName());
		adjustedBidAsk.setPair(pair);
		adjustedBidAsk.setUsedAsks(usedAsks);
		adjustedBidAsk.setUsedBids(usedBids);

		return adjustedBidAsk;
	}
	// bid from what you sell, ask from what you buy, divide by each other, then -1
	public static PLResults calcPL(List<AdjustedBidAsk> orders) {
		
		PLResults results = new PLResults();
		
    	for( AdjustedBidAsk buy : orders) {
    		for( AdjustedBidAsk sell : orders) {
    			PLCheck plCheck = new PLCheck();
    			plCheck.setBuyExchange(buy.getExchange());
    			plCheck.setSellExchange(sell.getExchange());
    			plCheck.setPl(buy.getExchange().equals(sell.getExchange()) ? 0. : (sell.getFeeAdjBid() / buy.getFeeAdjAsk()) - 1);
    			
    			results.getPlChecks().add(plCheck);
    		}
    	}

		return results;
	}
}