/**
 * LocationType.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class LocationType  {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LocationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    };

    public static final java.lang.String _HOME = "HOME";
    public static final java.lang.String _OFFICE = "OFFICE";
    public static final LocationType HOME = new LocationType(_HOME);
    public static final LocationType OFFICE = new LocationType(_OFFICE);
    public java.lang.String getValue() { return _value_;}
    public static LocationType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        LocationType enumeration = (LocationType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static LocationType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}

}
