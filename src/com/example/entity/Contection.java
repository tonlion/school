package com.example.entity;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Contection implements Parcelable {

	private String id;
	private String itemValue;
	private int type;
	private boolean isChecked;
	private List<Contection> nodes;
	public final static int ALL_MAJOR = 1;
	public final static int ALL_ROLE = 2;
	public final static int MAJOR = 3;
	public final static int ROLE = 4;
	public final static int CLZSS = 5;
	public final static int STUDENT = 6;

	public Contection() {
		super();
	}

	public Contection(String id, String itemValue, int type) {
		super();
		this.id = id;
		this.itemValue = itemValue;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<Contection> getNodes() {
		return nodes;
	}

	public void setNodes(List<Contection> nodes) {
		this.nodes = nodes;
	}

	protected Contection(Parcel in) {
		id = in.readString();
		itemValue = in.readString();
		type = in.readInt();
		nodes = in.createTypedArrayList(Contection.CREATOR);
	}

	public static final Creator<Contection> CREATOR = new Creator<Contection>() {
		@Override
		public Contection createFromParcel(Parcel in) {
			return new Contection(in);
		}

		@Override
		public Contection[] newArray(int size) {
			return new Contection[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(id);
		parcel.writeString(itemValue);
		parcel.writeInt(type);
		parcel.writeTypedList(nodes);
	}

}
