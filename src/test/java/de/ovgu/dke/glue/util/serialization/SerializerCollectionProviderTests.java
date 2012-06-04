package de.ovgu.dke.glue.util.serialization;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SerializerCollectionProviderTests {

	@Test
	public void T00_AvailableFormats() {
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);
		EasyMock.expect(stringSerializer.getFormat()).andReturn(
				SerializationProvider.STRING);
		SerializationProvider provider = new SerializerCollectionProvider(
				new Serializer[] { binSerializer, stringSerializer });
		EasyMock.replay(binSerializer);
		EasyMock.replay(stringSerializer);
		assertEquals(2, provider.availableFormats().size());
		assertEquals(SerializationProvider.BINARY, provider.availableFormats()
				.get(0));
		assertEquals(SerializationProvider.STRING, provider.availableFormats()
				.get(1));
		EasyMock.verify(binSerializer);
		EasyMock.verify(stringSerializer);
	}

	@Test
	public void T01_AvailableFormats_NoSerializers() {
		SerializationProvider provider = new SerializerCollectionProvider(null);
		assertEquals("Formats found, but no serializers defined.", 0, provider
				.availableFormats().size());
	}

	@Test
	public void T10_GetSchemas() {
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);
		// EasyMock.expect(binSerializer.getSchema()).andReturn("http://binary");
		// EasyMock.expect(stringSerializer.getSchema())
		// .andReturn("http://string");
		SerializationProvider provider = new SerializerCollectionProvider(
				new Serializer[] { binSerializer, stringSerializer });
		EasyMock.replay(binSerializer);
		EasyMock.replay(stringSerializer);
		// assertEquals(2, provider.getSchemas(null).size());
		// assertEquals("http://binary", provider.getSchemas(null).get(0));
		// assertEquals("http://string",
		// provider.getSchemas(SerializationProvider.STRING).get(1));
		EasyMock.verify(binSerializer);
		EasyMock.verify(stringSerializer);
	}

	@Test
	public void T11_GetSchemas_NoSerializers() {
		SerializationProvider ser = new SerializerCollectionProvider(null);
		// assertEquals("Schemas found, but no serializers defined.", 0, ser
		// .getSchemas(SerializationProvider.BINARY).size());
	}

	@Test
	public void T20_GetSerializers() {
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);

		// EasyMock.expect(binSerializer.getSchema()).andReturn("http://binary");
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);

		// EasyMock.expect(stringSerializer.getSchema())
		// .andReturn("http://string");
		EasyMock.expect(stringSerializer.getFormat()).andReturn(
				SerializationProvider.STRING);

		SerializationProvider provider = new SerializerCollectionProvider(
				new Serializer[] { binSerializer, stringSerializer });
		EasyMock.replay(binSerializer);
		EasyMock.replay(stringSerializer);
		// try {
		// assertEquals(stringSerializer, provider.getSerializer(
		// SerializationProvider.STRING, "http://string"));
		// } catch (SerializationException e1) {
		// fail(e1.toString());
		// }
		EasyMock.verify(stringSerializer);
	}

	@Test
	public void T21_GetSerializers_NoSuitableSerializer() {
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);

		// EasyMock.expect(binSerializer.getSchema()).andReturn("http://binary");
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);

		// EasyMock.expect(stringSerializer.getSchema())
		// .andReturn("http://string");
		EasyMock.expect(stringSerializer.getFormat()).andReturn(
				SerializationProvider.STRING);

		SerializationProvider provider = new SerializerCollectionProvider(
				new Serializer[] { binSerializer, stringSerializer });
		EasyMock.replay(binSerializer);
		EasyMock.replay(stringSerializer);
		// try {
		// provider.getSerializer(SerializationProvider.BINARY,
		// "http://string");
		// } catch (SerializationException e1) {
		// assertTrue(true);
		// }
	}

	@Test
	public void T22_GetSerializers_NoSerializers() {
		SerializationProvider ser = new SerializerCollectionProvider(null);
		// try {
		// ser.getSerializer(SerializationProvider.BINARY, "http://string");
		// } catch (SerializationException e) {
		// assertTrue(true);
		// }
	}

}
