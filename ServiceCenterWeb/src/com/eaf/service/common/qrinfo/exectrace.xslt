<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/execution-trace">
		<xsl:apply-templates select="execution-events">
			<xsl:with-param name="sp" select="''"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="//execution-events">
		<xsl:param name="sp" />
		<xsl:for-each select="rule">
			<xsl:value-of select="concat($sp, '> ', rule-info/business-name,'&#10;')" />
		</xsl:for-each>
		<xsl:for-each select="task">
			<xsl:value-of select="concat($sp, '+ ', task-info/business-name, '&#10;')" />
			<xsl:apply-templates select="execution-events">
				<xsl:with-param name="sp" select="concat($sp, '  ')"/>
			</xsl:apply-templates>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>