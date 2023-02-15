package gov.usbr.wq.dataaccess.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TokenValidatorTest
{
    @Test
    void testIsTokenExpired()
    {
        //expired token from 2022
        TokenContainer token = new JwtContainer("bad_token_placeholder");
        assertTrue(token.isExpired());
    }

    @Test
    void testIsTokenNotExpired()
    {
        //token that expires thousands of years in the future
        TokenContainer token = new JwtContainer("good_token_placeholder");
        assertFalse(token.isExpired());
    }
}
