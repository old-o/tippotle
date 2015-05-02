package net.doepner.speech;

public interface Speaker {

    void speak(String text);

    String name();

    void stopAll();
}
