package de.ovgu.dke.glue.util.transport;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ovgu.dke.glue.api.transport.TransportFactory;
import de.ovgu.dke.glue.util.transport.TransportRegistry;

public class TransportRegistryTests {

	private static String FACTORY_ONE_KEY = "FactoryOne";
	private static String FACTORY_TWO_KEY = "FactoryTwo";
	private static String DEFAULT_REGISTRY_KEY = "test";

	private TransportRegistry registry = null;

	private TransportFactory factoryOne = null;
	private TransportFactory factoryTwo = null;
	private TransportFactory factoryThree = null;

	@Before
	public void setUp() {
		registry = TransportRegistry.newInstance();

		factoryOne = EasyMock.createNiceMock(TransportFactory.class);
		factoryTwo = EasyMock.createNiceMock(TransportFactory.class);
		factoryThree = EasyMock.createNiceMock(TransportFactory.class);
	}

	@After
	public void tearDown() {
		registry.disposeAll();
	}

	/**
	 * <p>
	 * Register two factories and try to retrieve them.
	 * </p>
	 */
	@Test
	public void T00_registerTransportFactory() {
		// register two factories
		registry.registerTransportFactory(FACTORY_ONE_KEY, factoryOne,
				TransportRegistry.NO_DEFAULT);
		registry.registerTransportFactory(FACTORY_TWO_KEY, factoryTwo,
				TransportRegistry.NO_DEFAULT);

		assertSame("Returned factory is not the registered one!", factoryOne,
				registry.getTransportFactory(FACTORY_ONE_KEY));
		assertSame("Returned factory is not the registered one!", factoryTwo,
				registry.getTransportFactory(FACTORY_TWO_KEY));
	}

	/**
	 * <p>
	 * Factories can be overwritten by registering with the same key.
	 * </p>
	 */
	@Test
	public void T01_registerTransportFactory_OverwriteExisitingFactory() {
		// do test case T00
		T00_registerTransportFactory();
		// overwrite factory with key FACTORY_TWO_KEY
		registry.registerTransportFactory(FACTORY_TWO_KEY, factoryThree,
				TransportRegistry.NO_DEFAULT);

		assertNotSame("Factory with this key should have been overwritten.",
				factoryTwo, registry.getTransportFactory(FACTORY_TWO_KEY));

		assertSame("Returned factory is not the registered one!", factoryThree,
				registry.getTransportFactory(FACTORY_TWO_KEY));

	}

	/**
	 * <p>
	 * Every factory defines a default key if users are not interested in
	 * defining a key themselves. The use of this default key is enabled during
	 * registration process.
	 * </p>
	 */
	@Test
	public void T02_registerTransportFactory_DefaultKeyBehavior() {
		// set up mock to support default key behavior
		EasyMock.expect(factoryThree.getDefaultRegistryKey())
				.andReturn(DEFAULT_REGISTRY_KEY).anyTimes();
		EasyMock.replay(factoryThree);
		// register via DEFAULT_KEY option
		registry.registerTransportFactory(TransportRegistry.DEFAULT_KEY,
				factoryThree, TransportRegistry.NO_DEFAULT);

		assertSame("Returned factory is not the registered one!", factoryThree,
				registry.getTransportFactory("test"));

	}

	/**
	 * <p>
	 * During registration a factory can be set as default one.
	 * </p>
	 */
	@Test
	public void T03_registerTransportFactory_AsDefault() {
		// register as default
		registry.registerTransportFactory(FACTORY_TWO_KEY, factoryTwo,
				TransportRegistry.AS_DEFAULT);

		assertSame("Returned factory is not the registered one!", factoryTwo,
				registry.getDefaultTransportFactory());

	}

	/**
	 * <p>
	 * A newly created or empty transport registry should return NULL for a call
	 * on getTransportFactory.
	 * </p>
	 * <p>
	 * REMARK: Due to the fact that the transport registry is implemented as
	 * singleton the test cannot ensure to test the behavior of newly
	 * instantiated registry because the order of test case execution cannot be
	 * guaranteed.
	 * </p>
	 */
	@Test
	public void T10_getTransportFactory_NoFactoryAdded() {
		// delete all registered factories to make sure that no factory is added
		registry.disposeAll();
		assertNull("Expected NULL but factory was returned!",
				registry.getTransportFactory(FACTORY_ONE_KEY));
	}

	/**
	 * <p>
	 * If the key is not registered NULL must be returned.
	 * </p>
	 */
	@Test
	public void T11_getTransportFactory_KeyNotRegistered() {
		registry.registerTransportFactory(FACTORY_ONE_KEY, factoryOne,
				TransportRegistry.NO_DEFAULT);

		assertNull("Expected NULL but factory was returned!",
				registry.getTransportFactory(FACTORY_TWO_KEY));
	}

	/**
	 * <p>
	 * If the key is NULL a NPE must be thrown.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T12_getTransportFactory_KeyNull() {
		// do test case T00
		T00_registerTransportFactory();

		registry.getTransportFactory(null);
	}

	/**
	 * <p>
	 * Test if disposing of factories works correctly by calling the factories
	 * dispose methods.
	 * </p>
	 */
	@Test
	public void T20_disposeAll() {
		// enable mocks to support dispose calls once
		registry.registerTransportFactory(FACTORY_ONE_KEY, factoryOne,
				TransportRegistry.NO_DEFAULT);
		factoryOne.dispose();
		EasyMock.expectLastCall().once();
		EasyMock.replay(factoryOne);
		// second transport factory is the default one
		registry.registerTransportFactory(FACTORY_TWO_KEY, factoryTwo,
				TransportRegistry.AS_DEFAULT);
		factoryTwo.dispose();
		EasyMock.expectLastCall().once();
		EasyMock.replay(factoryTwo);

		// dispose all factories, EasyMock takes care that the dispose methods
		// are called exactly once, if not an assertion error is thrown
		registry.disposeAll();
		EasyMock.verify(factoryOne);
		EasyMock.verify(factoryTwo);
	}

	/**
	 * <p>
	 * The default factory can be accessed without accessing the registry
	 * directly.
	 * </p>
	 */
	@Test
	public void T30_setDefaultTransportFactory() {
		// register two factories
		T00_registerTransportFactory();
		// set one as default
		registry.setDefaultTransportFactory(FACTORY_ONE_KEY);

		assertSame("Unexpected default factory returned.", factoryOne,
				registry.getDefaultTransportFactory());
	}

	/**
	 * <p>
	 * If no factory is registered for a key supposed to be the default one an
	 * IllegalArgumentException is thrown.
	 * </p>
	 */
	@Test(expected = IllegalArgumentException.class)
	public void T31_setDefaultTransportFactory_KeyNotRegistered() {
		// register one factory as default
		registry.registerTransportFactory(FACTORY_ONE_KEY, factoryOne,
				TransportRegistry.AS_DEFAULT);

		try {
			// set not registered factory as default -> throws IAE
			registry.setDefaultTransportFactory(FACTORY_TWO_KEY);
		} catch (IllegalArgumentException e) {
			// register factory with key that led to IAE
			registry.registerTransportFactory(FACTORY_TWO_KEY, factoryTwo,
					TransportRegistry.NO_DEFAULT);
			// test if key wasn't stored by retrieving default factory and
			// checking if they do not match
			assertEquals("Unexpected default factory returned.", factoryOne,
					registry.getDefaultTransportFactory());
			// rethrow exception to fulfill asserts
			throw e;
		}

	}

	/**
	 * <p>
	 * The default setting can be removed by setting NULL key as default.
	 * </p>
	 */
	@Test
	public void T32_setDefaultTransportFactory_RemoveDefaultSetting() {
		// no default defined -> NULL
		assertNull("Unexpected default factory returned.",
				registry.getDefaultTransportFactory());
		// register factories and set one as default
		T30_setDefaultTransportFactory();
		// delete default setting
		registry.setDefaultTransportFactory(null);
		// no default defined -> NULL
		assertNull("Unexpected default factory returned.",
				registry.getDefaultTransportFactory());
	}

	/**
	 * <p>
	 * The registry can return all registered factory keys.
	 * </p>
	 */
	@Test
	public void T40_getTransportFactoryKeys() {
		// register two factories
		T00_registerTransportFactory();

		assertEquals("Number of registered factories differs!", 2, registry
				.getTransportFactoryKeys().size());
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(FACTORY_ONE_KEY));
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(FACTORY_TWO_KEY));
	}

	/**
	 * <p>
	 * Overwrites and double registering may not have effect on number of
	 * registered factories and keys.
	 * </p>
	 */
	@Test
	public void T41_getTransportFactoryKeys_SameKeyTwice() {
		// register two factories
		T00_registerTransportFactory();
		// register one factory twice
		registry.registerTransportFactory(FACTORY_ONE_KEY, factoryOne,
				TransportRegistry.AS_DEFAULT);
		// register one key twice with different factory
		registry.registerTransportFactory(FACTORY_TWO_KEY, factoryThree,
				TransportRegistry.AS_DEFAULT);

		assertEquals("Number of registered factories differs!", 2, registry
				.getTransportFactoryKeys().size());
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(FACTORY_ONE_KEY));
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(FACTORY_TWO_KEY));
	}

	/**
	 * <p>
	 * Check if the default key is also returned.
	 * </p>
	 */
	@Test
	public void T42_getTransportFactoryKeys_DefaultKeyBahavior() {
		T00_registerTransportFactory();
		T02_registerTransportFactory_DefaultKeyBehavior();

		assertEquals("Number of registered factories differs!", 3, registry
				.getTransportFactoryKeys().size());
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(FACTORY_ONE_KEY));
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(FACTORY_TWO_KEY));
		assertTrue("Expected key not contained.", registry
				.getTransportFactoryKeys().contains(DEFAULT_REGISTRY_KEY));
	}

}
