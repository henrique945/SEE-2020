package coder;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.exceptions.RTIinternalError;
import model.MarsMinerStatus;
import skf.coder.Coder;


public class MarsMinerStatusCoder implements Coder<MarsMinerStatus> {
	private HLAinteger16LE coder = null;
	private EncoderFactory factory = null;

	public MarsMinerStatusCoder() throws RTIinternalError {
		this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
		this.coder = factory.createHLAinteger16LE();
	}

	@Override
	public MarsMinerStatus decode(byte[] bytes) throws DecoderException {
		coder.decode(bytes);
		return MarsMinerStatus.lookup(coder.getValue());
	}

	@Override
	public byte[] encode(MarsMinerStatus marsMinerStatus) {
		coder.setValue(marsMinerStatus.getValue());
		return coder.toByteArray();
	}

	@Override
	public Class<MarsMinerStatus> getAllowedType() {
		return MarsMinerStatus.class;
	}
}
