package edu.kh.bcs.deviceDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellingDevice {
	
	private int sellingDeviceNo;
	private String clientBank;
	private String accountNo;
	private String requestComment;
	private int deviceNo;
	private int capacityNumber;
	private int gradeNumber;
	private int colorNo;
	private int memberNo;
	private String clientName;
	private String clientTel;
	

}
