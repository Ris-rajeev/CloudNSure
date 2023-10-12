const loadMenuByUser = async (req, res) => {
  try {
    // ... (previous code to get usrgrp and root items)
    const usrgrp = 1 // Replace this with your actual logic to get the user's group
    let sql = `SELECT * FROM sec_grp_menu_access WHERE menu_id = 0 AND status= 'Enable' AND usr_grp = ${usrgrp} ORDER BY item_seq`
    const root = await executeQuery(sql)

    for (let i = 0; i < root.length; i++) {
      const rootMenuItemId = root[i]

      // ... (previous code to fetch menu items)
      let sql1 = `SELECT * FROM sec_grp_menu_access a WHERE a.usr_grp =${usrgrp} and a.menu_item_id=${rootMenuItemId.menu_item_id}`
      const menu = await executeQuery(sql1)

      for (const menuItem of menu) {
        // ... (previous code to fetch submenu items)
        let sql2 = `SELECT * FROM sec_grp_menu_access a where a.status= 'Enable' and a.menu_id=${menuItem.menu_item_id} and a.usr_grp =${usrgrp} ORDER BY item_seq`
        const allSubmenu = await executeQuery(sql2)

        menuItem.subMenus = allSubmenu

        // Send the updated response after each submenu query
        res.write(JSON.stringify(root)) // Convert the root array to JSON
        res.flush() // Ensure the response is sent immediately

        // If you want to introduce some delay between updates, you can use a delay function
        // await delay(1000); // Delay for 1 second, if needed
      }
    }

    // Final response after all queries are complete
    res.end()
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Internal server error" })
  }
}
