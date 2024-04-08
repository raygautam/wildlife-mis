//package in.gov.forest.wildlifemis;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RateLimiterServiceTest {
//
//    private RateLimiterService rateLimiterService;
//
//    @BeforeEach
//    void setUp() {
//        rateLimiterService = new RateLimiterService();
//    }
//
//    @Test
//    void testRateLimitWithinLimit() {
//        String clientId = "client1";
//
//        // Allow requests within limit
//        for (int i = 0; i < 10; i++) {
//            assertTrue(rateLimiterService.allowRequest(clientId));
//        }
//    }
//
//    @Test
//    void testRateLimitExceeded() {
//        String clientId = "client2";
//
//        // Allow requests within limit
//        for (int i = 0; i < 100; i++) {
//            assertTrue(rateLimiterService.allowRequest(clientId));
//        }
//
//        // Requests exceed the limit
//        assertFalse(rateLimiterService.allowRequest(clientId));
//    }
//}
//
