package de.ovgu.dke.glue.util.serialization;

import java.util.List;

import de.ovgu.dke.glue.api.serialization.AbstractSerializationProviderTests;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SerializerCollectionProviderTest extends
		AbstractSerializationProviderTests {

	@Override
	public int getMaxNumOfSerializers() {
		return Integer.MAX_VALUE;
	}

	@Override
	public SerializationProvider getSerializationProvider(
			List<Serializer> serializers) {		
		return SerializerCollectionProvider
				.of(serializers);
	}

}
