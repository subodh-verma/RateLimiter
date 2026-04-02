package com.ratelimiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class RateLimiter 
{
    Logger logger = Logger.getLogger(RateLimiter.class.getName());
    private int limit;
    private int windowSeconds;
    private Map<String, List<Integer>> userInfo = new HashMap<>();

    public RateLimiter(int limit, int windowSeconds){
        this.limit = limit;
        this.windowSeconds = windowSeconds;
        this.userInfo = new HashMap<>();
    }
    
    public boolean allowRequest(String userID, int timestampSeconds){      
        try{  
            if(!userInfo.containsKey(userID))  {       
                userInfo.put(userID,new ArrayList<Integer>((Collections.nCopies(windowSeconds, 0))));    
            } 
            List<Integer> timestamps = userInfo.get(userID);
            
            if(timestampSeconds > windowSeconds)
                for(int i=0; i < timestampSeconds-timestamps.size(); i++){
                    if(timestamps.get(i) < windowSeconds)
                        timestamps.set(i,0);               
                }

            timestamps.set((timestampSeconds-1)%windowSeconds, timestampSeconds);
            int count = 0;
            
            for (int itr : timestamps) {
                if (itr != 0) {
                    count++;
                }
            }
            if(count > limit)
                return false;

            return true;

        }catch(IndexOutOfBoundsException e){
            logger.log(Level.INFO, "Timestamp seconds cannot be " + timestampSeconds);
            return false;
        }
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter(3, 10);
        System.out.println("Rate limit reached : " + rateLimiter.allowRequest("A", 1));
        System.out.println("Rate limit reached : " + rateLimiter.allowRequest("A", 2));
        System.out.println("Rate limit reached : " + rateLimiter.allowRequest("A", 3));
        System.out.println("Rate limit reached : " + rateLimiter.allowRequest("A", 9));
        System.out.println("Rate limit reached : " + rateLimiter.allowRequest("A", 12));
    }
}
