/*
 * COPYRIGHT. HSBC HOLDINGS PLC 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of HSBC Holdings plc.
 */

package services;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.client.WireMock;

/*
public class WireMockHelper {

    private static final Logger log = LoggerFactory.getLogger(WireMockHelper.class);

    public static void refreshWireMock() throws MalformedURLException {
        try {
            // Set up WireMock
            final URL url = new URL("sdaasd");

            WireMock.configureFor(url.getProtocol(), url.getHost(), url.getPort());
            WireMock.reset();
            WireMock.removeAllMappings();
        } catch (final MalformedURLException e) {
            log.error("Unable to parse the wiremock url. Have you generated properties? {}", e.toString());
            throw e;
        }
    }
}
*/
