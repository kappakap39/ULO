//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.eai.eaiws.kbank1550i01.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.kasikornbank.eai.eaiws.kbank1550i01.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DoService_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "doService");
    private final static QName _DoServiceResponse_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "doServiceResponse");
    private final static QName _DoServiceResponseTFB0152I01ResponseEAIHeaderUserId_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "userId");
    private final static QName _DoServiceResponseTFB0152I01ResponseEAIHeaderPassword_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "password");
    private final static QName _DoServiceResponseTFB0152I01ResponseINDCustomerName_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "name");
    private final static QName _DoServiceResponseTFB0152I01ResponseAMCCustomerCustName_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "custName");
    private final static QName _DoServiceTFB0152I01EAIHeaderEndTimestamp_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "endTimestamp");
    private final static QName _DoServiceTFB0152I01EAIHeaderStatus_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "status");
    private final static QName _DoServiceTFB0152I01EAIHeaderSourceTransactionId_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "sourceTransactionId");
    private final static QName _DoServiceTFB0152I01EAIHeaderReasonCode_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "reasonCode");
    private final static QName _DoServiceTFB0152I01EAIHeaderTransactionId_QNAME = new QName("http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", "transactionId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.kasikornbank.eai.eaiws.kbank1550i01.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DoServiceResponse }
     * 
     */
    public DoServiceResponse createDoServiceResponse() {
        return new DoServiceResponse();
    }

    /**
     * Create an instance of {@link DoServiceResponse.TFB0152I01Response }
     * 
     */
    public DoServiceResponse.TFB0152I01Response createDoServiceResponseTFB0152I01Response() {
        return new DoServiceResponse.TFB0152I01Response();
    }

    /**
     * Create an instance of {@link DoServiceResponse.TFB0152I01Response.EAIHeader }
     * 
     */
    public DoServiceResponse.TFB0152I01Response.EAIHeader createDoServiceResponseTFB0152I01ResponseEAIHeader() {
        return new DoServiceResponse.TFB0152I01Response.EAIHeader();
    }

    /**
     * Create an instance of {@link DoService }
     * 
     */
    public DoService createDoService() {
        return new DoService();
    }

    /**
     * Create an instance of {@link DoService.TFB0152I01 }
     * 
     */
    public DoService.TFB0152I01 createDoServiceTFB0152I01() {
        return new DoService.TFB0152I01();
    }

    /**
     * Create an instance of {@link DoServiceResponse.TFB0152I01Response.INDCustomer }
     * 
     */
    public DoServiceResponse.TFB0152I01Response.INDCustomer createDoServiceResponseTFB0152I01ResponseINDCustomer() {
        return new DoServiceResponse.TFB0152I01Response.INDCustomer();
    }

    /**
     * Create an instance of {@link DoServiceResponse.TFB0152I01Response.AMCCustomer }
     * 
     */
    public DoServiceResponse.TFB0152I01Response.AMCCustomer createDoServiceResponseTFB0152I01ResponseAMCCustomer() {
        return new DoServiceResponse.TFB0152I01Response.AMCCustomer();
    }

    /**
     * Create an instance of {@link DoServiceResponse.TFB0152I01Response.TMCCustomer }
     * 
     */
    public DoServiceResponse.TFB0152I01Response.TMCCustomer createDoServiceResponseTFB0152I01ResponseTMCCustomer() {
        return new DoServiceResponse.TFB0152I01Response.TMCCustomer();
    }

    /**
     * Create an instance of {@link DoServiceResponse.TFB0152I01Response.EAIHeader.Error }
     * 
     */
    public DoServiceResponse.TFB0152I01Response.EAIHeader.Error createDoServiceResponseTFB0152I01ResponseEAIHeaderError() {
        return new DoServiceResponse.TFB0152I01Response.EAIHeader.Error();
    }

    /**
     * Create an instance of {@link DoService.TFB0152I01 .EAIHeader }
     * 
     */
    public DoService.TFB0152I01 .EAIHeader createDoServiceTFB0152I01EAIHeader() {
        return new DoService.TFB0152I01 .EAIHeader();
    }

    /**
     * Create an instance of {@link DoService.TFB0152I01 .CISCustomer }
     * 
     */
    public DoService.TFB0152I01 .CISCustomer createDoServiceTFB0152I01CISCustomer() {
        return new DoService.TFB0152I01 .CISCustomer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "doService")
    public JAXBElement<DoService> createDoService(DoService value) {
        return new JAXBElement<DoService>(_DoService_QNAME, DoService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "doServiceResponse")
    public JAXBElement<DoServiceResponse> createDoServiceResponse(DoServiceResponse value) {
        return new JAXBElement<DoServiceResponse>(_DoServiceResponse_QNAME, DoServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "userId", scope = DoServiceResponse.TFB0152I01Response.EAIHeader.class)
    public JAXBElement<String> createDoServiceResponseTFB0152I01ResponseEAIHeaderUserId(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseEAIHeaderUserId_QNAME, String.class, DoServiceResponse.TFB0152I01Response.EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "password", scope = DoServiceResponse.TFB0152I01Response.EAIHeader.class)
    public JAXBElement<String> createDoServiceResponseTFB0152I01ResponseEAIHeaderPassword(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseEAIHeaderPassword_QNAME, String.class, DoServiceResponse.TFB0152I01Response.EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "name", scope = DoServiceResponse.TFB0152I01Response.INDCustomer.class)
    public JAXBElement<String> createDoServiceResponseTFB0152I01ResponseINDCustomerName(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseINDCustomerName_QNAME, String.class, DoServiceResponse.TFB0152I01Response.INDCustomer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "custName", scope = DoServiceResponse.TFB0152I01Response.AMCCustomer.class)
    public JAXBElement<String> createDoServiceResponseTFB0152I01ResponseAMCCustomerCustName(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseAMCCustomerCustName_QNAME, String.class, DoServiceResponse.TFB0152I01Response.AMCCustomer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "endTimestamp", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<XMLGregorianCalendar> createDoServiceTFB0152I01EAIHeaderEndTimestamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DoServiceTFB0152I01EAIHeaderEndTimestamp_QNAME, XMLGregorianCalendar.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "status", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<Integer> createDoServiceTFB0152I01EAIHeaderStatus(Integer value) {
        return new JAXBElement<Integer>(_DoServiceTFB0152I01EAIHeaderStatus_QNAME, Integer.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "userId", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<String> createDoServiceTFB0152I01EAIHeaderUserId(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseEAIHeaderUserId_QNAME, String.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "sourceTransactionId", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<String> createDoServiceTFB0152I01EAIHeaderSourceTransactionId(String value) {
        return new JAXBElement<String>(_DoServiceTFB0152I01EAIHeaderSourceTransactionId_QNAME, String.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "password", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<String> createDoServiceTFB0152I01EAIHeaderPassword(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseEAIHeaderPassword_QNAME, String.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "reasonCode", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<Integer> createDoServiceTFB0152I01EAIHeaderReasonCode(Integer value) {
        return new JAXBElement<Integer>(_DoServiceTFB0152I01EAIHeaderReasonCode_QNAME, Integer.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "transactionId", scope = DoService.TFB0152I01 .EAIHeader.class)
    public JAXBElement<String> createDoServiceTFB0152I01EAIHeaderTransactionId(String value) {
        return new JAXBElement<String>(_DoServiceTFB0152I01EAIHeaderTransactionId_QNAME, String.class, DoService.TFB0152I01 .EAIHeader.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eai.kasikornbank.com/EAIWS/KBANK1550I01/v1", name = "name", scope = DoServiceResponse.TFB0152I01Response.TMCCustomer.class)
    public JAXBElement<String> createDoServiceResponseTFB0152I01ResponseTMCCustomerName(String value) {
        return new JAXBElement<String>(_DoServiceResponseTFB0152I01ResponseINDCustomerName_QNAME, String.class, DoServiceResponse.TFB0152I01Response.TMCCustomer.class, value);
    }

}
