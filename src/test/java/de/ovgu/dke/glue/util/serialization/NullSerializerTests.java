package de.ovgu.dke.glue.util.serialization;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.test.serialization.AbstractSerializerTests;

public class NullSerializerTests extends AbstractSerializerTests {

	public NullSerializerTests() {
		super(NullSerializer.of(SerializationProvider.BINARY));
	}

}
