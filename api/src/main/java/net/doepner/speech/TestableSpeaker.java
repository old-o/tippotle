package net.doepner.speech;

/**
 * Speaker with a test method
 */
public interface TestableSpeaker extends Speaker {

    /**
     * @throws java.lang.IllegalStateException If speaker is not functional
     */
    void test();
}
