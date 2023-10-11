const mysql = require("mysql2/promise") // Using the promise-based version of mysql2

const db = mysql.createPool({
  host: "localhost",
  port: "3306",
  database: "test",
  user: "root",
  password: "password",
})

module.exports = db
