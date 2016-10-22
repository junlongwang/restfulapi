package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joybike.server.api.thirdparty.huaxinSdk.StringUtils;
import javax.swing.text.Document;
import javax.xml.bind.Element;

/**
 * Created by LongZiyuan on 2016/10/21.
 */
public class ParseXml {

    public static HashMap<String,String>  parseXml(String documentStr){
//		 log.info("需要解析的xml:"+documentStr);
        HashMap<String,String> resultMap = new HashMap<String,String>();
        if( !StringUtils.isNotBlank(documentStr) || "null".equals(documentStr) ) return resultMap;
        try {
            Document document = DocumentHelper.parseText(documentStr.toString());
            Element root =document.getRootElement();
            for( Iterator i=root.elementIterator();i.hasNext(); ){
                Element e1 =(Element)i.next();
                if( e1 == null ) continue;
                String parentName=e1.getName();
                String textValue = e1.getText();
                textValue=doStr(textValue);

	                /*//仅仅解析response里的内容和签名信息
	                if( !"sign".equals(parentName) && !"sign_type".equals(parentName)
	                        && !"response".equals(parentName) && !"is_success".equals(parentName) && !"error".equals(parentName)) continue; */
                //log.info("name \t"+e1.getName()+" \t "+e1.getText()+" \t e1.count \t"+(e1.elements()==null?"0":e1.elements().size()));
                int nodeCount = e1.elements()==null?0:e1.elements().size();
                if( nodeCount > 0 ){//循环得到子节点
                    resultMap=parseNode(e1,parentName,resultMap);
                }else{//无字节点
                    if( !StringUtils.isNotBlank(textValue) || "null".equals(textValue)  ) continue;
                    resultMap.put(parentName, textValue);
                }
            }
        }catch (Exception e) {
            return null;
        }
        return resultMap;
    }
}
