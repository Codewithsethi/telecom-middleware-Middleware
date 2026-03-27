package com.telecom.adapter;

import com.telecom.dto.RechargeRequest;
import com.telecom.dto.RechargeResponse;

public interface RechargeAdapter {
    RechargeResponse recharge(RechargeRequest request);
}
