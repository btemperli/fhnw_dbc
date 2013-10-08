<?xml version="1.0" encoding="UTF-8" ?>

<!--
    ===========================================================================
      Written by Beat Temperli
      (c) 2013, beat@temper.li
    ===========================================================================
-->

<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:d="urn:ZHAW.btemperli"
>
<!--<xsl:stylesheet-->
        <!--version="1.0"-->
        <!--xmlns:xsl="http://www.w3.org/1999/XSL/Transform"-->
    <!-->-->
    <xsl:output
            method="html"
            version="4.0"
            encoding="utf-8"
            indent="yes"/>

    <xsl:key name="mitarbeiter_id" match="d:zhaw/d:projekte/d:projekt/d:mitarbeiter/d:mitarbeiterReferenz" use="@mitarbeiterID"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Projekt 1 - Aufgabe 2 - btemperli</title>
                <!-- bind css -->
                <link rel="stylesheet" type="text/css" href="css/normalize.css" />
                <link rel="stylesheet" type="text/css" href="css/style.css" />
            </head>
            <body style="font-family:'Arial'">
            <h2>ZHAW Personenregister</h2>
            <table border="1">
                <tr bgcolor="#ff9900">
                    <th>Name</th>
                    <th>Bild</th>
                    <th>Projekte</th>
                </tr>
                <xsl:for-each select="d:zhaw/d:personen/d:person">
                    <tr>
                        <td>
                            <a href="mailto:{d:email}" >
                                <xsl:value-of select="d:name"/>
                            </a>
                            <br /><br />
                            <a href="mailto:{d:email}" >
                                Email
                            </a>
                            <br />
                            <xsl:if test="d:webseite">
                                <a href="{d:webseite}">
                                    Webseite
                                </a>
                                <br />
                            </xsl:if>
                        </td>
                        <td>
                            <div class="person-image" style="background-image: url('http://pd.zhaw.ch/portraet/images/{d:kuerzel}.jpg')" />
                        </td>
                        <td>
                            <ul>
                                <xsl:for-each select="key('mitarbeiter_id', @id)">
                                    <li>
                                        <xsl:value-of select="../../d:name" />
                                    </li>
                                </xsl:for-each>
                            </ul>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
            <div class="bottom" />
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>