package net.doepner.ui;


/**
 * A framework-agnostic action that is identified by an action id,
 * Design Pattern: Command
 */
public interface Action {

    /**
     * Executes the action
     */
    void execute();

    /**
     * @return Action identifier (mainly for localized description lookup)
     */
    ActionId id();

    /**
     * @return If the action is enabled
     */
    default boolean isEnabled() {
        return true;
    }

    /**
     * @return The key to look up the icon for this action
     */
    default Object iconKey() {
        return id();
    }


}
