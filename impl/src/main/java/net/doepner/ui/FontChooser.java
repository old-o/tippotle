package net.doepner.ui;


import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import java.awt.Font;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.util.Arrays.sort;

public class FontChooser extends JComboBox<Font> {

    public FontChooser(final Component... components) {

        final Font[] fonts = getLocalGraphicsEnvironment().getAllFonts();

        sort(fonts, (f1, f2) -> f1.getName().compareTo(f2.getName()));

        for (Font font : fonts) {
            if (font.canDisplayUpTo(font.getName()) == -1) {
                addItem(font);
            }
        }

        addItemListener(e -> {
            final Font font = (Font) e.getItem();
            for (Component comp : components) {
                setFontPreserveSize(comp, font);
            }
        });

        setRenderer(new FontCellRenderer());
    }

    private static class FontCellRenderer
            implements ListCellRenderer<Font> {

        private final ListCellRenderer<Object> renderer =
                new DefaultListCellRenderer();

        @Override
        public final Component getListCellRendererComponent(
                JList<? extends Font> list, Font font, int index,
                boolean isSelected, boolean cellHasFocus) {

            final Component result = renderer.getListCellRendererComponent(
                    list, font.getName(), index, isSelected, cellHasFocus);

            setFontPreserveSize(result, font);
            return result;
        }
    }

    private static void setFontPreserveSize(final Component comp, Font font) {
        final float size = (float) comp.getFont().getSize();
        comp.setFont(font.deriveFont(size));
    }
}
