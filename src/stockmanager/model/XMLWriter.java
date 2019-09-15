package stockmanager.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class can write a portfolio into a XML file to the given path.
 */
class XMLWriter {
  private String fileName;
  private Document document;

  /**
   * Constructs XMLWriter object to initialize the file path with the given filePath.
   *
   * @param fileName the file name containing path
   * @throws ParserConfigurationException if build new document fails
   */
  XMLWriter(String fileName)
          throws ParserConfigurationException {
    this.fileName = fileName;
    createXmlDocument();
  }

  /**
   * Creates a new document which will become the XML file.
   *
   * @throws ParserConfigurationException if build new document fails
   */
  private void createXmlDocument() throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    document = builder.newDocument();
  }

  /**
   * Creates the document element of and return it.
   *
   * @param name create document element with given name
   * @return the document element
   */
  Element createDocumentElement(String name) {
    Element root = document.createElement(name);
    document.appendChild(root);
    return root;
  }

  /**
   * Creates an element with given parent element, tag name and text, then returns it.
   *
   * @param parent  parent element
   * @param tagName tag name of the created element
   * @param text    text contend of the created element
   * @return an element with given parent element, tag name and text
   */
  Element createElement(Element parent, String tagName, String text) {
    Element element = document.createElement(tagName);
    if (text != null && text.length() > 0) {
      element.setTextContent(text);
    }
    parent.appendChild(element);
    return element;
  }

  /**
   * Saves this XML file with portfolio data in it.
   *
   * @throws FileNotFoundException if file not found
   * @throws TransformerException  if transform fails
   */
  void toSave() throws FileNotFoundException, TransformerException {
    TransformerFactory factory = TransformerFactory.newInstance();
    factory.setAttribute("indent-number", new Integer(4));
    Transformer transformer = factory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(document);
    transformer.transform(source, new StreamResult(new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))));
  }
}
