package domain;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Transactional;
import javax.jdo.annotations.Unique;

import org.json.JSONObject;

import api.APIUtils;
import database.GuestDAO;

@PersistenceCapable(detachable="true")
public class User {
	
	@Override
	public String toString() {
		return "User [nick=" + nick + ", password=" + password + ", dni=" + dni + ", legalInfo=" + legalInfo
				+ ", isHotelOwner=" + isHotelOwner + "]";
	}
	public static User fromJSON(JSONObject obj) {
		return new User(
				APIUtils.decode(obj.getString("nick")),
				PasswordEncryption.encryptPassword(APIUtils.decode(obj.getString("password"))),
				Guest.fromJSON(obj.getJSONObject("legalInfo")),
				obj.getBoolean("isHotelOwner")
			);
	}
	@PrimaryKey
	@Persistent
	private String nick;
	private String password;
	@Unique
	private String dni;
	@Transactional
	private Guest legalInfo;
	private boolean isHotelOwner;
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Guest getLegalInfo() {
		if(legalInfo == null && dni != null)
			return (legalInfo = GuestDAO.getInstance().find(dni));
		return legalInfo;
	}
	public void setLegalInfo(Guest legalInfo) {
		this.legalInfo = legalInfo;
		this.dni = legalInfo.getDni();
	}
	public boolean isHotelOwner() {
		return isHotelOwner;
	}

	public void setHotelOwner(boolean isHotelOwner) {
		this.isHotelOwner = isHotelOwner;
	}
	public boolean equals(Object o) {
		return o instanceof User && ((User)o).nick.equals(nick);
	}

	public User(String nick, String password, Guest legalInfo, boolean isHotelOwner) {
		super();
		this.nick = nick;
		this.dni = legalInfo.getDni();
		this.password = password;
		this.legalInfo = legalInfo;
		this.isHotelOwner = isHotelOwner;
	}
}
