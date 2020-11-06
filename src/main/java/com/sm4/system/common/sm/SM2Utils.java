package com.sm4.system.common.sm;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Random;

/**
 * SM2为非对称加密，基于ECC。该算法已公开。由于该算法基于ECC，故其签名速度与秘钥生成速度都快于RSA。
 * ECC 256位（SM2采用的就是ECC 256位的一种）安全强度比RSA 2048位高，但运算速度快于RSA
 *
 * @create 2019-11-18
 */
public class SM2Utils {

    /**
     * 生成随机秘钥对
     */
    public static void generateKeyPair(){
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();

        System.out.println("生成随机秘钥对 公钥: " + Util.byteToHex(publicKey.getEncoded()));
        System.out.println("生成随机秘钥对 私钥: " + Util.byteToHex(privateKey.toByteArray()));
    }

    /**
     * 加密（String）
     *
     * @param publicKey 公钥
     * @param data 数据(明文)
     * @return
     * @throws Exception
     */
    public static String encrypt(String publicKey, String data) throws Exception{
        return encrypt(Util.hexToByte(publicKey), data.getBytes());
    }

    /**
     * SM2 数据加密
     *
     * @param publicKey 公钥
     * @param data 数据(明文)
     * @return
     * @throws Exception
     */
    public static String encrypt(byte[] publicKey, byte[] data) throws Exception{
        if (publicKey == null || publicKey.length == 0 || data == null || data.length == 0){
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);
        //System.out.println("C1 " + Util.byteToHex(c1.getEncoded()));
        //System.out.println("C2 " + Util.byteToHex(source));
        //System.out.println("C3 " + Util.byteToHex(c3));
        //C1 C2 C3拼装成加密字串
        return Util.byteToHex(c1.getEncoded()) + Util.byteToHex(source) + Util.byteToHex(c3);
    }

    /**
     * SM2 数据解密（String）
     *
     * @param privateKey 私钥
     * @param encryptedData 加密数据
     * @return
     * @throws Exception
     */
    public static String decrypt(String privateKey, String encryptedData) throws Exception{
        return  new String(decrypt(Util.hexToByte(privateKey), Util.hexToByte(encryptedData)));
    }

    /**
     * SM2 数据解密
     *
     * @param privateKey 私钥
     * @param encryptedData 加密数据
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws Exception{
        if (privateKey == null || privateKey.length == 0 || encryptedData == null || encryptedData.length == 0){
            return null;
        }

        // 加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = Util.byteToHex(encryptedData);

        /**
         * 分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130）
         * （C3 = C3实体部分64位  = 64）
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
         */
        byte[] c1Bytes = Util.hexToByte(data.substring(0,130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = Util.hexToByte(data.substring(130,130 + 2 * c2Len));
        byte[] c3 = Util.hexToByte(data.substring(130 + 2 * c2Len,194 + 2 * c2Len));

        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);

        //通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        cipher.Decrypt(c2);
        cipher.Dofinal(c3);

        //返回解密结果
        return c2;
    }

    public static String verifyCode(){
        String str = "";
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }

    public static void main(String[] args) throws Exception {
        // 生成密钥对
        generateKeyPair();
    }
}