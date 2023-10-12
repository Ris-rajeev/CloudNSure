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
  .get("/api/getAllUsrGrp", getUsersUserGrpMaintenance)
  .get("/api1/getusracces1/:id", getUsersByUserIdUserGrpMaintenance)
  .put("/api/updateOneUsrGrp/:id", updateUsersUserGrpMaintenance)
  .post("/api/addOneUsrGrp", createUsersUserGrpMaintenance)
  .delete("/api/delete_usrgrp/:id", deleteUsersUserGrpMaintenance)

router
  .get("/api1/submenu1", getUsersMenuMaintainence)
  .get("/api1/getusracces1/:id", getUsersByUserIdMenuMaintainence)
  .post("/api1/Sec_menuDet", createUsersMenuMaintainence)
  .patch("/api1/submenu1/:id", updateUsersMenuMaintainence)
  .delete("/api1/menu/:id", deleteUsersMenuMaintainence)

router.get("/api1/submenu1/:id", getUsersByIdSubMenuMaintainence)

router
  .get("/api/getAllUsrGrp", getUsersMenuAccessControl)
  .post("/api1/addgrpwithsubmenu/:id", createUsersMenuAccessControl)

router
  .get("/api/getAllAppUser", getUserMaintainence)
  .get("/api/getOneAppUser/:id", getByIdUserMaintainence)
  .post("/api/addOneAppUser", createUserMaintainence)

router.get("/fndMenu/menuloadbyuser", loadMenuByUser)
router.get("/api/realnet-menu/user-menu", userMenu)

module.exports = router
