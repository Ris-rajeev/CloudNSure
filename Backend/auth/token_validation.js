const { verify } = require("jsonwebtoken")

const checkToken = async (req, res, next) => {
  let token = req.get("authorization")
  if (token) {
    token = token.slice(7)
    verify(token, "secret", (err, decoded) => {
      if (err) {
        console.log(err)
        res.json({
          success: 0,
          message: "Invalid token",
        })
      } else {
        next()
      }
    })
  } else {
    res.json({
      success: 0,
      message: "Access denied unauthorized user",
    })
  }
}

module.exports = {
  checkToken,
}
