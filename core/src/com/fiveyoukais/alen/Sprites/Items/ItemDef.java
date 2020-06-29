package com.fiveyoukais.alen.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Arthur on 09/06/2020.
 */

public class ItemDef {
    public Vector2 position;
    public Class<?> type;
    public ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}
