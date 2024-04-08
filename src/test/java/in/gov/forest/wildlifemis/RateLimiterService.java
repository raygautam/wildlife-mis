//package in.gov.forest.wildlifemis;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class RateLimiterService {
//
//    private final Map<String, Integer> requestCountMap = new ConcurrentHashMap<>();
//    private final int requestLimit = 100; // Maximum allowed requests per client
//
//    public synchronized boolean allowRequest(String clientId) {
//        requestCountMap.putIfAbsent(clientId, 0);
//        int count = requestCountMap.get(clientId);
//        if (count >= requestLimit) {
//            return false; // Rate limit exceeded
//        } else {
//            requestCountMap.put(clientId, count + 1);
//            return true;
//        }
//    }
//}
//
