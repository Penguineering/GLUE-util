package de.ovgu.dke.glue.util.serialization;

import de.ovgu.dke.glue.api.serialization.AbstractSerializerTests;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class NullSerializerTests extends AbstractSerializerTests {
	
	@Override
	public Serializer getSerializer(String format) {
		return NullSerializer.valueOf(format);
	}	

}
