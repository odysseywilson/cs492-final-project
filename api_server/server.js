const express = require("express");
const database = require("./components/database");

var app = express();

app.use(express.static("public"));

app.get("*", function(req, res, next) {
    res.status(404).send("ERROR 404");
});

database.init("spill_the_tea.db", function() {
    app.listen(3000, function() {
        console.log("server listening at port: ", 3000);
    });
});
