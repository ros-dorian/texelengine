package ch.texelengine.engine.scenegraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a node in a {@link Scene}
 *
 * <p>
 * This class the base class for every type of node that are
 * present in the engine.
 * </p>
 *
 * @author Dorian Ros
 */
public abstract class Node {

    /**
     * Parent node of <code>this</code> node
     */
    private Node parent;

    /**
     * List of child nodes of <code>this</code> node
     */
    private List<Node> children;

    /**
     * The {@link Scene} that owns this node
     *
     * <p>
     * Is null if the node is not part of any scene
     * </p>
     */
    private Scene scene;

    /**
     * Construct a new {@link Node} object
     */
    public Node() {
        this.parent = null;
        this.children = new ArrayList<>();
    }

    /**
     * Update <code>this</code> node
     *
     * <p>
     * This is responsible for updating this node and this node only
     * </p>
     */
    protected abstract void update();

    /**
     * Update <code>this</code> node and all its children
     *
     * <p>
     * This function is recursive so a call to this function from a node
     * automatically updates all of the nodes of the tree below
     * </p>
     */
    protected void updateAll() {
        //Update locally
        update();

        //Update the children
        for (Node child : this.children) {
            child.updateAll();
        }
    }

    /**
     * Add a child node to <code>this</code> node
     *
     * <p>
     * This method automatically set this node as a parent
     * of the child node and set the child's scene object.
     * </p>
     *
     * @param child the child node to add
     */
    public void addChild(Node child) {
        //Update the parenting
        this.children.add(child);
        child.setParent(this);
        //Update the scene object
        child.setScene(this.scene);
    }

    /**
     * Set the parent of <code>this</code> node
     *
     * @param parent the new parent of this node
     */
    private void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Notify <code>this</code> node and all its children for a state
     * change
     *
     * <p>
     * This function is recursive so a call to this function from a node
     * automatically notifies all of the nodes of the tree below
     * </p>
     */
    protected void notifyChanged(ChangeType change) {
        //Process the change event internally
        processChange(change);

        //notify the descendants
        for(Node child : this.children) {
            child.notifyChanged(change);
        }
    }

    /**
     * Process a change event locally
     *
     * <p>
     * It is not recommended to make anything
     * resource intensive in this function but rather
     * to set flags in the node and do the hard work
     * during the update function
     * </p>
     *
     * @param change the change event type as a {@link ChangeType}
     */
    protected abstract void processChange(ChangeType change);

    /**
     * Set the {@Scene} that owns <code>this</code> node
     *
     * <p>
     * This method is automatically called when the node is added
     * to a scene. It is also recursive so the scene is set for this
     * node and all of its descendants.
     * </p>
     *
     * @param scene the scene to add the node to
     */
    protected void setScene(Scene scene) {
        //Check if the scene is not already set to the correct value
        if(!this.scene.equals(scene)) {
            this.scene = scene;

            //Update the descendants
            for(Node child : this.children) {
                child.setScene(scene);
            }
        }
    }

    /**
     * Get the {@link Scene} that owns <code>this</code> node
     *
     * <p>
     * Can be <code>null</code>
     * </p>
     *
     * @return the scene that owns this node
     */
    protected Scene scene() {
        return this.scene;
    }
}
