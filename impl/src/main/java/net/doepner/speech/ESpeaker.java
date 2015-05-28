package net.doepner.speech;

import net.doepner.lang.LanguageProvider;
import net.doepner.util.ActionableItems;
import net.doepner.util.ActionableQueue;

import java.io.IOException;


public final class ESpeaker implements TestableSpeaker {

    private static final String ESPEAK_COMMAND = "espeak";

    private final LanguageProvider languageProvider;
    private final String name;

    private final ActionableItems<Process> processes =
            new ActionableQueue<>(Process::destroy);

    public ESpeaker(LanguageProvider languageProvider, String name) {
        this.languageProvider = languageProvider;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void stopAll() {
        processes.actOnAll();
    }

    @Override
    public void speak(String text) {
        exec(ESPEAK_COMMAND, "-v", languageProvider.language().code(), text);
    }

    @Override
    public void test() {
        exec(ESPEAK_COMMAND, "-h");
    }

    private void exec(String... commandline) {
        try {
            processes.add(new ProcessBuilder(commandline).start());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
