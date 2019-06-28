# movieInfoGetter
Little project for filling my school project database with data, because I am tired of it

This project is using jsoup library for working with web page elements from google. https://jsoup.org/
I am using goole search url in format :
https://www.google.com/search?hl=en&q=#{movie+name}+#{info+I+want}

This project is actually very faulty. I am just retrieving full html web page from basic google search and then axtracting elements with specific classes or ID´s. This doesn´t work for everything and sometimes program doesn´t recognize some elements. This program works only because of the specific way google sends the web pages. I only wanted to make this as fast as possible and didn´t catch a lot of errors. The code is pretty disgusting too. I finished the project and passed, so I will not refactor it probably ever.


# What i learned
- Java swing Drag&Drop
- Working with html document using JSoup
- Use JFileChooser
- Redirecting system output stream
