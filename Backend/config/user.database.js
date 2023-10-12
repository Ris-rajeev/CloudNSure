const mysql = require("mysql2")

const pool = mysql.createConnection({
  host: "realnet.cdtynkxfiu2h.ap-south-1.rds.amazonaws.com",
  port: "3306",
  database: "realnet_CNSBENEW",
  user: "cnsdev",
  password: "cnsdev1234",
})

pool.connect(function (err) {
  if (err) throw err
  console.log("Connected!")
})

module.exports = pool
