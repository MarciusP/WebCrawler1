# WebCrawler1

The exact amount of requests is unknown, because httpanalyzer was interfaring with Jsoup connection and Fiddler was not detecting any connections from this library.
However, I presume that only 30 requests were made, because Jsoup.connect function was called only 30 times. I think it is imposible to lower down this number, because every day in that website has different url.
