package com.cryppro.orderbookarbitrage.xchange;

import lombok.Data;

@Data
public class PLCheck {
	
	private String buyExchange;
	private String sellExchange;
	private double pl;

}
