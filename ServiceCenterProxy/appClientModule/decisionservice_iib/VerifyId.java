//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verifyId.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="verifyId">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VERIFY_CUSTOMER"/>
 *     &lt;enumeration value="VERIFY_HR"/>
 *     &lt;enumeration value="VERIFY_INCOME"/>
 *     &lt;enumeration value="VERIFY_WEB"/>
 *     &lt;enumeration value="VERIFY_PROJECT_CODE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "verifyId")
@XmlEnum
public enum VerifyId {

    VERIFY_CUSTOMER,
    VERIFY_HR,
    VERIFY_INCOME,
    VERIFY_WEB,
    VERIFY_PROJECT_CODE;

    public String value() {
        return name();
    }

    public static VerifyId fromValue(String v) {
        return valueOf(v);
    }

}
