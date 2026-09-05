package vn.iotstar.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username", columnDefinition = "NVARCHAR(100) NOT NULL")
	private String username;

	@Column(name = "password", columnDefinition = "NVARCHAR(255) NOT NULL")
	private String password;

	@Column(name = "images", columnDefinition = "NVARCHAR(255)")
	private String images;

	@Column(name = "fullname", columnDefinition = "NVARCHAR(200)")
	private String fullname;

	@Column(name = "email", columnDefinition = "NVARCHAR(150) NOT NULL")
	private String email;

	@Column(name = "phone", columnDefinition = "NVARCHAR(20)")
	private String phone;

	@Column(name = "roleid")
	private int roleid; // 1 = user, 2 = admin, 3 = manager

	@Column(name = "createDate")
	private Timestamp createDate;

	// 0 = chua kich hoat (cho xac thuc OTP khi dang ky), 1 = da kich hoat
	@Column(name = "active")
	private int active;

	// OTP dung chung cho ca kich hoat tai khoan va quen mat khau
	@Column(name = "otp", length = 10)
	private String otp;

	@Column(name = "otpExpiry")
	private Timestamp otpExpiry;

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(Timestamp otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", roleid=" + roleid + ", active="
				+ active + "]";
	}
}
