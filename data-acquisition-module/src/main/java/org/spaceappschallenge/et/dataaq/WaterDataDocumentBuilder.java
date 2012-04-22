package org.spaceappschallenge.et.dataaq;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WaterDataDocumentBuilder {

	public void convertToXml(List<WaterDataBean> list, File out) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			
			Document doc = builder.newDocument();
			Element root = doc.createElement("precipitation_info");
			doc.appendChild(root);
			Element lstElm = doc.createElement("list");
			
			root.appendChild(lstElm);
			
			for (WaterDataBean bean : list) {
				Element elem = doc.createElement("element");
				elem.setAttribute("date", bean.getDate());
				elem.setAttribute("value", Double.toString( bean.getResult() ));
				elem.setAttribute("station_id", bean.getSiteNo());
				elem.setAttribute("precip_interval", bean.getPecipInter7s());
				lstElm.appendChild(elem);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			
			StreamResult result = new StreamResult(out);
			transformer.transform(domSource, result);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
