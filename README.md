Assumption for the development.

1. User can search by booking id only
2. One room per reservation
3. For changing the status to cancel put has been used instead of patch.
4. Please create rooms before calling reservation api. Request to create new room is in collection.

Note: 
1. MySQL database has been used for persistence.
2. Service class have all the logic so has been covered 100% with JUnit.
3. Mockito has been used along with JUnit.
4. Spring boot 3.x version is used.
5. There is an API for room create also. So if user want to create a new room in hotel they can do that.
6. Collection of the request is attached in the source.

Non- Functional Req.
 load factor : 3000000 / 3600 = 833.333
 
 To maintain this much load with my sql server is pretty easy so we need not to worry about latency.