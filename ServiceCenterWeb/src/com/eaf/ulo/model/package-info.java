@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value=com.eaf.core.ulo.common.date.SqlDateAdapter.class, type=java.sql.Date.class),
    @XmlJavaTypeAdapter(value=com.eaf.core.ulo.common.date.SqlTimestampAdapter.class, type=java.sql.Timestamp.class)
})

package com.eaf.ulo.model;
import javax.xml.bind.annotation.adapters.*; 