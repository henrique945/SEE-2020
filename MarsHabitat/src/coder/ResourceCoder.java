package coder;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.*;
import hla.rti1516e.exceptions.RTIinternalError;
import objectClass.Resource;
import objectClass.ResourceType;
import skf.coder.Coder;

/**
 * Created by Lucas on 21/03/2017.
 */

public class ResourceCoder implements Coder<Resource>{
    private HLAfixedRecord coder = null;
    private EncoderFactory factory = null;
    private HLAinteger16LE type = null;
    private HLAfloat64LE quantity = null;

    public ResourceCoder() throws RTIinternalError {
        factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
        coder = factory.createHLAfixedRecord();
        quantity = factory.createHLAfloat64LE();
        type = factory.createHLAinteger16LE();

        coder.add(quantity);
        coder.add(type);
    }

    @Override
    public Resource decode(byte[] bytes) throws DecoderException {
        coder.decode(bytes);

        quantity = (HLAfloat64LE)coder.get(0);
        type = (HLAinteger16LE)coder.get(1);

        return new Resource(quantity.getValue(), ResourceType.lookup(type.getValue()));
    }

    @Override
    public byte[] encode(Resource resource) {
        quantity.setValue(resource.getQuantity());
        type.setValue(resource.getResourceType().getValue());
        return coder.toByteArray();
    }

    @Override
    public Class<Resource> getAllowedType() {
        return Resource.class;
    }
}
