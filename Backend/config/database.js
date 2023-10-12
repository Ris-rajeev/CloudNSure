const mysql = require("mysql2/promise") // Using the promise-based version of mysql2

const db = mysql.createPool({
  host: "realnet.cdtynkxfiu2h.ap-south-1.rds.amazonaws.com",
  port: "3306",
  database: "realnet_CNSBENEW",
  user: "cnsdev",
  password: "cnsdev1234",
})

module.exports = db
