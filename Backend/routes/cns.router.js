const {
  login,
  getUsersUserGrpMaintenance,
  getUsersByUserIdUserGrpMaintenance,
  updateUsersUserGrpMaintenance,
  createUsersUserGrpMaintenance,
  deleteUsersUserGrpMaintenance,
  getUsersMenuMaintainence,
  getUsersByUserIdMenuMaintainence,
  createUsersMenuMaintainence,
  updateUsersMenuMaintainence,
  deleteUsersMenuMaintainence,
  getUsersByIdSubMenuMaintainence,
  createUsersMenuAccessControl,
  getUsersMenuAccessControl,
  getUserMaintainence,
  getByIdUserMaintainence,
  createUserMaintainence,
  loadMenuByUser,
  userMenu,
} = require("../api/cns.controller")

const { checkToken } = require("../auth/token_validation")
const router = require("express").Router()

router.post("/token/session", login)
router
  .get("/api/getAllUsrGrp", checkToken, getUsersUserGrpMaintenance)
  .get("/api1/getusracces1/:id", checkToken, getUsersByUserIdUserGrpMaintenance)
  .put("/api/updateOneUsrGrp/:id", checkToken, updateUsersUserGrpMaintenance)
  .post("/api/addOneUsrGrp", checkToken, createUsersUserGrpMaintenance)
  .delete("/api/delete_usrgrp/:id", checkToken, deleteUsersUserGrpMaintenance)

router
  .get("/api1/submenu1", checkToken, getUsersMenuMaintainence)
  .get("/api1/getusracces1/:id", checkToken, getUsersByUserIdMenuMaintainence)
  .post("/api1/Sec_menuDet", checkToken, createUsersMenuMaintainence)
  .patch("/api1/submenu1/:id", checkToken, updateUsersMenuMaintainence)
  .delete("/api1/menu/:id", checkToken, deleteUsersMenuMaintainence)

router.get("/api1/submenu1/:id", checkToken, getUsersByIdSubMenuMaintainence)

router
  .get("/api/getAllUsrGrp", checkToken, getUsersMenuAccessControl)
  .post("/api1/addgrpwithsubmenu/:id", checkToken, createUsersMenuAccessControl)

router
  .get("/api/getAllAppUser", checkToken, getUserMaintainence)
  .get("/api/getOneAppUser/:id", checkToken, getByIdUserMaintainence)
  .post("/api/addOneAppUser", checkToken, createUserMaintainence)

router.get("/fndMenu/menuloadbyuser", checkToken, loadMenuByUser)
router.get("/api/realnet-menu/user-menu", checkToken, userMenu)

module.exports = router
