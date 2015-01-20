package xyz.anduo.myretrieval.util;

import java.math.BigInteger;

import junit.framework.TestCase;

public class MD5UtilsTest extends TestCase {

  public void testGetMD5ofStr() {
    String str1 = "历史";//6B2285CD0FC398CB44EA5566720A65B5
    String str2 = "秦朝";
    MD5Utils md5Utils = new MD5Utils();
    String md51 = md5Utils.getMD5ofStr(str1);
    String md52 = md5Utils.getMD5ofStr(str2);
    System.out.println(md51);
    BigInteger bi1 = new BigInteger(md51, 16);
    BigInteger bi2 = new BigInteger(md52,16);
    System.out.println(bi1.add(bi2));
  }

}
