package net.doepner.ui;


import net.doepner.IdentiedBy;

/**
 * A framework-agnostic action that is identified by an action id,
 * Design Pattern: Command
 */
public interface IAction extends IdentiedBy<ActionId> {

    void actionPerformed();

    String getIconName();
}
