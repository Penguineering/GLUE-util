package de.ovgu.dke.glue.util.serialization;

import java.util.List;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;
import de.ovgu.dke.glue.test.serialization.AbstractSerializationProviderTests;
import de.ovgu.dke.glue.test.serialization.SerializationProviderBuilder;

public class SerializerCollectionProviderTests extends
		AbstractSerializationProviderTests {

	public SerializerCollectionProviderTests() {
		super(new SerializationProviderBuilder() {

			@Override
			public SerializationProvider build(List<Serializer> serializers) {
				return SerializerCollectionProvider.of(serializers);
			}
		}, Integer.MAX_VALUE);
	}

}
