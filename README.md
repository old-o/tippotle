typepad
=======

Have fun typing words and numbers (mainly for children)

Simplistic text editor with
- Color-coded characters (letters, numbers, etc.)
- Spell-as-you-type in the currently selected language
- Switch between languages (currently English and German)
- Switch between 5 buffers (auto-saved to disk)
- Unlimited font zooming in/out
- Selectable font (via font preview combobox)
- All actions are available via toolbar and function keys
- Can automatically show images depicting current word

[!Build Status](https://travis-ci.org/odoepner/typepad.svg?branch=master)

[Download latest CI build]()

Technology
----------

- Requires Java 7 (uses NIO.2 for file storage)
- Uses Java Swing, mainly the JTextPane.
- Requires [espeak](http://sourceforge.net/projects/espeak/) command in the PATH.


Planned features
----------------

- "Login"-Dialog where computer speaker welcomes and asks user for their name
- Let computer read the current sentence or line
- Hand writing recognition (of single letters)
- Make computer voice more "interactive"
- Mute button for pure typing mode
- Configurable character colors
- ASCII art mode (restricts font selection to mono-spaced)
- Printing

