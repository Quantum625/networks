package dev.nanoflux.screen.option;

import dev.nanoflux.screen.InventoryMenu;

public class BooleanOption extends ConfigOption<Boolean> {


    private boolean value;

    public BooleanOption(InventoryMenu menu, String name) {
        super(menu, name);
    }

    /**
     * @return 
     */
    @Override
    public Boolean value() {
        return value;
    }


    @Override
    public void onClick() {
        value = !value;
    }
}
