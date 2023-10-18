const pool = require("../config/user.database")
const { genSaltSync, hashSync, compareSync } = require("bcrypt")
const { sign } = require("jsonwebtoken")
const otpGenerator = require("otp-generator")
const nodemailer = require("nodemailer")
const db = require("../config/database")

const transporter = nodemailer.createTransport({
  host: "smtp.ethereal.email",
  port: 587,
  auth: {
    user: "maynard.skiles@ethereal.email",
    pass: "E36dmz2ceNJV5eFvC4",
  },
})

async function executeQuery(sql, values) {
  const connection = await db.getConnection()
  try {
    const [rows] = await connection.execute(sql, values)
    return rows
  } finally {
    connection.release()
  }
}

var usr_grp_from_login

const login = (req, res) => {
  const body = req.body
  pool.query(
    `select *from sec_users where email = ?`,
    [body.email],
    (error, results, fields) => {
      if (error) {
        console.log(error)
      }
      if (!results) {
        return res.json({
          success: 0,
          data: "Invalid email or password",
        })
      }
      // const result = compareSync(body.password, results[0].password)
      // if (result) {
      // console.log(results)
      usr_grp_from_login = results[0].usr_grp
      const extractedData = results.map((user) => ({
        user_id: user.user_id,
        full_name: user.full_name,
        user_name: user.user_name,
        email: user.email,
      }))
      // console.log(extractedData)
      if (results) {
        const jsontoken = sign({ result: results }, "secret", {
          expiresIn: "1h",
        })
        return res.json({
          operationStatus: "SUCCESS",
          operationMessage: "Login Success",
          item: {
            token: jsontoken,
            userId: extractedData[0].user_id,
            fullname: extractedData[0].full_name,
            username: extractedData[0].user_name,
            email: extractedData[0].email,
            firstName: extractedData[0].full_name,
            roles: ["ProjectManager", "Developer", "ROLE ADMIN"],
          },
        })
      } else {
        res.json({
          success: 0,
          data: "Invalid email or password",
        })
      }
    }
  )
}

// Controllers for User grp maintenance

const getUsersUserGrpMaintenance = (req, res) => {
  pool.query(`SELECT * FROM sec_user_group`, [], (error, results, fields) => {
    if (error) {
      console.log(error)
      return res.json({
        success: 0,
        message: "Table is empty",
      })
    }
    return res.status(200).json(results)
  })
}

const getUsersByUserIdUserGrpMaintenance = (req, res) => {
  const id = req.params.id
  pool.query(
    `SELECT * FROM sec_user_group WHERE usr_grp = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Record not found",
        })
      }
      return res.status(200).json(results)
    }
  )
}

const updateUsersUserGrpMaintenance = (req, res) => {
  const body = req.body
  const id = req.params
  pool.query(
    `UPDATE sec_user_group SET createby = ?, createdate = ?, group_desc = ?, group_level = ?, group_name = ?, status = ?, updateby = ? WHERE usr_grp = ?`,
    [
      body.createby,
      body.createdate,
      body.group_desc,
      body.group_level,
      body.password,
      body.group_name,
      body.status,
      body.updateby,
      id,
    ],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Failed to update user",
        })
      }
      return res.json({
        success: 1,
        data: "updated succesfully",
      })
    }
  )
}

const createUsersUserGrpMaintenance = (req, res) => {
  const body = req.body
  pool.query(
    `INSERT sec_user_group SET createby = ?,createdate = ?,group_desc = ?,group_level = ?,group_name = ?, status = ?, updateby = ? usr_grp = ?`,
    [
      body.createby,
      body.createdate,
      body.groupDesc,
      body.group_level,
      body.password,
      body.groupName,
      body.status,
      body.updateby,
      body.usrGrp,
    ],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Failed to update user",
        })
      }
      return res.json({
        success: 1,
        data: "updated succesfully",
      })
    }
  )
}

const deleteUsersUserGrpMaintenance = (req, res) => {
  const id = req.params.id
  pool.query(
    `delete from registration where id = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      return res.json({
        success: 1,
        data: "user deleted succesfully",
      })
    }
  )
}

// Controllers for Menu maintainence

const getUsersMenuMaintainence = (req, res) => {
  pool.query(`SELECT * FROM sec_menu_det`, [], (error, results, fields) => {
    if (error) {
      console.log(error)
      return res.json({
        success: 0,
        message: "Table is empty",
      })
    }
    return res.status(200).json(results)
  })
}

const getUsersByUserIdMenuMaintainence = (req, res) => {
  const id = req.params.id
  pool.query(
    `SELECT * FROM sec_menu_det WHERE menu_id = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Record not found",
        })
      }
      // console.log(results)
      return res.status(200).json(results)
    }
  )
}

const updateUsersMenuMaintainence = (req, res) => {
  const body = req.body
  const { id } = req.params
  var today = new Date()
  var date =
    today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate()
  var time =
    today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
  var dateTime = date + " " + time
  pool.query(
    `UPDATE sec_menu_det SET item_seq = ?, main_menu_action_name = ?, main_menu_icon_name = ?, menu_id = ?, menu_item_desc = ?, module_name = ?, status = ?, updated_at = ? WHERE menu_item_id = ?`,
    [
      body.itemSeq,
      body.main_menu_action_name,
      body.main_menu_icon_name,
      body.menuId,
      body.menuItemDesc,
      body.moduleName,
      body.status,
      dateTime,
      id,
    ],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Failed to update user",
        })
      }
      return res.json({
        success: 1,
        data: "updated succesfully",
      })
    }
  )
}

const createUsersMenuMaintainence = (req, res) => {
  const body = req.body
  var today = new Date()
  var date =
    today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate()
  var time =
    today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
  var dateTime = date + " " + time
  pool.query(
    `INSERT sec_menu_det SET menu_item_id = ?, item_seq = ?, main_menu_action_name = ?, main_menu_icon_name = ?, menu_id = ?, menu_item_desc = ?, module_name = ?, status = ?, created_at = ?, updated_at = ?`,
    [
      body.menuItemId,
      body.itemSeq,
      body.main_menu_action_name,
      body.main_menu_icon_name,
      body.menuId,
      body.menuItemDesc,
      body.moduleName,
      body.status,
      body.createdAt,
      dateTime,
    ],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Failed to create user",
        })
      }
      return res.json({
        success: 1,
        data: "created succesfully",
      })
    }
  )
}

const deleteUsersMenuMaintainence = (req, res) => {
  const id = req.params.id
  pool.query(
    `DELETE FROM sec_menu_det WHERE menu_item_id = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      return res.json({
        success: 1,
        data: "user deleted succesfully",
      })
    }
  )
}

// Controller for Sub Menu maintainence

const getUsersSubMenuMaintainence = (req, res) => {}

const getUsersByIdSubMenuMaintainence = (req, res) => {
  const id = req.params.id
  pool.query(
    `SELECT * FROM sec_menu_det WHERE menu_item_id = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Record not found",
        })
      }
      // console.log(results)
      return res.status(200).json(results)
    }
  )
}

const updateUsersSubMenuMaintainence = (req, res) => {}

const createUsersSubMenuMaintainence = (req, res) => {}

const deleteUsersSubMenuMaintainence = (req, res) => {}

// Controller for MENU ACCESS CONTROL

const getUsersMenuAccessControl = (req, res) => {
  pool.query(
    `SELECT * FROM sec_grp_menu_access`,
    [],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      return res.status(200).json(results)
    }
  )
}
const getUsersByMenuAccessControl = (req, res) => {}

const createUsersMenuAccessControl = async (req, res) => {
  const { id } = req.params
  const sql = `SELECT * FROM sec_menu_det WHERE menu_item_id = ?`
  const values = [id]

  try {
    const [record] = await executeQuery(sql, values)
    if (!record) {
      return res.status(404).json({ message: "Record not found" })
    }
    var today = new Date()
    var date =
      today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate()
    var time =
      today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
    var dateTime = date + " " + time

    const sqlIn = `INSERT INTO sec_grp_menu_access SET menu_item_id = ?, usr_grp = 41, created_at = ?, main_menu_action_name = ?, main_menu_icon_name = ?, menu_id = ?, menu_item_desc = ?, module_name = ?, status = ?, item_seq = ?, updated_at = ?, isdisable = 'true', m_create = 'true', m_delete = 'true', m_edit = 'true' , m_query = 'true', m_visible = 'true'`
    const valuesIn = [
      record.menu_item_id,
      record.created_at,
      record.main_menu_action_name,
      record.main_menu_icon_name,
      record.menu_id,
      record.menu_item_desc,
      record.module_name,
      record.status,
      record.item_seq,
      dateTime,
    ]
    try {
      const [recordIn] = await executeQuery(sqlIn, valuesIn)
    } catch (errorIn) {
      console.error("inside", errorIn)
    }
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "error fetching record Outside" })
  }
  return res.json({
    success: 1,
    data: "created succesfully",
  })
}

// Controller for User maintenance

const getUserMaintainence = (req, res) => {
  pool.query(`SELECT * FROM sec_users`, [], (error, results, fields) => {
    if (error) {
      console.log(error)
      return res.json({
        success: 0,
        message: "Table is empty",
      })
    }
    return res.status(200).json(results)
  })
}

const getByIdUserMaintainence = (req, res) => {
  const { id } = req.params
  pool.query(
    `SELECT * FROM sec_users WHERE user_id = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      return res.status(200).json(results)
    }
  )
}

const updateUserMaintainence = (req, res) => {
  const body = req.body
  const { id } = req.params
  var today = new Date()
  var date =
    today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate()
  var time =
    today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
  var dateTime = date + " " + time
  pool.query(
    `UPDATE sec_menu_det SET item_seq = ?, main_menu_action_name = ?, main_menu_icon_name = ?, menu_id = ?, menu_item_desc = ?, module_name = ?, status = ?, updated_at = ? WHERE menu_item_id = ?`,
    [
      body.itemSeq,
      body.main_menu_action_name,
      body.main_menu_icon_name,
      body.menuId,
      body.menuItemDesc,
      body.moduleName,
      body.status,
      dateTime,
      id,
    ],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Failed to update user",
        })
      }
      return res.json({
        success: 1,
        data: "updated succesfully",
      })
    }
  )
}

const createUserMaintainence = (req, res) => {
  const body = req.body
  var today = new Date()
  var date =
    today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate()
  var time =
    today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
  var dateTime = date + " " + time
  pool.query(
    `INSERT sec_users SET user_id = ?, change_passw = ?, email = ?, full_name = ?, is_blocked = ?, mob_no = ?, user_passw = ?, user_name = ?, usr_grp_id = ?, account_id = ?, usr_grp = ?, createdate = ?`,
    [
      body.user_id,
      body.is_complete,
      body.change_passw,
      body.email,
      body.full_name,
      body.is_blocked,
      body.mob_no,
      body.user_passw,
      body.user_name,
      body.usr_grp_id,
      body.account_id,
      body.usr_grp,
      dateTime,
    ],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Failed to create user",
        })
      }
      return res.json({
        success: 1,
        data: "created succesfully",
      })
    }
  )
}

const deleteUserMaintainence = (req, res) => {
  const id = req.params.id
  pool.query(
    `DELETE FROM sec_menu_det WHERE menu_item_id = ?`,
    [id],
    (error, results, fields) => {
      if (error) {
        console.log(error)
        return
      }
      return res.json({
        success: 1,
        data: "user deleted succesfully",
      })
    }
  )
}

const loadMenuByUser = async (req, res) => {
  try {
    // Simulate getting the logged-in user's group (usrGrp)
    const usrgrp = usr_grp_from_login // Replace this with your actual logic to get the user's group

    // Find root menu items where menu_id is 0
    let sql = `SELECT * FROM sec_grp_menu_access WHERE menu_id = 0 AND status= 'Enable' AND usr_grp = ${usrgrp} ORDER BY item_seq`
    const root = await executeQuery(sql)

    const renameFields = (obj) => {
      const newObj = {}
      for (const key in obj) {
        switch (key) {
          case "usr_grp":
            newObj["usrGrp"] = obj[key]
            break
          case "menu_item_id":
            newObj["menuItemId"] = obj[key]
            break
          case "item_seq":
            newObj["itemSec"] = obj[key]
            break
          case "menu_id":
            newObj["menuId"] = obj[key]
            break
          case "menu_item_desc":
            newObj["menuItemDesc"] = obj[key]
            break
          case "m_create":
            newObj["mcreate"] = obj[key]
            break
          case "m_delete":
            newObj["mdelete"] = obj[key]
            break
          case "m_edit":
            newObj["medit"] = obj[key]
            break
          case "m_query":
            newObj["mquery"] = obj[key]
            break
          case "m_visible":
            newObj["mvisible"] = obj[key]
            break
          default:
            newObj[key] = obj[key]
        }
      }
      return newObj
    }

    const renameSubmenuFields = (submenu) => {
      const newSubmenu = {}
      for (const key in submenu) {
        switch (key) {
          case "usr_grp":
            newSubmenu["usrGrp"] = submenu[key]
            break
          case "menu_item_id":
            newSubmenu["menuItemId"] = submenu[key]
            break
          case "item_seq":
            newSubmenu["itemSec"] = submenu[key]
            break
          case "menu_id":
            newSubmenu["menuId"] = submenu[key]
            break
          case "menu_item_desc":
            newSubmenu["menuItemDesc"] = submenu[key]
            break
          case "m_create":
            newSubmenu["mcreate"] = submenu[key]
            break
          case "m_delete":
            newSubmenu["mdelete"] = submenu[key]
            break
          case "m_edit":
            newSubmenu["medit"] = submenu[key]
            break
          case "m_query":
            newSubmenu["mquery"] = submenu[key]
            break
          case "m_visible":
            newSubmenu["mvisible"] = submenu[key]
            break
          default:
            newSubmenu[key] = submenu[key]
        }
      }
      return newSubmenu
    }

    // for (let i = 0; i < root.length; i++) {
    //   const rootMenuItem = root[i]

    //   // Find menu items by menu_id (rootMenuItemId) and usrGrp
    //   let sql1 = `SELECT * FROM sec_grp_menu_access a WHERE a.usr_grp =${usrgrp} and a.menu_item_id=${rootMenuItem.menu_item_id}`
    //   const menu = await executeQuery(sql1)

    for (const menuItem of root) {
      // Find all submenu items with status
      let sql2 = `SELECT * FROM sec_grp_menu_access a where a.status= 'Enable' and a.menu_id=${menuItem.menu_item_id} and a.usr_grp =${usrgrp} ORDER BY item_seq`
      const allSubmenu = await executeQuery(sql2)

      for (const subMenus of allSubmenu) {
        let sqlNew = `SELECT * FROM sec_user_group where usr_grp=${usrgrp}`
        const sqlNewResult = await executeQuery(sqlNew)

        let sqlMenuItemId = `SELECT * FROM sec_menu_det where menu_item_id=${menuItem.menu_item_id}`
        const sqlMenuItemIdResult = await executeQuery(sqlMenuItemId)

        subMenus.usrGrp = sqlNewResult[0]
        subMenus.menuItemId = sqlMenuItemIdResult[0]
      }

      let sqlNew = `SELECT * FROM sec_user_group where usr_grp=${usrgrp}`
      const sqlNewResult = await executeQuery(sqlNew)

      let sqlMenuItemId = `SELECT * FROM sec_menu_det where menu_item_id=${menuItem.menu_item_id}`
      const sqlMenuItemIdResult = await executeQuery(sqlMenuItemId)

      menuItem.subMenus = allSubmenu.map(renameSubmenuFields)
      menuItem.usrGrp = sqlNewResult[0]
      // updatedUsrGrp = menuItem.usrGrp
      // menuItem.usrGrp = updatedUsrGrp.map(renameSubmenuFields)
      menuItem.menuItemId = sqlMenuItemIdResult[0]
    }

    // rootMenuItem.subMenus = menu
    // }
    for (let i = 0; i < root.length; i++) {
      const rootMenuItem = root[i]
      root[i] = renameFields(rootMenuItem)
    }

    res.status(200).json(root)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Internal server error" })
  }
}

const test = async (req, res) => {
  try {
    // Simulate getting the logged-in user's group (usrGrp)
    const usrgrp = 1 // Replace this with your actual logic to get the user's group

    // Find root menu items where menu_id is 0
    let sql = `SELECT * FROM sec_grp_menu_access WHERE menu_id = 0 AND status= 'Enable' AND usr_grp = ${usrgrp} ORDER BY item_seq`
    const root = await executeQuery(sql)

    for (let i = 0; i < root.length; i++) {
      const rootMenuItemId = root[i]

      // Find menu items by menu_id (rootMenuItemId) and usrGrp
      let sql1 = `SELECT * FROM sec_grp_menu_access a WHERE a.usr_grp = ${usrgrp} and a.menu_item_id= ${rootMenuItemId.menu_item_id}`
      const menu = await executeQuery(sql1)

      for (const menuItem of menu) {
        // Find all submenu items with status
        let sql2 = `SELECT * FROM sec_grp_menu_access a where a.status= 'Enable' and a.menu_id=${menuItem.menu_item_id} and a.usr_grp =${usrgrp} ORDER BY item_seq`
        const allSubmenu = await executeQuery(sql2)

        menuItem.subMenus = allSubmenu
      }
    }

    res.status(200).json(root)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Internal server error" })
  }
}

const userMenu = (req, res) => {
  const sql = `SELECT * FROM accesstype`
  res.status(200).json([])
}

module.exports = {
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
  login,
  test,
}
