package io.javaoperatorsdk.webhook.admission.sample.quarkus;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeAdmissionEndpointIT extends AdmissionEndpointTest {

  // Execute the same tests but in native mode.
}
