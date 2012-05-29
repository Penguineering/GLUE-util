package de.ovgu.dke.glue.util.serialization;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SingleSerializerProviderTests {

	@Test
	public void T00_AvailableFormats() {
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		// two method calls expected
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);

		SerializationProvider provider = new SingleSerializerProvider(
				binSerializer);

		EasyMock.replay(binSerializer);

		assertEquals(1, provider.availableFormats().size());
		assertEquals(SerializationProvider.BINARY, provider.availableFormats()
				.get(0));
		
		EasyMock.verify(binSerializer);
	}

	@Test
	public void T01_AvailableFormats_NoSerializers() {
		SerializationProvider provider = new SingleSerializerProvider(null);
		assertEquals("Formats found, but no serializers defined.", 0, provider
				.availableFormats().size());
	}

	@Test
	public void T10_GetSchemas() {
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);
		// two method calls expected
		EasyMock.expect(stringSerializer.getSchema())
				.andReturn("http://string");
		EasyMock.expect(stringSerializer.getSchema())
		.andReturn("http://string");

		SerializationProvider provider = new SingleSerializerProvider(
				stringSerializer);

		EasyMock.replay(stringSerializer);

		assertEquals(1, provider.getSchemas(null).size());

		assertEquals("http://string",
				provider.getSchemas(SerializationProvider.STRING).get(0));

		EasyMock.verify(stringSerializer);
	}

	@Test
	public void T11_GetSchemas_NoSerializers() {
		SerializationProvider ser = new SingleSerializerProvider(null);
		assertEquals("Schemas found, but no serializers defined.", 0, ser
				.getSchemas(SerializationProvider.BINARY).size());
	}

	@Test
	public void T20_GetSerializers() {
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);

		EasyMock.expect(stringSerializer.getSchema())
				.andReturn("http://string");
		EasyMock.expect(stringSerializer.getFormat()).andReturn(
				SerializationProvider.STRING);

		SerializationProvider provider = new SingleSerializerProvider(
				stringSerializer);

		EasyMock.replay(stringSerializer);
		try {
			assertEquals(stringSerializer, provider.getSerializer(
					SerializationProvider.STRING, "http://string"));
		} catch (SerializationException e1) {
			fail(e1.toString());
		}

		EasyMock.verify(stringSerializer);
	}

	@Test
	public void T21_GetSerializers_NoSuitableSerializer() {
		Serializer binSerializer = EasyMock.createMock(Serializer.class);

		EasyMock.expect(binSerializer.getSchema()).andReturn("http://binary");
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);

		SerializationProvider provider = new SingleSerializerProvider(
				binSerializer);

		EasyMock.replay(binSerializer);
		try {
			provider.getSerializer(SerializationProvider.BINARY,
					"http://string");
		} catch (SerializationException e1) {
			assertTrue(true);
		}
	}

	@Test
	public void T22_GetSerializers_NoSerializers() {
		SerializationProvider ser = new SingleSerializerProvider(null);
		try {
			ser.getSerializer(SerializationProvider.BINARY, "http://string");
		} catch (SerializationException e) {
			assertTrue(true);
		}
	}

}
