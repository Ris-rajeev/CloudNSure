const {
  getTable,
  getTableById,
  getTableByIdSec_Menu_Det,
  createDataInTable,
  updateDataInTable,
  deleteDataInTable,
  addMenu,
  deleteMenu,
  updateMenu,
  createDataInTableRole,
  updateDataInTableRole,
  deleteDataInTableRole,
  createDataInStudentTable,
  getStudentTable,
  getStudentTableById,
  updateDataInStudentTable,
  deleteDataInStudentTable,
} = require("../api/controller")

const { checkToken } = require("../auth/token_validation")
const router = require("express").Router()

router
  .get("/student", checkToken, getStudentTable)
  .get("/student/:id", checkToken, getStudentTableById)
  .post("/student", checkToken, createDataInStudentTable)
  .patch("/student/:id", checkToken, updateDataInStudentTable)
  .delete("/student:id", checkToken, deleteDataInStudentTable)
  .post("/role", checkToken, createDataInTableRole)
  .patch("/role/:id", checkToken, updateDataInTableRole)
  .delete("/role/:id", checkToken, deleteDataInTableRole)
  .post("/:id", checkToken, addMenu)
  .delete("/:id", checkToken, deleteMenu)
  .patch("/:id", checkToken, updateMenu)
router
  .get("/sec_menu_det/:id", checkToken, getTableByIdSec_Menu_Det)
  .get("/sec_grp_menu_access/:id", checkToken, getTableByIdSec_Menu_Det)
  .get("/:tableName/:id", checkToken, getTableById)
  .patch("/:tableName/:id", checkToken, updateDataInTable)
  .delete("/:tableName/:id", checkToken, deleteDataInTable)
router
  .get("/:tableName", checkToken, getTable)
  .post("/:tableName", checkToken, createDataInTable)

module.exports = router
