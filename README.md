ieTanks
=======

Branch develop status:

[![Build Status](https://travis-ci.org/artekorzel/ieTanks.png?branch=develop)](https://travis-ci.org/artekorzel/ieTanks)
[![Coverage Status](https://coveralls.io/repos/artekorzel/ieTanks/badge.png?branch=develop)](https://coveralls.io/r/artekorzel/ieTanks?branch=develop)


Branch master status:

[![Build Status](https://travis-ci.org/artekorzel/ieTanks.png?branch=master)](https://travis-ci.org/artekorzel/ieTanks)
[![Coverage Status](https://coveralls.io/repos/artekorzel/ieTanks/badge.png?branch=master)](https://coveralls.io/r/artekorzel/ieTanks?branch=master)

-----

Dependencies
------------

* Java 8 with maven
* Python 2.x with Tornado
* bower package manager

Installation
------------

##### Ubuntu 14.04
###### Fresh
1. Download/clone the repository and change working directory to the newly created project dir.
2. Run: `./install_ubuntu.sh firstrun`, script will resolve all dependencies mentioned above.

###### Rebuilding
Run inside project root: `./install_ubuntu.sh`

##### Windows (untested*)

1. Install [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Install [maven](http://maven.apache.org/download.cgi)
3. Install [Python 2.x](https://www.python.org/downloads/windows/)
4. Install [bower with msysgit](https://www.npmjs.com/package/bower#windows-users) dependency
5. Verify python, pip, bower & java executives in your system's `$PATH` env
6. Run `bower install` using command prompt in `ieTanks-server/src/main/webapp` inside project directory
7. Run `mvn install`

*According to Tornado docs:
> Tornado will also run on Windows, although this configuration is not officially supported and is recommended only for development use.

Running
-------

##### Linux
Run in terminal inside project's root dir:
```bash
(mvn jetty:run -pl ieTanks-server &)
(mvn exec:exec -P tornado -pl ieTanks-server &)
```

##### Windows
Run each in separate command prompt windows:
```cmd
mvn jetty:run -pl ieTanks-server
mvn exec:exec -P tornado -pl ieTanks-server
```

-----

When jetty server is ready, open [http://localhost:8080](http://localhost:8080) in your web browser.
