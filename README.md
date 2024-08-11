This Library uses spring security 6 to issue and validate JWT tokens
How to run it?
1. You need a local db running and connected to the library (Configure db username and password in application-local.yaml)
2. JWT uses a secret key to generate and validate JWT tokens. The key can also be changed (application-local.yaml)
3. @PostMapping("/register/user") To register a user to db
4. @PostMapping("register/authenticate") To authenticate user credintials
5. register.requestMatchers("/home", "/register/**").permitAll(); -> These endpoints are accessable for all users.
6. register.requestMatchers("/operator/**").hasRole("OPERATOR"); -> Operator can access any endpoint with /operator/**
7. register.requestMatchers("/admin/**").hasRole("ADMIN"); > Admin can access any endpoint with /admin/**
