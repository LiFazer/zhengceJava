package cn.gov.hebei.ylbzj.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncodeUtil {


    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKSEj4EA//imlBzeFFen+b2Jo/SpVUDBJgAzZa+NXKvCELFKhbuOCaFsr7X/gbdim7c0MFkJaT2n/vNNBvhjzhi4nIoCDzy1J+ISzvmX06bQo9Ll1NRJ3MrkLxUOr69XxUqntMypzNmlOS5HyX/GEn+pyixFul5bMKH7vPQq3IuvAgMBAAECgYBROIyscLTuqjanRdCfMfOu6lwCdvL65CCBqt5B3DVrKoUZMiRx4v7ILiNVY85DnjAoM2DHJElLwjuxHE8bpBJkAPDmuUUOzeam8wT5isMwBIoq7al+hlqW84W0oBopGNgDhbAEtP9Q36zB/l2RaLT87zg/6jDRCrZWrUgYrsugMQJBANtzFruulEXTikQLec3AwFgALAYYS4yF7l/3XrP5270TPQfGR6br+tWaJPvQ7qw8STSOvWu8V9ZPdwPXP0mkDEcCQQC/60le0/xmVGRDnKthPzXhZ089UaN12/96zgcPf5QsBLmjixdpNwUI+NGmLH0UsNCB3dSRTggzPwL7pD24+AFZAkAtIKSrIcKB/XdweL+qjRnAvwBOnI5krmIPwAnsjtQeNKeXQrIXZG2noVQ7qIJ8TsVrFnNjhU621o6N7qSoGyEfAkBseh5PhjyszliZys0nu2XGEPG99A4TiRT/ocJl5TOoKceQBAdohhvHdXRym8TbVBGWxf7nXpbN+HjEKZJloXCJAkEAzVj1lXNwq1rrfCsDrcgLpqG/dOf0xMNkwuYaD79jtCDOrdigCSnu2tVBjon0gKygR3e8vq2/iJHVjFkKwty0IQ==";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkhI+BAP/4ppQc3hRXp/m9iaP0qVVAwSYAM2WvjVyrwhCxSoW7jgmhbK+1/4G3Ypu3NDBZCWk9p/7zTQb4Y84YuJyKAg88tSfiEs75l9Om0KPS5dTUSdzK5C8VDq+vV8VKp7TMqczZpTkuR8l/xhJ/qcosRbpeWzCh+7z0KtyLrwIDAQAB";
    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }


    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = getKeyPair();
        String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
        System.out.println("私钥:" + privateKey);
        System.out.println("公钥:" + publicKey);
    }

}
