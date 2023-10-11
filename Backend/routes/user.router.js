const {
  getUsers,
  getUsersByUserId,
  updateUsers,
  deleteUsers,
  login,
  registerUser,
  verifyOTP,
} = require("../api/user.controller")

const { checkToken } = require("../auth/token_validation")
const router = require("express").Router()

// router.get("/", checkToken, getUsers)
// router
//   .get("/:id", checkToken, getUsersByUserId)
//   .patch("/:id", checkToken, updateUsers)
//   .delete("/:id", checkToken, deleteUsers)
// router.post("/login", login)
// router.post("/register", registerUser).post("/verify", verifyOTP)

router.get("/", checkToken, getUsers)
router
  .get("/:id", checkToken, getUsersByUserId)
  .patch("/:id", checkToken, updateUsers)
  .delete("/:id", checkToken, deleteUsers)
router.post("/token/session", login)
// router.post("/login", login)
router.post("/register", registerUser).post("/verify", verifyOTP)

module.exports = router
