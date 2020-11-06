package com.sm4.system.common.sm;

public class Test {
	public static void main(String[] args) {
		String key = "04371D3776156C8CADA08AFC2574417EACD18ABD3C1AACFDB0212F7903F9A6704169A1F929B8E4956F840718BD5EDA0BE09F6A22C6A3D99D9357076D713D520C07";
		try {
			SM4Utils utils = new SM4Utils();
			utils.secretKey="JeF8U9wHFOMfs2Y8";
			String jsonData = "{'vehicleNumber': '鄂FTC360',  'vehiclePlateColorCode': '2', 'vehicleType': 'H11',  'owner': '深圳市神风运输有限公司', 'useCharacter': '道路普通货物运输', 'vin': '123456789', 'issuingOrganizations': '运管局', 'registerDate': '20191112', 'issueDate': '20191112', 'vehicleEnergyType': 'A', 'vehicleTonnage': 21.3, 'grossMass': 9.9, 'roadTransportCertificateNumber': '420606208298', 'trailerVehiclePlateNumber': '', 'remark': '示例'}";
			String data = utils.encryptData_ECB(jsonData);
			System.out.println("加密后数据："+data);
			String deData = utils.decryptData_ECB("DMGZw7OZblfjQPWBRU3n2ujwwXs+Sgjw55H3hVqKecIxtGZWAeRLiTua/MQKJUjhFXSo4Jlvzxg+RSJVySFw+ZGXUnTe2oyfSBMqe8uUwYEIEiF58StuvPG7CSIrh4Xv5jefXamIJhsqv0bnNFfRAlzy5xjrh7wyVrShvX+I29ePqj+/3LPu9pqrMoThTPkeyORTuRzblWoeAKuHtEaAUaTzxw7VyPihdMUibUs4gsYI7iDGlJrp2Jr/0VIadgs9EPMOsnVSfU7gUJzsF9wvyUjMPTORzWQIfflwPzCcUtkQkDoJKMc2MdYZo3Poup439P3380DhpurHBfJbOcs4+GKxaWQVmRqrNUo8PZYqjIw7cT6ECZQ9uX+IyIvNY/te+U3wUY+s4oZLn5w9IoiM1vh/k3VyJW39X43nz81PsGMn8VoyzJb8L/fN/zzmdALsgEJvm2iV9oxik2T54sBrS3uV5uBr6Lzopn+1u2+r6G7DggGLbRbEsB0b1pgF5wQwy/KrD3Zfas/RlhHnlhq2570VJGVoND5qKIDeUl6pYpGIdDZd9pnAyF6KsG65CE8o");
			System.out.println("解密后数据："+deData);
//			String deData = SM2Utils.decrypt(privateKey, "045BC6CB50E7DCEE01C48518C108CAB69119ED041936D982F30DAB3C3AD0E33F1B7FFF94B66A1658F53C4A90FEF273BAE18EE95EFE8B5D623CE27D42BEEBF337038D2D4918AEF3948C76E0C8E6B07D851F0B64389D9D354922696A5B17F7983608B53D498D27FA75BF55A8A6A272F79DDF")
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
