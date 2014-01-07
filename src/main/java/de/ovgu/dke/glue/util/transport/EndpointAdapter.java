package de.ovgu.dke.glue.util.transport;

import java.net.URI;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;
import de.ovgu.dke.glue.api.endpoint.Endpoint;
import de.ovgu.dke.glue.api.transport.Connection;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;
import de.ovgu.dke.glue.api.transport.TransportFactory;

/**
 * Utility class for transport implementations containing support structures for
 * endpoints.
 * 
 * <p>
 * This class is thread safe.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@ThreadSafe
public class EndpointAdapter implements Endpoint {
	private final String id;
	private final String schema;

	private final Set<TransportFactory> transportFactories;

	public EndpointAdapter(String id, String schema) {
		this.id = id;
		if (id == null)
			throw new NullPointerException("ID parameter must not be null!");

		// TODO check schema parameter
		this.schema = schema;
		// TODO best solution for thread-safe set?
		this.transportFactories = Collections
				.newSetFromMap(new ConcurrentHashMap<TransportFactory, Boolean>());
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getSchema() {
		return schema;
	}

	@Override
	public PacketThread openPacketThread(URI peer, PacketHandler handler)
			throws TransportException {
		Connection con = null;

		// iterate the transport factories for the connection
		for (TransportFactory factory : transportFactories)
			if (factory.servesPeer(peer, schema)) {
				con = factory.createTransport(peer).getConnection(schema);
				break;
			}

		// not found -> Exception
		if (con == null)
			throw new NoSuchElementException(String.format(
					"Registry does not contain a transport factory "
							+ "for peer %s with schema %s!",
					peer.toASCIIString(), schema));

		return con.createThread(handler);
	}

	/**
	 * Register a transport factory to this endpoint, allowing to use the
	 * provided transport as channels for the dispatcher.
	 * 
	 * @param factory
	 *            The transport factory to be added to the endpoint.
	 * @throws NullPointerException
	 *             if the factory argument is <code>null</code>.
	 */
	public void registerTransportFactory(TransportFactory factory) {
		transportFactories.add(factory);
	}

	/**
	 * Remove a transport factory from this endpoint. Only removes the factory;
	 * existing connections are left open.
	 * 
	 * @param factory
	 *            The factory to be removed from the endpoint.
	 * @throws NullPointerException
	 *             if the factory argument is <code>null</code>.
	 */
	public void removeTransportFactory(TransportFactory factory) {
		transportFactories.remove(factory);
	}

}
