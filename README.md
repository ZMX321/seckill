## Seckill

### E-commerce flash sale system

Author: Zhiming Xue

## Features(V1.0)

* Based on Spring Boot to deal with high-traffic and high-concurrency scenarios
* Using MyBatis as the persistence framework, Redis as the cache, and MySQL as the database
* Using RocketMQ as a message middleware to prevent the database from crashing in a flash sale scenario
* Using snowflake algorithm to generate order ID

## Features(V1.1)

* Adding the cache preheating function,  the flash sale activities and product information are added to the Redis database in advance to improve the page access speed
* Using thymeleaf to make static pages to improve page access speed

## Features(V1.2)

* Add the Sentinel flow control framework to provide the system with traffic limiting and service fuse mechanisms in high-traffic situations to maintain the stable operation of the system

