# pepenetv2
Version 2 of the PepeNet wrapper for java websockets.

As before, a Java project that extends pepedeab's WebSockets project into a library that is platform-agnostic and as drag-n-drop as possible.

This wrapper not only uses a refactored version of pepedeab's awesome little demo, updated to deal with the goofy drafts issues (and no doubt we'll be revisiting THAT again soon, *sigh*) as well as certain weirdnesses that have crept in through the GWT websockets stuff, but also does not include any of the demo-specific code pepedeab used this stuff for - no playing cards, sorry. Although this wrapper (and pepedeab's original demo project, too) are intended for use with LibGDX, there is no reason why it wouldn't work in vanilla Java projects, or indeed, anywhere you can attach a java project as a reference.

As with the first version, this wrapper not only has the code to allow you to establish server-client connections, but also to handle messaging: through the Custom Event Messaging system, or you also have the option of using a polling system instead. In a future version of this wrapper, I will probably yank the polling-specific code, as server-client connections practically scream for event-oriented programming, but I'll leave it in for the time being.

