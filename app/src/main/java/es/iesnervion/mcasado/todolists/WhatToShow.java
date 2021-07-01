package es.iesnervion.mcasado.todolists;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * This class will be used to determine what tasks to show depending on the values of its fields
 * catID only has to be taken into account in case that whatToShowType is set to WhatToShowType.CAT
 */

public class WhatToShow implements Parcelable {

    public static final int NO_CAT = -1;

    public static final Parcelable.Creator<WhatToShow> CREATOR
            = new Parcelable.Creator<WhatToShow>() {
                    public WhatToShow createFromParcel(Parcel in) {
                        return new WhatToShow(in);
                    }

                    public WhatToShow[] newArray(int size) {
                        return new WhatToShow[size];
                    }
                };

    private WhatToShowType whatToShowType;
    private int catId;

    public WhatToShow () {
        this.whatToShowType = WhatToShowType.ALL;
        this.catId = NO_CAT;
    }
    public WhatToShow(WhatToShowType whatToShowType, int catId) {
        this.whatToShowType = whatToShowType;
        this.catId = catId;
    }

    public WhatToShow(Parcel in) {
        whatToShowType = WhatToShowType.valueOf(in.readString());
        catId = in.readInt();
    }

    public WhatToShowType getWhatToShowType() {
        return whatToShowType;
    }

    public void setWhatToShowType(WhatToShowType whatToShowType) {
        this.whatToShowType = whatToShowType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(whatToShowType.name());
        dest.writeInt(catId);
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
}
