         function load(){
            var pos = document.posForm;
            var lat = pos.lat.value;
            var lnt = pos.lat.value;
            
            if(!lat || !lnt){
                alert("위치를 입력하고 다시 시도해주세요.")
            }else{
                pos.submit();
            }
        }
        
        function pos() {
			// 현재 위치 가져오기
			navigator.geolocation.getCurrentPosition(getSuccess, getError);

			// 가져오기 성공
			function getSuccess(pos) {
				// 위도
				const lat = pos.coords.latitude;
				// 경도
				const lnt = pos.coords.longitude;
				// 위도 경도 오차(m)
				const accur = Math.floor(pos.coords.accuracy);
				console.log(pos);
				console.log('현재 위치:' + lat + ', ' + lnt + ', 오차: ' + accur);
				document.getElementsByName("lat")[0].value = lat;
				document.getElementsByName("lnt")[0].value = lnt;
			}

			// 가지오기 실패(거부)
			function getError() {
				alert('Geolocation Error');
				console.log('에러');
			}
		}