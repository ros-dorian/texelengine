package ch.texelengine.engine.api.context.callbacks;

/**
 * @author Dorian Ros
 */
@FunctionalInterface
public interface RestoreCallback extends WindowCallback {

    /**
     *
     */
    void invoke();

}
