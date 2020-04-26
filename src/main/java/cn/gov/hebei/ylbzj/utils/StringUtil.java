package cn.gov.hebei.ylbzj.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.IOException;
import java.io.StringReader;
@Slf4j
public class StringUtil {
    public static String str2RegStr(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        char[] ar = str.toCharArray();
        StringBuilder sbu = new StringBuilder();
        for (int i = 0; i < ar.length; i++) {
            sbu.append(ar[i]).append("|");
        }
        return sbu.toString().substring(0,sbu.length()-1);
    }

    /**
     * 分词
     * @param msg
     * @return
     */
    public static String iKSegmentMsg(String msg){
        IKTokenizer tokenizer = new IKTokenizer(new StringReader(msg), false);
        StringBuffer sb = new StringBuffer();
        try {
            while (tokenizer.incrementToken()) {
                TermAttribute termAtt = tokenizer.getAttribute(TermAttribute.class);
                sb.append(termAtt).append("|");
            }
            Object[] objects = deleteSubString(sb.toString().substring(0,sb.length()-1), "term=");
            return objects[0].toString();
        } catch (IOException e) {
            log.error("分词错误,p{},e=",msg,e);
            return "";
        }
    }

    /**
     * 去除字符串str1中的str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Object[] deleteSubString(String str1, String str2) {
        StringBuffer sb = new StringBuffer(str1);
        int delCount = 0;
        Object[] obj = new Object[2];

        while (true) {
            int index = sb.indexOf(str2);
            if (index == -1) {
                break;
            }
            sb.delete(index, index + str2.length());
            delCount++;

        }
        if (delCount != 0) {
            obj[0] = sb.toString();
            obj[1] = delCount;
        } else {
            //不存在返回-1
            obj[0] = -1;
            obj[1] = -1;
        }

        return obj;
    }
}
