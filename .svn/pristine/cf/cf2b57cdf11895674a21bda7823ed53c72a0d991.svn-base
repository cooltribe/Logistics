package com.seeyuan.logistics.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;

/**
 * 附近朋友
 * @author zhazhaobao
 *
 */
public class AroundFriends implements Parcelable {

	/**
	 * 头像
	 */
	private RemoteImageView headImage;

	/**
	 * 名称
	 */
	private String userName;

	/**
	 * 描述
	 */
	private String description;

	private boolean isAuthentication = false;

	/**
	 * 头像URL
	 */
	private String headImageURL;

	/**
	 * 维度
	 */
	private String latitude;

	/**
	 * 经度
	 */
	private String longitude;

	public RemoteImageView getHeadImage() {
		return headImage;
	}

	public void setHeadImage(RemoteImageView headImage) {
		this.headImage = headImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAuthentication() {
		return isAuthentication;
	}

	public void setAuthentication(boolean isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	public String getHeadImageURL() {
		return headImageURL;
	}

	public void setHeadImageURL(String headImageURL) {
		this.headImageURL = headImageURL;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public AroundFriends() {

	}

	public AroundFriends(Parcel p) {
		description = p.readString();
		headImageURL = p.readString();
		userName = p.readString();
		latitude = p.readString();
		longitude = p.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int arg1) {
		p.writeString(description);
		p.writeString(headImageURL);
		p.writeString(userName);
		p.writeString(latitude);
		p.writeString(longitude);
	}

	public static final Parcelable.Creator<AroundFriends> CREATOR = new Creator<AroundFriends>() {

		@Override
		public AroundFriends createFromParcel(Parcel p) {
			return new AroundFriends(p);
		}

		@Override
		public AroundFriends[] newArray(int size) {
			return new AroundFriends[size];
		}

	};
}
