package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.ResourceAccess;
import gov.usbr.wq.dataaccess.http.ApiConnectionInfo;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.HttpAccessUtils;
import gov.usbr.wq.dataaccess.http.TokenContainer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class ModelTest
{
    static final ApiConnectionInfo MERLIN_TEST_WEB_CONNECTION_INFO = new ApiConnectionInfo("https://www.grabdata2.com");

    TokenContainer getToken() throws HttpAccessException
    {
        String username = ResourceAccess.getUsername();
        char[] password = ResourceAccess.getPassword();
        return HttpAccessUtils.authenticate(MERLIN_TEST_WEB_CONNECTION_INFO, username, password);
    }

    String readFileAsString(String fileName) throws IOException
    {
        byte[] encodedBytes = Files.readAllBytes(getFile(fileName));
        return removeWhiteSpace(new String(encodedBytes, StandardCharsets.UTF_8));
    }

    private Path getFile(String fileName) throws IOException
    {
        String resource = fileName;
        URL resourceUrl = getClass().getClassLoader().getResource(resource);
        if (resourceUrl == null)
        {
            throw new IOException("Failed to get resource: " + resource);
        }
        return new File(resourceUrl.getFile().replace("%20", " ")).toPath();
    }

    private String removeWhiteSpace(String input) {
        StringBuilder output = new StringBuilder();
        boolean insideQuotes = false;
        char[] inputChars = input.toCharArray();

        for (int i = 0; i < inputChars.length; i++) {
            char currentChar = inputChars[i];

            if (currentChar == '"') {
                insideQuotes = !insideQuotes;
            }

            if (!Character.isWhitespace(currentChar) || insideQuotes) {
                output.append(currentChar);
            }
        }

        return output.toString();
    }
}
