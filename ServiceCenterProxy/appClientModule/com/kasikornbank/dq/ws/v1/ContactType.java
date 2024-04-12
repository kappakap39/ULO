/**
 * ContactType.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class ContactType  {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ContactType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    };

    public static final java.lang.String _MOBILE = "MOBILE";
    public static final java.lang.String _TELEPHONE = "TELEPHONE";
    public static final java.lang.String _FAX = "FAX";
    public static final ContactType MOBILE = new ContactType(_MOBILE);
    public static final ContactType TELEPHONE = new ContactType(_TELEPHONE);
    public static final ContactType FAX = new ContactType(_FAX);
    public java.lang.String getValue() { return _value_;}
    public static ContactType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ContactType enumeration = (ContactType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ContactType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}

}
