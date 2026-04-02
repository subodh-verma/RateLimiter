# RateLimiter
Design and implement an object-oriented rate limiter for an API gateway.

## TC-01: Basic Limit Enforcement
**Description:** Ensure a user is blocked after exceeding the allowed limit.
**Parameters:** `limit=3`, `window=10s`

### Steps:
1. Send request 1 for User "A" at timestamp 1s.
2. Send request 2 for User "A" at timestamp 2s.
3. Send request 3 for User "A" at timestamp 3s.
4. Send request 4 for User "A" at timestamp 9s.
5. Send request 5 for User "A" at timestamp 12s.

### Expected Result:
- Requests 1, 2, 3 and 5 return `true`.
- Request 4 returns `false`.
---

### Edge Cases:
1. timeStampSeconds cannot be 0. It should throw IndexOutOfBoundsException.
2. timeStampSeconds can only be a natural number.
