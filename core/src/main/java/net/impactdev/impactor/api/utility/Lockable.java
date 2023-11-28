package net.impactdev.impactor.api.utility;

public interface Lockable {

    /**
     * Specifies if the inheriting object has been locked from updates.
     *
     * @return <code>true</code> if locked, <code>false</code> otherwise
     * @since 5.2.0
     */
    boolean locked();

    /**
     * Locks the inheriting object from receiving updates.
     *
     * @since 5.2.0
     */
    void lock();

    /**
     * Unlocks the inheriting object, allowing any update requests to apply.
     *
     * @since 5.2.0
     */
    void unlock();

}
