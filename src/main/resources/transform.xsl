<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:key name="beersByType" match="Beer" use="Type"/>

    <xsl:template match="/BeerCatalog">
        <Root>
            <xsl:for-each select="Beer[generate-id() = generate-id(key('beersByType', Type)[1])]">
                <xsl:element name="{Type}">
                    <xsl:for-each select="key('beersByType', Type)">
                        <Beer id="{@id}">
                            <Name><xsl:value-of select="Name"/></Name>
                            <Manufacturer><xsl:value-of select="Manufacturer"/></Manufacturer>
                            <AlcoholVolume><xsl:value-of select="Chars/AlcoholVolume"/></AlcoholVolume>
                        </Beer>
                    </xsl:for-each>
                </xsl:element>
            </xsl:for-each>
        </Root>
    </xsl:template>
</xsl:stylesheet>