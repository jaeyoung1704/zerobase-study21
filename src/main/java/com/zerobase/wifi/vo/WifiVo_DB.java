package com.zerobase.wifi.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiVo_DB {
	float distance;
	String manageID;
	String district;
	String name;
	String address1;
	String address2;
	String installType;
	String installBy;
	String serviceType;
	String networkType;
	String year;
	String inOut;
	double lnt;
	double lat;
	String refreshDate;
}
