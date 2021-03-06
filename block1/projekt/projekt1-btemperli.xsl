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
    <xsl:output
            method="html"
            version="4.0"
            encoding="utf-8"
            indent="yes"/>

    <xsl:key name="mitarbeiter_id" match="d:zhaw/d:projekte/d:projekt/d:mitarbeiter/d:mitarbeiterReferenz" use="@mitarbeiterID"/>

    <xsl:template match="/">
        <html>

            <head>
                <!-- set title in browser -->
                <title>Projekt 1 - btemperli</title>

                <!-- bind css files -->
                <link rel="stylesheet" type="text/css" href="css/normalize.css" />
                <link rel="stylesheet" type="text/css" href="css/style.css" />
            </head>

            <body style="font-family:'Arial'">
                <h1>ZHAW Personenregister</h1>
                <table border="1">
                    <tr bgcolor="#ff9900">
                        <th>Name</th>
                        <th>Bild</th>
                        <th>Map</th>
                        <th>Social</th>
                        <th>Projekte</th>
                    </tr>
                    <xsl:for-each select="d:zhaw/d:personen/d:person">
                        <tr>
                            <td>
                                <p class="uppercase">
                                    <b>
                                        <xsl:value-of select="d:kuerzel" />
                                    </b>
                                </p>
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
                                <xsl:if test="d:phone">
                                    <a href="skype:{d:phone}?call">
                                        Call via Skype
                                    </a>
                                </xsl:if>
                            </td>
                            <td>
                                <div class="person-image" style="background-image: url('http://pd.zhaw.ch/portraet/images/{d:kuerzel}.jpg')" />
                            </td>
                            <td>
                                <xsl:if test="d:adresse">
                                    <div style="width:200px;height:200px">
                                        <iframe
                                                width="200"
                                                height="200"
                                                frameborder="0"
                                                scrolling="no"
                                                marginheight="0"
                                                marginwidth="0"
                                                src="http://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q={d:adresse/d:strasse}%2B{d:adresse/d:nummer}%2B{d:adresse/d:plz}%2B{d:adresse/d:ort}&amp;ie=UTF8&amp;z=12&amp;t=m&amp;iwloc=near&amp;output=embed" />
                                    </div>
                                </xsl:if>
                            </td>
                            <td>
                                <xsl:if test="d:facebook">
                                    <a href="http://www.facebook.com/{d:facebook/d:profile-username}">
                                        <img src="http://graph.facebook.com/{d:facebook/d:profile-username}/picture" />
                                    </a>
                                </xsl:if>
                                <br />
                                <br />
                                <xsl:if test="d:twitter">
                                    <a href="http://twitter.com/{d:twitter/d:profile-username}">
                                        @<xsl:value-of select="d:twitter/d:profile-username" />
                                    </a>
                                </xsl:if>
                            </td>

                            <!-- Projekte -->
                            <td>
                                <ul>
                                    <xsl:for-each select="key('mitarbeiter_id', @id)">
                                        <xsl:variable name="umsatz-pro-person" select="format-number(((../../d:umsatz) div count(../d:mitarbeiterReferenz)), '#,###.0')" />
                                        <li>
                                            <b><xsl:value-of select="../../d:name" /></b>
                                            <br />
                                            Umsatz Projekt: <xsl:value-of select="format-number((../../d:umsatz), '#,###.0')" />
                                            <br />
                                            Umsatz/Person: <xsl:value-of select="$umsatz-pro-person" />
                                        </li>
                                    </xsl:for-each>
                                </ul>
                                <xsl:variable name="umsatztotal-person" select="format-number((sum(key('mitarbeiter_id', @id)/../../d:umsatz)), '#,###')" />
                                Umsatz beteiligter Projekte:
                                <br />
                                <xsl:value-of select="$umsatztotal-person" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>