package com.zerobase.wifi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WifiVo_API {
	String X_SWIFI_MGR_NO;
	String X_SWIFI_WRDOFC;
	String X_SWIFI_MAIN_NM;
	String X_SWIFI_ADRES1;
	String X_SWIFI_ADRES2;
	String X_SWIFI_INSTL_FLOOR;
	String X_SWIFI_INSTL_TY;
	String X_SWIFI_INSTL_MBY;
	String X_SWIFI_SVC_SE;
	String X_SWIFI_CMCWR;
	String X_SWIFI_CNSTC_YEAR;
	String X_SWIFI_INOUT_DOOR;
	String X_SWIFI_REMARS3;
	double LAT;
	double LNT;
	String WORK_DTTM;

	// api는 lat lnt가 반대로 들어있기때문에 스위칭 작업필요
	public void swtichXY() {
		double temp = LAT;
		LAT = LNT;
		LNT = temp;
	}

}
