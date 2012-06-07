package de.ovgu.dke.glue.util.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

public class SingleSerializerProviderTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 1) Tests whether all registered Serializers' formats are returned. <br>
	 * 2) Checks the order of returned formats (preferred items at first)
	 */
	@Test
	public void T00_AvailableFormats() {
		// create serializer mocks
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);
		// get SerializationProvider for test
		SerializationProvider provider = new SingleSerializerProvider(
				binSerializer);
		//
		EasyMock.replay(binSerializer);
		// check count
		assertEquals(1, provider.availableFormats().size());
		// check order
		assertEquals(SerializationProvider.BINARY, provider.availableFormats()
				.get(0));
		// check whether expected methods where called on Serializers
		EasyMock.verify(binSerializer);
	}

	@Test
	public void T01_AvailableFormats_NoSerializers() {
		SerializationProvider provider = new SingleSerializerProvider(null);
		assertEquals("Formats found, but no serializers defined.", 0, provider
				.availableFormats().size());
	}

	/**
	 * 1) Get the most preferred Serializer.
	 */
	@Test
	public void T10_GetSerializer() {
		// create serializer mocks
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);
		EasyMock.expect(stringSerializer.getFormat()).andReturn(
				SerializationProvider.STRING);
		EasyMock.expect(stringSerializer.getFormat()).andReturn(
				SerializationProvider.STRING);
		// get SerializationProvider for test
		SerializationProvider provider =new SingleSerializerProvider(stringSerializer);
		//
		EasyMock.replay(stringSerializer);
		// get the serializer
		try {
			assertEquals(stringSerializer,
					provider.getSerializer(provider.availableFormats().get(0)));
		} catch (SerializationException e) {
			fail(e.toString());
		}
	}

	@Test
	public void T11_GetSerializers_NoSuitableSerializer() {
		// create serializer mocks
		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		EasyMock.expect(binSerializer.getFormat()).andReturn(
				SerializationProvider.BINARY);
		// get SerializationProvider for test
		SerializationProvider provider = new SingleSerializerProvider(binSerializer);
		//
		EasyMock.replay(binSerializer);
		// get the serializer
		try {
			provider.getSerializer(SerializationProvider.JAVA);
		} catch (SerializationException e) {
			assertTrue(true);
		}
		// check whether expected methods where called on Serializers
		EasyMock.verify(binSerializer);
	}

	@Test
	public void T12_GetSerializers_NoSerializers() {
		SerializationProvider provider = new SingleSerializerProvider(null);
		try {
			provider.getSerializer(SerializationProvider.BINARY);
		} catch (SerializationException e) {
			assertTrue(true);
		}
	}

}
