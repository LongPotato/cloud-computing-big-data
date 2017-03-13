var googleStocks = require('google-stocks');
var elasticsearch = require('elasticsearch');
var uuidV1 = require('uuid/v1');
var express = require('express');
var fs = require('fs');

var client = new elasticsearch.Client({
  host: 'https://search-longpotato-qrczla2hvd6j63kqm3nf3n7cnm.us-west-2.es.amazonaws.com/',
  log: 'info'
});

client.ping({
  // ping usually has a 3000ms timeout
  requestTimeout: 5000
}, function (error) {
  if (error) {
    console.trace('elasticsearch cluster is down!');
  } else {
    console.log('All is well');
  }
});

function loadDataSet(data, itemId) {
  var payload = {
    stockId: data["id"],
    timeStamp: data["lt_dts"],
    stockCode: data["t"],
    stockMarket: data["e"],
    price: parseFloat(data["l"])
  }
  client.index({
    index: 'apple-stock-prices',
    type: 'stock',
    body: payload
  }, function (error, response) {
    if (!error) {
      console.log("Success", payload)
    } else {
      console.log(error)
    }
  });
}

function getStockData(stockId) {
  googleStocks([stockId], function(error, data) {
    if (!error) {
      loadDataSet(data[0], uuidV1());
    }
  });
}

setInterval(function() {
  getStockData('AAPL');
}, 3600000);
