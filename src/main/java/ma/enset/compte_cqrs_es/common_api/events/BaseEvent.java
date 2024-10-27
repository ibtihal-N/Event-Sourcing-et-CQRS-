package ma.enset.compte_cqrs_es.common_api.events;

import lombok.Getter;

public abstract class BaseEvent<T> {
    @Getter private  T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
