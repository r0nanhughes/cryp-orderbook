package com.cryppro.orderbookarbitrage.xchange;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PLResults {

	private Instant timestamp = Instant.now();
	private List<PLCheck> plChecks = new ArrayList<>();

}
