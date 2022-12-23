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
        )
        ports = listOf("7070")
    }
}
```
and then:
1. `docker network create postgres-dev-network`
2. `docker network connect postgres-dev-network <postgresql-contaner-name>`
3. `docker run --network postgres-dev-network <app-container-name>`

## Apache Bench
`ab -k -c 50 -n 150 "localhost:7070/test/users/argon2"`
