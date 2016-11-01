package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by LongZiyuan on 2016/10/21.
 */
public class ParseXml {

    public static HashMap<String,String>  parseXml(String documentStr){
//		 log.info("需要解析的xml:"+documentStr);
        HashMap<String,String> resultMap = new HashMap<String,String>();
        if( documentStr == "" || "null".equals(documentStr) ) return resultMap;
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

    public static String parseXML(Map paraMap) {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("xml");
        if (paraMap != null && paraMap.size() > 0) {
            for (Object entry : paraMap.keySet()) {
                rootElement.addElement(entry.toString()).addText(paraMap.get(entry).toString());
            }
        }
        return document.asXML();
    }

    /**
     * @time:2014-07-18
     * @description:处理字符串
     * @param textValue
     * @return
     */
    private static String doStr(String textValue) {
        if (textValue.indexOf("<![CDATA[") > -1 && textValue.indexOf("]]>") > -1) {
            textValue = textValue.replaceAll("<![CDATA[", "").replaceAll("]]>", "");
        }
        return textValue;
    }

    /**
     * @time:2014-07-14
     * @description:解析子节点
     * @param node
     * @param pareName
     * @param resultMap
     * @return
     */
    private static HashMap<String, String> parseNode(Element node, String pareName, HashMap<String, String> resultMap) {
        if (node == null)
            return resultMap;
        for (Iterator j = node.elementIterator(); j.hasNext();) {
            Element e2 = (Element) j.next();
            String key = e2.attributeValue("name");
            if (!StringUtils.isNotBlank(key) || "null".equals(key))
                key = e2.getName();
            String textValue = e2.getText();
            if (e2.elements() != null && e2.elements().size() > 0) {
                for (int i = 0; i < e2.elements().size(); i++) {
                    parseNode(e2, key, resultMap);
                }
            } else {
                if (!StringUtils.isNotBlank(textValue))
                    continue;
                resultMap.put(key, textValue);
            }
        }
        return resultMap;
    }
}
