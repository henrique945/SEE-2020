package scanner;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

public static Map<String,String> getValues(){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Map<String,String> map = new HashMap<>();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("src\\constant\\Constantes.xml");
			NodeList constantsList = doc.getElementsByTagName("constant");
			
			
			for(int i = 0; i < constantsList.getLength(); i++){
				Node p = constantsList.item(i);
				
				if(p.getNodeType() == Node.ELEMENT_NODE){
				
					Element constant = (Element) p;
					map.put(constant.getAttribute("id"), constant.getElementsByTagName("value").item(0).getTextContent());
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}
}
