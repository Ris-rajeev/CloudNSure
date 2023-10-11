const pool = require("../config/user.database")
const { genSaltSync, hashSync, compareSync } = require("bcrypt")
const { sign } = require("jsonwebtoken")
const otpGenerator = require("otp-generator")
const nodemailer = require("nodemailer")

const transporter = nodemailer.createTransport({
  host: "smtp.ethereal.email",
  port: 587,
  auth: {
    user: "maynard.skiles@ethereal.email",
    pass: "E36dmz2ceNJV5eFvC4",
  },
})

async function executeQuery(sql, values) {
  const connection = await pool.getConnection()
  try {
    const [rows] = await connection.execute(sql, values)
    return rows
  } finally {
    connection.release()
  }
}

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
      if (results) {
        results.password = undefined
        const jsontoken = sign({ result: results }, "secret", {
          expiresIn: "1h",
        })
        console.log(results)
        return res.json({
          operationStatus: "SUCCESS",
          operationMessage: "Login Success",
          item: {
            token: jsontoken,
            userId: results.user_id,
            fullname: results.full_name,
            username: results.user_name,
            email: results.email,
            firstName: results.full_name,
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
  pool.query(`SELECT * FROM sec_user_group`, [], (erroror, results, fields) => {
    if (erroror) {
      console.log(error)
      return res.json({
        success: 0,
        message: "Table is empty",
      })
    }
    return res.status(200).json({ results })
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
      return res.status(200).json({ results })
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
  pool.query(`SELECT * FROM sec_menu_det`, [], (erroror, results, fields) => {
    if (erroror) {
      console.log(error)
      return res.json({
        success: 0,
        message: "Table is empty",
      })
    }
    return res.status(200).json({ results })
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
      return res.status(200).json({ results })
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
      return res.status(200).json({ results })
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
    (erroror, results, fields) => {
      if (erroror) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      return res.status(200).json({ results })
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
    } catch (errororIn) {
      console.erroror("inside", errororIn)
    }
    res.json(record)
  } catch (erroror) {
    console.erroror(erroror)
    res.status(500).json({ message: "erroror fetching record Outside" })
  }
}

// Controller for User maintenance

const getUserMaintainence = (req, res) => {
  pool.query(`SELECT * FROM sec_users`, [], (erroror, results, fields) => {
    if (erroror) {
      console.log(error)
      return res.json({
        success: 0,
        message: "Table is empty",
      })
    }
    return res.status(200).json({ results })
  })
}

const getByIdUserMaintainence = (req, res) => {
  const { id } = req.params
  pool.query(
    `SELECT * FROM sec_users WHERE user_id = ?`,
    [id],
    (erroror, results, fields) => {
      if (erroror) {
        console.log(error)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      return res.status(200).json({ results })
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
  login,
}
