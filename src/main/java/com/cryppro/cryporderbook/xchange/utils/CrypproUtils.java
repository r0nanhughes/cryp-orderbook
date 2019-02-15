package com.cryppro.cryporderbook.xchange.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

import com.cryppro.cryporderbook.gcp.pubsub.PLCheck;
import com.cryppro.cryporderbook.xchange.AdjustedBidAsk;
import com.cryppro.cryporderbook.xchange.XChangeOrderBook;

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

	public static List<PLCheck> calcPL(List<AdjustedBidAsk> orders, CurrencyPair pair, double size, Instant timestamp) {
		
		List<PLCheck> ret = new ArrayList<>();
		for( AdjustedBidAsk buy : orders) {
    		for( AdjustedBidAsk sell : orders) {
    			PLCheck plCheck = new PLCheck();
    			plCheck.setTimestamp(timestamp);
    			plCheck.setBuyExchange(buy.getExchange());
    			plCheck.setSellExchange(sell.getExchange());
    			plCheck.setPl(buy.getExchange().equals(sell.getExchange()) ? 0. : (sell.getFeeAdjBid() / buy.getFeeAdjAsk()) - 1);
    			plCheck.setPair(pair);
    			plCheck.setSize(size);
    			ret.add(plCheck);
    		}
    	}
		
		return ret;
	}
}
