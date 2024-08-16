package com.lts.channel.table;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * An object that acts as a go-between between a display model and the actual
 * data.
 * 
 * <P>
 * This interface is similar to {@link Serializable} in that it identifies 
 * classes rather than defining specific methods.
 * </P>
 * 
 * <P>
 * The value channel pattern (VCP) is used in the model-view-controller (MVC) approach
 * to user interface creation.  The idea behind MVC it allows the underlying 
 * data, the model, to have many ways of display.  This works for simple data,
 * but for things like JTable and JTree, the view forces a particular form onto 
 * the data.  In JTree, for example, it requires a hierarchy, whereas JTable requires
 * tables and columns.
 * </P>
 * 
 * <P>
 * Furthermore, the data is more often cached in some sort of object such as 
 * {@link DefaultMutableTreeNode} or a 2-dimensional array in the case of JTable.
 * These necessities reduce the benefits of MVC considerably.  One alternative
 * is to create custom subclasses of the models that know how to forward requests
 * for data and modifications onto the underlying data.  This approach is the 
 * basis of the value channel pattern.
 * </P>
 * 
 * <P>
 * The value channel pattern was created for use in Smalltalk.
 * </P>
 * 
 * @author cnh
 */
public interface ValueChannel
{}
