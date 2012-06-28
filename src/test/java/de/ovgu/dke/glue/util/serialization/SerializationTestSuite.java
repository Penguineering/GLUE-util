package de.ovgu.dke.glue.util.serialization;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SerializerCollectionProviderTest.class,
		SingleSerializerProviderTest.class, NullSerializerTest.class })
public class SerializationTestSuite {

}
