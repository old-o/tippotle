package net.doepner.typepad;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;

import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.LanguageChanger;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;
import net.doepner.text.WordExtractor;
import net.doepner.ui.action.FontSizingAction;
import net.doepner.ui.action.SpeakWordAction;
import net.doepner.ui.action.SwitchBufferAction;
import net.doepner.ui.action.SwitchLanguageAction;
import net.doepner.ui.text.UiTextModel;

import static net.doepner.typepad.DocUtil.prepareDocument;

public class App {

	public static void main(String[] args) {
		new App().run();
	}

	private final TypePad typePad;

	public App() {
		final LanguageChanger languageChanger = new EnglishOrGerman();
		final Speaker speaker = new ESpeaker(languageChanger);

		final DefaultStyledDocument doc = prepareDocument(speaker);

		final JTextPane pane = new JTextPane(doc);
		final UiTextModel textModel = new UiTextModel(pane);

		final Action switchLangAction = new SwitchLanguageAction(
				languageChanger);

		final Action speakWordAction = new SpeakWordAction(
                new WordExtractor(textModel), speaker);

		final Action switchBufferAction = new SwitchBufferAction(5,
                textModel, textModel);
		
		final Action biggerFontAction = new FontSizingAction(+1, pane);
		final Action smallerFontAction = new FontSizingAction(-1, pane);

		typePad = new TypePad(pane,
                switchLangAction, speakWordAction,
				smallerFontAction, biggerFontAction, 
				switchBufferAction);
	}

	private void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				typePad.show();
			}
		});
	}

}
