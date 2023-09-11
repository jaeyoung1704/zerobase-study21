package com.zerobase.wifi.vo;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class posVo {
	int ID;
	float lnt;
	float lat;
	String date;
}
