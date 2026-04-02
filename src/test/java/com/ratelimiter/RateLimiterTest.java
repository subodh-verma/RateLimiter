package com.ratelimiter;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit tests for RateLimiter class.
 */
public class RateLimiterTest {
    Logger logger = Logger.getLogger(RateLimiterTest.class.getName());

    private RateLimiter rateLimiter = new RateLimiter(3, 10);;
    List<Integer> timeStamp = Arrays.asList(1, 2, 3, 9, 12);
     
    @Test
    public void testRequest() {
        for(int i : timeStamp){
            try {          
                assertTrue("Rate Limit reached for " + i, rateLimiter.allowRequest("A", i));
                logger.log(Level.INFO, "Timestamp {0} is within limit", i);
            }
            catch (AssertionError e) {
                logger.log(Level.INFO, "Assertion failed for timestamp {0}.", i);
            }
        }
    }
}
