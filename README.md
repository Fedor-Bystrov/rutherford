
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

