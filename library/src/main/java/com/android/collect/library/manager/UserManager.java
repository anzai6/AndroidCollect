package com.android.collect.library.manager;


import android.content.Context;

import com.android.collect.library.bean.User;
import com.android.collect.library.util.Util;

public class UserManager {

	private static UserManager manager = new UserManager();
	public static boolean isLogin;
	private User user;

	public static UserManager getInstance() {
		return manager;
	}

	/*public String getUserSeq() {
		if (isLogin)
			return user.getUserSeq();
		else
			return "";
	}*/

	public String getUserName() {
		if (isLogin)
			return user.getUserName();
		else
			return "";
	}


	public String getMobilePhone() {
		if (isLogin)
			return user.getMobilePhone();
		else
			return "";
	}

	public void setMobilePhone(String phone) {
		if (isLogin)
			user.setMobilePhone(phone);
	}


	public String getIdNo() {
		if (isLogin)
			return user.getIdNo();
		else
			return "";
	}


	/*public String getAcNo() {
		if (isLogin)
			return user.getAcNo();
		else
			return null;
	}

	public String getAcctNo() {
		if (isLogin)
			return user.getAcctNo();
		else
			return null;
	}

	public String getCardBankName() {
		if (isLogin)
			return user.getCardBankName();
		else
			return null;
	}*/


	/**
	 * 登录对象
	 *
	 * @param user
	 */
	public void setUser(User user) {
		this.user = Util.checkNotNull(user);
	}

	public User getUser() {
		if (isLogin)
			return user;
		else
			return null;
	}

	/**
	 * 是否开户 true开户
	 *
	 * @param context
	 * @return
	 */
	public static boolean isUserOpen(Context context) {
		if (isLogin) {
			/*String IsOpenAcct = SharedPreferencesUtil.getString(context,
					KeyHelper.IsOpenAcct, "");// 1 有电子账户
			if ("1".equals(IsOpenAcct)) // 开户
				return true;
			else
				return false;*/
		}
		return false;
	}


	/**
	 * 清除user信息
	 */
	public void clearUser() {
		if (user != null) {
			user = null;
		}
		isLogin = false;
	}

}
