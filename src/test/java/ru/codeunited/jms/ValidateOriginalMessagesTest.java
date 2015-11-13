package ru.codeunited.jms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

/**
 * Created by ikonovalov on 13.11.15.
 */
public class ValidateOriginalMessagesTest {

    private Logger logger = LoggerFactory.getLogger(ValidateOriginalMessagesTest.class);

    @Test
    public void validate() throws ParserConfigurationException, IOException, SAXException {

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(ValidateOriginalMessagesTest.class.getResourceAsStream("/v5_2.xsd"));
        Schema schema = factory.newSchema(schemaFile);
        logger.debug("Schema uploaded.");

        Validator validator = schema.newValidator(); // not thread safe and not reenterz
        logger.debug("Validator ready.");

        try {
            validator.validate(new StreamSource(ValidateOriginalMessagesTest.class.getResourceAsStream("/message.xml")));
        } catch (SAXException e) {
            logger.error("Validation failed. {}", e.getMessage());
            throw e;
        }
    }
}