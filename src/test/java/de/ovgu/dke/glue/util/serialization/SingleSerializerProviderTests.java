package de.ovgu.dke.glue.util.serialization;

import java.util.ArrayList;

import de.ovgu.dke.glue.api.serialization.AbstractSerializationProviderTests;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SingleSerializerProviderTests extends AbstractSerializationProviderTests {

	@Override
	public int getMaxNumOfSerializers() {
		return 1;
	}

	@Override
	public SerializationProvider getSerializationProvider(
			ArrayList<Serializer> serializers) {
		return new SingleSerializerProvider(serializers.get(0));
	}

}
