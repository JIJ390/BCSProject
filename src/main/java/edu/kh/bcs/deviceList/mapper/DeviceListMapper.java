package edu.kh.bcs.deviceList.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.bcs.device.dto.Device;
import edu.kh.bcs.help.dto.MainBannerDto;

@Mapper
public interface DeviceListMapper {

	
	/** 비동기 필터 조회
	 * @param filterType 
	 * @return
	 */
	List<String> selectFilterList(String filterType);
	

	
	
	
	
	List<Device> selectDeviceList(
			@Param("ramList") List<String> ramList, 
			@Param("hddList") List<String> hddList,
			@Param("inchList") List<String> inchList,
			@Param("brand") String brand);





	List<Device> phoneList(String category);






	List<Device> brandList(String brand);






	List<Device> sellList(String category);






	List<Device> brandSellList(String brand);






	List<Device> searchDevices(String query);





	// 한글로 검색
	List<Device> searchDevicesKor(String query);






	List<MainBannerDto> bannerList();






	List<Device> newDevice();







	









	

	




	
	





	
	

	
	

	

}
