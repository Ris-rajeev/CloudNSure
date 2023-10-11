const db = require("../config/database")

async function executeQuery(sql, values) {
  const connection = await db.getConnection()
  try {
    const [rows] = await connection.execute(sql, values)
    return rows
  } finally {
    connection.release()
  }
}

// Create a new record
const createDataInTable = async (req, res) => {
  const { tableName } = req.params
  const { data } = req.body

  const sql = `INSERT INTO ${tableName} SET ?`

  try {
    const result = await executeQuery(sql, data)
    res.json({
      message: "Record added successfully",
      insertId: result.insertId,
    })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error adding record" })
  }
}

// Get all records from a table
const getTable = async (req, res) => {
  const { tableName } = req.params

  const sql = `SELECT * FROM ${tableName}`

  try {
    const records = await executeQuery(sql)
    res.json(records)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error fetching records" })
  }
}

// Get a record by ID
const getTableById = async (req, res) => {
  const { tableName, id } = req.params

  const sql = `SELECT * FROM ${tableName} WHERE id = ?`
  const values = [id]

  try {
    const [record] = await executeQuery(sql, values)
    if (!record) {
      return res.status(404).json({ message: "Record not found" })
    }
    res.json(record)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error fetching record" })
  }
}

// Update a record by ID
const updateDataInTable = async (req, res) => {
  const { tableName, id } = req.params
  const body = req.body

  const sql = `UPDATE ${tableName} SET ? WHERE id = ?`
  const values = [
    body.first_name,
    body.last_name,
    body.role,
    body.email,
    body.password,
    body.number,
    id,
  ]

  try {
    await executeQuery(sql, values)
    res.json({ message: "Record updated successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error updating record" })
  }
}

// Delete a record by ID
const deleteDataInTable = async (req, res) => {
  const { tableName, id } = req.params

  const sql = `DELETE FROM ${tableName} WHERE id = ?`
  const values = [id]

  try {
    await executeQuery(sql, values)
    res.json({ message: "Record deleted successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error deleting record" })
  }
}

// Controllers for sec_menu_det and sec_grp_menu_access
const getTableByIdSec_Menu_Det = async (req, res) => {
  const { id } = req.params

  const sql = `SELECT * FROM sec_menu_det WHERE menu_item_id = ?`
  const values = [id]

  try {
    const [record] = await executeQuery(sql, values)
    if (!record) {
      return res.status(404).json({ message: "Record not found" })
    }
    res.json(record)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error fetching record" })
  }
}

const addMenu = async (req, res) => {
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
    res.json(record)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error fetching record Outside" })
  }
}

const deleteMenu = async (req, res) => {
  const { id } = req.params
  const sql = `DELETE FROM sec_grp_menu_access WHERE menu_item_id = ?`
  const values = [id]
  try {
    await executeQuery(sql, values)
    res.json({ message: "Record deleted successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error deleting record" })
  }
}

const updateMenu = async (req, res) => {
  const { id } = req.params
  const data = req.body
  var today = new Date()
  var date =
    today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate()
  var time =
    today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
  var dateTime = date + " " + time
  const sql = `UPDATE sec_grp_menu_access SET usr_grp = 41, main_menu_action_name = ?, main_menu_icon_name = ?, menu_id = ?, menu_item_desc = ?, module_name = ?, status = ?, updated_at = ? WHERE menu_item_id = ?, isdisable = ?, item_seq= = ?, m_create= = ?, m_delete= = ?, m_edit= = ? , m_query= = ?, m_visible= = ?,`
  const values = [
    data.main_menu_action_name,
    data.main_menu_icon_name,
    data.menu_id,
    data.menu_item_desc,
    data.module_name,
    data.status,
    dateTime,
    id,
    data.isdisable,
    data.m_create,
    data.m_delete,
    data.m_edit,
    data.m_query,
    data.m_visible,
  ]
  try {
    await executeQuery(sql, values)
    res.json({ message: "Record updated successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error updating record" })
  }
}

// Controller for Role table get and get id works from default one
const createDataInTableRole = async (req, res) => {
  const data = req.body

  const sql = `INSERT INTO role SET description =  ?, name = ?`
  const values = [data.description, data.name]

  try {
    const result = await executeQuery(sql, values)
    res.json({
      message: "Record added successfully",
      insertId: result.insertId,
    })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error adding record" })
  }
}

// Update a record by ID
const updateDataInTableRole = async (req, res) => {
  const { id } = req.params
  const body = req.body

  const sql = `UPDATE role SET description =  ?, name = ? WHERE id = ?`
  const values = [body.description, body.name, id]

  try {
    await executeQuery(sql, values)
    res.json({ message: "Record updated successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error updating record" })
  }
}

// Delete a record by ID
const deleteDataInTableRole = async (req, res) => {
  const { id } = req.params

  const sql = `DELETE FROM role WHERE id = ?`
  const values = [id]

  try {
    await executeQuery(sql, values)
    res.json({ message: "Record deleted successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error deleting record" })
  }
}

// CRUD for student table

// Create a new record
const createDataInStudentTable = async (req, res) => {
  const data = req.body

  const sql = `INSERT INTO student SET username = ?, password = ?, email = ?, full_name = ?, date_of_birth = ?, gender = ?, address = ?, department = ?`
  const values = [
    data.username,
    data.password,
    data.email,
    data.full_name,
    data.date_of_birth,
    data.gender,
    data.address,
    data.department,
  ]

  try {
    const result = await executeQuery(sql, values)
    res.json({
      message: "Record added successfully",
      insertId: result.insertId,
    })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error adding record" })
  }
}

// Get all records from a table
const getStudentTable = async (req, res) => {
  const sql = `SELECT * FROM student`

  try {
    const records = await executeQuery(sql)
    res.json(records)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error fetching records" })
  }
}

// Get a record by ID
const getStudentTableById = async (req, res) => {
  const { id } = req.params

  const sql = `SELECT * FROM student WHERE id = ?`
  const values = [id]

  try {
    const [record] = await executeQuery(sql, values)
    if (!record) {
      return res.status(404).json({ message: "Record not found" })
    }
    res.json(record)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error fetching record" })
  }
}

// Update a record by ID
const updateDataInStudentTable = async (req, res) => {
  const { id } = req.params
  const data = req.body

  const sql = `UPDATE student SET username = ?, password = ?, email = ?, full_name = ?, date_of_birth = ?, gender = ?, address = ?, department = ? WHERE id = ?`
  const values = [
    data.username,
    data.password,
    data.email,
    data.full_name,
    data.date_of_birth,
    data.gender,
    data.address,
    data.department,
    id,
  ]

  try {
    await executeQuery(sql, values)
    res.json({ message: "Record updated successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error updating record" })
  }
}

// Delete a record by ID
const deleteDataInStudentTable = async (req, res) => {
  const sql = `DELETE FROM student WHERE id = ?`
  const values = [id]

  try {
    await executeQuery(sql, values)
    res.json({ message: "Record deleted successfully" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Error deleting record" })
  }
}

module.exports = {
  getTable,
  getTableById,
  createDataInTable,
  updateDataInTable,
  deleteDataInTable,
  addMenu,
  deleteMenu,
  updateMenu,
  getTableByIdSec_Menu_Det,
  createDataInTableRole,
  updateDataInTableRole,
  deleteDataInTableRole,
  createDataInStudentTable,
  getStudentTable,
  getStudentTableById,
  updateDataInStudentTable,
  deleteDataInStudentTable,
}
