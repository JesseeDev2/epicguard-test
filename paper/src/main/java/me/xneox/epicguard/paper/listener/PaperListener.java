package me.xneox.epicguard.paper.listener;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public interface PaperListener<E extends Event> {
    @SuppressWarnings("unchecked")
    default void handle(Object o) {
        this.handle((E)o);
    }
    void handle(E event);

    Class<E> clazz();

    default boolean ignoreCancelled() {
        return true;
    }

    default EventPriority priority() {
        return EventPriority.NORMAL;
    }
}
