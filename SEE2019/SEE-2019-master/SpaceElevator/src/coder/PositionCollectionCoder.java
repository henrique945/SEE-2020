package coder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DataElementFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedArray;
import hla.rti1516e.encoding.HLAfloat64LE;
import hla.rti1516e.encoding.HLAvariableArray;
import hla.rti1516e.exceptions.RTIinternalError;
import model.PositionCollection;
import skf.coder.Coder;

public class PositionCollectionCoder implements Coder<PositionCollection>{
	
	private EncoderFactory factory = null;
	private HLAvariableArray<HLAfixedArray<HLAfloat64LE>> coder = null;
	
	private DataElementFactory<HLAfixedArray<HLAfloat64LE>> elementFactory = new DataElementFactory<HLAfixedArray<HLAfloat64LE>>(){
		
		@Override
		public HLAfixedArray<HLAfloat64LE> createElement(int index) {
			
			return factory.createHLAfixedArray(factory.createHLAfloat64LE(), factory.createHLAfloat64LE(), factory.createHLAfloat64LE());
		}
	};	
	
	@SuppressWarnings("unchecked")
	public PositionCollectionCoder() throws RTIinternalError {
		
		this.factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
		
		this.coder = factory.createHLAvariableArray(elementFactory);

	}

	@SuppressWarnings("unchecked")
	@Override
	public PositionCollection decode(byte[] code) throws DecoderException {
		
		this.coder = factory.createHLAvariableArray(elementFactory);
		
		List<Vector3D> positions = new ArrayList<>();
		
		coder.decode(code);
		
		
		for (Iterator<HLAfixedArray<HLAfloat64LE>> iterator = coder.iterator(); iterator.hasNext();) {
			HLAfixedArray<HLAfloat64LE> hlaPosition = iterator.next();
			Vector3D pos = new Vector3D(
					hlaPosition.get(0).getValue(),
					hlaPosition.get(1).getValue(), 
					hlaPosition.get(2).getValue());
			
			positions.add(pos);
			
		}
		
		PositionCollection collection = new PositionCollection(positions);
		
		
		return collection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] encode(PositionCollection element) {
		
		this.coder = factory.createHLAvariableArray(elementFactory);
		
		List<Vector3D> positions = element.getArrayList();
		this.coder = factory.createHLAvariableArray(elementFactory);
		
		for (Vector3D position : positions) {
			
			HLAfloat64LE x = factory.createHLAfloat64LE(position.getX());
			HLAfloat64LE y = factory.createHLAfloat64LE(position.getY());
			HLAfloat64LE z = factory.createHLAfloat64LE(position.getZ());
			
			HLAfixedArray<HLAfloat64LE> dateElement = factory.createHLAfixedArray(x,y,z);
			
			this.coder.addElement(dateElement);
		}
		
		return coder.toByteArray();
	}

	@Override
	public Class<PositionCollection> getAllowedType() {
		
		return PositionCollection.class;
	}

}
