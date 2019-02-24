package coder;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.exceptions.RTIinternalError;
import model.WaterFinderStatus;
import skf.coder.Coder;

public class WaterFinderStatusCoder implements Coder<WaterFinderStatus> {
	private HLAinteger16LE coder = null;
	private EncoderFactory factory = null;

	public WaterFinderStatusCoder() throws RTIinternalError {
		this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
		this.coder = factory.createHLAinteger16LE();
	}

	@Override
	public WaterFinderStatus decode(byte[] bytes) throws DecoderException {
		coder.decode(bytes);
		return WaterFinderStatus.lookup(coder.getValue());
	}

	@Override
	public byte[] encode(WaterFinderStatus marsMinerStatus) {
		coder.setValue(marsMinerStatus.getValue());
		return coder.toByteArray();
	}

	@Override
	public Class<WaterFinderStatus> getAllowedType() {
		return WaterFinderStatus.class;
	}
}