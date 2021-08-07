package me.jhim.retale.neighborhoods;

import me.jhim.retale.stores.Store;

public class Plot {

    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean isEmpty() {
        return store == null;
    }
}
