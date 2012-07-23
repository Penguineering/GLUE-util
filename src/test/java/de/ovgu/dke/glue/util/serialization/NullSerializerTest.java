package de.ovgu.dke.glue.util.serialization;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.test.serialization.AbstractSerializerTests;

public class NullSerializerTest extends AbstractSerializerTests {

	public NullSerializerTest() {
		super(NullSerializer.of(SerializationProvider.BINARY));
	}

}
