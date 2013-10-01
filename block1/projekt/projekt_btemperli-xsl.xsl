<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output
            method="html"
            version="4.0"
            encoding="utf-8"
            indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Projekt 1 - btemperli</title>
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
                    <th>Map</th>
                    <th>Facebook</th>
                    <th>Twitter</th>
                </tr>
                <xsl:for-each select="zhaw/personen/person">
                    <tr>
                        <td>
                            <a href="mailto:{email}" >
                                <xsl:value-of select="name"/>
                            </a>
                            <br /><br />
                            <a href="{webseite}">
                                Webseite
                            </a>
                        </td>
                        <td>
                            <div class="person-image" style="background-image: url('http://pd.zhaw.ch/portraet/images/{kuerzel}.jpg')" />
                        </td>
                        <td>
                            <div style="width:200px;height:200px">
                                <iframe
                                        width="200"
                                        height="200"
                                        frameborder="0"
                                        scrolling="no"
                                        marginheight="0"
                                        marginwidth="0"
                                        src="http://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q={adresse/strasse}%2B{adresse/nummer}%2B{adresse/plz}%2B{adresse/ort}&amp;ie=UTF8&amp;z=12&amp;t=m&amp;iwloc=near&amp;output=embed" />
                            </div>
                        </td>
                        <td>
                            <a href="http://www.facebook.com/{facebook/profile-username}">
                                <img src="http://graph.facebook.com/{facebook/profile-username}/picture" />
                            </a>
                        </td>
                        <td>
                            <a href="http://twitter.com/{twitter/profile-username}">
                                @<xsl:value-of select="twitter/profile-username" />
                            </a>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
            <div class="bottom" />
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>