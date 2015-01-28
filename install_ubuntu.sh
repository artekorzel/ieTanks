#!/bin/bash

# Tested on Ubuntu 14.04 LTS

update_system() {
        if [ -z `locate add-apt-repository` ]; then
                apt-get update && apt-get install -y software-properties-common
        fi
        add-apt-repository -y ppa:webupd8team/java
        apt-get update
        apt-get install -y git build-essential python-dev python-pip oracle-java8-installer oracle-java8-set-default maven npm
        pip install tornado
        npm install -g bower

        if [ ! -f /usr/bin/python ]; then
                ln -s `readlink -f $(which python2)` /usr/bin/python
        fi
        if [ ! -f /usr/bin/bower ]; then
                ln -s `readlink -f $(which bower)` /usr/bin/bower
        fi
        if [ ! -f /usr/bin/node ]; then
                ln -s `readlink -f $(which nodejs)` /usr/bin/node
        fi
}

prepare() {
        pushd ieTanks-server/src/main/webapp
        bower install
        popd
        mvn clean install
}

case $(id -u) in
        0)
                update_system
        ;;
        *)
                if [ "$1" -eq "firstrun" ]; then
                        echo 'Installing required packages'
                        sudo $0
                fi
                echo 'Building project'
                prepare
        ;;
esac

