package com.group2.smartfridge.smartfridge.diasuite;

public class RemoteDeviceInformationsMessenger implements fr.inria.phoenix.diasuitebox.remote.client.RemoteDeviceInformation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String type;
	private final String user;
	private final String location;

	public String getLocation() {
		return location;
	}
	
	public RemoteDeviceInformationsMessenger() {
		this.type = "Messenger";
		this.user = "Android";
		this.location = "FrigoApp";
	}

	@Override
	public String getType() {
		return type;
	}

	public String getUser() {
		return user;
	}

}
