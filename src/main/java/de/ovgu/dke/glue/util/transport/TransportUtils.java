package de.ovgu.dke.glue.util.transport;

import java.util.Properties;

import de.ovgu.dke.glue.api.transport.TransportException;
import de.ovgu.dke.glue.api.transport.TransportFactory;

/**
 * Utility methods for transport handling.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
public class TransportUtils {

	/**
	 * <p>
	 * Generic loading for a transport factory, which only needs to be specified
	 * by its class name. This way a transport implementation can be invoked
	 * without any compile time dependency.
	 * </p>
	 * 
	 * <p>
	 * After creating an instance of the transport factory, <code>init()</code>
	 * is called.
	 * </p>
	 * 
	 * @param factoryClass
	 *            The canonical class name of the transport factory.
	 * @param config
	 *            A properties instance which will be handed to the transport
	 *            implementation.
	 * @return the transport factory instance
	 * @throws ClassNotFoundException
	 *             if the factoryClass cannot be loaded
	 * @throws TransportException
	 *             if anything goes wrong during instantiation or setup
	 */
	public static TransportFactory loadTransportFactory(String factoryClass,
			Properties config) throws ClassNotFoundException,
			TransportException {
		try {
			// get the class
			final Class<?> clazz = Class.forName(factoryClass);

			// create instance
			final TransportFactory factory = (TransportFactory) clazz
					.newInstance();

			if (factory == null)
				throw new TransportException(
						"Could not intantiate the transport factory!");

			// initialize the factory
			factory.init(config);

			return factory;
		} catch (SecurityException e) {
			throw new TransportException(String.format(
					"Security exception on accessing constructor for %s: %s",
					factoryClass, e.getMessage()), e);
		} catch (IllegalArgumentException e) {
			throw new TransportException(String.format(
					"Illegal arguments calling constructor for %s: %s",
					factoryClass, e.getMessage()), e);
		} catch (InstantiationException e) {
			throw new TransportException(String.format(
					"Could not instantiate %s: %s", factoryClass,
					e.getMessage()), e);
		} catch (IllegalAccessException e) {
			throw new TransportException(String.format("Illegal access: %s",
					e.getMessage()), e);
		}

	}

	private TransportUtils() {
	}
}
