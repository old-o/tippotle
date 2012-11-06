package net.doepner.ui.action;


import net.doepner.IdentiedBy;

/**
 * Action that can be more precisely identified by an action id
 */
public interface Action extends javax.swing.Action, IdentiedBy<ActionId> {

    // no additional methods
}
