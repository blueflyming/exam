package cool.scx.example.car;

import cool.scx.annotation.ScxModel;
import cool.scx.base.BaseModel;

/**
 * 汽车品牌
 *
 * @author scx567888
 * @version $Id: $Id
 */
@ScxModel(tablePrefix = "example")
public class CarBrand extends BaseModel {

    /**
     * 汽车品牌名称
     */
    public String name;

}
