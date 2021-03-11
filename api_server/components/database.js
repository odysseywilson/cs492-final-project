const sqlite3 = require("sqlite3").verbose();

var db;

dbSchema = `
CREATE TABLE IF NOT EXISTS Posts (
    id integer NOT NULL PRIMARY KEY,
    username text NOT NULL,
    image text NOT NULL,
    caption text,
    time integer NUT NULL
);
`
// initialization function, connects to database and does first time setup
var init = function (filename, next) {
    // opens connection to database, default mode is OPEN_READWRITE | CREATE
    db = new sqlite3.Database(filename, function (err) {
        if (err) {
            console.error(err.message);
        } else {
            console.log("DATABASE: connected to database");

            db.exec(dbSchema, function (err) {
                if (err) {
                    console.error(err.message);
                } else {
                    console.log("DATABASE: executed schema");

                    next();
                }
            });
        }
    });
};

var query = function(query, next, params = [])
{
    db.all(query, params, function(err, rows) {
        if(err)
        {
            console.error(err.message);
            next(true, null);
        }
        else
        {
            next(false, rows);
        }
    });
}

module.exports = {
    init,
    query
}