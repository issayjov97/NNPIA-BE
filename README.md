**BackEnd for Skills exchange application**

**Description**

Skills exchange is the place, where exchanging skills and talents becomes easy.
Swap your skills, find what you are looking for.
It's all free, you just "pay" with your time.

**Development tools**
* Spring framework
* Kotlin
* IntelliJ IDEA
* Postgresql
* Flyway
* JDK 11

**API descriprion**
* [POST]/api/v1/auth/login  - for login
* [POST]/api/v1/auth/logout - for logout
* [POST]/api/v1/auth/signup - for signup
* [GET]/api/v1/categories  - return all catgegories
* [POST]/api/v1/auth/posts - create a new post
* [PUT]/api/v1/auth/posts/{postId} - update created post
* [DELETE]/api/v1/auth/posts/{postId} - delete created post
* [POST]/api/v1/auth/posts/search - return posts by conditions
* [GET]/api/v1/auth/posts/{postId} - return selected post
* [POST]/api/v1/transactions - create new transaction (user request, who is interested in some skills to share or get)
* [GET]/api/v1/transactions - return transactions
* [PUT]/api/v1/transactions - update transaction state
* [POSR]/api/v1/users/password - change user password
* [PUT]/api/v1/users/info - update user's information
* [POST]/api/v1/users/wishlist - add post to user's wishlist
* [GET]/api/v1/users/wishlist - return user's wishlist

**Databaze schema**

[
![talent_exchange](https://user-images.githubusercontent.com/57341735/120441987-7111f100-c385-11eb-9309-589a93f25ddd.png)
](url)

**Technical requirments**

Before launching Skills exchange app, install Postgresql and clone that repository. You also must to install JDK in order to start that application.
Users for testing: 
* username: st55409@upce.cz password: 275822dd,
* username: jovkhar.issayev@addai.cz password: 275822df.

That application was deployed on Heroku.

BE url(temporary): https://nnpia-skillsap-be.herokuapp.com/
