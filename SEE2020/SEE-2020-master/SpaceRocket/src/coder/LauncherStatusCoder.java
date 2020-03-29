package coder;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.exceptions.RTIinternalError;
import model.LauncherStatus;
import skf.coder.Coder;


public class LauncherStatusCoder implements Coder<LauncherStatus> {
	
	private HLAinteger16LE coder = null;
	private EncoderFactory factory = null;

	public LauncherStatusCoder() throws RTIinternalError {
		this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
		this.coder = factory.createHLAinteger16LE();
	}

	@Override
	public Class<LauncherStatus> getAllowedType() {
		return LauncherStatus.class;
	}

	@Override
	public LauncherStatus decode(byte[] bytes) throws DecoderException {
		coder.decode(bytes);
		return LauncherStatus.lookup(coder.getValue());
	}

	@Override
	public byte[] encode(LauncherStatus oxygenFactoryRooftopStatus) {
		coder.setValue(oxygenFactoryRooftopStatus.getValue());
		return coder.toByteArray();
	}
}
