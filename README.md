
[![Build Status](https://github.com/nwillc/slf4jkext/workflows/CICD/badge.svg)](https://github.com/nwillc/slf4jkext/actions?query=workflow%3ACICD)
[![license](https://img.shields.io/github/license/nwillc/slf4jkext.svg)](https://tldrlegal.com/license/-isc-license)
[![Download](https://api.bintray.com/packages/nwillc/maven/slf4jkext/images/download.svg)](https://bintray.com/nwillc/maven/slf4jkext/_latestVersion)
---
# slf4jkext
One liner Kotlin extension functions to simplify SLF4J use.

```kotlin
// Use any of these ways...
val logger1 = getLogger<SomeClass>()
val logger2 = getLogger("name")
val logger3 = getLogger(SomeClass::class.java)
```

## See Also

- [API Docs](https://nwillc.github.io/slf4jkext/dokka/slf4jkext)
