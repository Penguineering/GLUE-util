package de.ovgu.dke.glue.util.serialization;

import java.util.ArrayList;

import de.ovgu.dke.glue.api.serialization.AbstractSerializationProviderTests;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SerializerCollectionProviderTests extends
		AbstractSerializationProviderTests {

	@Override
	public int getMaxNumOfSerializers() {
		return Integer.MAX_VALUE;
	}

	@Override
	public SerializationProvider getSerializationProvider(
			ArrayList<Serializer> serializers) {
		return new SerializerCollectionProvider(
				serializers.toArray(new Serializer[0]));
	}

}
