const express = require("express")
const mysql = require("mysql2/promise")

const app = express()
const port = 3000 // You can set the desired port number.

app.use(express.json())

async function getMenuAccessByUserGroup(userGroup) {
  try {
    const connection = await mysql.createConnection({
      host: "your-mysql-host",
      user: "your-mysql-username",
      password: "your-mysql-password",
      database: "your-database-name",
    })

    const [rows] = await connection.execute(
      "SELECT * FROM sec_grp_menu_access WHERE usr_grp = ?",
      [userGroup]
    )

    if (rows.length === 0) {
      return null // No records found for the user group.
    }

    // Construct the JSON output using the fetched data.
    const jsonOutput = {
      usrGrp: {
        // Populate user group data here.
      },
      // Other properties from your provided JSON go here.
    }

    const subMenus = rows.map((row) => {
      // Construct sub-menu data based on the database row.
      return {
        // Populate sub-menu properties here.
      }
    })

    // Add sub-menus to the JSON structure.
    jsonOutput.subMenus = subMenus

    return jsonOutput
  } catch (error) {
    console.error("Error fetching data from the database:", error)
    throw error
  }
}

app.get("/menu-access/:userGroup", async (req, res) => {
  const userGroup = req.params.userGroup

  try {
    const jsonOutput = await getMenuAccessByUserGroup(userGroup)

    if (jsonOutput) {
      res.json(jsonOutput)
    } else {
      res.status(404).json({ error: "User group not found" })
    }
  } catch (error) {
    res
      .status(500)
      .json({ error: "An error occurred while processing the request" })
  }
})

app.listen(port, () => {
  console.log(`Server is running on port ${port}`)
})
