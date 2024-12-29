# CatClient 
[![Build](https://img.shields.io/github/actions/workflow/status/markm001/CatClient/workflow.yml?branch=master)](https://github.com/markm001/CatClient/actions)

A lightweight API Client with minimal dependencies, which supports
several Request Methods, such as: GET, POST and DELETE.

# Contributing

To contribute please see the [issues tab](https://github.com/markm001/CatClient/issues).
Alternatively, see the section containing the desired future improvements.

# Running the project yourself
### Prerequisites

- The project requires JDK 21 or later.

### Project Setup

Download the latest available JAR from the releases.
As an alternative, pull the repository and add the following lines to the build.gradle file:

```
plugins {
    id("maven-publish")
}

publishing {
    publications.create<MavenPublication>("libs") {
        groupId = "com.ccat"
        artifactId = "catclient"
        version = "1.0"

        from(components["java"])
    }
    repositories.maven("/utils")
}
```

This will allow publishing to the Local Maven Repository:
``%USERNAME%\.m2\repository\com\ccat\catclient\1.0``

or to the /utils folder within the project root.

## Future Improvements
- Replace the Gson with our own serialization and deserialization library.
- Improve readability and simplify the code structure further.
- Allow use with or without the Call<> Wrapper.
- Improve readability of tests and test asynchronous execution.

# License

This project is licensed under the [MIT-License](https://en.wikipedia.org/wiki/MIT_License)
