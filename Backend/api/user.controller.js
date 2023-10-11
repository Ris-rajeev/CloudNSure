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

const registerUser = (req, res) => {
  const { email } = req.body
  const otp = otpGenerator.generate(6, {
    digits: true,
    alphabets: false,
    upperCase: false,
    specialChars: false,
  })

  pool.query(
    "INSERT INTO registration (email, otp) VALUES (?, ?)",
    [email, otp],
    (err, result) => {
      if (err) {
        console.error(err)
        return res.json({
          success: 0,
          data: "Email already exit",
        })
      }

      transporter.sendMail({
        from: "your-email@example.com",
        to: email,
        subject: "Email Verification OTP",
        text: `Your OTP for email verification is: ${otp}`,
      })

      return res.json({
        success: 1,
        data: "otp sent successfully",
      })
    }
  )
}

const verifyOTP = (req, res) => {
  const { email, otp } = req.body

  pool.query(
    "SELECT * FROM registration WHERE email = ?",
    [email],
    (err, results) => {
      if (err) {
        console.error(err)
        return res.json({ success: 0, message: "Error fetching user data" })
      }

      if (results.length === 0) {
        return res.json({ success: 0, message: "User not found" })
      }

      const user = results[0]

      if (user.otp === otp) {
        // Mark the user as verified
        pool.query(
          "UPDATE registration SET isVerified = true WHERE id = ?",
          [user.id],
          (err, result) => {
            if (err) {
              console.error(err)
              return res.json({
                success: 0,
                message: "Error updating user verification status",
              })
            }
            const token = sign({ result: results }, "secret", {
              expiresIn: "1h",
            })
            res.json({ success: 1, message: "Email verified", token })
          }
        )
      } else {
        res.status(400).json({ message: "Invalid OTP" })
      }
    }
  )
}

const getUsers = (req, res) => {
  pool.query(
    `select id,firstname, lastname, role, email, password, number from registration`,
    [],
    (error, results, fields) => {
      if (error) {
        console.log(err)
        return res.json({
          success: 0,
          message: "Table is empty",
        })
      }
      return res.json({
        success: 1,
        data: results,
      })
    }
  )
}

const getUsersByUserId = (req, res) => {
  const id = req.params.id
  pool.query(
    `select id,firstname, lastname, role, email, password, number from registration where id = ?`,
    [id],
    (err, results, fields) => {
      if (err) {
        console.log(err)
        return
      }
      if (!results) {
        return res.json({
          success: 0,
          message: "Record not found",
        })
      }
      return res.json({
        success: 1,
        data: results,
      })
    }
  )
}

const updateUsers = (req, res) => {
  const body = req.body
  const salt = genSaltSync(10)
  body.password = hashSync(body.password, salt)
  pool.query(
    `update registration set firstName=?, lastName=?, role=?, email=?, password=?, number=? where id = ?`,
    [
      body.first_name,
      body.last_name,
      body.role,
      body.email,
      body.password,
      body.number,
      body.id,
    ],
    (err, results, fields) => {
      if (err) {
        console.log(err)
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

const deleteUsers = (req, res) => {
  const id = req.params.id
  pool.query(
    `delete from registration where id = ?`,
    [id],
    (err, results, fields) => {
      if (err) {
        console.log(err)
        return
      }
      return res.json({
        success: 1,
        data: "user deleted succesfully",
      })
    }
  )
}

const login = (req, res) => {
  const body = req.body
  pool.query(
    `select *from sec_users where email = ?`,
    [body.email],
    (err, results, fields) => {
      if (err) {
        console.log(err)
      }
      if (!results) {
        return res.json({
          success: 0,
          data: "Invalid email or password",
        })
      }
      // console.log(results)
      // const result = compareSync(body.password, results[0].password)
      // if (result) {
      if (results) {
        results.password = undefined
        const jsontoken = sign({ result: results }, "secret", {
          expiresIn: "1h",
        })
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

module.exports = {
  getUsers,
  getUsersByUserId,
  updateUsers,
  deleteUsers,
  login,
  registerUser,
  verifyOTP,
}
