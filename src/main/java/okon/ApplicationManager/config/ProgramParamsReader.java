package okon.ApplicationManager.config;

import okon.ApplicationManager.AppException;
import okon.ApplicationManager.Main;
import okon.ApplicationManager.Program;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProgramParamsReader {
    private static final Logger logger = LogManager.getLogger(ProgramParamsReader.class);

    public static List<Program> readConfigParams(File file) {
        Element root = parseXml(file);
        List<Program> result = new ArrayList<>();
        NodeList programs = root.getElementsByTagName("program");
        if (programs != null && programs.getLength() > 0) {
            for (int i = 0; i < programs.getLength(); i++) {
                Node program = programs.item(i);
                if (program.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) program;
                    String alias = element.getElementsByTagName("alias").item(0).getTextContent();
                    String filename = element.getElementsByTagName("filename").item(0).getTextContent();
                    String path = element.getElementsByTagName("path").item(0).getTextContent();
                    result.add(new Program(alias, filename, path));
                }
            }
        }
        if (Main.isLoggerEnable()) {
            logger.debug("ReadConfigParams(" + file.getName() + ") : OK");
        }
        return result;
    }

    private static Element parseXml(File file) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            return document.getDocumentElement();
        } catch (Exception e) {
            if (Main.isLoggerEnable()) {
                logger.error("ParseXml(" + file.getName() + ") : " + e.getMessage());
            }
            throw new AppException(e);
        }
    }
}
