# Check-Nutch-Regex-Urlfilter

This is a small project to check and ensure the behaviour of a regex-urlfilter file which is used by the nutch
injector and db updater.

Such a regex-urlfilter file contains of a list of entries like:
```
[+-]<REGEX_1>
...
[+-]<REGEX_N>
```
For each url nutch checks each <REGEX> in the order how they appear in the file and the sign
of the first matching regex determines if the url is ignored (for -) or fetched (for +).

## Goal

The main goal of this project is to give a test driven way to create such a regex-urlfilter-file.
The Approach is:
* Divide the content that you want to crawl into small parts
* For each part, you will
  * give some example-urls that must be fetched
  * give some example-urls that must be ignored
  * give regex rules for those examples
  
With that, this project will do the following:
* Put together all rules from the examples to the final regex-urlfilter-file
* Check for all ignore-examples if this global regex-urlfilter-file will ignore each of them
* Check for all fetch-examples if this global regex-urlfilter-file will fetch each of them

## Installation

```
#Clone the sourcecode
git clone https://github.com/mam10eks/check-nutch-regex-urlfilter.git
cd check-nutch-regex-urlfilter

#Compile the code
mvn clean install
```

## Usage

For each example you create a directory within `regex_base_dir` in the pattern `<NUMBER>_<ARBITRARY_NAME>` where
wher `<NUMBER>` is an unique integer within `regex_base_dir`.
The `<NUMBER>` determines the order in which the examples are concatenated in the final
regex-urlfilter-file (i.e. regexes from 1 comes before 2 and so on).

Each such `<NUMBER>_<ARBITRARY_NAME>` directory consists out of three files:
* `black.txt`: urls which should be ignored
* `white.txt`: urls which should be crawled
* `url-regex.txt`: nutch url-regexes to fulfill the examples provided in `black.txt` and `white.txt`

### Note

If you want to check/crawl your examples only,
remove all examples for other domains in your local copy and only commit your new examples.

### Build the regex-urlfilter

Simply execute
```
./build_regex_url_filter.sh
```
