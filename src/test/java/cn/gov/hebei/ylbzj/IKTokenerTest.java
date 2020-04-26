/**
 *
 */
package cn.gov.hebei.ylbzj;

import junit.framework.TestCase;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 林良益
 */
public class IKTokenerTest extends TestCase {

    public void testLucene3Tokenizer() {
        String t = "医疗保问题政策医保解答医保";
        IKTokenizer tokenizer = new IKTokenizer(new StringReader(t), false);
        StringBuffer sb = new StringBuffer();
        try {
            while (tokenizer.incrementToken()) {
                TermAttribute termAtt = tokenizer.getAttribute(TermAttribute.class);
                //System.out.println(termAtt);
                sb.append(termAtt).append("|");
            }

            Object[] objects = deleteSubString(sb.toString().substring(0,sb.length()-1), "term=");
            System.out.println(objects[0].toString());
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * 去除字符串str1中的str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public Object[] deleteSubString(String str1, String str2) {
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
