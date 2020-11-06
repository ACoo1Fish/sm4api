package com.sm4.api.controller.sm4;

import cn.hutool.core.util.StrUtil;
import com.sm4.system.common.CommonResult;
import com.sm4.system.common.sm.SM2Utils;
import com.sm4.system.common.sm.SM4Utils;
import com.sm4.system.common.sm.Sm4Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Sm4DataController
 * @Author: ACoolFish
 * @Description: 登录成功处理逻辑
 * @Date Create in 2020/11/5 20:53
 */
@RestController
@RequestMapping("/api2")
public class Sm4DataController {
    @PostMapping(value = "/getsm4data")
    public CommonResult getsm4data(@RequestBody Sm4Data sm4Data) {
        SM4Utils utils = new SM4Utils();
        String key = sm4Data.getKey();
        String data = sm4Data.getData();
        if (StrUtil.isEmpty(key))
            return CommonResult.failed("秘钥不允许为空");
        utils.secretKey=key;
        if (StrUtil.isEmpty(data))
            return CommonResult.failed("加密数据不允许为空");
        String returnData = null;
        try {
            returnData = utils.encryptData_ECB(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,String> returnMap = new HashMap<String,String>();
        returnMap.put("key",key);
        returnMap.put("sm4Data",returnData);
        return CommonResult.success(returnMap);
    }

    @PostMapping(value = "/getsm2data")
    public CommonResult getsm2data(@RequestBody Sm4Data sm4Data) {
//        SM4Utils utils = new SM4Utils();
        String key = sm4Data.getKey();
        String data = sm4Data.getData();
        if (StrUtil.isEmpty(key))
            return CommonResult.failed("公钥不允许为空");
        if (StrUtil.isEmpty(data))
            return CommonResult.failed("加密数据不允许为空");
        String returnData = null;
        try {
            returnData = SM2Utils.encrypt(key,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,String> returnMap = new HashMap<String,String>();
        returnMap.put("publickey",key);
        returnMap.put("sm2Data",returnData);
        return CommonResult.success(returnMap);
    }

}
