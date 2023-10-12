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
    const usrgrp = 1 // Replace this with your actual logic to get the user's group

    // Find root menu items where menu_id is 0
    let sql = `SELECT * FROM sec_grp_menu_access WHERE menu_id = 0 AND status= 'Enable' AND usr_grp = ${usrgrp} ORDER BY item_seq`
    const root = await executeQuery(sql)

    for (let i = 0; i < root.length; i++) {
      const rootMenuItemId = root[i]

      // Find menu items by menu_id (rootMenuItemId) and usrGrp
      let sql1 = `SELECT * FROM sec_grp_menu_access a WHERE a.usr_grp =${usrgrp} and a.menu_item_id=${rootMenuItemId.menu_item_id}`
      const menu = await executeQuery(sql1)

      for (const menuItem of menu) {
        // Find all submenu items with status
        let sql2 = `SELECT * FROM sec_grp_menu_access a where a.status= 'Enable' and a.menu_id=${menuItem.menu_item_id} and a.usr_grp =${usrgrp} ORDER BY item_seq`
        const allSubmenu = await executeQuery(sql2)

        menuItem.subMenus = allSubmenu
      }
    }
    const response = [
      {
        usrGrp: {
          usrGrp: 42,
          groupName: "Admin",
          groupDesc: "Admin",
          createby: null,
          createdate: 1657880466000,
          updatedate: 1683869494000,
          updateby: null,
          status: "E",
          groupLevel: 20,
          createDateFormated: null,
          updateDateFormated: null,
        },
        menuItemId: {
          createdAt: 1658078563000,
          updatedAt: 1695612701000,
          menuItemId: 1,
          itemSeq: null,
          menuItemDesc: null,
          status: null,
          menuId: null,
          moduleName: null,
          main_menu_action_name: "as",
          main_menu_icon_name: null,
          subMenus: [],
        },
        mexport: "true",
        menuId: 0,
        createby: null,
        createdAt: 1686194106000,
        updateby: null,
        updatedAt: 1686194106000,
        isdisable: "true",
        itemSeq: 1000,
        menuItemDesc: "SuperAdmin",
        status: "Enable",
        moduleName: "SA1000",
        main_menu_action_name: "Super Admin",
        main_menu_icon_name: "crown",
        subMenus: [
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1677317209000,
              menuItemId: 9,
              itemSeq: 1010,
              menuItemDesc: "Query",
              status: "Enable",
              menuId: 1,
              moduleName: "Q1000",
              main_menu_action_name: "query",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1010,
            menuItemDesc: "Query",
            status: "Enable",
            moduleName: "Q1000",
            main_menu_action_name: "query",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1660107160000,
              updatedAt: 1677317450000,
              menuItemId: 1310,
              itemSeq: 1030,
              menuItemDesc: "Dynamic Form",
              status: "Enable",
              menuId: 1,
              moduleName: "D3000",
              main_menu_action_name: "dynamicform",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1030,
            menuItemDesc: "Dynamic Form",
            status: "Enable",
            moduleName: "D3000",
            main_menu_action_name: "dynamicform",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1661407911000,
              updatedAt: 1677317438000,
              menuItemId: 1466,
              itemSeq: 1040,
              menuItemDesc: "Form Extension",
              status: "Enable",
              menuId: 1,
              moduleName: "FE4000",
              main_menu_action_name: "extension",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1040,
            menuItemDesc: "Form Extension",
            status: "Enable",
            moduleName: "FE4000",
            main_menu_action_name: "extension",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1661407973000,
              updatedAt: 1677317424000,
              menuItemId: 1467,
              itemSeq: 1050,
              menuItemDesc: "Stepper Workflow",
              status: "Enable",
              menuId: 1,
              moduleName: "S5000",
              main_menu_action_name: "stepper",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1050,
            menuItemDesc: "Stepper Workflow",
            status: "Enable",
            moduleName: "S5000",
            main_menu_action_name: "stepper",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1661430565000,
              updatedAt: 1677317410000,
              menuItemId: 1522,
              itemSeq: 1060,
              menuItemDesc: "Workflow Instance",
              status: "Enable",
              menuId: 1,
              moduleName: "WI6000",
              main_menu_action_name: "stepper1",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1060,
            menuItemDesc: "Workflow Instance",
            status: "Enable",
            moduleName: "WI6000",
            main_menu_action_name: "stepper1",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1684582902000,
              updatedAt: 1684808098000,
              menuItemId: 8164,
              itemSeq: 1080,
              menuItemDesc: "Report Builder",
              status: "Enable",
              menuId: 1,
              moduleName: "reportBuild",
              main_menu_action_name: "reportbuild",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1080,
            menuItemDesc: "Report Builder",
            status: "Enable",
            moduleName: "reportBuild",
            main_menu_action_name: "reportbuild",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1684565939000,
              updatedAt: 1684808108000,
              menuItemId: 8134,
              itemSeq: 1090,
              menuItemDesc: "Report Runner",
              status: "Enable",
              menuId: 1,
              moduleName: "R2000",
              main_menu_action_name: "rerunner",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1686194106000,
            updateby: null,
            updatedAt: 1686194106000,
            isdisable: "true",
            itemSeq: 1090,
            menuItemDesc: "Report Runner",
            status: "Enable",
            moduleName: "R2000",
            main_menu_action_name: "rerunner",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1689395002000,
              updatedAt: 1689395002000,
              menuItemId: 10408,
              itemSeq: 1091,
              menuItemDesc: "Super User Maintenance",
              status: "Enable",
              menuId: 1,
              moduleName: "SuperUserMaintenance",
              main_menu_action_name: "superusermaintenance",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1689395025000,
            updateby: null,
            updatedAt: 1689395025000,
            isdisable: "true",
            itemSeq: 1091,
            menuItemDesc: "Super User Maintenance",
            status: "Enable",
            moduleName: "SuperUserMaintenance",
            main_menu_action_name: "superusermaintenance",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1689882357000,
              updatedAt: 1689882373000,
              menuItemId: 10584,
              itemSeq: 1092,
              menuItemDesc: "Test Form",
              status: "Enable",
              menuId: 1,
              moduleName: "TestForm",
              main_menu_action_name: "testingform",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 1,
            createby: null,
            createdAt: 1689882380000,
            updateby: null,
            updatedAt: 1689882380000,
            isdisable: "true",
            itemSeq: 1092,
            menuItemDesc: "Test Form",
            status: "Enable",
            moduleName: "TestForm",
            main_menu_action_name: "testingform",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
        ],
        grpid: null,
        gmenuid: null,
        mdelete: "true",
        mvisible: "true",
        medit: "true",
        mquery: "true",
        mcreate: "true",
      },
      {
        usrGrp: {
          usrGrp: 42,
          groupName: "Admin",
          groupDesc: "Admin",
          createby: null,
          createdate: 1657880466000,
          updatedate: 1683869494000,
          updateby: null,
          status: "E",
          groupLevel: 20,
          createDateFormated: null,
          updateDateFormated: null,
        },
        menuItemId: {
          createdAt: 1658078563000,
          updatedAt: 1687147222000,
          menuItemId: 5,
          itemSeq: 2000,
          menuItemDesc: "Admin",
          status: "Enable",
          menuId: 0,
          moduleName: "ADM2000",
          main_menu_action_name: "Admin",
          main_menu_icon_name: "administrator",
          subMenus: [],
        },
        mexport: "true",
        menuId: 0,
        createby: null,
        createdAt: 1685351276000,
        updateby: null,
        updatedAt: 1685351276000,
        isdisable: "true",
        itemSeq: 2000,
        menuItemDesc: "Admin",
        status: "Enable",
        moduleName: "ADM2000",
        main_menu_action_name: "Admin",
        main_menu_icon_name: "administrator",
        subMenus: [
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1659754897000,
              menuItemId: 12,
              itemSeq: 2020,
              menuItemDesc: "Workflow",
              status: "Enable",
              menuId: 5,
              moduleName: "ADM2000",
              main_menu_action_name: "workflow",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 2020,
            menuItemDesc: "Workflow",
            status: "Enable",
            moduleName: "ADM2000",
            main_menu_action_name: "workflow",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658213015000,
              updatedAt: 1659383787000,
              menuItemId: 1160,
              itemSeq: 2030,
              menuItemDesc: "NCSO",
              status: "Enable",
              menuId: 5,
              moduleName: "ADM2000",
              main_menu_action_name: "ncso",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 2030,
            menuItemDesc: "NCSO",
            status: "Enable",
            moduleName: "ADM2000",
            main_menu_action_name: "ncso",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658230944000,
              updatedAt: 1659383795000,
              menuItemId: 1168,
              itemSeq: 2040,
              menuItemDesc: "headerlines",
              status: "Enable",
              menuId: 5,
              moduleName: "ADM2000",
              main_menu_action_name: "university",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 2040,
            menuItemDesc: "headerlines",
            status: "Enable",
            moduleName: "ADM2000",
            main_menu_action_name: "university",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1663738325000,
              updatedAt: 1696944753000,
              menuItemId: 1768,
              itemSeq: 2050,
              menuItemDesc: "Git And Registery Profile",
              status: "Enable",
              menuId: 5,
              moduleName: "ADM2000",
              main_menu_action_name: "deploymentprofile",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 2050,
            menuItemDesc: "Git And Registery Profile",
            status: "Enable",
            moduleName: "ADM2000",
            main_menu_action_name: "deploymentprofile",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1696224351000,
              updatedAt: 1696224351000,
              menuItemId: 12903,
              itemSeq: 2055,
              menuItemDesc: "Deployment Profile 2",
              status: "Enable",
              menuId: 5,
              moduleName: "DeploymentProfile2",
              main_menu_action_name: "deploymentprofile2",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1696224397000,
            updateby: null,
            updatedAt: 1696224397000,
            isdisable: "true",
            itemSeq: 2055,
            menuItemDesc: "Deployment Profile 2",
            status: "Enable",
            moduleName: "DeploymentProfile2",
            main_menu_action_name: "deploymentprofile2",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1668510873000,
              updatedAt: 1668510873000,
              menuItemId: 2497,
              itemSeq: 2060,
              menuItemDesc: "SureOps Script Master",
              status: "Enable",
              menuId: 5,
              moduleName: "ADM2000",
              main_menu_action_name: "sureopsscriptmaster",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 2060,
            menuItemDesc: "SureOps Script Master",
            status: "Enable",
            moduleName: "ADM2000",
            main_menu_action_name: "sureopsscriptmaster",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1677735979000,
              updatedAt: 1677735979000,
              menuItemId: 5242,
              itemSeq: 2070,
              menuItemDesc: "Bug Tracker",
              status: "Enable",
              menuId: 5,
              moduleName: "B6000",
              main_menu_action_name: "bugtracker",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 2070,
            menuItemDesc: "Bug Tracker",
            status: "Enable",
            moduleName: "B6000",
            main_menu_action_name: "bugtracker",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1682560494000,
              updatedAt: 1683343056000,
              menuItemId: 7388,
              itemSeq: 3000,
              menuItemDesc: "Service Mesh",
              status: "Enable",
              menuId: 5,
              moduleName: "CloudNsureServices",
              main_menu_action_name: "CloudNsureServices",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 5,
            createby: null,
            createdAt: 1685351276000,
            updateby: null,
            updatedAt: 1685351276000,
            isdisable: "true",
            itemSeq: 3000,
            menuItemDesc: "Service Mesh",
            status: "Enable",
            moduleName: "CloudNsureServices",
            main_menu_action_name: "CloudNsureServices",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
        ],
        grpid: null,
        gmenuid: null,
        mdelete: "true",
        mvisible: "true",
        medit: "true",
        mquery: "true",
        mcreate: "true",
      },
      {
        usrGrp: {
          usrGrp: 42,
          groupName: "Admin",
          groupDesc: "Admin",
          createby: null,
          createdate: 1657880466000,
          updatedate: 1683869494000,
          updateby: null,
          status: "E",
          groupLevel: 20,
          createDateFormated: null,
          updateDateFormated: null,
        },
        menuItemId: {
          createdAt: 1673066274000,
          updatedAt: 1673066274000,
          menuItemId: 3933,
          itemSeq: 3000,
          menuItemDesc: "Log Management",
          status: "Enable",
          menuId: 0,
          moduleName: "LB3000",
          main_menu_action_name: "log",
          main_menu_icon_name: "eye",
          subMenus: [],
        },
        mexport: "true",
        menuId: 0,
        createby: null,
        createdAt: 1685351307000,
        updateby: null,
        updatedAt: 1685351307000,
        isdisable: "true",
        itemSeq: 3000,
        menuItemDesc: "Log Management",
        status: "Enable",
        moduleName: "LB3000",
        main_menu_action_name: "log",
        main_menu_icon_name: "eye",
        subMenus: [
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1673066699000,
              updatedAt: 1673066699000,
              menuItemId: 3934,
              itemSeq: 3010,
              menuItemDesc: "Log configuration",
              status: "Enable",
              menuId: 3933,
              moduleName: "log configuration",
              main_menu_action_name: "log",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 3933,
            createby: null,
            createdAt: 1685351307000,
            updateby: null,
            updatedAt: 1685351307000,
            isdisable: "true",
            itemSeq: 3010,
            menuItemDesc: "Log configuration",
            status: "Enable",
            moduleName: "log configuration",
            main_menu_action_name: "log",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1673066930000,
              updatedAt: 1673066930000,
              menuItemId: 3935,
              itemSeq: 3020,
              menuItemDesc: "Exceptions",
              status: "Enable",
              menuId: 3933,
              moduleName: "Ex3000",
              main_menu_action_name: "exception",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 3933,
            createby: null,
            createdAt: 1685351307000,
            updateby: null,
            updatedAt: 1685351307000,
            isdisable: "true",
            itemSeq: 3020,
            menuItemDesc: "Exceptions",
            status: "Enable",
            moduleName: "Ex3000",
            main_menu_action_name: "exception",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1673066991000,
              updatedAt: 1673067088000,
              menuItemId: 3936,
              itemSeq: 3030,
              menuItemDesc: "Session Logs",
              status: "Enable",
              menuId: 3933,
              moduleName: "Sl3000",
              main_menu_action_name: "sessionlogger",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 3933,
            createby: null,
            createdAt: 1685351307000,
            updateby: null,
            updatedAt: 1685351307000,
            isdisable: "true",
            itemSeq: 3030,
            menuItemDesc: "Session Logs",
            status: "Enable",
            moduleName: "Sl3000",
            main_menu_action_name: "sessionlogger",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1673067031000,
              updatedAt: 1673067031000,
              menuItemId: 3937,
              itemSeq: 3040,
              menuItemDesc: "Audit Report",
              status: "Enable",
              menuId: 3933,
              moduleName: "AR3000",
              main_menu_action_name: "auditreport",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 3933,
            createby: null,
            createdAt: 1685351307000,
            updateby: null,
            updatedAt: 1685351307000,
            isdisable: "true",
            itemSeq: 3040,
            menuItemDesc: "Audit Report",
            status: "Enable",
            moduleName: "AR3000",
            main_menu_action_name: "auditreport",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
        ],
        grpid: null,
        gmenuid: null,
        mdelete: "true",
        mvisible: "true",
        medit: "true",
        mquery: "true",
        mcreate: "true",
      },
      {
        usrGrp: {
          usrGrp: 42,
          groupName: "Admin",
          groupDesc: "Admin",
          createby: null,
          createdate: 1657880466000,
          updatedate: 1683869494000,
          updateby: null,
          status: "E",
          groupLevel: 20,
          createDateFormated: null,
          updateDateFormated: null,
        },
        menuItemId: {
          createdAt: 1658078563000,
          updatedAt: 1673066344000,
          menuItemId: 7,
          itemSeq: 4000,
          menuItemDesc: "Job",
          status: "Enable",
          menuId: 0,
          moduleName: "JOB4000",
          main_menu_action_name: "job",
          main_menu_icon_name: "clock",
          subMenus: [],
        },
        mexport: "true",
        menuId: 0,
        createby: null,
        createdAt: 1685416791000,
        updateby: null,
        updatedAt: 1685416791000,
        isdisable: "true",
        itemSeq: 4000,
        menuItemDesc: "Job",
        status: "Enable",
        moduleName: "JOB4000",
        main_menu_action_name: "job",
        main_menu_icon_name: "clock",
        subMenus: [
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483663000,
              menuItemId: 14,
              itemSeq: 4010,
              menuItemDesc: "Job log",
              status: "Enable",
              menuId: 7,
              moduleName: "JOB3000",
              main_menu_action_name: "scheduler",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 7,
            createby: null,
            createdAt: 1685416791000,
            updateby: null,
            updatedAt: 1685416791000,
            isdisable: "true",
            itemSeq: 4010,
            menuItemDesc: "Job log",
            status: "Enable",
            moduleName: "JOB3000",
            main_menu_action_name: "scheduler",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483670000,
              menuItemId: 15,
              itemSeq: 4020,
              menuItemDesc: "Job Queue",
              status: "Enable",
              menuId: 7,
              moduleName: "JOB3000",
              main_menu_action_name: "pipeline",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 7,
            createby: null,
            createdAt: 1685416791000,
            updateby: null,
            updatedAt: 1685416791000,
            isdisable: "true",
            itemSeq: 4020,
            menuItemDesc: "Job Queue",
            status: "Enable",
            moduleName: "JOB3000",
            main_menu_action_name: "pipeline",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1689119874000,
              menuItemId: 16,
              itemSeq: 4030,
              menuItemDesc: "Job Schedule",
              status: "Enable",
              menuId: 7,
              moduleName: "JOB3000",
              main_menu_action_name: "schduleinfo",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 7,
            createby: null,
            createdAt: 1685416791000,
            updateby: null,
            updatedAt: 1685416791000,
            isdisable: "true",
            itemSeq: 4030,
            menuItemDesc: "Job Schedule",
            status: "Enable",
            moduleName: "JOB3000",
            main_menu_action_name: "scheduleinfo",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483690000,
              menuItemId: 34,
              itemSeq: 4040,
              menuItemDesc: "Sure Connect",
              status: "Enable",
              menuId: 7,
              moduleName: "JOB3000",
              main_menu_action_name: "sureconnect",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 7,
            createby: null,
            createdAt: 1685416791000,
            updateby: null,
            updatedAt: 1685416791000,
            isdisable: "true",
            itemSeq: 4040,
            menuItemDesc: "Sure Connect",
            status: "Enable",
            moduleName: "JOB3000",
            main_menu_action_name: "sureconnect",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1668506697000,
              updatedAt: 1675483708000,
              menuItemId: 2496,
              itemSeq: 4050,
              menuItemDesc: "Sure API",
              status: "Enable",
              menuId: 7,
              moduleName: "JOB3000",
              main_menu_action_name: "sureapi",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 7,
            createby: null,
            createdAt: 1685416791000,
            updateby: null,
            updatedAt: 1685416791000,
            isdisable: "true",
            itemSeq: 4050,
            menuItemDesc: "Sure API",
            status: "Enable",
            moduleName: "JOB3000",
            main_menu_action_name: "sureapi",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1659694095000,
              updatedAt: 1684283882000,
              menuItemId: 1284,
              itemSeq: 4060,
              menuItemDesc: "Connector Mapping",
              status: "Enable",
              menuId: 7,
              moduleName: "Connector Mapping",
              main_menu_action_name: "connectormapping",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 7,
            createby: null,
            createdAt: 1685416791000,
            updateby: null,
            updatedAt: 1685416791000,
            isdisable: "true",
            itemSeq: 4060,
            menuItemDesc: "Connector Mapping",
            status: "Enable",
            moduleName: "Connector Mapping",
            main_menu_action_name: "connectormapping",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
        ],
        grpid: null,
        gmenuid: null,
        mdelete: "true",
        mvisible: "true",
        medit: "true",
        mquery: "true",
        mcreate: "true",
      },
      {
        usrGrp: {
          usrGrp: 42,
          groupName: "Admin",
          groupDesc: "Admin",
          createby: null,
          createdate: 1657880466000,
          updatedate: 1683869494000,
          updateby: null,
          status: "E",
          groupLevel: 20,
          createDateFormated: null,
          updateDateFormated: null,
        },
        menuItemId: {
          createdAt: 1684582902000,
          updatedAt: 1684808098000,
          menuItemId: 8,
          itemSeq: 5000,
          menuItemDesc: "Security",
          status: "Enable",
          menuId: 0,
          moduleName: "secu",
          main_menu_action_name: "security",
          main_menu_icon_name: "lock",
          subMenus: [],
        },
        mexport: "true",
        menuId: 0,
        createby: null,
        createdAt: 1685353028000,
        updateby: null,
        updatedAt: 1685353028000,
        isdisable: "true",
        itemSeq: 5000,
        menuItemDesc: "Security",
        status: "Enable",
        moduleName: "secu",
        main_menu_action_name: "security",
        main_menu_icon_name: "lock",
        subMenus: [
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483774000,
              menuItemId: 18,
              itemSeq: 5010,
              menuItemDesc: "User Maintenance",
              status: "Enable",
              menuId: 8,
              moduleName: "SEC4000",
              main_menu_action_name: "usermaintance",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 8,
            createby: null,
            createdAt: 1685353028000,
            updateby: null,
            updatedAt: 1685353028000,
            isdisable: "true",
            itemSeq: 5010,
            menuItemDesc: "User Maintenance",
            status: "Enable",
            moduleName: "SEC4000",
            main_menu_action_name: "usermaintance",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483783000,
              menuItemId: 19,
              itemSeq: 5020,
              menuItemDesc: "User Group Maintenance",
              status: "Enable",
              menuId: 8,
              moduleName: "SEC4000",
              main_menu_action_name: "usergrpmaintance",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 8,
            createby: null,
            createdAt: 1685353028000,
            updateby: null,
            updatedAt: 1685353028000,
            isdisable: "true",
            itemSeq: 5020,
            menuItemDesc: "User Group Maintenance",
            status: "Enable",
            moduleName: "SEC4000",
            main_menu_action_name: "usergrpmaintance",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483790000,
              menuItemId: 20,
              itemSeq: 5030,
              menuItemDesc: "Menu Maintenance",
              status: "Enable",
              menuId: 8,
              moduleName: "SEC4000",
              main_menu_action_name: "menumaintance",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 8,
            createby: null,
            createdAt: 1685353028000,
            updateby: null,
            updatedAt: 1685353028000,
            isdisable: "true",
            itemSeq: 5030,
            menuItemDesc: "Menu Maintenance",
            status: "Enable",
            moduleName: "SEC4000",
            main_menu_action_name: "menumaintance",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483798000,
              menuItemId: 21,
              itemSeq: 5040,
              menuItemDesc: "Menu Access Control",
              status: "Enable",
              menuId: 8,
              moduleName: "SEC4000",
              main_menu_action_name: "menuaccess",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 8,
            createby: null,
            createdAt: 1685353028000,
            updateby: null,
            updatedAt: 1685353028000,
            isdisable: "true",
            itemSeq: 5040,
            menuItemDesc: "Menu Access Control",
            status: "Enable",
            moduleName: "SEC4000",
            main_menu_action_name: "menuaccess",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1658078563000,
              updatedAt: 1675483807000,
              menuItemId: 17,
              itemSeq: 5050,
              menuItemDesc: "System Parameters",
              status: "Enable",
              menuId: 8,
              moduleName: "SEC4000",
              main_menu_action_name: "systemparameters",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 8,
            createby: null,
            createdAt: 1685353028000,
            updateby: null,
            updatedAt: 1685353028000,
            isdisable: "true",
            itemSeq: 5050,
            menuItemDesc: "System Parameters",
            status: "Enable",
            moduleName: "SEC4000",
            main_menu_action_name: "systemparameters",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
          {
            usrGrp: {
              usrGrp: 42,
              groupName: "Admin",
              groupDesc: "Admin",
              createby: null,
              createdate: 1657880466000,
              updatedate: 1683869494000,
              updateby: null,
              status: "E",
              groupLevel: 20,
              createDateFormated: null,
              updateDateFormated: null,
            },
            menuItemId: {
              createdAt: 1669178455000,
              updatedAt: 1675483815000,
              menuItemId: 2498,
              itemSeq: 5060,
              menuItemDesc: "Access Type",
              status: "Enable",
              menuId: 8,
              moduleName: "SEC4000",
              main_menu_action_name: "accesstype",
              main_menu_icon_name: null,
              subMenus: [],
            },
            mexport: "true",
            menuId: 8,
            createby: null,
            createdAt: 1685353028000,
            updateby: null,
            updatedAt: 1685353028000,
            isdisable: "true",
            itemSeq: 5060,
            menuItemDesc: "Access Type",
            status: "Enable",
            moduleName: "SEC4000",
            main_menu_action_name: "accesstype",
            main_menu_icon_name: null,
            subMenus: [],
            grpid: null,
            gmenuid: null,
            mdelete: "true",
            mvisible: "true",
            medit: "true",
            mquery: "true",
            mcreate: "true",
          },
        ],
        grpid: null,
        gmenuid: null,
        mdelete: "true",
        mvisible: "true",
        medit: "true",
        mquery: "true",
        mcreate: "true",
      },
    ]
    res.status(200).json(response)
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
