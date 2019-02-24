package objectClass;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.exceptions.RTIinternalError;
import skf.coder.Coder;

/**
 * Created by Lucas on 21/03/2017.
 */
public class ResourceTypeCoder implements Coder<ResourceType> {
    private HLAinteger16LE coder  = null;
    private EncoderFactory factory = null;

    public ResourceTypeCoder() throws RTIinternalError {
        this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
        this.coder = factory.createHLAinteger16LE();
    }

    @Override
    public ResourceType decode(byte[] bytes) throws DecoderException {
        coder.decode(bytes);
        return ResourceType.lookup(coder.getValue());
    }

    @Override
    public byte[] encode(ResourceType resourceType) {
        coder.setValue(resourceType.getValue());
        return coder.toByteArray();
    }

    @Override
    public Class<ResourceType> getAllowedType() {
        return ResourceType.class;
    }
}
