package eu.cloudtm;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import eu.cloudtm.stats.WPMSample;

public class SimpleModule extends AbstractModule {

	@Override
	protected void configure() {

        bind(StatsManager.class).to(StatsManager.class);

		//bind(StudentStore.class).to(DummyStudentStore.class);

		//bind(Registrar.class).to(LenientRegistrar.class);
	}

    @Provides
    WPMSample provideLastSample(StatsManager statsManager) {
        return statsManager.getLastSample();
    }

}
