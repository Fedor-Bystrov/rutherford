## Rutherford

An application backend for 12in12 project. Provides JWT authorization and authentication as  well as API for different fronted hypothesis applicaitons. 

---

### Instructions
- Run `./gradlew clean jibDockerBuild` to build docker image
- Run `./gradlew clean jib` to build docker image and push it to GCP registry (TODO)


### Run rutherford container locally
Add `environment` and `ports` to jib cofig
```kotlin
jib {
    //...
    container {
        // ...
        environment = mapOf(
            "DB_URL" to "jdbc:postgresql://postgres-dev:5432/rutherford",
            "DB_USER" to "rutherford_app",
            "DB_PASS" to "123",
            "PORT" to "7070",
            "AUTH_USER_SECRET" to "nX278YBYkZyLu9CKaqj8xFr8Vq/OILVlbkJ0C+tF08g=",
        )
        ports = listOf("7070")
    }
}
```
and then:
1. `docker network create postgres-dev-network`
2. `docker network connect postgres-dev-network <postgresql-contaner-name>`
3. `docker run --network postgres-dev-network <app-container-name>`

## Notes
- secret length is 32 bytes
- salt length is 16 bytes

## Apache Bench
`ab -k -c 50 -n 150 "localhost:7070/test/users/argon2"`

# TODOs
1. Basic Auth Functionality It should have following functionalities:
   - sign_in (issue access and refresh tokens given correct user details)
        - Use asymmetric encryption for JWT?
        - Expose JWT public key list endpoint?
   - log_out (remove refresh_token i.e. move refresh_token to deleted state)
   - change_password
   - anything else (check .net identity as a reference)

2. Add CORS, allow only my apps to access the BE

3. Email confirmation Functionality
   - sign_up should send an email to confirm the user's email address
   - sign_in should return error and ask to confirm the email
       - user should be able to resend confirmation email
