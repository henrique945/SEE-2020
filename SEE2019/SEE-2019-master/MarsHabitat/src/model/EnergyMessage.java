package model;

import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;


@InteractionClass(name = "EnergyMessage")
public class EnergyMessage {
	
	@Parameter(name= "payload", coder = HLAunicodeStringCoder.class)
	private String payload = null;

	public EnergyMessage() {
	}
	
	public EnergyMessage(String payload) {
		this.payload = payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getPayload() {
		return this.payload;
	}

}
