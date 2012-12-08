package net.doepner.ui.action;


import javax.swing.Action;

import net.doepner.IdentiedBy;

/**
 * IdAction that can be more precisely identified by an action id
 */
public interface IdAction extends Action, IdentiedBy<ActionId> {

    // no additional methods
}
