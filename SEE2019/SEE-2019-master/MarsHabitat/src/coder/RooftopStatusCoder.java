package coder;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.exceptions.RTIinternalError;
import model.RooftopStatus;
import skf.coder.Coder;

/**
 * Created by Lucas on 31/03/2017.
 */
public class RooftopStatusCoder implements Coder<RooftopStatus> {
    private HLAinteger16LE coder  = null;
    private EncoderFactory factory = null;

    public RooftopStatusCoder() throws RTIinternalError {
        this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
        this.coder = factory.createHLAinteger16LE();
    }

    @Override
    public RooftopStatus decode(byte[] bytes) throws DecoderException {
        coder.decode(bytes);
        return RooftopStatus.lookup(coder.getValue());
    }

    @Override
    public byte[] encode(RooftopStatus rooftopStatus) {
        coder.setValue(rooftopStatus.getValue());
        return coder.toByteArray();
    }

    @Override
    public Class<RooftopStatus> getAllowedType() {
        return RooftopStatus.class;
    }
}
