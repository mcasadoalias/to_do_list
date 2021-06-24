package es.iesnervion.mcasado.todolists;

/**
 * This class will be used to determine what tasks to show depending on the values of its fields
 * catID only has to be taken into account in case that whatToShowType is set to WhatToShowType.CAT
 */
public class WhatToShow {

    private WhatToShowType whatToShowType;
    private int catId;

    public WhatToShow(WhatToShowType whatToShowType, int catId) {
        this.whatToShowType = whatToShowType;
        this.catId = catId;
    }

    public WhatToShowType getWhatToShowType() {
        return whatToShowType;
    }

    public void setWhatToShowType(WhatToShowType whatToShowType) {
        this.whatToShowType = whatToShowType;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
}
