const mysql = require("mysql2")

const pool = mysql.createConnection({
  host: "localhost",
  port: "3306",
  database: "test",
  user: "root",
  password: "password",
})

pool.connect(function (err) {
  if (err) throw err
  console.log("Connected!")
})

module.exports = pool
