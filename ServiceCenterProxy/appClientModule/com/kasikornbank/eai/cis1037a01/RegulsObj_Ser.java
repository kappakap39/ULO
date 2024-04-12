/**
 * RegulsObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class RegulsObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public RegulsObj_Ser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    public void serialize(
        javax.xml.namespace.QName name,
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        context.startElement(name, addAttributes(attributes, value, context));
        addElements(value, context);
        context.endElement();
    }
    protected org.xml.sax.Attributes addAttributes(
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
           javax.xml.namespace.QName
           elemQName = QName_0_162;
           context.qName2String(elemQName, true);
           elemQName = QName_0_163;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        RegulsObj bean = (RegulsObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_162;
          propValue = bean.getRegulsVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_164,
              false,null,context);
          propQName = QName_0_163;
          propValue = bean.getTotRegulCnt();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_91,
              true,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_0_162 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "regulsVect");
    private final static javax.xml.namespace.QName QName_0_91 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "intLength2");
    private final static javax.xml.namespace.QName QName_0_164 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>>>doServiceResponse>CIS1037A01Response>CISIndivCust>CVRSInfoObj>regulsObj>regulsVect");
    private final static javax.xml.namespace.QName QName_0_163 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "totRegulCnt");
}
