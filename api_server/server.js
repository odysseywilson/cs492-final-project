const express = require("express");
const fs = require("fs");
const multer = require("multer");
const database = require("./components/database");

var app = express();
var storage = multer.memoryStorage();
var upload = multer({ storage: storage });

app.use(express.static(__dirname + "/public"));

app.get("/api/tea", function (req, res, next) {
    query = `SELECT * FROM Posts ORDER BY time DESC LIMIT 10;`;
    database.query(query, function (err, rows) {
        if (err) {
            res.status(500).send("INTERNAL SERVER ERROR");
        } else {
            res.status(200).json(rows);
        }
    });
});

app.post("/api/tea", upload.single("upload"), function (req, res, next) {
    query0 = `INSERT INTO Posts (username, caption, time) VALUES (?, ?, ?);`;
    database.query(
        query0,
        function (err, rows) {
            if (err) {
                res.status(500).send("INTERNAL SERVER ERROR");
            } else {
                query1 = `SELECT last_insert_rowid();`;
                database.query(query1, function (err, rows) {
                    if (err) {
                        res.status(500).send("INTERNAL SERVER ERROR");
                    }
                    var file =
                        __dirname +
                        "/public/" +
                        rows[0]["last_insert_rowid()"] +
                        ".png";
                    fs.writeFile(file, req.file.buffer, function (err) {
                        if (err) {
                            console.error(err);
                            query2 = `DELETE FROM Posts WHERE id = ?`;
                            // delete row in db if file save fails
                            database.query(query2, null, [
                                rows[0]["last_insert_rowid()"],
                            ]);
                            res.status(500).send("INTERNAL SERVER ERROR");
                        } else {
                            res.status(201).end();
                        }
                    });
                });
            }
        },
        [req.query.username, req.query.caption, Date.now()]
    );
});

app.get("*", function (req, res, next) {
    res.status(404).send("ERROR 404");
});

database.init("spill_the_tea.db", function () {
    app.listen(3000, function () {
        console.log("server listening at port: ", 3000);
    });
});
