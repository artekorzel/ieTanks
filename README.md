ieTanks
=======

Branch develop status:

[![Build Status](https://travis-ci.org/artekorzel/ieTanks.png?branch=develop)](https://travis-ci.org/artekorzel/ieTanks)
[![Coverage Status](https://coveralls.io/repos/artekorzel/ieTanks/badge.png?branch=develop)](https://coveralls.io/r/artekorzel/ieTanks?branch=develop)


Branch master status:

[![Build Status](https://travis-ci.org/artekorzel/ieTanks.png?branch=master)](https://travis-ci.org/artekorzel/ieTanks)
[![Coverage Status](https://coveralls.io/repos/artekorzel/ieTanks/badge.png?branch=master)](https://coveralls.io/r/artekorzel/ieTanks?branch=master)

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
1. Run inside project root: `./install_ubuntu.sh`

Running
-------

##### Linux
Run in terminal inside project's root dir:
```bash
(mvn jetty:run -pl ieTanks-server &)
(mvn exec:exec -P tornado -pl ieTanks-server &)
```
When jetty server is ready, open http://localhost:8080 in your web browser.
