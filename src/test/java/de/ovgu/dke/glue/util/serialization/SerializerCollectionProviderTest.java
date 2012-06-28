package de.ovgu.dke.glue.util.serialization;

import java.util.ArrayList;
import java.util.Arrays;

import de.ovgu.dke.glue.api.serialization.AbstractSerializationProviderTests;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SerializerCollectionProviderTest extends
		AbstractSerializationProviderTests {

	@Override
	public int getMaxNumOfSerializers() {
		return Integer.MAX_VALUE;
	}

	// TODO always prefer interfaces over implementations!! (List instead of
	// ArrayList!)
	@Override
	public SerializationProvider getSerializationProvider(
			ArrayList<Serializer> serializers) {
		return SerializerCollectionProvider
				.of(Arrays.asList(new Serializer[0]));
	}

}
