#!/bin/bash -e

mvn clean install

java -cp target/nutch-regex-tests-1.0-SNAPSHOT-jar-with-dependencies.jar:nutch_plugins/lib-regex-filter.jar:nutch_plugins/urlfilter-regex.jar de.uni_leipzig.nutch_regex_tests.Main regex_base_dir regex-urlfilter.txt
