package controller;

import dao.PhieuMuonDAO;
import model.phieuMuon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;

public class XmlLogController {
	
	public static void ghiLogQuaHan() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("PhieuQuaHan");
            doc.appendChild(root);

            List<phieuMuon> list = new PhieuMuonDAO().getAll();
            for (phieuMuon pm : list) {
                if ("Quá hạn".equals(pm.getTrangThai())) {
                    Element e = doc.createElement("Phieu");
                    e.setAttribute("ma", String.valueOf(pm.getMaPhieuMuon()));
                    e.appendChild(createElement(doc, "BanDoc", pm.getTenBanDoc()));
                    e.appendChild(createElement(doc, "NgayHenTra", pm.getNgayHenTra().toString()));
                    root.appendChild(e);
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(doc), new StreamResult(new File("log_qua_han.xml")));

            System.out.println("Đã ghi log quá hạn vào file: log_qua_han.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private static Element createElement(Document doc, String name, String value) {
        Element e = doc.createElement(name);
        e.appendChild(doc.createTextNode(value));
        return e;
    }
	
	public static void main(String[] args) {
        ghiLogQuaHan();
    }
	
}
