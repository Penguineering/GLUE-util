package de.ovgu.dke.glue.util.serialization;

import de.ovgu.dke.glue.api.serialization.Serializer;
import de.ovgu.dke.glue.test.serialization.AbstractSerializerTests;

public class NullSerializerTest extends AbstractSerializerTests {
	
	@Override
	public Serializer getSerializer(String format) {
		return NullSerializer.of(format);
	}	

}
