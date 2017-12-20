#!/bin/bash

find regex_base_dir/ -name white.txt -exec cat {} \; > seed_urls.txt
