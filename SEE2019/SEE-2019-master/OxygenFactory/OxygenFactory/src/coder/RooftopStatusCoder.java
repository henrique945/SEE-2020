package coder;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.exceptions.RTIinternalError;
import model.OxygenFactoryRooftopStatus;
import skf.coder.Coder;


public class RooftopStatusCoder implements Coder<OxygenFactoryRooftopStatus> {
	
	private HLAinteger16LE coder = null;
	private EncoderFactory factory = null;

	public RooftopStatusCoder() throws RTIinternalError {
		this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
		this.coder = factory.createHLAinteger16LE();
	}

	@Override
	public Class<OxygenFactoryRooftopStatus> getAllowedType() {
		return OxygenFactoryRooftopStatus.class;
	}

	@Override
	public OxygenFactoryRooftopStatus decode(byte[] bytes) throws DecoderException {
		coder.decode(bytes);
		return OxygenFactoryRooftopStatus.lookup(coder.getValue());
	}

	@Override
	public byte[] encode(OxygenFactoryRooftopStatus oxygenFactoryRooftopStatus) {
		coder.setValue(oxygenFactoryRooftopStatus.getValue());
		return coder.toByteArray();
	}
}
