package gov.usbr.wq.dataaccess.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

final class TestApiConnectionInfo
{

    @Test
    void testEquals()
    {
        ApiConnectionInfo api1 = new ApiConnectionInfo("https://www.epicurl.com");
        ApiConnectionInfo api2 = new ApiConnectionInfo("https://www.epicurl.com/");
        ApiConnectionInfo api3 = new ApiConnectionInfo("httpsL//www.epicurl2.com");
        assertEquals(api1, api1);
        assertEquals(api1, api2);
        assertNotEquals(api1, api3);
    }
}
