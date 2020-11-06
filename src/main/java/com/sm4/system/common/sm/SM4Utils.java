package com.sm4.system.common.sm;

import cn.hutool.core.util.StrUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SM4 无线局域网标准的分组数据算法。对称加密，密钥长度和分组长度均为128位
 *
 * @create 2019-11-19
 */
public class SM4Utils {

    public String secretKey = "";  // 密钥（随机码）
    public String iv = "";  // 初始化向量IV(CBC方式),使用初始化向量先加密第一个分组，然后使用得到的密文加密第二个数组，加密第二个分组的密文再用来加密第三个分组，依次类推
    public boolean hexString = false;

    public SM4Utils(){}

    /**
     * ECB 加密
     * ECB加密方式 对每一个明文分组独立处理，所以明文若8个字节一组相同的话，加密出来的结果也是8个字节一组是相同的
     * @param plainText
     * @return
     */
    public String encryptData_ECB(String plainText) throws Exception {
        if(StrUtil.isEmpty(plainText)){
            return null;
        }
        SM4Context ctx = new SM4Context();
        ctx.isPadding = true;
        ctx.mode = SM4.SM4_ENCRYPT;

        byte[] keyBytes;
        if (hexString){
            keyBytes = Util.hexStringToBytes(secretKey);
        }else{
            keyBytes = secretKey.getBytes();
        }

        SM4 sm4 = new SM4();
        sm4.sm4_setkey_enc(ctx, keyBytes);
        byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("GBK"));
        String cipherText = new BASE64Encoder().encode(encrypted);
        if (cipherText != null && cipherText.trim().length() > 0){
            Pattern p = Pattern.compile("\\s*|\t|\r|");
            Matcher m = p.matcher(cipherText);
            cipherText = m.replaceAll("");
        }
        return cipherText;
    }

    /**
     * ECB解密
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public String decryptData_ECB(String cipherText) throws Exception {
        if(StrUtil.isEmpty(cipherText)){
            return null;
        }
        SM4Context ctx = new SM4Context();
        ctx.isPadding = true;
        ctx.mode = SM4.SM4_DECRYPT;

        byte[] keyBytes;
        if (hexString) {
            keyBytes = Util.hexStringToBytes(secretKey);
        } else {
            keyBytes = secretKey.getBytes();
        }
        SM4 sm4 = new SM4();
        sm4.sm4_setkey_dec(ctx, keyBytes);
        byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
        return new String(decrypted, "GBK");
    }

    /**
     * CBC 加密
     * CBC方式，使用初始化向量先加密第一个分组，然后使用得到的密文加密第二个数组，加密第二个分组的密文再用来加密第三个分组，依次类推
     * 如此一来，每次即使之后的每组字节相同，也会因为 密钥的不同，而产生不同的密文。剩下的问题就是，如果待加密数据的前两组数据相同，那还是有可能产生两组相同的密文的。为此，CBC使用一个8个字节的随机数来加密第一个分组，其作用类似于口令中的盐
     * @param plainText
     * @return
     */
    public String encryptData_CBC(String plainText) throws Exception {
        if(StrUtil.isEmpty(plainText)){
            return null;
        }
        SM4Context ctx = new SM4Context();
        ctx.isPadding = true;
        ctx.mode = SM4.SM4_ENCRYPT;

        byte[] keyBytes;
        byte[] ivBytes;
        if (hexString) {
            keyBytes = Util.hexStringToBytes(secretKey);
            ivBytes = Util.hexStringToBytes(iv);
        } else {
            keyBytes = secretKey.getBytes();
            ivBytes = iv.getBytes(); // 初始化向量IV
        }

        SM4 sm4 = new SM4();
        sm4.sm4_setkey_enc(ctx, keyBytes);
        byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("GBK"));
        String cipherText = new BASE64Encoder().encode(encrypted);
        if (cipherText != null && cipherText.trim().length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|");
            Matcher m = p.matcher(cipherText);
            cipherText = m.replaceAll("");
        }
        return cipherText;
    }

    /**
     * CBC 解密
     *
     * @param cipherText
     * @return
     */
    public String decryptData_CBC(String cipherText) throws Exception {

        SM4Context ctx = new SM4Context();
        ctx.isPadding = true;
        ctx.mode = SM4.SM4_DECRYPT;

        byte[] keyBytes;
        byte[] ivBytes;
        if (hexString) {
            keyBytes = Util.hexStringToBytes(secretKey);
            ivBytes = Util.hexStringToBytes(iv);
        } else {
            keyBytes = secretKey.getBytes();
            ivBytes = iv.getBytes();  // 初始化向量IV
        }

        SM4 sm4 = new SM4();
        sm4.sm4_setkey_dec(ctx, keyBytes);
        byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
        return new String(decrypted, "GBK");
    }
}
