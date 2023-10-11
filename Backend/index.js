const express = require("express")
const app = express()
const bodyParser = require("body-parser")
// const router = require("./routes/router")
const router = require("./routes/cns.router")
// const userRouter = require("./routes/user.router")

app.use(express.json())
// app.use("/api/tables/v1", router)
// app.use("/api/users/v1", userRouter)
// app.use("", router)
// app.use("", userRouter)
app.use("", router)
app.use(bodyParser.json())

const port = process.env.PORT || 9292

app.get("/test", (req, res) => {
  res.json({
    success: 1,
    message: "This is hello from auth backend",
  })
})

app.listen(port, () => {
  console.log(`Server is up and running on port ${port}`)
})
