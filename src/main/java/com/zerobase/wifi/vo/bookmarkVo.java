package com.zerobase.wifi.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class bookmarkVo {
	// 데이터 저장에 사용하는 필드
	int id;
	int groupId;
	String manageId;
	String date;
	// 데이터 출력에 사용하는 필드
	String groupName;
	String wifiName;
}
