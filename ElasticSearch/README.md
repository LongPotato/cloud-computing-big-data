Apple stock price Elastic Search & Kibana

The program uses Node.js to get the Apple stock price from Google finance every one hour.
It then inputs the data into AWS elastic search.

On Kibana, the dash board display the line graph of the stock price for every 30 min mark.
The other data shows the maximun stock price that the program recored so far.

Notice: You might not see anything displayed on Kibana, because Kibana dashboard default timeframe is "last 15 min". Change that to "today" or "this week" to see the data.
