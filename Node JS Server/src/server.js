const http = require("http");
const express = require("express");
var net = require('net');
const app = express();
const bodyParser = require('body-parser');
//app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get("/main", function(req, res) {
    res.sendFile('/Users/mac/Desktop/BACKEND/src/index-4.html');
});

app.post("/receber", function(req, res) {
    var client = new net.Socket();
    client.connect(15432, '10.0.1.2', function() {
        client.write(JSON.stringify(req.body));
        client.end();
    });

    client.on('data', function(data) {
        console.log('Received: ' + data);
        client.destroy();
    });

    client.on('close', function() {
        console.log('Connection closed');
    });
});

app.post("/redirect", function(req, res) {
    var client = new net.Socket();
    client.connect(16432, '10.0.1.2', function() {
        client.write("");
    });

    client.on('data', function(data) {
        console.log('Received: ' + data);
        res.send(data);
        client.end();
        client.destroy();
    });

    client.on('close', function() {
        console.log('Connection closed');
    });
});

http.createServer(app).listen(3000, () => console.log("Servidor rodando local na porta 3000"));



